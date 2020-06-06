package com.example.earplay.HomeActivity.Entities.AlbumProfile;

import android.os.Parcel;
import android.os.Parcelable;

public class TracksFromAlbumProfile implements Parcelable {

    private String id;
    private String title_short;
    private String preview;
    private String link;

    public TracksFromAlbumProfile(String id, String title_short, String preview, String link) {
        this.id = id;
        this.title_short = title_short;
        this.preview = preview;
        this.link = link;
    }


    protected TracksFromAlbumProfile(Parcel in) {
        id = in.readString();
        title_short = in.readString();
        preview = in.readString();
        link = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title_short);
        dest.writeString(preview);
        dest.writeString(link);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TracksFromAlbumProfile> CREATOR = new Creator<TracksFromAlbumProfile>() {
        @Override
        public TracksFromAlbumProfile createFromParcel(Parcel in) {
            return new TracksFromAlbumProfile(in);
        }

        @Override
        public TracksFromAlbumProfile[] newArray(int size) {
            return new TracksFromAlbumProfile[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle_short() {
        return title_short;
    }

    public String getPreview() {
        return preview;
    }

    public String getLink() {
        return link;
    }

}
