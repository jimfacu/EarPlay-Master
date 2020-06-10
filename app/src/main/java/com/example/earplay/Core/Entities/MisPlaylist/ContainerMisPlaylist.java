package com.example.earplay.Core.Entities.MisPlaylist;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ContainerMisPlaylist implements Parcelable {

    private List<Playlist> MiPlaylists;
    private int total;


    public ContainerMisPlaylist() {
    }

    public ContainerMisPlaylist(List<Playlist> miPlaylists, int total) {
        MiPlaylists = miPlaylists;
        this.total = total;
    }

    protected ContainerMisPlaylist(Parcel in) {
        MiPlaylists = in.createTypedArrayList(Playlist.CREATOR);
        total = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(MiPlaylists);
        dest.writeInt(total);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ContainerMisPlaylist> CREATOR = new Creator<ContainerMisPlaylist>() {
        @Override
        public ContainerMisPlaylist createFromParcel(Parcel in) {
            return new ContainerMisPlaylist(in);
        }

        @Override
        public ContainerMisPlaylist[] newArray(int size) {
            return new ContainerMisPlaylist[size];
        }
    };

    public List<Playlist> getMiPlaylists() {
        return MiPlaylists;
    }

    public void setMiPlaylists(List<Playlist> miPlaylists) {
        MiPlaylists = miPlaylists;
    }
}
