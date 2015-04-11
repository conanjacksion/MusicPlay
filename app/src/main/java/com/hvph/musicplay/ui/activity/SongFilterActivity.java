package com.hvph.musicplay.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.hvph.musicplay.R;
import com.hvph.musicplay.model.Song;
import com.hvph.musicplay.ui.fragment.PlaybackFragmentMinSize;
import com.hvph.musicplay.ui.fragment.SongFragment;

import java.util.ArrayList;

public class SongFilterActivity extends FragmentActivity
        implements SongFragment.OnSongFragmentInteractionListener{

    public static final String FILTER_TITLE = "filterTitle";
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_filter);
        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            String selection = bundle.getString(SongFragment.ARG_SELECTION);
            int loaderType = bundle.getInt(SongFragment.ARG_LOADER_TYPE);
            SongFragment songFragment = SongFragment.newInstance(selection, loaderType);
            mFragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, songFragment);
            fragmentTransaction.commit();
            String name = bundle.getString(FILTER_TITLE);
            TextView textViewFilterTitle = (TextView) findViewById(R.id.text_view_filter_title);
            textViewFilterTitle.setText(name);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.song_filter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
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
}
