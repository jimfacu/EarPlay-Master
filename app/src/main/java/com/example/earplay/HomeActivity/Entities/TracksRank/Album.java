package com.example.earplay.HomeActivity.Entities.TracksRank;

import android.os.Parcel;
import android.os.Parcelable;

public class Album implements Parcelable {

    private int id;
    private String title;
    private String cover_medium;


    public Album(int id, String title, String cover_medium) {
        this.id = id;
        this.title = title;
        this.cover_medium = cover_medium;
    }

    protected Album(Parcel in) {
        id = in.readInt();
        title = in.readString();
        cover_medium = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(cover_medium);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Album> CREATOR = new Creator<Album>() {
        @Override
        public Album createFromParcel(Parcel in) {
            return new Album(in);
        }

        @Override
        public Album[] newArray(int size) {
            return new Album[size];
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

    public String getCover_medium() {
        return cover_medium;
    }

}
