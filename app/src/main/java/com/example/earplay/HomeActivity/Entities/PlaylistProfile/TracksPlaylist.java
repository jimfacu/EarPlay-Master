package com.example.earplay.HomeActivity.Entities.PlaylistProfile;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.earplay.HomeActivity.Entities.TracksRank.Track;

import java.util.List;

public class TracksPlaylist implements Parcelable {

    private List<Track> data;

    public TracksPlaylist(List<Track> data) {
        this.data = data;
    }

    protected TracksPlaylist(Parcel in) {
        data = in.createTypedArrayList(Track.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(data);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TracksPlaylist> CREATOR = new Creator<TracksPlaylist>() {
        @Override
        public TracksPlaylist createFromParcel(Parcel in) {
            return new TracksPlaylist(in);
        }

        @Override
        public TracksPlaylist[] newArray(int size) {
            return new TracksPlaylist[size];
        }
    };

    public List<Track> getData() {
        return data;
    }

    public void setData(List<Track> data) {
        this.data = data;
    }
}
