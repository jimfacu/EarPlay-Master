package com.example.earplay.HomeActivity.Entities.AlbumSearch;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.earplay.HomeActivity.Entities.ArtistRank.Artist;

public class AlbumsSearch implements Parcelable {

    private String id;
    private String title;
    private String cover_medium;
    private Artist artist;

    public AlbumsSearch(String id, String title, String cover_medium, Artist artist) {
        this.id = id;
        this.title = title;
        this.cover_medium = cover_medium;
        this.artist = artist;
    }

    protected AlbumsSearch(Parcel in) {
        id = in.readString();
        title = in.readString();
        cover_medium = in.readString();
        artist = in.readParcelable(Artist.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(cover_medium);
        dest.writeParcelable(artist, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AlbumsSearch> CREATOR = new Creator<AlbumsSearch>() {
        @Override
        public AlbumsSearch createFromParcel(Parcel in) {
            return new AlbumsSearch(in);
        }

        @Override
        public AlbumsSearch[] newArray(int size) {
            return new AlbumsSearch[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover_medium() {
        return cover_medium;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }
}
