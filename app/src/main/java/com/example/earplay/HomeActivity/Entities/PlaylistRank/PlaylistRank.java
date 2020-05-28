package com.example.earplay.HomeActivity.Entities.PlaylistRank;

import android.os.Parcel;
import android.os.Parcelable;

public class PlaylistRank implements Parcelable {

    private String picture;
    private String title;
    private String id;
    private String link;

    public PlaylistRank(String picture, String title,String id, String link) {
        this.picture = picture;
        this.title = title;
        this.id = id;
        this.link = link;
    }

    protected PlaylistRank(Parcel in) {
        picture = in.readString();
        title = in.readString();
        id = in.readString();
        link = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(picture);
        dest.writeString(title);
        dest.writeString(id);
        dest.writeString(link);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PlaylistRank> CREATOR = new Creator<PlaylistRank>() {
        @Override
        public PlaylistRank createFromParcel(Parcel in) {
            return new PlaylistRank(in);
        }

        @Override
        public PlaylistRank[] newArray(int size) {
            return new PlaylistRank[size];
        }
    };

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
