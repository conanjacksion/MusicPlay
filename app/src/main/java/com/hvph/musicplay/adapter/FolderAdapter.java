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
import com.hvph.musicplay.model.Folder;

import java.util.ArrayList;

/**
 * Created by HoangHVP on 10/14/2014.
 */
public class FolderAdapter extends AbsMPBaseAdapter<Folder> {
    private class ViewHolder {
        TextView txtViewFolderName, txtViewFolderPath, txtViewFolderSongCount;
        ImageView imageViewFolderThumbnail;
    }

    private Folder mItem;

    public FolderAdapter(Context context, ArrayList<Folder> itemList) {
        super(context,itemList, Definition.TYPE_FOLDER);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        mItem = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_folder_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.imageViewFolderThumbnail = (ImageView) convertView.findViewById(R.id.image_view_folder_thumbnail);
            viewHolder.txtViewFolderName = (TextView) convertView.findViewById(R.id.txt_view_folder_name);
            viewHolder.txtViewFolderPath = (TextView) convertView.findViewById(R.id.txt_view_folder_path);
            viewHolder.txtViewFolderSongCount = (TextView) convertView.findViewById(R.id.txt_view_folder_song_count);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if(mItem.getThumbnail() != null){
            viewHolder.imageViewFolderThumbnail.setImageBitmap(mItem.getThumbnail());
        }
        viewHolder.txtViewFolderName.setText(mItem.getName());
        viewHolder.txtViewFolderPath.setText(mItem.getPath());
        viewHolder.txtViewFolderSongCount.setText(mItem.getSongCount());
        return convertView;
    }
}
