package com.hvph.musicplay.content;

import com.hvph.musicplay.model.Model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by HoangHVP on 11/21/2014.
 */
public interface IContent {
    public void getDataFromSource();
    public void mapDataFromSourceToDatabase();
    public void mapDataFromSourceToDatabase(HashMap<Integer, ArrayList> data);
    public void registerEventListener(OnContentEventListener onContentEventListener);
}
