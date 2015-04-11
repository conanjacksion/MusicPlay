package com.hvph.musicplay.model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.hvph.musicplay.dao.DaoDefinition;
import com.hvph.musicplay.model.annotation.DBColumn;
import com.hvph.musicplay.model.annotation.DBTable;

/**
 * Created by ThuyPT4 on 10/6/2014.
 */
@DBTable(name = DaoDefinition.SongEntry.TABLE_NAME)
public class Song extends Model implements Parcelable {
    @DBColumn(name = DaoDefinition.SongEntry.COLUMN_NAME_TITLE)
    private String title;
    @DBColumn(name = DaoDefinition.SongEntry.COLUMN_NAME_DATA)
    private String data;
    @DBColumn(name = DaoDefinition.SongEntry.COLUMN_NAME_DURATION)
    private String duration;
    @DBColumn(name = DaoDefinition.SongEntry.COLUMN_NAME_GENRE_ID, type = "INTEGER", isForeignKey = true,
            foreignColumn = DaoDefinition.GenreEntry._ID, foreignTable = DaoDefinition.GenreEntry.TABLE_NAME)
    private String genreId;
    @DBColumn(name = DaoDefinition.SongEntry.COLUMN_NAME_ARTIST_ID, type = "INTEGER", isForeignKey = true,
            foreignColumn = DaoDefinition.ArtistEntry._ID, foreignTable = DaoDefinition.ArtistEntry.TABLE_NAME)
    private String artistId;
    @DBColumn(name = DaoDefinition.SongEntry.COLUMN_NAME_ALBUM_ID, type = "INTEGER", isForeignKey = true,
            foreignColumn = DaoDefinition.AlbumEntry._ID, foreignTable = DaoDefinition.AlbumEntry.TABLE_NAME)
    private String albumId;
    @DBColumn(name = DaoDefinition.SongEntry.COLUMN_NAME_FOLDER_ID, type = "INTEGER", isForeignKey = true,
            foreignColumn = DaoDefinition.FolderEntry._ID, foreignTable = DaoDefinition.FolderEntry.TABLE_NAME)
    private String folderId;
    @DBColumn(name = DaoDefinition.SongEntry.COLUMN_NAME_IS_FAVORITE)
    private String isFavorite;
    private Bitmap thumbnail;
    private String artistName;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getGenreId() {
        return genreId;
    }

    public void setGenreId(String genreId) {
        this.genreId = genreId;
    }

    public String getArtistId() {
        return artistId;
    }

    public void setArtistId(String artistId) {
        this.artistId = artistId;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public String getFolderId() {
        return folderId;
    }

    public void setFolderId(String folderId) {
        this.folderId = folderId;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(String isFavorite) {
        this.isFavorite = isFavorite;
    }

    public Bitmap getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Bitmap thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(title);
        dest.writeString(data);
        dest.writeString(duration);
        dest.writeString(genreId);
        dest.writeString(artistId);
        dest.writeString(albumId);
        dest.writeString(folderId);
        dest.writeString(isFavorite);
        dest.writeString(artistName);
    }

    public Song() {
    }

    private Song(Parcel in) {
        id = in.readString();
        name = in.readString();
        title = in.readString();
        data = in.readString();
        duration = in.readString();
        genreId = in.readString();
        artistId = in.readString();
        albumId = in.readString();
        folderId = in.readString();
        isFavorite = in.readString();
        artistName = in.readString();
    }

    public static final Creator<Song> CREATOR = new Creator<Song>() {

        @Override
        public Song createFromParcel(Parcel source) {
            return new Song(source);
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[size];
        }
    };
}


