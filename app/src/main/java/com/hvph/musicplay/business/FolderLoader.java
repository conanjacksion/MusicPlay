package com.hvph.musicplay.business;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.hvph.musicplay.dao.DaoFactory;
import com.hvph.musicplay.dao.FolderDao;
import com.hvph.musicplay.model.Folder;

import java.util.ArrayList;

/**
 * Created by HoangHVP on 10/14/2014.
 */
public class FolderLoader extends AsyncTaskLoader<ArrayList<Folder>> {
    private ArrayList<Folder> mFolderList;
    private Context mContext;
    private FolderDao mFolderDao;

    public FolderLoader(Context context) {
        super(context);
        this.mContext = context;
        this.mFolderDao = (FolderDao) DaoFactory.getDao(Definition.TYPE_FOLDER);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        // AsyncTaskLoader doesn't start unless you forceLoad http://code.google.com/p/android/issues/detail?id=14944
        if(mFolderList != null){
            deliverResult(mFolderList);
        }
        if(takeContentChanged() || mFolderList == null){
            forceLoad();
        }
    }

    @Override
    public void deliverResult(ArrayList<Folder> folderList) {
        if(isReset()){
            // An async query came in while the loader is stopped. We don't need the result
            if(folderList != null){
                onReleaseResources(folderList);
            }
        }
        ArrayList<Folder> oldSongList = this.mFolderList;
        this.mFolderList = folderList;

        if(isStarted()){
            // If the loader is currently started, we can immediately deliver a result
            super.deliverResult(folderList);
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
    public void onCanceled(ArrayList<Folder> folderList) {
        super.onCanceled(folderList);

        // At this point we can release the resources associated with 'apps' if needed
        onReleaseResources(folderList);
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
        if(mFolderList != null){
            onReleaseResources(mFolderList);
            mFolderList = null;
        }
    }

    /**
     * Helper function to take care of releasing resources associated with an actively loaded data set
     */
    private void onReleaseResources(ArrayList<Folder> folderList){
        // For a simple list there is nothing to do
        // but for a Cursor we would close it here
    }

    @Override
    public ArrayList<Folder> loadInBackground() {
        mFolderList = mFolderDao.getAllFolder();
        return mFolderList;
    }
}
