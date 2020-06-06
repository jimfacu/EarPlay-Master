package com.example.earplay.HomeActivity.Entities.AlbumsArtist;

import android.os.Parcel;
import android.os.Parcelable;

public class Albums implements Parcelable {

    private String id;
    private String title;
    private String cover_small;
    private String cover_medium;
    private String release_date;

    public Albums(String id, String title, String cover_small, String cover_medium, String release_date) {
        this.id = id;
        this.title = title;
        this.cover_small = cover_small;
        this.cover_medium = cover_medium;
        this.release_date = release_date;
    }

    protected Albums(Parcel in) {
        id = in.readString();
        title = in.readString();
        cover_small = in.readString();
        cover_medium = in.readString();
        release_date = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(cover_small);
        dest.writeString(cover_medium);
        dest.writeString(release_date);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Albums> CREATOR = new Creator<Albums>() {
        @Override
        public Albums createFromParcel(Parcel in) {
            return new Albums(in);
        }

        @Override
        public Albums[] newArray(int size) {
            return new Albums[size];
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

    public String getRelease_date() {
        return release_date;
    }
}
