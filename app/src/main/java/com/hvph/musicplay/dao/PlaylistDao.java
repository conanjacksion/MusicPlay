package com.hvph.musicplay.dao;

import android.database.Cursor;
import android.net.Uri;

import com.hvph.musicplay.business.Definition;
import com.hvph.musicplay.model.Playlist;
import com.hvph.musicplay.model.Song;
import com.hvph.musicplay.util.Image;

import java.util.ArrayList;

/**
 * Created by bibo on 09/11/2014.
 */
public class PlaylistDao extends MPBaseDao<Playlist>{
    public PlaylistDao(Class<Playlist> tClass){
        super(tClass);
    }

    public Playlist getPlaylist(String id) {
        //Ability to read mDb

        //Build query
        Cursor cursor = super.mDB.query(DaoDefinition.PlaylistEntry.TABLE_NAME,
                null,
                DaoDefinition.PlaylistEntry.COLUMN_NAME_ENTRY_ID + " = ?",
                new String[]{id},
                null,
                null,
                null,
                null);
        //Set cursor as the first record
        if (cursor != null) {
            cursor.moveToFirst();
        }
        //Return instance
        Playlist playlist = new Playlist();
        playlist.setId(cursor.getString(0));
        playlist.setName(cursor.getString(1));
        playlist.setSongCount(String.valueOf(getSongByPlaylist(playlist.getId()).size()));
        playlist.setThumbnail(Image.getThumbnail(mContext, null, Definition.TYPE_FOLDER));
        //mDb.close();
        return playlist;
    }

    public ArrayList<Playlist> getAllPlaylist() {
        ArrayList<Playlist> playlistList = new ArrayList<Playlist>();
        //create query
        String query = "SELECT * FROM " + DaoDefinition.PlaylistEntry.TABLE_NAME;

        Cursor cursor = super.mDB.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Playlist playlist = new Playlist();
                playlist.setId(cursor.getString(0));
                playlist.setName(cursor.getString(1));
                playlist.setSongCount(String.valueOf(getSongByPlaylist(playlist.getId()).size()));
                playlist.setThumbnail(Image.getThumbnail(mContext, null, Definition.TYPE_FOLDER));
                playlistList.add(playlist);
            } while (cursor.moveToNext());
        }
        //mDb.close();
        return playlistList;
    }

    public ArrayList<Song> getSongByPlaylist(String id) {
        ArrayList<Song> songList = new ArrayList<Song>();

        //Build query
        String query = "SELECT " + DaoDefinition.SongEntry.TABLE_NAME + ".*,"
                + DaoDefinition.ArtistEntry.TABLE_NAME
                + "." + DaoDefinition.ArtistEntry.COLUMN_NAME_NAME
                + " AS " + DaoDefinition.SongEntry.COLUMN_NAME_ARTIST_NAME
                + " FROM " + DaoDefinition.SongEntry.TABLE_NAME
                + "," + DaoDefinition.ArtistEntry.TABLE_NAME
                + " WHERE " + DaoDefinition.SongEntry.TABLE_NAME
                + "." + DaoDefinition.SongEntry.COLUMN_NAME_ARTIST_ID
                + " = " + DaoDefinition.ArtistEntry.TABLE_NAME
                + "." + DaoDefinition.ArtistEntry.COLUMN_NAME_ENTRY_ID
                + " AND " + DaoDefinition.SongEntry.COLUMN_NAME_PLAYLIST_ID + "=" + id;
//        Cursor cursor = mDb.query(DaoDefinition.SongEntry.TABLE_NAME,
//                null,
//                DaoDefinition.SongEntry.COLUMN_NAME_PLAYLIST_ID+" = ?",
//                new String[]{id},
//                null,
//                null,
//                null,
//                null);
        Cursor cursor = super.mDB.rawQuery(query, null);
        int columnId = cursor.getColumnIndexOrThrow(DaoDefinition.SongEntry._ID);
        int columnName = cursor.getColumnIndexOrThrow(DaoDefinition.SongEntry.COLUMN_NAME_NAME);
        int columnTitle = cursor.getColumnIndexOrThrow(DaoDefinition.SongEntry.COLUMN_NAME_TITLE);
        int columnData = cursor.getColumnIndexOrThrow(DaoDefinition.SongEntry.COLUMN_NAME_DATA);
        int columnDuration = cursor.getColumnIndexOrThrow(DaoDefinition.SongEntry.COLUMN_NAME_DURATION);
        int columnGenreId = cursor.getColumnIndexOrThrow(DaoDefinition.SongEntry.COLUMN_NAME_GENRE_ID);
        int columnArtistId = cursor.getColumnIndexOrThrow(DaoDefinition.SongEntry.COLUMN_NAME_ARTIST_ID);
        int columnAlbumId = cursor.getColumnIndexOrThrow(DaoDefinition.SongEntry.COLUMN_NAME_ALBUM_ID);
        int columnFolderId = cursor.getColumnIndexOrThrow(DaoDefinition.SongEntry.COLUMN_NAME_FOLDER_ID);
        int columnIsFavorite = cursor.getColumnIndexOrThrow(DaoDefinition.SongEntry.COLUMN_NAME_IS_FAVORITE);
        int columnArtistName = cursor.getColumnIndexOrThrow(DaoDefinition.SongEntry.COLUMN_NAME_ARTIST_NAME);
        //Set cursor as the first record
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Song song = new Song();
                song.setId(cursor.getString(columnId));
                song.setName(cursor.getString(columnName));
                song.setTitle(cursor.getString(columnTitle));
                song.setData(cursor.getString(columnData));
                song.setDuration(cursor.getString(columnDuration));
                song.setGenreId(cursor.getString(columnGenreId));
                song.setArtistId(cursor.getString(columnArtistId));
                song.setAlbumId(cursor.getString(columnAlbumId));
                song.setFolderId(cursor.getString(columnFolderId));
                song.setIsFavorite(cursor.getString(columnIsFavorite));
                Uri songUri = Uri.parse(song.getData());
                //song.setThumbnail(Image.getThumbnail(mContext,songUri,Definition.TYPE_SONG));
                song.setThumbnail(null);
                song.setArtistName(cursor.getString(columnArtistName));
                songList.add(song);
            } while (cursor.moveToNext());
        }
        //mDb.close();
        return songList;
    }
}
