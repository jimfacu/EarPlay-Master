package com.example.earplay.HomeActivity.Entities.PlaylistRank;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ContainerPlaylistRank implements Parcelable {

    private List<PlaylistRank> data;

    public ContainerPlaylistRank(List<PlaylistRank> data) {
        this.data = data;
    }

    protected ContainerPlaylistRank(Parcel in) {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ContainerPlaylistRank> CREATOR = new Creator<ContainerPlaylistRank>() {
        @Override
        public ContainerPlaylistRank createFromParcel(Parcel in) {
            return new ContainerPlaylistRank(in);
        }

        @Override
        public ContainerPlaylistRank[] newArray(int size) {
            return new ContainerPlaylistRank[size];
        }
    };

    public List<PlaylistRank> getData() {
        return data;
    }

    public void setData(List<PlaylistRank> data) {
        this.data = data;
    }
}
