package com.example.earplay.HomeActivity.Utils;

import com.example.earplay.Core.Entities.AlbumProfile.ContainerAlbumProfile;
import com.example.earplay.Core.Entities.AlbumSearch.ContainerAlbumSearch;
import com.example.earplay.Core.Entities.AlbumsArtist.ContainerAlbums;
import com.example.earplay.Core.Entities.ArtistRank.ContainerArtistRank;
import com.example.earplay.Core.Entities.ArtistSearch.ContainerArtistSearch;
import com.example.earplay.Core.Entities.PlaylistProfile.ContainerPlaylistProfile;
import com.example.earplay.Core.Entities.PlaylistRank.ContainerPlaylistRank;
import com.example.earplay.Core.Entities.TracksRank.ContainerTracksRank;



import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ServiceApi_HomeActivity {

    @GET("https://api.deezer.com/chart/0/artists")
    Call<ContainerArtistRank> getListArtistRank();

    @GET("https://api.deezer.com/chart/0/tracks")
    Call<ContainerTracksRank> getListTracksRank();

    @GET("https://api.deezer.com/chart/0/playlists")
    Call<ContainerPlaylistRank> getListPlaylistRank();

    @GET(" https://api.deezer.com/artist/{id}/top")
    Call<ContainerTracksRank> getTracksArtistProfile(@Path("id")int id,@Query("limit")int limit);

    @GET("https://api.deezer.com/artist/{id}/albums")
    Call<ContainerAlbums> getAlbumsArtist(@Path("id")int id);

    @GET("https://api.deezer.com/album/{id}/tracks")
    Call<ContainerAlbumProfile> getAlbumProfile(@Path("id")int id);

    @GET("https://api.deezer.com/playlist/{id}")
    Call<ContainerPlaylistProfile> getPlaylistProfileTracks(@Path("id")long id);

    @GET("https://api.deezer.com/search/album")
    Call<ContainerAlbumSearch> getAlbumsSearch(@Query("q")String nameAlbum);

    @GET("https://api.deezer.com/search/artist")
    Call<ContainerArtistSearch> getArtistSearch(@Query("q")String nameArtist);

    @GET("https://api.deezer.com/search/track")
    Call<ContainerTracksRank> getTracksSearch(@Query("q")String nameTrack);
}
