package com.hvph.musicplay.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

import com.hvph.musicplay.R;
import com.hvph.musicplay.business.Definition;
import com.hvph.musicplay.model.Artist;
import com.hvph.musicplay.model.Song;
import com.hvph.musicplay.ui.activity.MainActivity;

import java.util.ArrayList;
import java.util.Random;

public class MediaService extends Service implements MediaPlayer.OnPreparedListener,
        MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener {
    private MediaPlayer mMediaPlayer;
    private ArrayList<Song> mSongList;
    private int mSongPosition;
    private final IBinder mMediaBinder = new MediaBinder();
    private static final int NOTIFICATION_ID = 1;
    private boolean mShuffle;
    private Random mRandom;
    private final String TAG = MediaService.class.getSimpleName();
    public final String ACTION_PLAYING = "com.hoanghvp.mediaplayer.action_playing";
    private MediaServiceListener mMediaServiceListener;
    private Repeat mRepeat = Repeat.OFF;

    public MediaService() {
    }

    public void registerMediaServiceListener(MediaServiceListener mediaServiceListener) {
        this.mMediaServiceListener = mediaServiceListener;
    }

    public void updatePlayback(boolean checkPlaying) {
        if (mSongList != null) {
            if (!checkPlaying) {
                mMediaServiceListener.updatePlaybackUI(mSongList, mSongPosition, true, mShuffle, mRepeat);
            } else {
                mMediaServiceListener.updatePlaybackUI(mSongList, mSongPosition, isPlaying(), mShuffle, mRepeat);
            }
        }
    }

    @Override
    public void onCreate() {
        Log.v(TAG, "onCreate() called");
        mRandom = new Random();
        mSongPosition = 0;
        mMediaPlayer = new MediaPlayer();
        initMediaPlayer();
    }

    @Override
    public void onDestroy() {
        Log.v(TAG, "onDestroy() called");
        stopForeground(true);
        release();
    }

    private void release() {
        if (mMediaPlayer == null) {
            return;
        }

        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
        }
        mMediaPlayer.release();
        mMediaPlayer = null;
    }


    public void initMediaPlayer() {
        //set player properties
        mMediaPlayer.setWakeMode(getApplicationContext(),
                PowerManager.PARTIAL_WAKE_LOCK);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setOnPreparedListener(this);
        mMediaPlayer.setOnCompletionListener(this);
        mMediaPlayer.setOnErrorListener(this);
    }

    public void setList(ArrayList<Song> songList) {
        mSongList = songList;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v(TAG, "onStartCommand() called");
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            ArrayList<Song> songList = bundle.getParcelableArrayList(Definition.SONG_LIST_BUNDLE_KEY);
            int songPosition = bundle.getInt(Definition.SONG_POSITION);
            //set song list
            setList(songList);
            //set song position
            setSong(songPosition);
            //start to play song
            playSong();
        }
        return START_STICKY;
    }

    public class MediaBinder extends Binder {
        public MediaService getService() {
            return MediaService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        Log.v(TAG, "onBind() called");
        return mMediaBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.v(TAG, "onUnbind() called");
        //release();
        return false;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        Log.v(TAG, "onCompletion() called");
        if (getCurrentPosition() > 0) {
            //mMediaPlayer.reset();
            playNext(false);
        }
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        Log.v(TAG, "onError() called");
        mMediaPlayer.reset();
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        Log.v(TAG, "onPrepared() called");
        //start playback
        mMediaPlayer.start();
        Intent notIntent = new Intent(this, MainActivity.class);
        notIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendInt = PendingIntent.getActivity(this, 0,
                notIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder builder = new Notification.Builder(this);

        builder.setContentIntent(pendInt)
                .setSmallIcon(R.drawable.ic_launcher)
                .setTicker(mSongList.get(mSongPosition).getName())
                .setOngoing(true)
                .setContentTitle("Now Playing")
                .setContentText(mSongList.get(mSongPosition).getName());
        Notification not = builder.build();
        startForeground(NOTIFICATION_ID, not);
        Intent intentStartPlay = new Intent(ACTION_PLAYING);
    }

    public void playSong() {
        //play a song
        mMediaPlayer.reset();
        //get song
        Song playSong = mSongList.get(mSongPosition);
        Uri songUri = Uri.parse(playSong.getData());
        try {
            mMediaPlayer.setDataSource(getApplicationContext(), songUri);
        } catch (Exception e) {
            Log.e("MUSIC SERVICE", "Error setting data source", e);
        }
        mMediaPlayer.prepareAsync();
        //update UI by call callback
        updatePlayback(false);
    }

    public void setSong(int songIndex) {
        mSongPosition = songIndex;
    }

    public int getCurrentPosition() {
        return mMediaPlayer.getCurrentPosition();
    }

    public int getDuration() {
        return mMediaPlayer.getDuration();
    }

    public boolean isPlaying() {
        return mMediaPlayer.isPlaying();
    }

    public void pausePlayer() {
        mMediaPlayer.pause();
    }

    public void seek(int position) {
        mMediaPlayer.seekTo(position);
    }

    public void startPlayer() {
        mMediaPlayer.start();
    }

    public void playPrevious() {
        mSongPosition--;
        if (mSongPosition < 0) {
            mSongPosition = mSongList.size() - 1;
        }
        playSong();
    }

    public void playNext(boolean fromControl) {
        if ((mShuffle && mRepeat != Repeat.ONE_SONG && !fromControl) || (mShuffle && fromControl)) {
            int newSong = mSongPosition;
            while (newSong == mSongPosition) {
                newSong = mRandom.nextInt(mSongList.size());
            }
            mSongPosition = newSong;
        } else if (fromControl || (mRepeat != Repeat.ONE_SONG && !fromControl)) {
            mSongPosition++;
            if (mSongPosition >= mSongList.size()) {
                mSongPosition = 0;
            }
        }
        if (!fromControl && mRepeat == Repeat.OFF && mSongPosition == 0) {

        } else {
            playSong();
        }
    }

    public void setRepeat(Repeat repeat) {
        this.mRepeat = repeat;
    }

    public void toggleShuffle() {
        mShuffle = !mShuffle;
    }

    public interface MediaServiceListener {
        public void updatePlaybackUI(ArrayList<Song> songList, int songPosition, boolean isPlaying, boolean shuffle, Repeat repeat);
    }

    public enum Repeat {
        OFF, ONE_SONG, ALL_SONG;
    }
}
