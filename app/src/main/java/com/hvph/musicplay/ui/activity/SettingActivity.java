package com.hvph.musicplay.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.hvph.musicplay.R;
import com.hvph.musicplay.adapter.SettingAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by ThuyPT4 on 10/9/2014.
 */
public class SettingActivity extends Activity{
    private ListView mlistView;
    private ArrayList<String> marrayList;
    private SettingAdapter msettingAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        marrayList = new ArrayList<String>();
        marrayList.add(0,"About");
        marrayList.add(1,"Contact");
        String[] web = {
                "Contact",
                "About"
        };

        Integer[] imageId = {
                R.drawable.contact,
                R.drawable.about,

        };
        mlistView = (ListView)findViewById(R.id.list_setting);
        msettingAdapter = new SettingAdapter(this,web,imageId);
        mlistView.setAdapter(msettingAdapter);
        mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                switch (position)
                {
                    case 0:
                        Intent intent = new Intent(SettingActivity.this, ContactActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent_start_about = new Intent(SettingActivity.this, AboutActivity.class);
                        startActivity(intent_start_about);
                        break;

                }
            }
        });


    }
}
