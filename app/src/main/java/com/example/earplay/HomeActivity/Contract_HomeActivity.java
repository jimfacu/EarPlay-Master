package com.example.earplay.HomeActivity;

import com.example.earplay.Core.Entities.AlbumProfile.ContainerAlbumProfile;
import com.example.earplay.Core.Entities.AlbumSearch.ContainerAlbumSearch;
import com.example.earplay.Core.Entities.AlbumsArtist.ContainerAlbums;
import com.example.earplay.Core.Entities.ArtistRank.ContainerArtistRank;
import com.example.earplay.Core.Entities.ArtistSearch.ContainerArtistSearch;
import com.example.earplay.Core.Entities.Genericos.ContainerTracksFav;
import com.example.earplay.Core.Entities.MisPlaylist.ContainerMisPlaylist;
import com.example.earplay.Core.Entities.PlaylistProfile.ContainerPlaylistProfile;
import com.example.earplay.Core.Entities.PlaylistRank.ContainerPlaylistRank;
import com.example.earplay.Core.Entities.TracksRank.ContainerTracksRank;


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
        void mostrarRsptaMyPlaylist(boolean ok);

        //FavTracks
        void mostrarMisPlaylist(ContainerMisPlaylist containerMisPlaylist);
        void mostrarMisTracksFav(ContainerTracksFav containerTracksFav);

        //Mensajes a mostrar
        void mostrarMensajeDeError(String s);
        void mostrarMensajeDeActualizacion(String s);
        void mostrarMensajeSinInternet();

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
        void pedirTracksPlaylistProfile(long id);
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

        //Mensajes
        void recibirMensajeDeError(String s);
        void recibirMensajeDeActualizacion(String s);
    }

    interface Interactor{
        void pedirListaDeArtistasRank();
        void pedirListaDeTracksRank();
        void pedirListaPlaylistRank();
        void pedirTracksProfileArtist(int id);
        void pedirAlbumArtist(int id);
        void pedirAlbumProfile(int id);
        void pedirAlbumsAlbumProfile(int id);
        void pedirPlaylistProfileTracks(long id);

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
        void refreshPositionFromDeleteMyPlaylist();
        void cargarListaFavTrackReproductor(ContainerTracksFav containerTracksFav);
        void playPause();
    }

    interface Refresh{
        void refreshListFavTracks(ContainerTracksFav containerTracksFav);
        void refreshListPlayList(ContainerMisPlaylist containerMisPlaylist);
    }

}

