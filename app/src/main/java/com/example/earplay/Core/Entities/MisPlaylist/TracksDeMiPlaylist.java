package com.example.earplay.Core.Entities.MisPlaylist;

import android.os.Parcel;
import android.os.Parcelable;

public class TracksDeMiPlaylist implements Parcelable {

    private String id;
    private String preview;
    private String share;
    private String title_short;


    public TracksDeMiPlaylist() {
    }

    public TracksDeMiPlaylist(String id, String preview, String share, String title_short) {
        this.id = id;
        this.preview = preview;
        this.share = share;
        this.title_short = title_short;
    }

    public TracksDeMiPlaylist(Parcel in) {
        id = in.readString();
        preview = in.readString();
        share = in.readString();
        title_short = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(preview);
        dest.writeString(share);
        dest.writeString(title_short);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TracksDeMiPlaylist> CREATOR = new Creator<TracksDeMiPlaylist>() {
        @Override
        public TracksDeMiPlaylist createFromParcel(Parcel in) {
            return new TracksDeMiPlaylist(in);
        }

        @Override
        public TracksDeMiPlaylist[] newArray(int size) {
            return new TracksDeMiPlaylist[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPreview() {
        return preview;
    }

    public String getTitle_short() {
        return title_short;
    }
}
