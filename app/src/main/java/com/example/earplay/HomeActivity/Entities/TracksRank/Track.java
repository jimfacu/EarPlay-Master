package com.example.earplay.HomeActivity.Entities.TracksRank;

import android.os.Parcel;
import android.os.Parcelable;

public class Track implements Parcelable {

    private int id ;
    private String title;
    private String title_short;
    private String preview;
    private String link;
    private Album album;
    private Artist artist;



    public Track(int id, String preview, String link, String title_short) {
        this.id = id;
        this.preview = preview;
        this.link = link;
        this.title_short = title_short;
    }

    public Track(int id, String title, String title_short, String preview, String link, Album album, Artist artist) {
        this.id = id;
        this.title = title;
        this.title_short = title_short;
        this.preview = preview;
        this.link = link;
        this.album = album;
        this.artist = artist;
    }

    protected Track(Parcel in) {
        id = in.readInt();
        title = in.readString();
        title_short = in.readString();
        preview = in.readString();
        link = in.readString();
        album = in.readParcelable(Album.class.getClassLoader());
        artist = in.readParcelable(Artist.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(title_short);
        dest.writeString(preview);
        dest.writeString(link);
        dest.writeParcelable(album, flags);
        dest.writeParcelable(artist, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Track> CREATOR = new Creator<Track>() {
        @Override
        public Track createFromParcel(Parcel in) {
            return new Track(in);
        }

        @Override
        public Track[] newArray(int size) {
            return new Track[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle_short() {
        return title_short;
    }

    public String getPreview() {
        return preview;
    }

    public String getLink() {
        return link;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }
}
