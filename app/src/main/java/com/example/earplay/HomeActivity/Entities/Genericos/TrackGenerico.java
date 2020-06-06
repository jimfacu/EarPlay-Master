package com.example.earplay.HomeActivity.Entities.Genericos;

import android.os.Parcel;
import android.os.Parcelable;

public class TrackGenerico implements Parcelable {

    private int id ;
    private String title_short;
    private String preview;
    private String link;
    private ArtistGenerico artistGenerico;
    private AlbumGenerico albumGenerico;

    public TrackGenerico() {
    }

    public TrackGenerico(int id, String title_short, String preview, String link, ArtistGenerico artistGenerico, AlbumGenerico albumGenerico) {
        this.id = id;
        this.title_short = title_short;
        this.preview = preview;
        this.link = link;
        this.artistGenerico = artistGenerico;
        this.albumGenerico = albumGenerico;
    }

    protected TrackGenerico(Parcel in) {
        id = in.readInt();
        title_short = in.readString();
        preview = in.readString();
        link = in.readString();
        artistGenerico = in.readParcelable(ArtistGenerico.class.getClassLoader());
        albumGenerico = in.readParcelable(AlbumGenerico.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title_short);
        dest.writeString(preview);
        dest.writeString(link);
        dest.writeParcelable(artistGenerico, flags);
        dest.writeParcelable(albumGenerico, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TrackGenerico> CREATOR = new Creator<TrackGenerico>() {
        @Override
        public TrackGenerico createFromParcel(Parcel in) {
            return new TrackGenerico(in);
        }

        @Override
        public TrackGenerico[] newArray(int size) {
            return new TrackGenerico[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public ArtistGenerico getArtistGenerico() {
        return artistGenerico;
    }

    public void setArtistGenerico(ArtistGenerico artistGenerico) {
        this.artistGenerico = artistGenerico;
    }

    public AlbumGenerico getAlbumGenerico() {
        return albumGenerico;
    }

    public void setAlbumGenerico(AlbumGenerico albumGenerico) {
        this.albumGenerico = albumGenerico;
    }
}
