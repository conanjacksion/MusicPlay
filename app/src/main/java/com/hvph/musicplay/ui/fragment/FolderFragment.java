package com.hvph.musicplay.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hvph.musicplay.R;
import com.hvph.musicplay.adapter.FolderAdapter;
import com.hvph.musicplay.business.Definition;
import com.hvph.musicplay.business.FolderLoader;
import com.hvph.musicplay.model.Folder;
import com.hvph.musicplay.ui.activity.SongFilterActivity;

import java.util.ArrayList;

/**
 * Created by ThuyPT4 on 10/7/2014.
 */
public class FolderFragment extends BaseFragment implements LoaderManager.LoaderCallbacks<ArrayList<Folder>> {
    private ListView mListView;
    private FolderAdapter mFolderAdapter;
    private Context mContext;

    public static FolderFragment newInstance(String param1, int param2) {
        FolderFragment fragment = new FolderFragment();
        return fragment;
    }

    public FolderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mContext = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_listview, container, false);
        if (savedInstanceState != null) {
            return rootView;
        }
        mListView = (ListView) rootView.findViewById(R.id.list_view);
        mFolderAdapter = new FolderAdapter(mContext, new ArrayList<Folder>());
        mListView.setAdapter(mFolderAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selection = mFolderAdapter.getItem(position).getId();
                String name = mFolderAdapter.getItem(position).getName();
                Intent intent = new Intent(mContext, SongFilterActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt(SongFragment.ARG_LOADER_TYPE, Definition.TYPE_FOLDER);
                bundle.putString(SongFragment.ARG_SELECTION, selection);
                bundle.putString(SongFilterActivity.FILTER_TITLE, name);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        setDismissItem(mFolderAdapter,mListView);
        loadData();
        return rootView;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public AsyncTaskLoader<ArrayList<Folder>> onCreateLoader(int i, Bundle bundle) {
//        mProgressDialog = new ProgressDialog(getActivity());
//        mProgressDialog.setMessage("Load data ...");
//        mProgressDialog.setTitle("Waiting...");
//        mProgressDialog.setCanceledOnTouchOutside(false);
//        mProgressDialog.show();
        return new FolderLoader(mContext);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Folder>> arrayListLoader, ArrayList<Folder> folders) {
        mFolderAdapter.setData(folders);
        //mProgressDialog.dismiss();
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Folder>> arrayListLoader) {
        mFolderAdapter.setData(null);
    }

    public void loadData() {
        getLoaderManager().restartLoader(0, null, this);
    }
}
