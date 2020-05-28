package com.example.earplay.HomeActivity.Entities.AlbumsArtist;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ContainerAlbums implements Parcelable {

    private List<Albums> data;

    public ContainerAlbums(List<Albums> data) {
        this.data = data;
    }

    protected ContainerAlbums(Parcel in) {
        data = in.createTypedArrayList(Albums.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(data);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ContainerAlbums> CREATOR = new Creator<ContainerAlbums>() {
        @Override
        public ContainerAlbums createFromParcel(Parcel in) {
            return new ContainerAlbums(in);
        }

        @Override
        public ContainerAlbums[] newArray(int size) {
            return new ContainerAlbums[size];
        }
    };

    public List<Albums> getData() {
        return data;
    }

    public void setData(List<Albums> data) {
        this.data = data;
    }
}
