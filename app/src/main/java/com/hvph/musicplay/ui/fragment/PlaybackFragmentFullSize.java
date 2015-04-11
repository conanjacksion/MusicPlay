package com.hvph.musicplay.ui.fragment;


import android.content.res.Resources;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.hvph.circleseekbar.HoloCircleSeekBar;
import com.hvph.musicplay.R;
import com.hvph.musicplay.model.Song;
import com.hvph.musicplay.service.MediaService;
import com.hvph.musicplay.ui.activity.PlaybackActivity;

import java.util.ArrayList;

public class PlaybackFragmentFullSize extends AbsBasePlaybackFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ImageButton mImageButtonRepeat;
    private ImageButton mImageButtonActionPreviousSong;
    private ImageButton mImageButtonActionNextSong;
    private ToggleButton mToggleButtonActionPlayPause;
    private ToggleButton mToggleButtonShuffle;
    private HoloCircleSeekBar mSeekBarVolume;
    private SeekBar mSeekBarDuration;
    private TextView mTextViewDuration;
    private TextView mTextViewPastDuration;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PlaybackFragmentFullSize.
     */
    // TODO: Rename and change types and number of parameters
    public static PlaybackFragmentFullSize newInstance(String param1, String param2) {
        PlaybackFragmentFullSize fragment = new PlaybackFragmentFullSize();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public PlaybackFragmentFullSize() {
        // Required empty public constructor
    }

    @Override
    protected void setMusicController() {
        mImageButtonActionPreviousSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionPlayPrevious();
            }
        });
        mImageButtonActionNextSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionPlayNext();
            }
        });
        mToggleButtonActionPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mToggleButtonActionPlayPause.isChecked()) {
                    actionPlay();
                } else {
                    actionPause();
                }
            }
        });
        mToggleButtonShuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionToggleShuffle();
            }
        });
        mImageButtonRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((Integer) mImageButtonRepeat.getTag() == R.drawable.playback_action_repeat_off) {
                    mImageButtonRepeat.setImageResource(R.drawable.playback_action_repeat_all_song);
                    mImageButtonRepeat.setTag(R.drawable.playback_action_repeat_all_song);
                    actionSetRepeat(MediaService.Repeat.ALL_SONG);
                } else if ((Integer) mImageButtonRepeat.getTag() == R.drawable.playback_action_repeat_all_song) {
                    mImageButtonRepeat.setImageResource(R.drawable.playback_action_repeat_one_song);
                    mImageButtonRepeat.setTag(R.drawable.playback_action_repeat_one_song);
                    actionSetRepeat(MediaService.Repeat.ONE_SONG);
                } else {
                    mImageButtonRepeat.setImageResource(R.drawable.playback_action_repeat_off);
                    mImageButtonRepeat.setTag(R.drawable.playback_action_repeat_off);
                    actionSetRepeat(MediaService.Repeat.OFF);
                }
            }
        });
        mSeekBarVolume.setMax(mAudioManager.getStreamMaxVolume(AUDIO_STREAM_TYPE));
        mSeekBarVolume.setInitPosition(mAudioManager.getStreamVolume(AUDIO_STREAM_TYPE));
        mSeekBarVolume.setOnSeekBarChangeListener(new HoloCircleSeekBar.OnCircleSeekBarChangeListener() {
            @Override
            public void onProgressChanged(HoloCircleSeekBar seekBar, int progress, boolean fromUser) {
                mAudioManager.setStreamVolume(AUDIO_STREAM_TYPE, progress, 0);
            }
        });
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
        View view = inflater.inflate(R.layout.fragment_playback_fragment_full_size, container, false);
        mImageButtonActionPreviousSong = (ImageButton) view.findViewById(R.id.image_button_action_previous_song);
        mImageButtonActionNextSong = (ImageButton) view.findViewById(R.id.image_button_action_next_song);
        mToggleButtonActionPlayPause = (ToggleButton) view.findViewById(R.id.toggle_button_action_play_pause);
        mImageButtonRepeat = (ImageButton) view.findViewById(R.id.image_button_action_repeat);
        mToggleButtonShuffle = (ToggleButton) view.findViewById(R.id.toggle_button_action_shuffle);
        mSeekBarVolume = (HoloCircleSeekBar) view.findViewById(R.id.seek_bar_volume);
        mSeekBarDuration = (SeekBar) view.findViewById(R.id.seek_bar_duration);
        mImageButtonRepeat.setTag(R.drawable.playback_action_repeat_off);
        return view;
    }

    @Override
    public void updatePlaybackUI(ArrayList<Song> songList, int songPosition, boolean isPlaying, boolean shuffle, MediaService.Repeat repeat) {
        super.updatePlaybackUI(songList, songPosition, isPlaying, shuffle, repeat);
        mToggleButtonActionPlayPause.setChecked(isPlaying);
        mToggleButtonShuffle.setChecked(shuffle);
        if (repeat == MediaService.Repeat.ALL_SONG) {
            mImageButtonRepeat.setImageResource(R.drawable.playback_action_repeat_all_song);
            mImageButtonRepeat.setTag(R.drawable.playback_action_repeat_all_song);
        } else if (repeat == MediaService.Repeat.ONE_SONG) {
            mImageButtonRepeat.setImageResource(R.drawable.playback_action_repeat_one_song);
            mImageButtonRepeat.setTag(R.drawable.playback_action_repeat_one_song);
        } else {
            mImageButtonRepeat.setImageResource(R.drawable.playback_action_repeat_off);
            mImageButtonRepeat.setTag(R.drawable.playback_action_repeat_off);
        }
    }
}
