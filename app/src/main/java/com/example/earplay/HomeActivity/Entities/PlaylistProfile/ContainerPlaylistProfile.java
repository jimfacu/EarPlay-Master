package com.example.earplay.HomeActivity.Entities.PlaylistProfile;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ContainerPlaylistProfile implements Parcelable {

    private String id;
    private String title;
    private String picture_big;
    private TracksPlaylist tracks;

    public ContainerPlaylistProfile(String id, String title, String picture_big, TracksPlaylist tracks) {
        this.id = id;
        this.title = title;
        this.picture_big = picture_big;
        this.tracks = tracks;
    }

    protected ContainerPlaylistProfile(Parcel in) {
        id = in.readString();
        title = in.readString();
        picture_big = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(picture_big);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ContainerPlaylistProfile> CREATOR = new Creator<ContainerPlaylistProfile>() {
        @Override
        public ContainerPlaylistProfile createFromParcel(Parcel in) {
            return new ContainerPlaylistProfile(in);
        }

        @Override
        public ContainerPlaylistProfile[] newArray(int size) {
            return new ContainerPlaylistProfile[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPicture_big() {
        return picture_big;
    }

    public TracksPlaylist getTracks() {
        return tracks;
    }
}
