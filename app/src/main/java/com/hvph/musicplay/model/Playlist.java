package com.hvph.musicplay.model;

import android.graphics.Bitmap;

import com.hvph.musicplay.dao.DaoDefinition;
import com.hvph.musicplay.model.annotation.DBTable;

/**
 * Created by HoangHVP on 10/22/2014.
 */
@DBTable(name= DaoDefinition.PlaylistEntry.TABLE_NAME)
public class Playlist extends Model{
    private String songCount;
    private Bitmap thumbnail;

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
