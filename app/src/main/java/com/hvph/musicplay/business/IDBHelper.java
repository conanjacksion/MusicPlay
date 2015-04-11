package com.hvph.musicplay.business;

import com.hvph.musicplay.model.Album;
import com.hvph.musicplay.model.Artist;
import com.hvph.musicplay.model.Folder;
import com.hvph.musicplay.model.Genre;
import com.hvph.musicplay.model.Playlist;
import com.hvph.musicplay.model.Song;

import java.util.ArrayList;

/**
 * Created by TruongND1 on 10/13/2014.
 */
public interface IDBHelper {
    /**
    public long addSong(Song song);
    public Song getSong(String id);
    public ArrayList<Song> getAllSong();
    public int updateSong(Song song);
    public int deleteSong(Song song);
    public boolean checkSongExist(Song song);

    public long addPlaylist(Playlist playlist);
    public Playlist getPlaylist(String id);
    public ArrayList<Playlist> getAllPlaylist();
    public int updatePlaylist(Playlist playlist);
    public int deletePlaylist(Playlist playlist);
    public boolean checkPlaylistExist(Playlist playlist);

    public long addArtist(Artist artist);
    public Artist getArtist(String id);
    public ArrayList<Artist> getAllArtist();
    public int updateArtist(Artist artist);
    public int deleteArtist(Artist artist);
    public boolean checkArtistExist(Artist artist);

    public long addAlbum(Album album);
    public Album getAlbum(String id);
    public ArrayList<Album> getAllAlbum();
    public int updateAlbum(Album album);
    public int deleteAlbum(Artist album);
    public boolean checkAlbumExist(Album album);

    public long addGenre(Genre genre);
    public Genre getGenre(String id);
    public ArrayList<Genre> getAllGenre();
    public int updateGenre(Genre genre);
    public int deleteGenre(Genre genre);
    public boolean checkGenreExist(Genre genre);

    public long addFolder(Folder folder);
    public Folder getFolder(String id);
    public ArrayList<Folder> getAllFolder();
    public int updateFolder(Folder folder);
    public int deleteFolder(Folder folder);
    public boolean checkFolderExist(Folder folder);

    public ArrayList<Song> getSongByArtist(String id);
    public ArrayList<Song> getSongByAlbum(String id);
    public ArrayList<Song> getSongByGenre(String id);
    public ArrayList<Song> getSongByPlaylist(String id);
    public ArrayList<Song> getSongByFolder(String id);
    public ArrayList<Song> getSongFavorite();

    public String getArtistIdByArtistName(String artistName);
    public String getAlbumIdByAlbumName(String albumName);
    public String getGenreIdByGenreName(String genreName);
    public String getFolderIdByFolderPath(String folderPath);
     */
}
