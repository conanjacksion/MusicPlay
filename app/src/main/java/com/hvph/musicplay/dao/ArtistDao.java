package com.hvph.musicplay.dao;

import android.database.Cursor;

import com.hvph.musicplay.business.Definition;
import com.hvph.musicplay.model.Artist;
import com.hvph.musicplay.model.Song;
import com.hvph.musicplay.util.Image;

import java.util.ArrayList;

/**
 * Created by bibo on 09/11/2014.
 */
public class ArtistDao extends MPBaseDao<Artist>{
    public ArtistDao(Class<Artist> tClass){
        super(tClass);
    }

    public Artist getArtist(String id) {
        //Ability to read mDb

        //Build query
        Cursor cursor = super.mDB.query(DaoDefinition.ArtistEntry.TABLE_NAME,
                null,
                DaoDefinition.ArtistEntry.COLUMN_NAME_ENTRY_ID + " = ?",
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
        Artist artist = new Artist();
        artist.setId(cursor.getString(0));
        artist.setName(cursor.getString(1));
        artist.setData(cursor.getString(2));
        artist.setSongCount(String.valueOf(getSongByArtist(artist.getId()).size()));
        artist.setThumbnail(Image.getThumbnail(mContext, null, Definition.TYPE_ARTIST));
        //mDb.close();
        return artist;
    }

    public ArrayList<Artist> getAllArtist() {
        ArrayList<Artist> artistList = new ArrayList<Artist>();
        //create query
        String query = "SELECT * FROM " + DaoDefinition.ArtistEntry.TABLE_NAME;

        Cursor cursor = super.mDB.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Artist artist = new Artist();
                artist.setId(cursor.getString(0));
                artist.setName(cursor.getString(1));
                artist.setData(cursor.getString(2));
                artist.setThumbnail(null);
                int totalSong = getSongByArtist(artist.getId()).size();
                artist.setSongCount(String.valueOf(totalSong));
                if(totalSong == 0) {
                    if (!artist.getName().equals(DaoDefinition.VALUE_UNKNOWN)) {
                        super.delete(artist);
                    }
                }
                else{
                    artistList.add(artist);
                }
            } while (cursor.moveToNext());
        }
        //mDb.close();
        return artistList;
    }

    public ArrayList<Song> getSongByArtist(String id) {
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
                + " AND " + DaoDefinition.SongEntry.COLUMN_NAME_ARTIST_ID + "=" + id;
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
        //mDb.close();
        return songList;
    }

    public String getArtistIdByArtistName(String artistName) {
        if (artistName == null || artistName.equalsIgnoreCase("")) {
            artistName = DaoDefinition.VALUE_UNKNOWN;
        }
        //Ability to read mDb

        //Build query
        Cursor cursor = super.mDB.query(DaoDefinition.ArtistEntry.TABLE_NAME,
                new String[]{DaoDefinition.ArtistEntry.COLUMN_NAME_ENTRY_ID},
                DaoDefinition.ArtistEntry.COLUMN_NAME_NAME + " = ?",
                new String[]{artistName},
                null,
                null,
                null,
                null);
        //Set cursor as the first record
        int idColumn = cursor.getColumnIndexOrThrow(DaoDefinition.ArtistEntry.COLUMN_NAME_ENTRY_ID);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        String id = cursor.getString(idColumn);
        //mDb.close();
        return id;
    }
}
