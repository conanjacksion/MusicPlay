package com.hvph.musicplay.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.hvph.musicplay.R;
import com.hvph.musicplay.business.Definition;
import com.hvph.musicplay.dao.MPBaseDao;
import com.hvph.musicplay.dao.DaoDefinition;
import com.hvph.musicplay.model.Song;
import com.hvph.musicplay.pref.AppPrefs;
import com.hvph.musicplay.util.Image;

import java.util.ArrayList;

/**
 * Created by HoangHVP on 10/13/2014.
 */
public class SongAdapter extends AbsMPBaseAdapter<Song> {
    private class ViewHolder {
        TextView txtViewSongTitle, txtViewSongArtist, txtViewSongDuration;
        ImageView imageViewSongThumbnail;
        CheckBox chkSongFavorite;
    }

    private SharedPreferences mPreferencesNowPlaying;
    private SharedPreferences.Editor mEdit;
    private String idPlaying;
    private int mDataType;
    private Song mItem;


    public SongAdapter(Context context, ArrayList<Song> itemList, int dataType) {
        super(context, itemList, dataType);
        this.mDataType = dataType;
        this.mPreferencesNowPlaying = context.getSharedPreferences(AppPrefs.SharedPreferencesName.NOW_PLAYING.getValue(), context.MODE_PRIVATE);
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        mItem = getItem(position);
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_song_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.imageViewSongThumbnail = (ImageView) convertView.findViewById(R.id.image_view_song_thumbnail);
            viewHolder.txtViewSongTitle = (TextView) convertView.findViewById(R.id.txt_view_song_title);
            viewHolder.txtViewSongArtist = (TextView) convertView.findViewById(R.id.txt_view_artist_name);
            viewHolder.txtViewSongDuration = (TextView) convertView.findViewById(R.id.txt_view_song_duration);
            viewHolder.chkSongFavorite = (CheckBox) convertView.findViewById(R.id.chk_add_to_favorite);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.imageViewSongThumbnail.setImageResource(R.drawable.song_thumbnail_default);
        Image.loadImage(mContext, Uri.parse(mItem.getData()), viewHolder.imageViewSongThumbnail, R.drawable.song_thumbnail_default);
        viewHolder.txtViewSongTitle.setText(mItem.getName());
        viewHolder.txtViewSongArtist.setText(mItem.getArtistName());
        viewHolder.txtViewSongDuration.setText(mItem.getDuration());

        if (mItem.getIsFavorite().equalsIgnoreCase(DaoDefinition.SongEntry.VALUE_IS_FAVORITE)) {
            viewHolder.chkSongFavorite.setChecked(true);
        } else {
            viewHolder.chkSongFavorite.setChecked(false);
        }

        //Item checked listener
        viewHolder.chkSongFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItem = getItem(position);
                if (mDataType == Definition.TYPE_FAVORITE) {
                    if (!viewHolder.chkSongFavorite.isChecked()) {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
                        // set title
                        alertDialogBuilder.setTitle("Alert !!!");

                        // set dialog message
                        alertDialogBuilder
                                .setMessage("Remove this song from this list !!!")
                                .setCancelable(false)
                                .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        //Song song = mSongList.get(position);
                                        mItem.setIsFavorite(DaoDefinition.SongEntry.VALUE_IS_NOT_FAVORITE);
                                        mMPBaseDao.update(mItem);
                                        removeItem(position);
                                        notifyDataSetChanged();
                                        dialog.cancel();
                                    }
                                })
                                .setPositiveButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        notifyDataSetChanged();
                                        dialog.cancel();
                                    }
                                });
                        // create alert dialog
                        AlertDialog alertDialog = alertDialogBuilder.create();

                        // show it
                        alertDialog.show();
                    }
                } else {
                    if (viewHolder.chkSongFavorite.isChecked()) {
                        mItem.setIsFavorite(DaoDefinition.SongEntry.VALUE_IS_FAVORITE);
                    } else {
                        mItem.setIsFavorite(DaoDefinition.SongEntry.VALUE_IS_NOT_FAVORITE);
                    }
                    mMPBaseDao.update(mItem);
                }
            }
        });

        idPlaying = mPreferencesNowPlaying.getString(AppPrefs.Song.ID.getValue(), "");
        if (String.valueOf(getItemId(position)).equalsIgnoreCase(idPlaying)) {
            //is now playing
        } else {
            //is not now playing
        }
        return convertView;
    }

}


