package com.hvph.musicplay.content;

import com.hvph.musicplay.model.Model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by HoangHVP on 11/21/2014.
 */
public interface OnContentEventListener {
    public void onGetDataFromSourceFinished(HashMap<Integer, ArrayList> data);
}
