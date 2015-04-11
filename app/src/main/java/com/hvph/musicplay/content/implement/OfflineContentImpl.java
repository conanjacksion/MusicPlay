package com.hvph.musicplay.content.implement;

import android.content.Context;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.hvph.musicplay.business.Definition;
import com.hvph.musicplay.content.IOfflineContent;
import com.hvph.musicplay.content.OnContentEventListener;
import com.hvph.musicplay.dao.AlbumDao;
import com.hvph.musicplay.dao.ArtistDao;
import com.hvph.musicplay.dao.DaoFactory;
import com.hvph.musicplay.dao.DaoDefinition;
import com.hvph.musicplay.dao.FolderDao;
import com.hvph.musicplay.dao.GenreDao;
import com.hvph.musicplay.dao.SongDao;
import com.hvph.musicplay.model.Album;
import com.hvph.musicplay.model.Artist;
import com.hvph.musicplay.model.Folder;
import com.hvph.musicplay.model.Genre;
import com.hvph.musicplay.model.Song;
import com.hvph.musicplay.util.DateTime;
import com.hvph.musicplay.util.Image;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Created by HoangHVP on 11/21/2014.
 */
public class OfflineContentImpl implements IOfflineContent {
    private Context mContext;
    private MediaMetadataRetriever mediaMetadataRetriever;

    private SongDao mSongDao;
    private AlbumDao mAlbumDao;
    private ArtistDao mArtistDao;
    private GenreDao mGenreDao;
    private FolderDao mFolderDao;

    private OnContentEventListener mOnContentEventListener;

    public static final String NO_THUMBNAIL = "";

    private static OfflineContentImpl mOfflineContentImpl;

    public static OfflineContentImpl getInstance(Context context) {
        if (mOfflineContentImpl == null) {
            mOfflineContentImpl = new OfflineContentImpl(context);
        }
        return mOfflineContentImpl;
    }

    private OfflineContentImpl(Context context) {
        this.mContext = context;
        mediaMetadataRetriever = new MediaMetadataRetriever();
        this.mSongDao = (SongDao) DaoFactory.getDao(Definition.TYPE_SONG);
        this.mAlbumDao = (AlbumDao) DaoFactory.getDao(Definition.TYPE_ALBUM);
        this.mArtistDao = (ArtistDao) DaoFactory.getDao(Definition.TYPE_ARTIST);
        this.mGenreDao = (GenreDao) DaoFactory.getDao(Definition.TYPE_GENRE);
        this.mFolderDao = (FolderDao) DaoFactory.getDao(Definition.TYPE_FOLDER);
    }

    public void registerEventListener(OnContentEventListener onContentEventListener) {
        this.mOnContentEventListener = onContentEventListener;
    }

    //unused
    public void getDataFromSource() {
        try {
            ArrayList<Artist> artistList = getArtistListFromSystem(null);
            ArrayList<Album> albumList = getAlbumListFromSystem(null);
            ArrayList<Genre> genreList = getGenreListFromSystem(null);
            ArrayList<Folder> folderList = getFolderListFromSystem(null);
            ArrayList<Song> songList = getSongListFromSystem(null);
            HashMap<Integer, ArrayList> data = new HashMap<Integer, ArrayList>();
            data.put(Definition.TYPE_ARTIST, artistList);
            data.put(Definition.TYPE_ALBUM, albumList);
            data.put(Definition.TYPE_GENRE, genreList);
            data.put(Definition.TYPE_FOLDER, folderList);
            data.put(Definition.TYPE_SONG, songList);
            mOnContentEventListener.onGetDataFromSourceFinished(data);
            Log.d("Offline content", "Get success");
        } catch (Exception ex) {
            Log.e("Offline content", "Get failed");
        }
    }

    //unused
    public void mapDataFromSourceToDatabase(HashMap<Integer, ArrayList> data) {
        //delete all records form all table
        mArtistDao.deleteAll();
        mArtistDao.resetSequence();
        mAlbumDao.deleteAll();
        mAlbumDao.resetSequence();
        mGenreDao.deleteAll();
        mGenreDao.resetSequence();
        mFolderDao.deleteAll();
        mFolderDao.resetSequence();
        mSongDao.deleteAll();
        mSongDao.resetSequence();
        //fill data to database
        Iterator iterator = data.keySet().iterator();
        while (iterator.hasNext()) {
            int modelType = (Integer) iterator.next();
            ArrayList modelList = data.get(modelType);
            switch (modelType) {
                case Definition.TYPE_ARTIST: {
                    for (Object model : modelList) {
                        mArtistDao.insert((Artist) model);
                    }
                    break;
                }
                case Definition.TYPE_ALBUM: {
                    for (Object model : modelList) {
                        mAlbumDao.insert((Album) model);
                    }
                    break;
                }
                case Definition.TYPE_GENRE: {
                    for (Object model : modelList) {
                        mGenreDao.insert((Genre) model);
                    }
                    break;
                }
                case Definition.TYPE_FOLDER: {
                    for (Object model : modelList) {
                        mFolderDao.insert((Folder) model);
                    }
                    break;
                }
                case Definition.TYPE_SONG: {
                    for (Object model : modelList) {
                        mSongDao.insert((Song) model);
                    }
                    break;
                }
            }
        }
    }

