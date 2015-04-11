package com.hvph.musicplay.model;

import android.graphics.Bitmap;
import com.hvph.musicplay.dao.DaoDefinition;
import com.hvph.musicplay.model.annotation.DBColumn;
import com.hvph.musicplay.model.annotation.DBTable;

/**
 * Created by HoangHVP on 10/8/2014.
 */

@DBTable(name = DaoDefinition.AlbumEntry.TABLE_NAME)
public class Album extends Model{
    @DBColumn(name= DaoDefinition.AlbumEntry.COLUMN_NAME_ARTIST_ID,type="INTEGER",
            isForeignKey = true, foreignColumn = DaoDefinition.ArtistEntry._ID,
            foreignTable =  DaoDefinition.ArtistEntry.TABLE_NAME)
    private String artistId;
    private String songCount;
    private Bitmap thumbnail;
    private String artistName;
    @DBColumn(name= DaoDefinition.AlbumEntry.COLUMN_NAME_DATA)
    private String data;

    public Album() {
    }


    public String getArtistId() {
        return artistId;
    }

    public void setArtistId(String artistId) {
        this.artistId = artistId;
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

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}