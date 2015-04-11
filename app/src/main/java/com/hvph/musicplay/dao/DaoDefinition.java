package com.hvph.musicplay.dao;

import android.provider.BaseColumns;

/**
 * Created by HoangHVP on 11/10/2014.
 */
public class DaoDefinition {
    public static final String VALUE_UNKNOWN = "<UnKnown>";
    public static final String COLUMN_NAME_NAME = "name";

    //Declare table and columns attributes
    //TABLE : SONGS
    public static abstract class SongEntry implements BaseColumns {
        public static final String TABLE_NAME = "Song";
        public static final String COLUMN_NAME_ENTRY_ID = _ID;
        public static final String COLUMN_NAME_NAME = DaoDefinition.COLUMN_NAME_NAME;
        public static final String COLUMN_NAME_TITLE = "SongTitle";
        public static final String COLUMN_NAME_DATA = "SongData";
        public static final String COLUMN_NAME_DURATION = "SongDuration";
        public static final String COLUMN_NAME_GENRE_ID = "SongGenreId";
        public static final String COLUMN_NAME_ARTIST_ID = "SongArtistId";
        public static final String COLUMN_NAME_ALBUM_ID = "SongAlbumId";
        public static final String COLUMN_NAME_PLAYLIST_ID = "SongPlaylistId";
        public static final String COLUMN_NAME_FOLDER_ID = "SongFolderId";
        public static final String COLUMN_NAME_IS_FAVORITE = "IsSongFavorite";
        public static final String VALUE_IS_FAVORITE = "1";
        public static final String VALUE_IS_NOT_FAVORITE = "0";
        public static final String QUERY_DELETE_ALL_ENTRY = "DELETE FROM " + TABLE_NAME;
        public static final String COLUMN_NAME_ARTIST_NAME = "ArtistName";
    }

    //TABLE : PLAYLIST
    public static abstract class PlaylistEntry implements BaseColumns {
        public static final String TABLE_NAME = "Playlist";
        public static final String COLUMN_NAME_ENTRY_ID = _ID;
        public static final String COLUMN_NAME_NAME = DaoDefinition.COLUMN_NAME_NAME;
        public static final String QUERY_DELETE_ALL_ENTRY = "DELETE FROM " + TABLE_NAME;
    }

    //TABLE : PLAYLIST ITEM
    public static abstract class PlaylistItemEntry implements BaseColumns {
        public static final String TABLE_NAME = "PlaylistItem";
        public static final String COLUMN_NAME_ENTRY_ID = _ID;
        public static final String COLUMN_NAME_SONG_ID = "SongId";
        public static final String COLUMN_NAME_PLAYLIST_ID = "PlaylistId";
        public static final String QUERY_DELETE_ALL_ENTRY = "DELETE FROM " + TABLE_NAME;
    }

    //TABLE : GENRES
    public static abstract class GenreEntry implements BaseColumns {
        public static final String TABLE_NAME = "Genre";
        public static final String COLUMN_NAME_ENTRY_ID = _ID;
        public static final String COLUMN_NAME_NAME = DaoDefinition.COLUMN_NAME_NAME;
        public static final String QUERY_DELETE_ALL_ENTRY = "DELETE FROM " + TABLE_NAME;
    }

    //TABLE : FOLDERS
    public static abstract class FolderEntry implements BaseColumns {
        public static final String TABLE_NAME = "Folder";
        public static final String COLUMN_NAME_ENTRY_ID = _ID;
        public static final String COLUMN_NAME_NAME = DaoDefinition.COLUMN_NAME_NAME;
        public static final String COLUMN_NAME_PATH = "FolderPath";
        public static final String QUERY_DELETE_ALL_ENTRY = "DELETE FROM " + TABLE_NAME;
    }

    //TABLE : ARTISTS
    public static abstract class ArtistEntry implements BaseColumns {
        public static final String TABLE_NAME = "Artist";
        public static final String COLUMN_NAME_ENTRY_ID = _ID;
        public static final String COLUMN_NAME_NAME = DaoDefinition.COLUMN_NAME_NAME;
        public static final String COLUMN_NAME_DATA = "Data";
        public static final String QUERY_DELETE_ALL_ENTRY = "DELETE FROM " + TABLE_NAME;
    }

    //TABLE : ALBUMS
    public static abstract class AlbumEntry implements BaseColumns {
        public static final String TABLE_NAME = "Album";
        public static final String COLUMN_NAME_ENTRY_ID = _ID;
        public static final String COLUMN_NAME_NAME = DaoDefinition.COLUMN_NAME_NAME;
        public static final String COLUMN_NAME_ARTIST_ID = "ArtistId";
        public static final String COLUMN_NAME_DATA = "Data";
        public static final String QUERY_DELETE_ALL_ENTRY = "DELETE FROM " + TABLE_NAME;
    }
}
