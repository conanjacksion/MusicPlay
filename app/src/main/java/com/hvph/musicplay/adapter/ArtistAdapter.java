package com.hvph.musicplay.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hvph.musicplay.R;
import com.hvph.musicplay.business.Definition;
import com.hvph.musicplay.model.Artist;
import com.hvph.musicplay.util.Image;

import java.util.ArrayList;

/**
 * Created by ThuyPT4 on 10/6/2014.
 */
public class ArtistAdapter extends AbsMPBaseAdapter<Artist> {

    private class ViewHolder {
        TextView txtViewArtistName;
        TextView txtViewArtistSongCount;
        ImageView imageViewArtistThumbnail;
    }

    private Artist mItem;

    public ArtistAdapter(Context context, ArrayList<Artist> itemList) {
        super(context,itemList, Definition.TYPE_ARTIST);
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        mItem = getItem(position);
        ViewHolder viewHolder;
        if (view == null) {
            view = mInflater.inflate(R.layout.list_artist_item, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.imageViewArtistThumbnail = (ImageView) view.findViewById(R.id.image_view_artist_thumbnail);
            viewHolder.txtViewArtistName = (TextView) view.findViewById(R.id.txt_view_artist_name);
            viewHolder.txtViewArtistSongCount = (TextView) view.findViewById(R.id.txt_view_artist_song_count);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.imageViewArtistThumbnail.setImageResource(R.drawable.singer_thumbnail_default);
        Image.loadImage(mContext, Uri.parse(mItem.getData()), viewHolder.imageViewArtistThumbnail, R.drawable.singer_thumbnail_default);
        viewHolder.txtViewArtistName.setText(mItem.getName());
        viewHolder.txtViewArtistSongCount.setText(mItem.getSongCount());
        return view;
    }


}
