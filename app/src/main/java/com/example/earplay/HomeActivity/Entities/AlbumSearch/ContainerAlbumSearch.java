package com.example.earplay.HomeActivity.Entities.AlbumSearch;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ContainerAlbumSearch implements Parcelable {

    private List<AlbumsSearch> data;

    public ContainerAlbumSearch(List<AlbumsSearch> data) {
        this.data = data;
    }

    protected ContainerAlbumSearch(Parcel in) {
    }

    public static final Creator<ContainerAlbumSearch> CREATOR = new Creator<ContainerAlbumSearch>() {
        @Override
        public ContainerAlbumSearch createFromParcel(Parcel in) {
            return new ContainerAlbumSearch(in);
        }

        @Override
        public ContainerAlbumSearch[] newArray(int size) {
            return new ContainerAlbumSearch[size];
        }
    };

    public List<AlbumsSearch> getData() {
        return data;
    }

    public void setData(List<AlbumsSearch> data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }
}
