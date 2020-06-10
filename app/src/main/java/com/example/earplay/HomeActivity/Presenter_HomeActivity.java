package com.example.earplay.HomeActivity;

import android.content.Context;

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
import com.example.earplay.Core.Utils;

public class Presenter_HomeActivity implements Contract_HomeActivity.Presenter {

    private Contract_HomeActivity.View view;
    private Contract_HomeActivity.Interactor interactor;
    private Context context;

    public Presenter_HomeActivity(Contract_HomeActivity.View view, Context context) {
        this.view = view;
        this.context = context;
        interactor = new Interactor_HomeActivity(this,context);
    }

    @Override
    public void recibirListaDeArtistasRank(ContainerArtistRank containerArtistRankList) {
        if(view != null){
            view.mostrarListaDeArtistasRank(containerArtistRankList);
        }
    }

    @Override
    public void recibirListaDeTracksRank(ContainerTracksRank containerTracksRank) {
        if(view !=null){
            view.mostrarListaDeTracksRank(containerTracksRank);
        }
    }

    @Override
    public void pedirArtistasRankAlInteractor() {
        if (Utils.internetAvalible(context)) {
            interactor.pedirListaDeArtistasRank();
        }else{
            if(view!= null){
                view.mostrarMensajeSinInternet();
            }
        }
    }

    @Override
    public void pedirTracksRankAlInteractor() {
        interactor.pedirListaDeTracksRank();
    }

    @Override
    public void pedirPlaylistRank() {
        interactor.pedirListaPlaylistRank();
    }

    @Override
    public void recibirListaPlaylistRank(ContainerPlaylistRank containerPlaylistRank) {
        if(view != null){
            view.mostrarPlayListRank(containerPlaylistRank);
        }
    }

    @Override
    public void pedirTracksProfileArtist(int id) {
        interactor.pedirTracksProfileArtist(id);
    }

    @Override
    public void recibirTracksProfileArtistDelInteractor(ContainerTracksRank containerTracksArtistProfile) {
        if (view != null) {
            view.mostrarTracksProfileArtist(containerTracksArtistProfile);
        }
    }

    @Override
    public void pedirAlbumsArtistAlInteractor(int id) {
        interactor.pedirAlbumArtist(id);
    }

    @Override
    public void recibirAlbumsArtist(ContainerAlbums containerAlbums) {
        if(view != null){
            view.mostrarAlbumProfileArtis(containerAlbums);
        }
    }

    @Override
    public void pedirAlbumProfile(int id) {
        interactor.pedirAlbumProfile(id);
    }

    @Override
    public void recibirAlbumProfile(ContainerAlbumProfile containerAlbumProfile) {
        if(view != null){
            view.mostrarAlbumAlbumProfile(containerAlbumProfile);
        }
    }

    @Override
    public void pedirAlbumsAlbumProfile(int id) {
        interactor.pedirAlbumsAlbumProfile(id);
    }

    @Override
    public void recibirAlbumsAlbumProfile(ContainerAlbums containerAlbums) {
        if(view != null){
            view.mostrarOtrosAlbumsAlbumProfile(containerAlbums);
        }
    }

    @Override
    public void pedirTracksPlaylistProfile(long id) {
        interactor.pedirPlaylistProfileTracks(id);
    }

    @Override
    public void recibirPlaylistProfileTracks(ContainerPlaylistProfile containerPlaylistProfile) {
        if(view != null){
            view.mostrarPlaylisProfileTracks(containerPlaylistProfile);
        }
    }

    //BUSQUEDAS

    @Override
    public void pedirTrackABuscar(String s) {
        interactor.pedirTrackABuscar(s);
    }

    @Override
    public void pedirAlbumABuscar(String s) {
        interactor.pedirAlbumABuscar(s);
    }

    @Override
    public void pedirArtistaABuscar(String s) {
        interactor.pedirArtistaABuscar(s);
    }

    @Override
    public void recibirTrackABuscar(ContainerTracksRank containerTracksRank) {
        if(view!=null){
            view.mostrarBusquedaTracks(containerTracksRank);
        }
    }

    @Override
    public void recibirAlbumABuscar(ContainerAlbumSearch containerAlbumSearch) {
        if(view != null){
            view.mostrarBusquedaAlbums(containerAlbumSearch);
        }
    }

    @Override
    public void recibirArtistaABuscar(ContainerArtistSearch containerArtistSearch) {
        if(view!=null){
            view.mostrarBusquedaArtistas(containerArtistSearch);
        }

    }

    @Override
    public void pedirListaDeMisPlaylist() {
        interactor.pedirContainerMyPlaylists();
    }

    @Override
    public void pedirListaDeFavTracks() {
        interactor.pedirContainerFavTracks();
    }

    @Override
    public void recibirMisPlaylist(ContainerMisPlaylist containerMisPlaylist) {
        if(view!= null){
            view.mostrarMisPlaylist(containerMisPlaylist);
        }
    }

    @Override
    public void recibirNuevaListDePlaylist(ContainerMisPlaylist containerMisPlaylist) {
        interactor.guardarContainerMyPlaylist(containerMisPlaylist);
    }

    @Override
    public void recibirRsptaGuardadoPlaylist(boolean guardado) {
        if(view!= null){
            view.mostrarRsptaMyPlaylist(guardado);
        }
    }

    @Override
    public void recibirListaDeFavTracks(ContainerTracksFav containerTracksFav) {
        if(view!= null){
            view.mostrarMisTracksFav(containerTracksFav);
        }
    }

    @Override
    public void recibirListaConNuevoTrack(ContainerTracksFav containerTracksFav) {
        interactor.guardarContainerFavTracks(containerTracksFav);
    }

    @Override
    public void recibirMensajeDeError(String s) {
        if(view!= null){
            view.mostrarMensajeDeError(s);
        }
    }

    @Override
    public void recibirMensajeDeActualizacion(String s) {
        if(view!= null){
            view.mostrarMensajeDeActualizacion(s);
        }
    }
}
