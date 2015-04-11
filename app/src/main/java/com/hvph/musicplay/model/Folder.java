package com.hvph.musicplay.model;

import android.graphics.Bitmap;
import com.hvph.musicplay.dao.DaoDefinition;
import com.hvph.musicplay.model.annotation.DBColumn;
import com.hvph.musicplay.model.annotation.DBTable;

/**
 * Created by HoangHVP on 10/14/2014.
 */
@DBTable(name= DaoDefinition.FolderEntry.TABLE_NAME)
public class Folder extends Model{
    @DBColumn(name = DaoDefinition.FolderEntry.COLUMN_NAME_PATH)
    private String path;
    private String songCount;
    private Bitmap thumbnail;

    public Folder(){}

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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
}
