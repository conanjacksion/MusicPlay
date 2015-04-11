package com.hvph.musicplay.dao;

import android.database.Cursor;
import android.net.Uri;

import com.hvph.musicplay.business.Definition;
import com.hvph.musicplay.model.Album;
import com.hvph.musicplay.model.Song;
import com.hvph.musicplay.util.Image;

import java.util.ArrayList;

/**
 * Created by bibo on 09/11/2014.
 */
public class AlbumDao extends MPBaseDao<Album>{
    public AlbumDao(Class<Album> tClass) {
        super(tClass);
    }

    public Album getAlbum(String id) {
        //Build query
        String query = "SELECT *,"
                + DaoDefinition.ArtistEntry.COLUMN_NAME_NAME
                + " FROM " + DaoDefinition.AlbumEntry.TABLE_NAME
                + "," + DaoDefinition.ArtistEntry.TABLE_NAME
                + " WHERE " + DaoDefinition.AlbumEntry.TABLE_NAME
                + "." + DaoDefinition.AlbumEntry.COLUMN_NAME_ARTIST_ID
                + " = " + DaoDefinition.ArtistEntry.TABLE_NAME
                + "." + DaoDefinition.ArtistEntry.COLUMN_NAME_ENTRY_ID
                + " AND " + DaoDefinition.AlbumEntry.COLUMN_NAME_ENTRY_ID + " = " + id;
        Cursor cursor = super.mDB.rawQuery(query, null);
        int columnArtistName = cursor.getColumnIndexOrThrow(DaoDefinition.ArtistEntry.COLUMN_NAME_NAME);
        //Set cursor as the first record
        if (cursor != null) {
            cursor.moveToFirst();
        }
        //Return instance
        Album album = new Album();
        album.setId(cursor.getString(0));
        album.setName(cursor.getString(1));
        album.setArtistId(String.valueOf(cursor.getInt(2)));
        album.setSongCount(String.valueOf(getSongByAlbum(album.getId()).size()));
        album.setArtistName(cursor.getString(columnArtistName));
        album.setData(cursor.getString(3));
        album.setThumbnail(Image.getThumbnail(mContext, Uri.parse(album.getData()), Definition.TYPE_ALBUM));
        //mDb.close();
        return album;
    }

    public ArrayList<Album> getAllAlbum() {
        ArrayList<Album> albumList = new ArrayList<Album>();
        //create query
        String query = "SELECT "+DaoDefinition.AlbumEntry.TABLE_NAME+".*,"
                + DaoDefinition.ArtistEntry.TABLE_NAME
                + "." + DaoDefinition.ArtistEntry.COLUMN_NAME_NAME
                + " AS "+DaoDefinition.SongEntry.COLUMN_NAME_ARTIST_NAME
                + " FROM " + DaoDefinition.AlbumEntry.TABLE_NAME
                + "," + DaoDefinition.ArtistEntry.TABLE_NAME
                + " WHERE " + DaoDefinition.AlbumEntry.TABLE_NAME
                + "." + DaoDefinition.AlbumEntry.COLUMN_NAME_ARTIST_ID
                + " = " + DaoDefinition.ArtistEntry.TABLE_NAME
                + "." + DaoDefinition.ArtistEntry.COLUMN_NAME_ENTRY_ID;

        Cursor cursor = super.mDB.rawQuery(query, null);
        int columnArtistName = cursor.getColumnIndexOrThrow(DaoDefinition.SongEntry.COLUMN_NAME_ARTIST_NAME);
        if (cursor.moveToFirst()) {
            do {
                Album album = new Album();
                album.setId(cursor.getString(0));
                album.setName(cursor.getString(1));
                album.setArtistId(String.valueOf(cursor.getInt(2)));
                int totalSong = getSongByAlbum(album.getId()).size();
                album.setSongCount(String.valueOf(totalSong));
                album.setArtistName(cursor.getString(columnArtistName));
                album.setData(cursor.getString(3));
                album.setThumbnail(null);
                //album.setThumbnail(Image.getThumbnail(mContext,Uri.parse(album.getData()),Definition.TYPE_ALBUM));
                if(totalSong == 0){
                    super.delete(album);
                }else {
                    albumList.add(album);
                }
            } while (cursor.moveToNext());
        }
        //mDb.close();
        return albumList;
    }

    public ArrayList<Song> getSongByAlbum(String id) {
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
                + " AND " + DaoDefinition.SongEntry.COLUMN_NAME_ALBUM_ID + "=" + id;
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
                song.setArtistName(cursor.getString(columnArtistName));
                songList.add(song);
            } while (cursor.moveToNext());
        }
        return songList;
    }

    public String getAlbumIdByAlbumName(String albumName) {
        if (albumName == null || albumName.equalsIgnoreCase("")) {
            albumName = DaoDefinition.VALUE_UNKNOWN;
        }
        //Ability to read mDb

        //Build query
        Cursor cursor = super.mDB.query(DaoDefinition.AlbumEntry.TABLE_NAME,
                new String[]{DaoDefinition.AlbumEntry.COLUMN_NAME_ENTRY_ID},
                DaoDefinition.AlbumEntry.COLUMN_NAME_NAME + " = ?",
                new String[]{albumName},
                null,
                null,
                null,
                null);
        //Set cursor as the first record
        int idColumn = cursor.getColumnIndexOrThrow(DaoDefinition.AlbumEntry.COLUMN_NAME_ENTRY_ID);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        String id = cursor.getString(idColumn);
        //mDb.close();
        return id;
    }
}
