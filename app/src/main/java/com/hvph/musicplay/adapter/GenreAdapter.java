package com.hvph.musicplay.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hvph.musicplay.R;
import com.hvph.musicplay.business.Definition;
import com.hvph.musicplay.model.Genre;

import java.util.ArrayList;

/**
 * Created by HoangHVP on 10/14/2014.
 */
public class GenreAdapter extends AbsMPBaseAdapter<Genre> {
    private class ViewHolder {
        TextView txtViewGenreName, txtViewGenreSongCount;
        ImageView imageViewGenreThumbnail;
    }

    private Genre mItem;

    public GenreAdapter(Context context, ArrayList<Genre> itemList) {
        super(context,itemList, Definition.TYPE_GENRE);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        mItem = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_genre_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.imageViewGenreThumbnail = (ImageView) convertView.findViewById(R.id.image_view_genre_thumbnail);
            viewHolder.txtViewGenreName = (TextView) convertView.findViewById(R.id.txt_view_genre_name);
            viewHolder.txtViewGenreSongCount = (TextView) convertView.findViewById(R.id.txt_view_genre_song_count);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if(mItem.getThumbnail() != null){
            viewHolder.imageViewGenreThumbnail.setImageBitmap(mItem.getThumbnail());
        }
        else{
            viewHolder.imageViewGenreThumbnail.setImageResource(R.drawable.genre_thumbnail_default);
        }
        viewHolder.txtViewGenreName.setText(mItem.getName());
        viewHolder.txtViewGenreSongCount.setText(mItem.getSongCount());
        return convertView;
    }
}
