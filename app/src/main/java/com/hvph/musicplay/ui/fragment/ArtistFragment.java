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
import com.hvph.musicplay.adapter.ArtistAdapter;
import com.hvph.musicplay.business.ArtistLoader;
import com.hvph.musicplay.business.Definition;
import com.hvph.musicplay.model.Artist;
import com.hvph.musicplay.ui.activity.SongFilterActivity;

import java.util.ArrayList;


public class ArtistFragment extends BaseFragment implements LoaderManager.LoaderCallbacks<ArrayList<Artist>> {
    private ListView mListView;
    private ArtistAdapter mArtistAdapter;
    private Context mContext;

    public static ArtistFragment newInstance(String param1, int param2) {
        ArtistFragment fragment = new ArtistFragment();
        return fragment;
    }

    public ArtistFragment() {
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
        mArtistAdapter = new ArtistAdapter(mContext, new ArrayList<Artist>());
        mListView.setAdapter(mArtistAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selection = mArtistAdapter.getItem(position).getId();
                String name = mArtistAdapter.getItem(position).getName();
                Intent intent = new Intent(mContext, SongFilterActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt(SongFragment.ARG_LOADER_TYPE, Definition.TYPE_ARTIST);
                bundle.putString(SongFragment.ARG_SELECTION, selection);
                bundle.putString(SongFilterActivity.FILTER_TITLE, name);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        setDismissItem(mArtistAdapter,mListView);
        loadData();
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public AsyncTaskLoader<ArrayList<Artist>> onCreateLoader(int i, Bundle bundle) {
        return new ArtistLoader(mContext);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Artist>> arrayListLoader, ArrayList<Artist> artists) {
        mArtistAdapter.setData(artists);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Artist>> arrayListLoader) {
        mArtistAdapter.setData(null);
    }

    public void loadData() {
        getLoaderManager().restartLoader(0, null, this);
    }
}
