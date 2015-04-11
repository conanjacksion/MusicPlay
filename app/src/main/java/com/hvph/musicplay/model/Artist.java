package com.hvph.musicplay.model;

import android.graphics.Bitmap;

import com.hvph.musicplay.dao.DaoDefinition;
import com.hvph.musicplay.model.annotation.DBColumn;
import com.hvph.musicplay.model.annotation.DBTable;

/**
 * Created by HoangHVP on 10/8/2014.
 */
@DBTable(name = DaoDefinition.ArtistEntry.TABLE_NAME)
public class Artist extends Model {
    private String songCount;
    private Bitmap thumbnail;
    @DBColumn(name = DaoDefinition.ArtistEntry.COLUMN_NAME_DATA)
    private String data;

    public Artist() {
    }

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
