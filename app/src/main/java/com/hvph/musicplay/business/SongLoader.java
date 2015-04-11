package com.hvph.musicplay.business;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.hvph.musicplay.dao.AlbumDao;
import com.hvph.musicplay.dao.ArtistDao;
import com.hvph.musicplay.dao.DaoFactory;
import com.hvph.musicplay.dao.FolderDao;
import com.hvph.musicplay.dao.GenreDao;
import com.hvph.musicplay.dao.SongDao;
import com.hvph.musicplay.model.Song;

import java.util.ArrayList;

/**
 * Created by HoangHVP on 10/13/2014.
 */
public class SongLoader extends AsyncTaskLoader<ArrayList<Song>> {
    private ArrayList<Song> mSongList;
    private Context mContext;
    private String mSelection;
    private int mLoaderType;
    private SongDao mSongDao;
    private AlbumDao mAlbumDao;
    private ArtistDao mArtistDao;
    private GenreDao mGenreDao;
    private FolderDao mFolderDao;

    public SongLoader(Context context,String selection,int loaderType) {
        super(context);
        this.mContext = context;
        this.mSelection = selection;
        this.mLoaderType = loaderType;
        this.mSongDao = (SongDao)DaoFactory.getDao(Definition.TYPE_SONG);
        this.mAlbumDao = (AlbumDao) DaoFactory.getDao(Definition.TYPE_ALBUM);
        this.mArtistDao = (ArtistDao)DaoFactory.getDao(Definition.TYPE_ARTIST);
        this.mGenreDao = (GenreDao)DaoFactory.getDao(Definition.TYPE_GENRE);
        this.mFolderDao = (FolderDao)DaoFactory.getDao(Definition.TYPE_FOLDER);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        // AsyncTaskLoader doesn't start unless you forceLoad http://code.google.com/p/android/issues/detail?id=14944
        if(mSongList != null){
            deliverResult(mSongList);
        }
        if(takeContentChanged() || mSongList == null){
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
        ArrayList<Song> oldSongList = this.mSongList;
        this.mSongList = songList;

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
        if(mSongList != null){
            onReleaseResources(mSongList);
            mSongList = null;
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
        switch (mLoaderType){
            case Definition.TYPE_SONG:
                mSongList = mSongDao.getAllSong();
                break;
            case Definition.TYPE_ARTIST:
                mSongList = mArtistDao.getSongByArtist(mSelection);
                break;
            case Definition.TYPE_ALBUM:
                mSongList = mAlbumDao.getSongByAlbum(mSelection);
                break;
            case Definition.TYPE_GENRE:
                mSongList = mGenreDao.getSongByGenre(mSelection);
                break;
            case Definition.TYPE_FOLDER:
                mSongList = mFolderDao.getSongByFolder(mSelection);
                break;
            case Definition.TYPE_FAVORITE:
                mSongList = mSongDao.getSongFavorite();
                break;
        }
        return mSongList;
    }
}
