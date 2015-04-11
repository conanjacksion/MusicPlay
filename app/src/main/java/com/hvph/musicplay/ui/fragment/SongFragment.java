package com.hvph.musicplay.ui.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hvph.musicplay.R;
import com.hvph.musicplay.adapter.SongAdapter;
import com.hvph.musicplay.business.Definition;
import com.hvph.musicplay.business.LocalReceiver;
import com.hvph.musicplay.business.SongLoader;
import com.hvph.musicplay.model.Song;
import com.hvph.musicplay.ui.activity.PlaybackActivity;

import java.util.ArrayList;



public class SongFragment extends BaseFragment implements LoaderManager.LoaderCallbacks<ArrayList<Song>>
        , LocalReceiver.LocalReceiverListener {
    private ListView mListView;
    private SongAdapter mSongAdapter;
    private String mSelection;
    private int mLoaderType;
    private Context mContext;
    private LocalReceiver mLocalReceiver;
    private OnSongFragmentInteractionListener mOnSongFragmentInteractionListener;

    public static final String ARG_LOADER_TYPE = "loaderType";
    public static final String ARG_SELECTION = "selection";

    public static SongFragment newInstance(String param1, int param2) {
        SongFragment fragment = new SongFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SELECTION, param1);
        args.putInt(ARG_LOADER_TYPE, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public SongFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mContext = getActivity();
        mOnSongFragmentInteractionListener = (OnSongFragmentInteractionListener) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mLoaderType = getArguments().getInt(ARG_LOADER_TYPE);
            mSelection = getArguments().getString(ARG_SELECTION);
        } else {
            mLoaderType = Definition.TYPE_SONG;
            mSelection = null;
        }
        mLocalReceiver = new LocalReceiver(this);
        IntentFilter filter = new IntentFilter();
        filter.addAction(Definition.ACTION_NOW_PLAYING_INDICATOR);
        LocalBroadcastManager.getInstance(mContext).registerReceiver(mLocalReceiver, filter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_listview, container, false);
        if (savedInstanceState != null) {
            return rootView;
        }
        mListView = (ListView) rootView.findViewById(R.id.list_view);
        mSongAdapter = new SongAdapter(mContext, new ArrayList<Song>(), mLoaderType);
        mListView.setAdapter(mSongAdapter);
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
                // set title
                alertDialogBuilder.setTitle("Alert !!!");

                // set dialog message
                alertDialogBuilder
                        .setMessage("Do you want to set ringtone as default !!!")
                        .setCancelable(false)
                        .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                RingtoneManager.setActualDefaultRingtoneUri(mContext,
                                        RingtoneManager.TYPE_RINGTONE,
                                        MediaStore.Audio.Media.getContentUriForPath(mSongAdapter.getItem(i).getData()));
                                Settings.System.putString(mContext.getContentResolver(), Settings.System.RINGTONE,
                                        mSongAdapter.getItem(i).getData());
                                dialog.cancel();
                            }
                        })
                        .setPositiveButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();
                return true;
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mOnSongFragmentInteractionListener
                        .onSongSelected(mSongAdapter.getData(), i);
            }
        });
        setDismissItem(mSongAdapter, mListView);
        if (mLoaderType != Definition.TYPE_PLAYING_LIST) {
            loadData();
        }
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(mContext).unregisterReceiver(mLocalReceiver);
    }

    @Override
    public AsyncTaskLoader<ArrayList<Song>> onCreateLoader(int i, Bundle bundle) {
        return new SongLoader(getActivity(), mSelection, mLoaderType);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Song>> arrayListLoader, ArrayList<Song> songs) {
        mSongAdapter.setData(songs);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Song>> arrayListLoader) {
        mSongAdapter.setData(null);
    }

    @Override
    public void updateNowPlaying() {
        mSongAdapter.notifyDataSetChanged();
        mListView.invalidate();
    }

    @Override
    public void loadData() {
        getLoaderManager().restartLoader(0, null, this);
    }

    public void setData(ArrayList<Song> songList, int songPosition, boolean isPlaying) {
        if(mLoaderType == Definition.TYPE_PLAYING_LIST) {
            mSongAdapter.setData(songList);
        }
    }

    public interface OnSongFragmentInteractionListener {
        public void onSongSelected(ArrayList<Song> songList, int songPosition);
    }
}
