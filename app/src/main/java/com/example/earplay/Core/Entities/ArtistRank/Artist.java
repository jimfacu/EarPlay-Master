package com.example.earplay.Core.Entities.ArtistRank;

import android.os.Parcel;
import android.os.Parcelable;

public class Artist implements Parcelable {

    private int id;
    private String name;
    private String link;
    private String picture;
    private String picture_medium;
    private String picture_big;

    public Artist(int id, String name, String link, String picture, String picture_medium, String picture_big) {
        this.id = id;
        this.name = name;
        this.link = link;
        this.picture = picture;
        this.picture_medium = picture_medium;
        this.picture_big = picture_big;
    }

    protected Artist(Parcel in) {
        id = in.readInt();
        name = in.readString();
        link = in.readString();
        picture = in.readString();
        picture_medium = in.readString();
        picture_big = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(link);
        dest.writeString(picture);
        dest.writeString(picture_medium);
        dest.writeString(picture_big);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Artist> CREATOR = new Creator<Artist>() {
        @Override
        public Artist createFromParcel(Parcel in) {
            return new Artist(in);
        }

        @Override
        public Artist[] newArray(int size) {
            return new Artist[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getPicture_medium() {
        return picture_medium;
    }

    public void setPicture_medium(String picture_medium) {
        this.picture_medium = picture_medium;
    }

    public String getPicture_big() {
        return picture_big;
    }

    public void setPicture_big(String picture_big) {
        this.picture_big = picture_big;
    }
}
