package com.hvph.musicplay.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;

import com.hvph.musicplay.R;
import com.hvph.musicplay.model.Song;
import com.hvph.musicplay.ui.fragment.MainTabHost;
import com.hvph.musicplay.ui.fragment.PlaybackFragmentMinSize;
import com.hvph.musicplay.ui.fragment.SongFragment;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity implements
        SongFragment.OnSongFragmentInteractionListener {
    private FragmentManager mFragmentManager;
    public static final String REFRESH_DATABASE = "refreshDatabase";
    public static final String FORCE_REFRESH_DATABASE = "forceRefreshDatabase";
    public static final String NOT_FORCE_REFRESH_DATABASE = "notForceRefreshDatabase";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            //mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab"));
            return;
        }
        mFragmentManager = getSupportFragmentManager();
        MainTabHost mainTabHost = new MainTabHost();
        mFragmentManager.beginTransaction().replace(R.id.main_tabhost_container, mainTabHost).commit();
    }


    @Override
    protected void onResume() {
        super.onResume();
        //AppUtils.loadFragmentData(mFragmentManager, mViewPager, mViewPager.getCurrentItem());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.action_refresh_database:
                this.finish();
                Intent intentRefreshDatabase = new Intent(this, WelcomeActivity.class);
                intentRefreshDatabase.putExtra(REFRESH_DATABASE, FORCE_REFRESH_DATABASE);
                startActivity(intentRefreshDatabase);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public void onSongSelected(ArrayList<Song> songList, int songPosition) {
        PlaybackFragmentMinSize playbackFragmentMinSize = (PlaybackFragmentMinSize) getSupportFragmentManager()
                .findFragmentById(R.id.playback_fragment);
        playbackFragmentMinSize.setSongSelected(songList, songPosition);
    }
}
