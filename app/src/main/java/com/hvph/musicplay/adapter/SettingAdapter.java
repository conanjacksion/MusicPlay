package com.hvph.musicplay.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.hvph.musicplay.R;

import java.util.ArrayList;

/**
 * Created by ThuyPT4 on 10/9/2014.
 */
public class SettingAdapter extends ArrayAdapter<String> {
    private Activity context;
    private ArrayList<String> arrayList;
    private String[]web;
    private final Integer[] imageId;

    public SettingAdapter(Activity context, String[]web, Integer[] imageId) {
        super(context, R.layout.list_setting_item, web);
        this.context = context;
        //this.arrayList = arrayList;
        this.web = web;
        this.imageId = imageId;
    }
  @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
      LayoutInflater inflater = context.getLayoutInflater();
      View rowView= inflater.inflate(R.layout.list_setting_item, null, true);
      TextView txtTitle = (TextView) rowView.findViewById(R.id.txt_view_setting);
      ImageView imageView = (ImageView) rowView.findViewById(R.id.image_view_setting);
      txtTitle.setText(web[position]);
      imageView.setImageResource(imageId[position]);
      return rowView ;
    }
}
