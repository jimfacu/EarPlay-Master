package com.example.earplay.HomeActivity.Entities.Genericos;

import android.os.Parcel;
import android.os.Parcelable;

public class ArtistGenerico implements Parcelable {

    private int id;
    private String name ;
    private String picture_big;

    public ArtistGenerico() {
    }

    public ArtistGenerico(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public ArtistGenerico(int id, String name, String picture_big) {
        this.id = id;
        this.name = name;
        this.picture_big = picture_big;
    }

    protected ArtistGenerico(Parcel in) {
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

    public static final Creator<ArtistGenerico> CREATOR = new Creator<ArtistGenerico>() {
        @Override
        public ArtistGenerico createFromParcel(Parcel in) {
            return new ArtistGenerico(in);
        }

        @Override
        public ArtistGenerico[] newArray(int size) {
            return new ArtistGenerico[size];
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
