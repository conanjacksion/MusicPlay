package com.hvph.musicplay.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;

import com.hvph.musicplay.R;
import com.hvph.musicplay.business.Definition;
import com.hvph.musicplay.model.Song;
import com.hvph.musicplay.ui.fragment.AbsBasePlaybackFragment;
import com.hvph.musicplay.ui.fragment.PlaybackFragmentMinSize;
import com.hvph.musicplay.ui.fragment.SongFragment;

import java.util.ArrayList;

public class PlaybackActivity extends FragmentActivity implements SongFragment.OnSongFragmentInteractionListener,
        AbsBasePlaybackFragment.BasePlaybackFragmentListener{

    private FragmentManager mFragmentManager;
    private SongFragment mSongFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playback);
        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }
            mSongFragment = SongFragment.newInstance("", Definition.TYPE_PLAYING_LIST);
            mFragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, mSongFragment);
            fragmentTransaction.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_playback, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSongSelected(ArrayList<Song> songList, int songPosition) {
        PlaybackFragmentMinSize playbackFragmentMinSize = (PlaybackFragmentMinSize) mFragmentManager
                .findFragmentById(R.id.playback_fragment);
        playbackFragmentMinSize.setSongSelected(songList, songPosition);
    }

    @Override
    public void updateSongFragment(ArrayList<Song> songList, int songPosition, boolean isPlaying) {
        mSongFragment.setData(songList,songPosition,isPlaying);
    }
}
