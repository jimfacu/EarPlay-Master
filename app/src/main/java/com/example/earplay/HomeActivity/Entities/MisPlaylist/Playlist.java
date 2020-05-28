package com.example.earplay.HomeActivity.Entities.MisPlaylist;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import com.example.earplay.HomeActivity.Entities.Genericos.TrackGenerico;
import com.example.earplay.HomeActivity.Entities.TracksRank.Track;

import java.util.List;

public class Playlist implements Parcelable {

    private List<TrackGenerico> tracksDeMiPlaylists;
    private String namePlaylist;

    public Playlist() {
    }

    public Playlist(List<TrackGenerico> tracksDeMiPlaylists, String namePlaylist) {
        this.tracksDeMiPlaylists = tracksDeMiPlaylists;
        this.namePlaylist = namePlaylist;
    }

    protected Playlist(Parcel in) {
        tracksDeMiPlaylists = in.createTypedArrayList(TrackGenerico.CREATOR);
        namePlaylist = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(tracksDeMiPlaylists);
        dest.writeString(namePlaylist);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Playlist> CREATOR = new Creator<Playlist>() {
        @Override
        public Playlist createFromParcel(Parcel in) {
            return new Playlist(in);
        }

        @Override
        public Playlist[] newArray(int size) {
            return new Playlist[size];
        }
    };

    public List<TrackGenerico> getTracksDeMiPlaylists() {
        return tracksDeMiPlaylists;
    }

    public void setTracksDeMiPlaylists(List<TrackGenerico> tracksDeMiPlaylists) {
        this.tracksDeMiPlaylists = tracksDeMiPlaylists;
    }

    public String getNamePlaylist() {
        return namePlaylist;
    }

    public void setNamePlaylist(String namePlaylist) {
        this.namePlaylist = namePlaylist;
    }

}
