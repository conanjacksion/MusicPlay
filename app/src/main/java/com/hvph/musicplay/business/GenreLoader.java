package com.hvph.musicplay.business;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.hvph.musicplay.dao.DaoFactory;
import com.hvph.musicplay.dao.GenreDao;
import com.hvph.musicplay.model.Genre;

import java.util.ArrayList;

/**
 * Created by HoangHVP on 10/14/2014.
 */
public class GenreLoader extends AsyncTaskLoader<ArrayList<Genre>> {
    private ArrayList<Genre> mGenreList;
    private Context mContext;
    private GenreDao mGenreDao;

    public GenreLoader(Context context) {
        super(context);
        this.mContext = context;
        this.mGenreDao = (GenreDao)DaoFactory.getDao(Definition.TYPE_GENRE);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        // AsyncTaskLoader doesn't start unless you forceLoad http://code.google.com/p/android/issues/detail?id=14944
        if(mGenreList != null){
            deliverResult(mGenreList);
        }
        if(takeContentChanged() || mGenreList == null){
            forceLoad();
        }
    }

    @Override
    public void deliverResult(ArrayList<Genre> genreList) {
        if(isReset()){
            // An async query came in while the loader is stopped. We don't need the result
            if(genreList != null){
                onReleaseResources(genreList);
            }
        }
        ArrayList<Genre> oldSongList = this.mGenreList;
        this.mGenreList = genreList;

        if(isStarted()){
            // If the loader is currently started, we can immediately deliver a result
            super.deliverResult(genreList);
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
    public void onCanceled(ArrayList<Genre> genreList) {
        super.onCanceled(genreList);

        // At this point we can release the resources associated with 'apps' if needed
        onReleaseResources(genreList);
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
        if(mGenreList != null){
            onReleaseResources(mGenreList);
            mGenreList = null;
        }
    }

    /**
     * Helper function to take care of releasing resources associated with an actively loaded data set
     */
    private void onReleaseResources(ArrayList<Genre> genreList){
        // For a simple list there is nothing to do
        // but for a Cursor we would close it here
    }

    @Override
    public ArrayList<Genre> loadInBackground() {
        mGenreList = mGenreDao.getAllGenre();
        return mGenreList;
    }
}
