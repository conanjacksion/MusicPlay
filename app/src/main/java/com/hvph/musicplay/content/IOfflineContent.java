package com.hvph.musicplay.content;

import com.hvph.musicplay.model.Album;
import com.hvph.musicplay.model.Artist;
import com.hvph.musicplay.model.Folder;
import com.hvph.musicplay.model.Genre;
import com.hvph.musicplay.model.Song;

import java.util.ArrayList;

/**
 * Created by HoangHVP on 11/21/2014.
 */
public interface IOfflineContent extends IContent {
    public ArrayList<Song> getSongListFromSystem(String selection);

    public ArrayList<Artist> getArtistListFromSystem(String selection);

    public ArrayList<Album> getAlbumListFromSystem(String selection);

    public ArrayList<Genre> getGenreListFromSystem(String selection);

    public ArrayList<Folder> getFolderListFromSystem(String selection);
}
