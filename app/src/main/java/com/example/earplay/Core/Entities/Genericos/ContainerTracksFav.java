package com.example.earplay.Core.Entities.Genericos;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ContainerTracksFav implements Parcelable {

    private List<FavTracks> favTracks;

    public ContainerTracksFav() {
    }

    public ContainerTracksFav(List<FavTracks> favTracks) {
        this.favTracks = favTracks;
    }

    protected ContainerTracksFav(Parcel in) {
        favTracks = in.createTypedArrayList(FavTracks.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(favTracks);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ContainerTracksFav> CREATOR = new Creator<ContainerTracksFav>() {
        @Override
        public ContainerTracksFav createFromParcel(Parcel in) {
            return new ContainerTracksFav(in);
        }

        @Override
        public ContainerTracksFav[] newArray(int size) {
            return new ContainerTracksFav[size];
        }
    };

    public List<FavTracks> getFavTracks() {
        return favTracks;
    }

    public void setFavTracks(List<FavTracks> favTracks) {
        this.favTracks = favTracks;
    }
}
