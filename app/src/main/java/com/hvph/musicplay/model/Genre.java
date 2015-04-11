package com.hvph.musicplay.model;

import android.graphics.Bitmap;

import com.hvph.musicplay.dao.DaoDefinition;
import com.hvph.musicplay.model.annotation.DBTable;

/**
 * Created by HoangHVP on 10/14/2014.
 */
@DBTable(name= DaoDefinition.GenreEntry.TABLE_NAME)
public class Genre extends Model{
    private String songCount;
    private Bitmap thumbnail;

    public Genre(){}

    public String getSongCount() {
        return songCount;
    }

    public void setSongCount(String songCount) {
        this.songCount = songCount;
    }

    public Bitmap getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Bitmap thumbnail) {
        this.thumbnail = thumbnail;
    }
}
