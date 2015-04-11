package com.hvph.musicplay.business;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.hvph.musicplay.dao.DaoFactory;
import com.hvph.musicplay.dao.SongDao;
import com.hvph.musicplay.model.Song;

import java.util.ArrayList;

/**
 * Created by HoangHVP on 10/13/2014.
 */
public class FavoriteLoader extends AsyncTaskLoader<ArrayList<Song>> {
    private ArrayList<Song> mFSongs;
    private Context mContext;
    private SongDao mSongDao;

    public FavoriteLoader(Context context) {
        super(context);
        this.mContext = context;
        this.mSongDao = (SongDao)DaoFactory.getDao(Definition.TYPE_SONG);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        // AsyncTaskLoader doesn't start unless you forceLoad http://code.google.com/p/android/issues/detail?id=14944
        if(mFSongs != null){
            deliverResult(mFSongs);
        }
        if(takeContentChanged() || mFSongs == null){
            forceLoad();
        }
    }

    @Override
    public void deliverResult(ArrayList<Song> songList) {
        if(isReset()){
            // An async query came in while the loader is stopped. We don't need the result
            if(songList != null){
                onReleaseResources(songList);
            }
        }
        ArrayList<Song> oldSongList = this.mFSongs;
        this.mFSongs = songList;

        if(isStarted()){
            // If the loader is currently started, we can immediately deliver a result
            super.deliverResult(songList);
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
    public void onCanceled(ArrayList<Song> songList) {
        super.onCanceled(songList);

        // At this point we can release the resources associated with 'apps' if needed
        onReleaseResources(songList);
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
        if(mFSongs != null){
            onReleaseResources(mFSongs);
            mFSongs = null;
        }
    }

    /**
     * Helper function to take care of releasing resources associated with an actively loaded data set
     */
    private void onReleaseResources(ArrayList<Song> songList){
        // For a simple list there is nothing to do
        // but for a Cursor we would close it here
    }

    @Override
    public ArrayList<Song> loadInBackground() {
        mFSongs = mSongDao.getSongFavorite();
        return mFSongs;
    }

}
