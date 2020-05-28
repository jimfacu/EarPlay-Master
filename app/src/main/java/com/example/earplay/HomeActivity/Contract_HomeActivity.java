package com.example.earplay.HomeActivity;

import com.example.earplay.HomeActivity.Entities.AlbumProfile.ContainerAlbumProfile;
import com.example.earplay.HomeActivity.Entities.AlbumSearch.ContainerAlbumSearch;
import com.example.earplay.HomeActivity.Entities.AlbumsArtist.ContainerAlbums;
import com.example.earplay.HomeActivity.Entities.ArtistRank.ContainerArtistRank;
import com.example.earplay.HomeActivity.Entities.ArtistSearch.ContainerArtistSearch;
import com.example.earplay.HomeActivity.Entities.Genericos.ContainerTracksFav;
import com.example.earplay.HomeActivity.Entities.MisPlaylist.ContainerMisPlaylist;
import com.example.earplay.HomeActivity.Entities.MisPlaylist.Playlist;
import com.example.earplay.HomeActivity.Entities.PlaylistProfile.ContainerPlaylistProfile;
import com.example.earplay.HomeActivity.Entities.PlaylistRank.ContainerPlaylistRank;
import com.example.earplay.HomeActivity.Entities.TracksRank.ContainerTracksRank;

import java.util.List;


public interface Contract_HomeActivity {

    interface View{
        void mostrarListaDeArtistasRank(ContainerArtistRank containerArtistRankList);
        void mostrarListaDeTracksRank(ContainerTracksRank containerTracksRank);
        void mostrarPlayListRank(ContainerPlaylistRank containerPlaylistRank);
        void mostrarAlbumProfileArtis(ContainerAlbums containerAlbums);
        void mostrarTracksProfileArtist(ContainerTracksRank containerTracksArtistProfile);
        void mostrarAlbumAlbumProfile(ContainerAlbumProfile containerAlbumProfile);
        void mostrarOtrosAlbumsAlbumProfile(ContainerAlbums containerAlbums);
        void mostrarPlaylisProfileTracks(ContainerPlaylistProfile containerPlaylistProfile);
        void mostrarBusquedaTracks(ContainerTracksRank containerTracksRank);
        void mostrarBusquedaAlbums(ContainerAlbumSearch containerAlbumSearch);
        void mostrarBusquedaArtistas(ContainerArtistSearch containerArtistSearch);

        //FavTracks

        //Firebased
        void mostrarMisPlaylist(ContainerMisPlaylist containerMisPlaylist);
        void mostrarMisTracksFav(ContainerTracksFav containerTracksFav);
    }

    interface Presenter{
        void recibirListaDeArtistasRank(ContainerArtistRank containerArtistRankList);
        void pedirArtistasRankAlInteractor();
        void recibirListaDeTracksRank(ContainerTracksRank containerTracksRank);
        void pedirTracksRankAlInteractor();
        void pedirPlaylistRank();
        void recibirListaPlaylistRank(ContainerPlaylistRank containerPlaylistRank);
        void pedirTracksProfileArtist(int id);
        void recibirTracksProfileArtistDelInteractor(ContainerTracksRank containerTracksArtistProfile);
        void pedirAlbumsArtistAlInteractor(int id);
        void recibirAlbumsArtist(ContainerAlbums containerAlbums);
        void pedirAlbumProfile(int id);
        void recibirAlbumProfile(ContainerAlbumProfile containerAlbumProfile);
        void pedirAlbumsAlbumProfile(int id);
        void recibirAlbumsAlbumProfile(ContainerAlbums containerAlbums);
        void pedirTracksPlaylistProfile(int id);
        void recibirPlaylistProfileTracks(ContainerPlaylistProfile containerPlaylistProfile);

        //Busquedas
        void pedirTrackABuscar(String s);
        void pedirAlbumABuscar(String s);
        void pedirArtistaABuscar(String s);
        void recibirTrackABuscar(ContainerTracksRank containerTracksRank);
        void recibirAlbumABuscar(ContainerAlbumSearch containerAlbumSearch);
        void recibirArtistaABuscar(ContainerArtistSearch containerArtistSearch);

        //Firebased
        void pedirListaDeMisPlaylist();
        void pedirListaDeFavTracks();
        void recibirMisPlaylist(ContainerMisPlaylist containerMisPlaylist);
        void recibirNuevaListDePlaylist(ContainerMisPlaylist containerMisPlaylist);
        void recibirRsptaGuardadoPlaylist(boolean guardado);
        void recibirListaDeFavTracks(ContainerTracksFav containerTracksFav);
        void recibirListaConNuevoTrack(ContainerTracksFav containerTracksFav);
    }

    interface Interactor{
        void pedirListaDeArtistasRank();
        void pedirListaDeTracksRank();
        void pedirListaPlaylistRank();
        void pedirTracksProfileArtist(int id);
        void pedirAlbumArtist(int id);
        void pedirAlbumProfile(int id);
        void pedirAlbumsAlbumProfile(int id);
        void pedirPlaylistProfileTracks(int id);

        //Busquedas
        void pedirTrackABuscar(String s);
        void pedirAlbumABuscar(String s);
        void pedirArtistaABuscar(String s);

        //Firebased
        void pedirContainerMyPlaylists();
        void guardarContainerMyPlaylist(ContainerMisPlaylist containerMisPlaylist);
        void pedirContainerFavTracks();
        void guardarContainerFavTracks(ContainerTracksFav containerTracksFav);
        }
        interface FavTrack{
            void pedirTrackReproductor();
            void borrarTrackFavTrack();
            void cargarListaFavTrackReproductor(ContainerTracksFav containerTracksFav);
        }

        }

