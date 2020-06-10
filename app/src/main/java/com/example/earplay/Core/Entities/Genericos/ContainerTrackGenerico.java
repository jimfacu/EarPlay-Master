package com.example.earplay.Core.Entities.Genericos;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ContainerTrackGenerico implements Parcelable {

    private List<TrackGenerico> trackGenericosList;

    public ContainerTrackGenerico(List<TrackGenerico> trackGenericosList) {
        this.trackGenericosList = trackGenericosList;
    }

    protected ContainerTrackGenerico(Parcel in) {
        trackGenericosList = in.createTypedArrayList(TrackGenerico.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(trackGenericosList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ContainerTrackGenerico> CREATOR = new Creator<ContainerTrackGenerico>() {
        @Override
        public ContainerTrackGenerico createFromParcel(Parcel in) {
            return new ContainerTrackGenerico(in);
        }

        @Override
        public ContainerTrackGenerico[] newArray(int size) {
            return new ContainerTrackGenerico[size];
        }
    };

}
