package com.hvph.musicplay.dao;

import android.database.Cursor;
import android.net.Uri;

import com.hvph.musicplay.business.Definition;
import com.hvph.musicplay.model.Song;
import com.hvph.musicplay.util.Image;

import java.util.ArrayList;

/**
 * Created by bibo on 09/11/2014.
 */
public class SongDao extends MPBaseDao<Song> {
    public SongDao(Class<Song> tClass){
        super(tClass);
    }

    public Song getSong(String id) {
        //Ability to read mDb

        //Build query
        String query = "SELECT *,"
                + DaoDefinition.ArtistEntry.COLUMN_NAME_NAME
                + " FROM " + DaoDefinition.SongEntry.TABLE_NAME
                + "," + DaoDefinition.ArtistEntry.TABLE_NAME
                + " WHERE " + DaoDefinition.SongEntry.TABLE_NAME
                + "." + DaoDefinition.SongEntry.COLUMN_NAME_ARTIST_ID
                + " = " + DaoDefinition.ArtistEntry.TABLE_NAME
                + "." + DaoDefinition.ArtistEntry.COLUMN_NAME_ENTRY_ID
                + " AND " + DaoDefinition.SongEntry.COLUMN_NAME_ENTRY_ID + " = " + id;
        Cursor cursor = super.mDB.rawQuery(query, null);
        int columnArtistName = cursor.getColumnIndexOrThrow(DaoDefinition.ArtistEntry.COLUMN_NAME_NAME);
//        Cursor cursor = mDb.query(DaoDefinition.SongEntry.TABLE_NAME,
//                null,
//                DaoDefinition.SongEntry.COLUMN_NAME_ENTRY_ID+" = ?",
//                new String[]{id},
//                null,
//                null,
//                null,
//                null);
        //Set cursor as the first record
        if (cursor != null) {
            cursor.moveToFirst();
        }
        //Return instance
        Song song = new Song();
        song.setId(cursor.getString(0));
        song.setName(cursor.getString(1));
        song.setTitle(cursor.getString(2));
        song.setData(cursor.getString(3));
        song.setDuration(cursor.getString(4));
        song.setGenreId(cursor.getString(5));
        song.setArtistId(cursor.getString(6));
        song.setAlbumId(cursor.getString(7));
        song.setFolderId(cursor.getString(8));
        song.setIsFavorite(cursor.getString(9));
        song.setArtistName(cursor.getString(columnArtistName));
        Uri songUri = Uri.parse(song.getData());
        song.setThumbnail(Image.getThumbnail(mContext, songUri, Definition.TYPE_SONG));
        //mDb.close();
        return song;
    }

    public ArrayList<Song> getAllSong() {
        ArrayList<Song> songList = new ArrayList<Song>();
        //create query
        String query = "SELECT " + DaoDefinition.SongEntry.TABLE_NAME + ".*,"
                + DaoDefinition.ArtistEntry.TABLE_NAME
                + "." + DaoDefinition.ArtistEntry.COLUMN_NAME_NAME
                + " AS " + DaoDefinition.SongEntry.COLUMN_NAME_ARTIST_NAME
                + " FROM " + DaoDefinition.SongEntry.TABLE_NAME
                + "," + DaoDefinition.ArtistEntry.TABLE_NAME
                + " WHERE " + DaoDefinition.SongEntry.TABLE_NAME
                + "." + DaoDefinition.SongEntry.COLUMN_NAME_ARTIST_ID
                + " = " + DaoDefinition.ArtistEntry.TABLE_NAME
                + "." + DaoDefinition.ArtistEntry.COLUMN_NAME_ENTRY_ID;

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
        if (cursor.moveToFirst()) {
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
                song.setArtistName(cursor.getString(columnArtistName));
                Uri songUri = Uri.parse(song.getData());
                //song.setThumbnail(Image.getThumbnail(mContext,songUri,Definition.TYPE_SONG));
                song.setThumbnail(null);
                songList.add(song);
            } while (cursor.moveToNext());
        }
        //mDb.close();
        return songList;
    }

    public ArrayList<Song> getSongFavorite() {
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
                + " AND " + DaoDefinition.SongEntry.COLUMN_NAME_IS_FAVORITE
                + "=" + DaoDefinition.SongEntry.VALUE_IS_FAVORITE;
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
