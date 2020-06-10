package com.example.earplay.Core.Entities.ArtistRank;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ContainerArtistRank implements Parcelable {

    private int total;
    private List<Artist> data;

    public ContainerArtistRank(int total, List<Artist> data) {
        this.total = total;
        this.data = data;
    }

    protected ContainerArtistRank(Parcel in) {
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

    public static final Creator<ContainerArtistRank> CREATOR = new Creator<ContainerArtistRank>() {
        @Override
        public ContainerArtistRank createFromParcel(Parcel in) {
            return new ContainerArtistRank(in);
        }

        @Override
        public ContainerArtistRank[] newArray(int size) {
            return new ContainerArtistRank[size];
        }
    };

    public List<Artist> getData() {
        return data;
    }

    public void setData(List<Artist> data) {
        this.data = data;
    }
}
