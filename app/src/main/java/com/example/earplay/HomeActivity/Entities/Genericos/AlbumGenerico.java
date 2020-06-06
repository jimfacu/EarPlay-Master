package com.example.earplay.HomeActivity.Entities.Genericos;

import android.os.Parcel;
import android.os.Parcelable;

public class AlbumGenerico implements Parcelable {

    private int id;
    private String title;
    private String cover_medium;

    public AlbumGenerico() {
    }

    public AlbumGenerico(int id, String title, String cover_medium) {
        this.id = id;
        this.title = title;
        this.cover_medium = cover_medium;
    }

    protected AlbumGenerico(Parcel in) {
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

    public static final Creator<AlbumGenerico> CREATOR = new Creator<AlbumGenerico>() {
        @Override
        public AlbumGenerico createFromParcel(Parcel in) {
            return new AlbumGenerico(in);
        }

        @Override
        public AlbumGenerico[] newArray(int size) {
            return new AlbumGenerico[size];
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
