package com.example.earplay.HomeActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

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
import com.example.earplay.R;

import java.util.List;

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
        if (internetAvalible()) {
            interactor.pedirListaDeArtistasRank();
        }else{
            Toast.makeText(context, context.getString(R.string.PorFavor_Conectarse_A_Internet), Toast.LENGTH_SHORT).show();
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
    public void pedirTracksPlaylistProfile(int id) {
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

    private boolean internetAvalible(){
        boolean connected;
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            connected = true;
        }else{
            connected = false;
        }
        return connected;
    }
}
