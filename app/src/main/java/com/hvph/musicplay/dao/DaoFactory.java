package com.hvph.musicplay.dao;

import com.hvph.musicplay.business.Definition;
import com.hvph.musicplay.model.Album;
import com.hvph.musicplay.model.Artist;
import com.hvph.musicplay.model.Folder;
import com.hvph.musicplay.model.Genre;
import com.hvph.musicplay.model.Model;
import com.hvph.musicplay.model.Playlist;
import com.hvph.musicplay.model.PlaylistItem;
import com.hvph.musicplay.model.Song;

/**
 * Created by HoangHVP on 11/20/2014.
 */
public class DaoFactory {
    public static MPBaseDao getDao(int dataType){
        switch (dataType){
            case Definition.TYPE_SONG:{
                return new SongDao(Song.class);
            }
            case Definition.TYPE_FAVORITE:{
                return new SongDao(Song.class);
            }
            case Definition.TYPE_ALBUM: {
                return new AlbumDao(Album.class);
            }
            case Definition.TYPE_ARTIST: {
                return new ArtistDao(Artist.class);
            }
            case Definition.TYPE_GENRE: {
                return new GenreDao(Genre.class);
            }
            case Definition.TYPE_FOLDER: {
                return new FolderDao(Folder.class);
            }
            case Definition.TYPE_PLAYLIST: {
                return new PlaylistDao(Playlist.class);
            }
            case Definition.TYPE_PLAYLIST_ITEM: {
                return new PlaylistItemDao(PlaylistItem.class);
            }
            default:
                return null;
        }
    }
}
