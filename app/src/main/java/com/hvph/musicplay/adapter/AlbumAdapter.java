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
import com.hvph.musicplay.model.Album;
import com.hvph.musicplay.util.Image;

import java.util.ArrayList;

/**
 * Created by HoangHVP on 10/8/2014.
 */
public class AlbumAdapter extends AbsMPBaseAdapter<Album> {

    private class ViewHolder {
        TextView txtViewAlbumTitle;
        ImageView imageViewAlbumThumbnail;
    }

    private Album mItem;

    public AlbumAdapter(Context context, ArrayList<Album> itemList) {
        super(context, itemList, Definition.TYPE_ALBUM);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        mItem = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            // inflate the layout
            convertView = mInflater.inflate(R.layout.grid_album_item, parent, false);
            // well set up the ViewHolder
            viewHolder = new ViewHolder();
            viewHolder.txtViewAlbumTitle = (TextView) convertView.findViewById(R.id.txt_view_album_title);
            viewHolder.imageViewAlbumThumbnail = (ImageView) convertView.findViewById(R.id.image_view_album_thumbnail);
            // store the holder with the view.
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.imageViewAlbumThumbnail.setImageResource(R.drawable.album_thumbnail_default);
        Image.loadImage(mContext, Uri.parse(mItem.getData()), viewHolder.imageViewAlbumThumbnail, R.drawable.album_thumbnail_default);
        viewHolder.txtViewAlbumTitle.setText(mItem.getName());
        return convertView;
    }
}
