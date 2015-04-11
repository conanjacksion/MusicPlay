package com.hvph.musicplay.business;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.hvph.musicplay.dao.DaoFactory;
import com.hvph.musicplay.dao.AlbumDao;
import com.hvph.musicplay.model.Album;

import java.util.ArrayList;

/**
 * Created by HoangHVP on 10/13/2014.
 */
public class AlbumLoader extends AsyncTaskLoader<ArrayList<Album>> {
    private ArrayList<Album> mAlbumList;
    private Context mContext;
    private AlbumDao mAlbumDao;

    public AlbumLoader(Context context) {
        super(context);
        this.mContext = context;
        this.mAlbumDao = (AlbumDao) DaoFactory.getDao(Definition.TYPE_ALBUM);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        // AsyncTaskLoader doesn't start unless you forceLoad http://code.google.com/p/android/issues/detail?id=14944
        if(mAlbumList != null){
            deliverResult(mAlbumList);
        }
        if(takeContentChanged() || mAlbumList == null){
            forceLoad();
        }
    }

    @Override
    public void deliverResult(ArrayList<Album> albumList) {
        if(isReset()){
            // An async query came in while the loader is stopped. We don't need the result
            if(albumList != null){
                onReleaseResources(albumList);
            }
        }
        ArrayList<Album> oldSongList = this.mAlbumList;
        this.mAlbumList = albumList;

        if(isStarted()){
            // If the loader is currently started, we can immediately deliver a result
            super.deliverResult(albumList);
        }

        // At this point we can release the resources associated with 'oldApps' if needed;
        // now that the new result is delivered we know that it is no longer in use
        if(oldSongList != null){
            onReleaseResources(oldSongList);
        }
    }

    @Override
    protected void onStopLoading() {
        // Attempts to cancel the current load task if possible
        cancelLoad();
    }

    @Override
    public void onCanceled(ArrayList<Album> albumList) {
        super.onCanceled(albumList);

        // At this point we can release the resources associated with 'apps' if needed
        onReleaseResources(albumList);
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
        if(mAlbumList != null){
            onReleaseResources(mAlbumList);
            mAlbumList = null;
        }
    }

    /**
     * Helper function to take care of releasing resources associated with an actively loaded data set
     */
    private void onReleaseResources(ArrayList<Album> albumList){
        // For a simple list there is nothing to do
        // but for a Cursor we would close it here
    }

    @Override
    public ArrayList<Album> loadInBackground() {
        mAlbumList = mAlbumDao.getAllAlbum();
        return mAlbumList;
    }
}
