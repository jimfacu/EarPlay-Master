package com.example.earplay.HomeActivity.Entities.TracksRank;

import android.os.Parcel;
import android.os.Parcelable;

public class Artist implements Parcelable {

    private int id;
    private String name;
    private String picture_big;

    public Artist(int id, String name, String picture_big) {
        this.id = id;
        this.name = name;
        this.picture_big = picture_big;
    }

    protected Artist(Parcel in) {
        id = in.readInt();
        name = in.readString();
        picture_big = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
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

    public String getPicture_big() {
        return picture_big;
    }
}
