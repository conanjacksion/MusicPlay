package com.hvph.musicplay.business;

/**
 * Created by bibo on 14/10/2014.
 */
public class Definition {
    public static final int TYPE_SONG = 0;
    public static final int TYPE_ARTIST = 1;
    public static final int TYPE_ALBUM = 2;
    public static final int TYPE_GENRE = 3;
    public static final int TYPE_FOLDER = 4;
    public static final int TYPE_FAVORITE = 5;
    public static final int TYPE_PLAYLIST = 6;
    public static final int TYPE_PLAYLIST_ITEM = 7;
    public static final int TYPE_PLAYING_LIST = 8;

    public static final String ACTION_NOW_PLAYING_INDICATOR = "com.hvph.musicplay.action.NOW_PLAYING_INDICATOR";
    public static final String SONG_LIST_BUNDLE_KEY = "songListBundleKey";
    public static final String SONG_POSITION = "songPosition";
}
