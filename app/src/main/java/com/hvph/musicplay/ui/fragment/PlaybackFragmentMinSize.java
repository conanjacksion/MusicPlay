package com.hvph.musicplay.ui.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.hvph.musicplay.R;
import com.hvph.musicplay.model.Song;
import com.hvph.musicplay.service.MediaService;
import com.hvph.musicplay.ui.activity.PlaybackActivity;
import com.hvph.musicplay.util.Image;

import java.util.ArrayList;


public class PlaybackFragmentMinSize extends AbsBasePlaybackFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public final String TAG = PlaybackFragmentMinSize.class.getSimpleName();
    private TextView mTextViewSongTitle;
    private ImageView mImageViewSongThumbnail;
    private ImageButton mImageButtonActionPreviousSong;
    private ImageButton mImageButtonActionNextSong;
    private ToggleButton mToggleButtonActionPlayPause;
    private ImageButton mImageButtonVolumeControl;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PlaybackFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PlaybackFragmentMinSize newInstance(String param1, String param2) {
        PlaybackFragmentMinSize fragment = new PlaybackFragmentMinSize();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public PlaybackFragmentMinSize() {
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
        mImageButtonVolumeControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View volumeControllerLayout = inflater.inflate(R.layout.volume_dialog_layout, null);
                AlertDialog.Builder volumeControllerBuilder = new AlertDialog.Builder(mContext);
                volumeControllerBuilder.setView(volumeControllerLayout);
                AlertDialog volumeController = volumeControllerBuilder.create();
                volumeController.requestWindowFeature(Window.FEATURE_NO_TITLE);
                //DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
                volumeController.show();
                WindowManager.LayoutParams wmlp = new WindowManager.LayoutParams();
                wmlp.width = 95;
                wmlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                wmlp.gravity = Gravity.RIGHT;
                wmlp.y = 140;
                wmlp.alpha = 0.9f;
                //volumeController.getWindow().setLayout(95,wmlp.WRAP_CONTENT);
                volumeController.getWindow().setAttributes(wmlp);
                volumeController.getWindow().setBackgroundDrawableResource(R.drawable.volume_controller_bg);
                SeekBar seekBarVolume = (SeekBar) volumeControllerLayout.findViewById(R.id.seek_bar_volume);
                seekBarVolume.setMax(mAudioManager.getStreamMaxVolume(AUDIO_STREAM_TYPE));
                seekBarVolume.setProgress(mAudioManager.getStreamVolume(AUDIO_STREAM_TYPE));
                seekBarVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        mAudioManager.setStreamVolume(AUDIO_STREAM_TYPE, progress, 0);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
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
        View view = inflater.inflate(R.layout.fragment_playback_min_size, container, false);
        mTextViewSongTitle = (TextView) view.findViewById(R.id.text_view_song_title);
        mImageViewSongThumbnail = (ImageView) view.findViewById(R.id.image_view_song_thumbnail);
        mImageButtonActionPreviousSong = (ImageButton) view.findViewById(R.id.image_button_action_previous_song);
        mImageButtonActionNextSong = (ImageButton) view.findViewById(R.id.image_button_action_next_song);
        mToggleButtonActionPlayPause = (ToggleButton) view.findViewById(R.id.toggle_button_action_play_pause);
        mImageButtonVolumeControl = (ImageButton) view.findViewById(R.id.image_button_volume);
        mImageViewSongThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PlaybackActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void updatePlaybackUI(ArrayList<Song> songList, int songPosition, boolean isPlaying, boolean shuffle, MediaService.Repeat repeat) {
        super.updatePlaybackUI(songList, songPosition, isPlaying, shuffle, repeat);
        mTextViewSongTitle.setText(songList.get(songPosition).getName());
        Image.loadImage(mContext, Uri.parse(songList.get(songPosition).getData())
                , mImageViewSongThumbnail, R.drawable.song_thumbnail_default);
        mToggleButtonActionPlayPause.setChecked(isPlaying);
    }
}
