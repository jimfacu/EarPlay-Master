package com.example.earplay.HomeActivity.Entities.ArtistSearch;

import android.os.Parcel;
import android.os.Parcelable;

public class ArtistSearch implements Parcelable {

    private String id;
    private String name;
    private String picture_medium;
    private String picture_big;

    public ArtistSearch(String id, String name, String picture_medium, String picture_big) {
        this.id = id;
        this.name = name;
        this.picture_medium = picture_medium;
        this.picture_big = picture_big;
    }

    protected ArtistSearch(Parcel in) {
        id = in.readString();
        name = in.readString();
        picture_medium = in.readString();
        picture_big = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(picture_medium);
        dest.writeString(picture_big);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ArtistSearch> CREATOR = new Creator<ArtistSearch>() {
        @Override
        public ArtistSearch createFromParcel(Parcel in) {
            return new ArtistSearch(in);
        }

        @Override
        public ArtistSearch[] newArray(int size) {
            return new ArtistSearch[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture_medium() {
        return picture_medium;
    }

    public String getPicture_big() {
        return picture_big;
    }
}