    public void mapDataFromSourceToDatabase() {
        //delete all records form all table
        mArtistDao.deleteAll();
        mArtistDao.resetSequence();
        mAlbumDao.deleteAll();
        mAlbumDao.resetSequence();
        mGenreDao.deleteAll();
        mGenreDao.resetSequence();
        mFolderDao.deleteAll();
        mFolderDao.resetSequence();
        mSongDao.deleteAll();
        mSongDao.resetSequence();
        //fill data to database
        ArrayList<Artist> artistList = getArtistListFromSystem(null);
        for (Artist artist : artistList) {
            mArtistDao.insert(artist);
        }
        ArrayList<Album> albumList = getAlbumListFromSystem(null);
        ArrayList<Genre> genreList = getGenreListFromSystem(null);
        ArrayList<Folder> folderList = getFolderListFromSystem(null);
        for (Genre genre : genreList) {
            mGenreDao.insert(genre);
        }
        for (Album album : albumList) {
            mAlbumDao.insert(album);
        }
        for (Folder folder : folderList) {
            mFolderDao.insert(folder);
        }
        ArrayList<Song> songList = getSongListFromSystem(null);
        for (Song song : songList) {
            mSongDao.insert(song);
        }
    }

    public ArrayList<Song> getSongListFromSystem(String selection) {
        Uri message = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        //your projection statement
        String[] projection = {};
        //query
        Cursor cursor = mContext.getContentResolver().query(message,
                projection, selection, null, null);

        int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID);
        int nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE);
        int titleColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME);
        int dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        int durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION);
        ArrayList<Song> songList = new ArrayList<Song>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                try {
                    Song song = new Song();
                    song.setId(cursor.getString(idColumn));
                    song.setName(cursor.getString(nameColumn));
                    song.setTitle(cursor.getString(titleColumn));
                    song.setData(cursor.getString(dataColumn));
                    int durationInMillis = Integer.parseInt(cursor.getString(durationColumn));
                    song.setDuration(DateTime.getTimeFromMilliseconds(durationInMillis, DateTime.MINUTE_SECOND_FORMAT));
                    song.setIsFavorite(DaoDefinition.SongEntry.VALUE_IS_NOT_FAVORITE);
                    Uri songUri = Uri.parse(song.getData());
                    song.setThumbnail(Image.getThumbnail(mContext, songUri, Definition.TYPE_SONG));
                    mediaMetadataRetriever.setDataSource(cursor.getString(dataColumn));
                    String genreName =
                            mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_GENRE);
                    String artistName =
                            mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
                    String albumName =
                            mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
                    String genreId = mGenreDao.getGenreIdByGenreName(genreName);
                    song.setGenreId(genreId);
                    String artistId = mArtistDao.getArtistIdByArtistName(artistName);
                    song.setArtistId(artistId);
                    String albumId = mAlbumDao.getAlbumIdByAlbumName(albumName);
                    song.setAlbumId(albumId);
                    if (song.getData().lastIndexOf('/') != -1) {
                        String folderPath = song.getData().substring(0, song.getData().lastIndexOf('/') + 1);
                        String folderId = mFolderDao.getFolderIdByFolderPath(folderPath);
                        song.setFolderId(folderId);
                    }
                    songList.add(song);
                } catch (Exception ex) {
                }
            } while (cursor.moveToNext());
            cursor.close();
        }
        return songList;
    }

    public ArrayList<Artist> getArtistListFromSystem(String selection) {
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        //your projection statement
        String[] projection = {};
        //query
        Cursor cursor = mContext.getContentResolver().query(uri,
                projection, selection, null, null);
        int dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        TreeMap<String, String> artistMap = new TreeMap<String, String>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                try {
                    String data = cursor.getString(dataColumn);
                    mediaMetadataRetriever.setDataSource(cursor.getString(dataColumn));
                    String artistName =
                            mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
                    if (artistName == null || artistName.equalsIgnoreCase("")) {
                        artistName = DaoDefinition.VALUE_UNKNOWN;
                    }
                    String dataArtistThumbnail = NO_THUMBNAIL;
                    if (Image.getThumbnail(mContext, Uri.parse(data), Definition.TYPE_SONG) != null) {
                        dataArtistThumbnail = data;
                    }
                    if (artistMap.containsKey(artistName)) {
                        String dataPre = artistMap.get(artistName);
                        if (!dataPre.equalsIgnoreCase(NO_THUMBNAIL)) {
                            dataArtistThumbnail = dataPre;
                        }
                    }
                    artistMap.put(artistName, dataArtistThumbnail);
                } catch (Exception ex) {
                }
            } while (cursor.moveToNext());
            cursor.close();
        }
        ArrayList<Artist> artistList = new ArrayList<Artist>();
        Iterator<String> iterator = artistMap.keySet().iterator();
        while (iterator.hasNext()) {
            Artist artist = new Artist();
            String artistName = iterator.next();
            String dataArtistThumbnail = artistMap.get(artistName);
            artist.setName(artistName);
            artist.setData(dataArtistThumbnail);
            artistList.add(artist);
        }
        return artistList;
    }

    public ArrayList<Album> getAlbumListFromSystem(String selection) {
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        //your projection statement
        String[] projection = {};
        //query
        Cursor cursor = mContext.getContentResolver().query(uri,
                projection, selection, null, null);
        int dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        TreeMap<String, ArrayList<String>> albumMap = new TreeMap<String, ArrayList<String>>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                try {
                    String data = cursor.getString(dataColumn);
                    mediaMetadataRetriever.setDataSource(data);
                    String albumName =
                            mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
                    String artistName =
                            mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
                    if (albumName == null || albumName.equalsIgnoreCase("")) {
                        albumName = DaoDefinition.VALUE_UNKNOWN;
                    }
                    String artistId = mArtistDao.getArtistIdByArtistName(artistName);
                    ArrayList<String> infoBonus = new ArrayList<String>();
                    String dataAlbumThumbnail = NO_THUMBNAIL;
                    if (Image.getThumbnail(mContext, Uri.parse(data), Definition.TYPE_SONG) != null) {
                        dataAlbumThumbnail = data;
                    }
                    if (albumMap.containsKey(albumName)) {
                        String dataPre = albumMap.get(albumName).get(1);
                        if (!dataPre.equalsIgnoreCase(NO_THUMBNAIL)) {
                            dataAlbumThumbnail = dataPre;
                        }
                    }
                    infoBonus.add(artistId);
                    infoBonus.add(dataAlbumThumbnail);
                    albumMap.put(albumName, infoBonus);
                } catch (Exception ex) {
                }
            } while (cursor.moveToNext());
            cursor.close();
        }
        ArrayList<Album> albumList = new ArrayList<Album>();
        Iterator<String> iterator = albumMap.keySet().iterator();
        while (iterator.hasNext()) {
            Album album = new Album();
            String albumName = iterator.next();
            ArrayList<String> infoBonus = albumMap.get(albumName);
            album.setName(albumName);
            album.setArtistId(infoBonus.get(0));
            album.setData(infoBonus.get(1));
            albumList.add(album);
        }
        return albumList;
    }

    public ArrayList<Genre> getGenreListFromSystem(String selection) {
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        //your projection statement
        String[] projection = {};
        //query
        Cursor cursor = mContext.getContentResolver().query(uri,
                projection, selection, null, null);
        int dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        TreeSet<String> genreSet = new TreeSet<String>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                try {
                    mediaMetadataRetriever.setDataSource(cursor.getString(dataColumn));
                    String genreName =
                            mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_GENRE);
                    if (genreName == null || genreName.equalsIgnoreCase("")) {
                        genreSet.add(DaoDefinition.VALUE_UNKNOWN);
                    } else {
                        genreSet.add(genreName);
                    }
                } catch (Exception ex) {
                }
            } while (cursor.moveToNext());
            cursor.close();
        }
        ArrayList<Genre> genreList = new ArrayList<Genre>();
        for (String genreName : genreSet) {
            Genre genre = new Genre();
            genre.setName(genreName);
            genreList.add(genre);
        }
        return genreList;
    }

    public ArrayList<Folder> getFolderListFromSystem(String selection) {
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        //your projection statement
        String[] projection = {};
        //query
        Cursor cursor = mContext.getContentResolver().query(uri,
                projection, selection, null, null);
        int dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        TreeSet<String> folderSet = new TreeSet<String>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String data = cursor.getString(dataColumn);
                String folderPath = data.substring(0, data.lastIndexOf('/') + 1);
                folderSet.add(folderPath);
            } while (cursor.moveToNext());
            cursor.close();
        }
        ArrayList<Folder> folderList = new ArrayList<Folder>();
        for (String folderPath : folderSet) {
            Folder folder = new Folder();
            folder.setPath(folderPath);
            String[] folderPathSplit = folderPath.split("/");
            folder.setName(folderPathSplit[folderPathSplit.length - 1]);
            folderList.add(folder);
        }
        return folderList;
    }
}
