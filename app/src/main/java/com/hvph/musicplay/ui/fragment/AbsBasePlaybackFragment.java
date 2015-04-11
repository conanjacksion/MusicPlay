package com.hvph.musicplay.ui.fragment;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;

import com.hvph.musicplay.business.Definition;
import com.hvph.musicplay.model.Song;
import com.hvph.musicplay.service.MediaService;
import com.hvph.musicplay.util.Image;

import java.util.ArrayList;

/**
 * Created by bibo on 29/03/2015.
 */
public abstract class AbsBasePlaybackFragment extends Fragment implements MediaService.MediaServiceListener{

    protected MediaService mMediaService;
    protected Intent mPlayIntent;
    protected boolean mMediaBound = false;
    protected Context mContext;
    protected boolean mPaused = false, mPlaybackPaused = false;
    protected AudioManager mAudioManager;
    protected final int AUDIO_STREAM_TYPE = AudioManager.STREAM_MUSIC;
    protected BasePlaybackFragmentListener mBasePlaybackFragmentListener;

    public interface BasePlaybackFragmentListener {
        public void updateSongFragment(ArrayList<Song> songList, int songPosition, boolean isPlaying);
    }

    //connect to the media service
    private ServiceConnection mMediaConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MediaService.MediaBinder binder = (MediaService.MediaBinder) service;
            //get service
            mMediaService = binder.getService();
            mMediaBound = true;
            //register Media Service Listener
            mMediaService.registerMediaServiceListener(AbsBasePlaybackFragment.this);
            mMediaService.updatePlayback(true);
//            if(mMediaService!=null && mMediaService.isPlaying()){
//                mToggleButtonActionPlayPause.setChecked(true);
//            }
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
            mMediaBound = false;
        }
    };

    public void setSongSelected(ArrayList<Song> songList, int songPosition) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(Definition.SONG_LIST_BUNDLE_KEY, songList);
        bundle.putInt(Definition.SONG_POSITION, songPosition);
        mPlayIntent.putExtras(bundle);
        mContext.startService(mPlayIntent);
        mContext.bindService(mPlayIntent, mMediaConnection, Context.BIND_AUTO_CREATE);
    }
    protected void actionToggleShuffle() {
        if (mMediaService != null) {
            mMediaService.toggleShuffle();
        }
    }

    protected void actionSetRepeat(MediaService.Repeat repeat) {
        if (mMediaService != null) {
            mMediaService.setRepeat(repeat);
        }
    }

    protected void actionPlay() {
        if (mMediaService != null) {
            mMediaService.startPlayer();
        }
    }

    protected void actionPause() {
        if (mMediaService != null) {
            mMediaService.pausePlayer();
        }
    }

    protected void actionPlayNext() {
        if (mMediaService != null) {
            mMediaService.playNext(true);
        }
    }

    protected void actionPlayPrevious() {
        if (mMediaService != null) {
            mMediaService.playPrevious();
        }
    }

    protected abstract void setMusicController();

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mContext = activity;
        if (mContext instanceof BasePlaybackFragmentListener) {
            mBasePlaybackFragmentListener = (BasePlaybackFragmentListener) mContext;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mPlayIntent == null) {
            mPlayIntent = new Intent(mContext, MediaService.class);
        }
        ((Activity) mContext).setVolumeControlStream(AUDIO_STREAM_TYPE);
        mAudioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
    }

    @Override
    public void onStart() {
        super.onStart();
        setMusicController();
        mContext.bindService(mPlayIntent, mMediaConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onStop() {
        super.onStop();
        mContext.unbindService(mMediaConnection);
    }

    @Override
    public void updatePlaybackUI(ArrayList<Song> songList, int songPosition, boolean isPlaying, boolean shuffle, MediaService.Repeat repeat) {
        if (mBasePlaybackFragmentListener != null) {
            mBasePlaybackFragmentListener.updateSongFragment(songList, songPosition, isPlaying);
        }
    }
}
