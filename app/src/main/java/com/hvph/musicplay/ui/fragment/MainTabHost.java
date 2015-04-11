package com.hvph.musicplay.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hvph.musicplay.R;
import com.hvph.musicplay.business.Definition;
import com.hvph.musicplay.ui.view.TabIndicator;


public class MainTabHost extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentTabHost mTabHost;
    private Context mContext;

    public static MainTabHost newInstance(String param1, String param2) {
        MainTabHost fragment = new MainTabHost();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public MainTabHost() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_main_tabhost, container, false);
        mTabHost = (FragmentTabHost) rootView.findViewById(R.id.tabhost);
        mTabHost.setup(mContext, getChildFragmentManager(),R.id.real_tab_content);
        Bundle argsFavorite = new Bundle();
        argsFavorite.putString(SongFragment.ARG_SELECTION, null);
        argsFavorite.putInt(SongFragment.ARG_LOADER_TYPE, Definition.TYPE_FAVORITE);

        mTabHost.addTab(mTabHost.newTabSpec("Song")
                .setIndicator(new TabIndicator(mContext, "Song", R.drawable.music_icon)), SongFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("Artist")
                .setIndicator(new TabIndicator(mContext, "Artists", R.drawable.artist_icon)), ArtistFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("Album")
                .setIndicator(new TabIndicator(mContext, "Albums", R.drawable.album_icon)), AlbumFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("Genre")
                .setIndicator(new TabIndicator(mContext, "Genres", R.drawable.genres_icon)), GenreFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("Folder")
                .setIndicator(new TabIndicator(mContext, "Folders", R.drawable.ic_action_collection)), FolderFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("Favorite")
                .setIndicator(new TabIndicator(mContext, "Favorite", R.drawable.favorite_icon)), SongFragment.class, argsFavorite);

        return rootView;
    }
}
