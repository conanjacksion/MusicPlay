package com.hvph.musicplay.adapter;

/**
 * Created by ThuyPT4 on 10/7/2014.
 */

import com.hvph.musicplay.ui.fragment.AlbumFragment;
import com.hvph.musicplay.ui.fragment.ArtistFragment;
import com.hvph.musicplay.ui.fragment.GenreFragment;
import com.hvph.musicplay.ui.fragment.FolderFragment;
import com.hvph.musicplay.ui.fragment.SongFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsAdapter extends FragmentPagerAdapter {

    public TabsAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                // all song fragment activity
                return new SongFragment();
            case 1:
                // fragment_artist fragment activity
                return new ArtistFragment();
            case 2:
                // fragment_gridview fragment activity
                return new AlbumFragment();
            case 3:
                // fragment_genre fragment activity
                return new GenreFragment();
            case 4:
                // fragment_folder fragment activity
                return new FolderFragment();
            case 5:
                // fragment_folder fragment activity
                return new SongFragment();

        }
        return null;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 6;
    }

}
