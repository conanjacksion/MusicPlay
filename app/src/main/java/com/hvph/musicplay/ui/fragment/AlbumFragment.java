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
import com.hvph.musicplay.adapter.AlbumAdapter;
import com.hvph.musicplay.business.AlbumLoader;
import com.hvph.musicplay.business.Definition;
import com.hvph.musicplay.model.Album;
import com.hvph.musicplay.ui.activity.SongFilterActivity;

import java.util.ArrayList;

/**
 * Created by ThuyPT4 on 10/7/2014.
 */
public class AlbumFragment extends BaseFragment implements LoaderManager.LoaderCallbacks<ArrayList<Album>> {
    private ListView mListView;
    private AlbumAdapter mAlbumAdapter;
    private Context mContext;

    public static AlbumFragment newInstance(String param1, int param2) {
        AlbumFragment fragment = new AlbumFragment();
        return fragment;
    }

    public AlbumFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mContext = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_listview, container, false);
        if (savedInstanceState != null) {
            return rootView;
        }
        mListView = (ListView) rootView.findViewById(R.id.list_view);
        mAlbumAdapter = new AlbumAdapter(mContext, new ArrayList<Album>());
        mListView.setAdapter(mAlbumAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selection = mAlbumAdapter.getItem(position).getId();
                String name = mAlbumAdapter.getItem(position).getName();
                Intent intent = new Intent(mContext, SongFilterActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt(SongFragment.ARG_LOADER_TYPE, Definition.TYPE_ALBUM);
                bundle.putString(SongFragment.ARG_SELECTION, selection);
                bundle.putString(SongFilterActivity.FILTER_TITLE, name);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        setDismissItem(mAlbumAdapter,mListView);
        loadData();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public AsyncTaskLoader<ArrayList<Album>> onCreateLoader(int i, Bundle bundle) {
//        mProgressDialog = new ProgressDialog(getActivity());
//        mProgressDialog.setMessage("Load data ...");
//        mProgressDialog.setTitle("Waiting...");
//        mProgressDialog.setCanceledOnTouchOutside(false);
//        mProgressDialog.show();
        return new AlbumLoader(mContext);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Album>> arrayListLoader, ArrayList<Album> albums) {
        mAlbumAdapter.setData(albums);
        //mProgressDialog.dismiss();
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Album>> arrayListLoader) {
        mAlbumAdapter.setData(null);
    }

    public void loadData() {
        getLoaderManager().restartLoader(0, null, this);
    }
}
