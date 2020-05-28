package com.example.earplay.HomeActivity.Entities.TracksRank;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ContainerTracksRank implements Parcelable {

    private List<Track> data;

    public ContainerTracksRank() {
    }

    public ContainerTracksRank(List<Track> data) {
        this.data = data;
    }



    protected ContainerTracksRank(Parcel in) {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ContainerTracksRank> CREATOR = new Creator<ContainerTracksRank>() {
        @Override
        public ContainerTracksRank createFromParcel(Parcel in) {
            return new ContainerTracksRank(in);
        }

        @Override
        public ContainerTracksRank[] newArray(int size) {
            return new ContainerTracksRank[size];
        }
    };

    public List<Track> getTracks() {
        return data;
    }

    public void setTracks(List<Track> data) {
        this.data = data;
    }
}
