package com.example.earplay.Core.Entities.AlbumProfile;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ContainerAlbumProfile implements Parcelable {

    private List<TracksFromAlbumProfile> data;
    private int total;

    public ContainerAlbumProfile(List<TracksFromAlbumProfile> data, int total) {
        this.data = data;
        this.total = total;
    }

    protected ContainerAlbumProfile(Parcel in) {
        total = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(total);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ContainerAlbumProfile> CREATOR = new Creator<ContainerAlbumProfile>() {
        @Override
        public ContainerAlbumProfile createFromParcel(Parcel in) {
            return new ContainerAlbumProfile(in);
        }

        @Override
        public ContainerAlbumProfile[] newArray(int size) {
            return new ContainerAlbumProfile[size];
        }
    };

    public List<TracksFromAlbumProfile> getData() {
        return data;
    }

    public void setData(List<TracksFromAlbumProfile> data) {
        this.data = data;
    }

    public int getTotal() {
        return total;
    }
}
