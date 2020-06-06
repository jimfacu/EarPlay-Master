package com.example.earplay.HomeActivity.Entities.Genericos;

import android.os.Parcel;
import android.os.Parcelable;

public class FavTracks implements Parcelable {

    private int id ;
    private String title_short;
    private String preview;
    private String link;
    private ArtistGenerico artistGenerico;
    private AlbumGenerico albumGenerico;

    public FavTracks() {
    }

    public FavTracks(int id, String title_short, String preview, String link, ArtistGenerico artistGenerico, AlbumGenerico albumGenerico) {
        this.id = id;
        this.title_short = title_short;
        this.preview = preview;
        this.link = link;
        this.artistGenerico = artistGenerico;
        this.albumGenerico = albumGenerico;
    }

    protected FavTracks(Parcel in) {
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

    public static final Creator<FavTracks> CREATOR = new Creator<FavTracks>() {
        @Override
        public FavTracks createFromParcel(Parcel in) {
            return new FavTracks(in);
        }

        @Override
        public FavTracks[] newArray(int size) {
            return new FavTracks[size];
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

    public AlbumGenerico getAlbumGenerico() {
        return albumGenerico;
    }

    public void setAlbumGenerico(AlbumGenerico albumGenerico) {
        this.albumGenerico = albumGenerico;
    }
}
