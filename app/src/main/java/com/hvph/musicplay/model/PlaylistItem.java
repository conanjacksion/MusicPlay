package com.hvph.musicplay.model;

import com.hvph.musicplay.dao.DaoDefinition;
import com.hvph.musicplay.model.annotation.DBColumn;
import com.hvph.musicplay.model.annotation.DBTable;

/**
 * Created by HoangHVP on 11/6/2014.
 */
@DBTable(name = DaoDefinition.PlaylistItemEntry.TABLE_NAME)
public class PlaylistItem extends Model {
    @DBColumn(name = DaoDefinition.PlaylistItemEntry.COLUMN_NAME_SONG_ID,type="INTEGER",isForeignKey = true,
            foreignColumn = DaoDefinition.SongEntry._ID,foreignTable = DaoDefinition.SongEntry.TABLE_NAME)
    private String songId;
    @DBColumn(name = DaoDefinition.PlaylistItemEntry.COLUMN_NAME_PLAYLIST_ID,type="INTEGER",isForeignKey = true,
            foreignColumn = DaoDefinition.PlaylistEntry._ID,foreignTable = DaoDefinition.PlaylistEntry.TABLE_NAME)
    private String playlistId;

    public String getSongId() {
        return songId;
    }

    public void setSongId(String songId) {
        this.songId = songId;
    }

    public String getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(String playlistId) {
        this.playlistId = playlistId;
    }
}
