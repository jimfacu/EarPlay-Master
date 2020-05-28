package com.example.earplay.HomeActivity.Entities.ArtistSearch;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ContainerArtistSearch implements Parcelable {

    private List<ArtistSearch> data;

    public ContainerArtistSearch(List<ArtistSearch> data) {
        this.data = data;
    }

    protected ContainerArtistSearch(Parcel in) {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ContainerArtistSearch> CREATOR = new Creator<ContainerArtistSearch>() {
        @Override
        public ContainerArtistSearch createFromParcel(Parcel in) {
            return new ContainerArtistSearch(in);
        }

        @Override
        public ContainerArtistSearch[] newArray(int size) {
            return new ContainerArtistSearch[size];
        }
    };

    public List<ArtistSearch> getData() {
        return data;
    }

    public void setData(List<ArtistSearch> data) {
        this.data = data;
    }
}
