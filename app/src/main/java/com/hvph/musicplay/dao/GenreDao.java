package com.hvph.musicplay.dao;

import android.database.Cursor;
import android.net.Uri;

import com.hvph.musicplay.business.Definition;
import com.hvph.musicplay.model.Genre;
import com.hvph.musicplay.model.Song;
import com.hvph.musicplay.util.Image;

import java.util.ArrayList;

/**
 * Created by bibo on 09/11/2014.
 */
public class GenreDao extends MPBaseDao<Genre>{
    public GenreDao(Class<Genre> tClass){
        super(tClass);
    }

    public Genre getGenre(String id) {
        //Ability to read mDb

        //Build query
        Cursor cursor = super.mDB.query(DaoDefinition.GenreEntry.TABLE_NAME,
                null,
                DaoDefinition.GenreEntry.COLUMN_NAME_ENTRY_ID + " = ?",
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
        Genre genre = new Genre();
        genre.setId(cursor.getString(0));
        genre.setName(cursor.getString(1));
        genre.setSongCount(String.valueOf(getSongByGenre(genre.getId()).size()));
        genre.setThumbnail(Image.getThumbnail(mContext, null, Definition.TYPE_GENRE));
        //mDb.close();
        return genre;
    }

    public ArrayList<Genre> getAllGenre() {
        ArrayList<Genre> genreList = new ArrayList<Genre>();
        //create query
        String query = "SELECT * FROM " + DaoDefinition.GenreEntry.TABLE_NAME;

        Cursor cursor = super.mDB.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Genre genre = new Genre();
                genre.setId(cursor.getString(0));
                genre.setName(cursor.getString(1));
                int totalSong = getSongByGenre(genre.getId()).size();
                genre.setSongCount(String.valueOf(totalSong));
                genre.setThumbnail(Image.getThumbnail(mContext, null, Definition.TYPE_GENRE));
                if(totalSong == 0){
                    super.delete(genre);
                }else{
                    genreList.add(genre);
                }
            } while (cursor.moveToNext());
        }
        //mDb.close();
        return genreList;
    }

    public ArrayList<Song> getSongByGenre(String id) {
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
                + " AND " + DaoDefinition.SongEntry.COLUMN_NAME_GENRE_ID + "=" + id;
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

    public String getGenreIdByGenreName(String genreName) {
        if (genreName == null || genreName.equalsIgnoreCase("")) {
            genreName = DaoDefinition.VALUE_UNKNOWN;
        }
        Cursor cursor = super.mDB.query(DaoDefinition.GenreEntry.TABLE_NAME,
                new String[]{DaoDefinition.GenreEntry.COLUMN_NAME_ENTRY_ID},
                DaoDefinition.GenreEntry.COLUMN_NAME_NAME + " = ?",
                new String[]{genreName},
                null,
                null,
                null,
                null);
        //Set cursor as the first record
        int idColumn = cursor.getColumnIndexOrThrow(DaoDefinition.GenreEntry.COLUMN_NAME_ENTRY_ID);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        String id = cursor.getString(idColumn);
        //mDb.close();
        return id;
    }
}
