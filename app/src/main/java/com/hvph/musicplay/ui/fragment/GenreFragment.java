package com.hvph.musicplay.ui.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
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
import com.hvph.musicplay.adapter.GenreAdapter;
import com.hvph.musicplay.business.Definition;
import com.hvph.musicplay.business.GenreLoader;
import com.hvph.musicplay.model.Genre;
import com.hvph.musicplay.ui.activity.SongFilterActivity;

import java.util.ArrayList;

/**
 * Created by HoangHVP on 10/7/2014.
 */
public class GenreFragment extends BaseFragment implements LoaderManager.LoaderCallbacks<ArrayList<Genre>>{
    private ListView mListView;
    private GenreAdapter mGenreAdapter;
    private ProgressDialog mProgressDialog;
    private Context mContext;

    public static GenreFragment newInstance(String param1, int param2) {
        GenreFragment fragment = new GenreFragment();
        return fragment;
    }
    public GenreFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mContext = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_listview, container, false);
        if(savedInstanceState!=null){
            return rootView;
        }
        mListView = (ListView) rootView.findViewById(R.id.list_view);
        mGenreAdapter = new GenreAdapter(mContext,new ArrayList<Genre>());
        mListView.setAdapter(mGenreAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selection = mGenreAdapter.getItem(position).getId();
                String name = mGenreAdapter.getItem(position).getName();
                Intent intent = new Intent(mContext,SongFilterActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt(SongFragment.ARG_LOADER_TYPE, Definition.TYPE_GENRE);
                bundle.putString(SongFragment.ARG_SELECTION, selection);
                bundle.putString(SongFilterActivity.FILTER_TITLE, name);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        setDismissItem(mGenreAdapter,mListView);
        loadData();
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public AsyncTaskLoader<ArrayList<Genre>> onCreateLoader(int i, Bundle bundle) {
//        mProgressDialog = new ProgressDialog(getActivity());
//        mProgressDialog.setMessage("Load data ...");
//        mProgressDialog.setTitle("Waiting...");
//        mProgressDialog.setCanceledOnTouchOutside(false);
//        mProgressDialog.show();
        return new GenreLoader(mContext);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Genre>> arrayListLoader, ArrayList<Genre> genres) {
        mGenreAdapter.setData(genres);
        //mProgressDialog.dismiss();
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Genre>> arrayListLoader) {
        mGenreAdapter.setData(null);
    }

    public void loadData(){
        getLoaderManager().restartLoader(0, null, this);
    }

}
