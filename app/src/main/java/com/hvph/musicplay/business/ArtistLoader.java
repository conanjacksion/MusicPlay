package com.hvph.musicplay.business;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.hvph.musicplay.dao.ArtistDao;
import com.hvph.musicplay.dao.DaoFactory;
import com.hvph.musicplay.model.Artist;

import java.util.ArrayList;

/**
 * Created by HoangHVP on 10/13/2014.
 */
public class ArtistLoader extends AsyncTaskLoader<ArrayList<Artist>> {
    private ArrayList<Artist> mArtistList;
    private ArtistDao mArtistDao;

    public ArtistLoader(Context context) {
        super(context);
        this.mArtistDao = (ArtistDao) DaoFactory.getDao(Definition.TYPE_ARTIST);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        // AsyncTaskLoader doesn't start unless you forceLoad http://code.google.com/p/android/issues/detail?id=14944
        if(mArtistList != null){
            deliverResult(mArtistList);
        }
        if(takeContentChanged() || mArtistList == null){
            forceLoad();
        }
    }

    @Override
    public void deliverResult(ArrayList<Artist> artistList) {
        if(isReset()){
            // An async query came in while the loader is stopped. We don't need the result
            if(artistList != null){
                onReleaseResources(artistList);
            }
        }
        ArrayList<Artist> oldArtistList = this.mArtistList;
        this.mArtistList = artistList;

        if(isStarted()){
            // If the loader is currently started, we can immediately deliver a result
            super.deliverResult(artistList);
        }

        // At this point we can release the resources associated with 'oldApps' if needed;
        // now that the new result is delivered we know that it is no longer in use
        if(oldArtistList != null){
            onReleaseResources(oldArtistList);
        }
    }

    @Override
    protected void onStopLoading() {
        // Attempts to cancel the current load task if possible
        cancelLoad();
    }

    @Override
    public void onCanceled(ArrayList<Artist> artistList) {
        super.onCanceled(artistList);

        // At this point we can release the resources associated with 'apps' if needed
        onReleaseResources(artistList);
    }

    /**
     * Handles request to completely reset the loader
     */
    @Override
    protected void onReset() {
        super.onReset();

        // ensure the loader is stopped
        onStopLoading();

        // At this point we can release the resources associated with 'apps' if needed
        if(mArtistList != null){
            onReleaseResources(mArtistList);
            mArtistList = null;
        }
    }

    /**
     * Helper function to take care of releasing resources associated with an actively loaded data set
     */
    private void onReleaseResources(ArrayList<Artist> artistList){
        // For a simple list there is nothing to do
        // but for a Cursor we would close it here
    }

    @Override
    public ArrayList<Artist> loadInBackground() {
        mArtistList = mArtistDao.getAllArtist();
        return mArtistList;
    }
}

