package com.example.earplay.HomeActivity;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.earplay.Core.Entities.AlbumProfile.ContainerAlbumProfile;
import com.example.earplay.Core.Entities.AlbumSearch.ContainerAlbumSearch;
import com.example.earplay.Core.Entities.AlbumsArtist.ContainerAlbums;
import com.example.earplay.Core.Entities.ArtistRank.ContainerArtistRank;
import com.example.earplay.Core.Entities.ArtistSearch.ContainerArtistSearch;
import com.example.earplay.Core.Entities.Genericos.ContainerTracksFav;
import com.example.earplay.Core.Entities.Genericos.FavTracks;
import com.example.earplay.Core.Entities.MisPlaylist.ContainerMisPlaylist;
import com.example.earplay.Core.Entities.MisPlaylist.Playlist;
import com.example.earplay.Core.Entities.PlaylistProfile.ContainerPlaylistProfile;
import com.example.earplay.Core.Entities.PlaylistRank.ContainerPlaylistRank;
import com.example.earplay.Core.Entities.TracksRank.ContainerTracksRank;
import com.example.earplay.HomeActivity.Utils.ServiceApi_HomeActivity;
import com.example.earplay.HomeActivity.Utils.ServiceRetrofit_HomeActivity;
import com.example.earplay.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Interactor_HomeActivity implements Contract_HomeActivity.Interactor {

    private static final String Users ="Users";
    private static final String MyPlaylists = "myPlaylists";
    private static final String TracksFavoritos = "favoriteTracks";

    private Contract_HomeActivity.Presenter presenter;
    private Context context;
    private ServiceApi_HomeActivity serviceApi;


    public Interactor_HomeActivity(Contract_HomeActivity.Presenter presenter, Context context) {
        this.presenter = presenter;
        this.context = context;
        serviceApi = ServiceRetrofit_HomeActivity.getInstance().create(ServiceApi_HomeActivity.class);
    }

    @Override
    public void pedirListaDeArtistasRank() {
        Call<ContainerArtistRank> call = serviceApi.getListArtistRank();
        call.enqueue(new Callback<ContainerArtistRank>() {
            @Override
            public void onResponse(Call<ContainerArtistRank> call, Response<ContainerArtistRank> response) {
                if(response.isSuccessful()){
                    presenter.recibirListaDeArtistasRank(response.body());
                }else{
                    presenter.recibirMensajeDeError(context.getString(R.string.ErrorArtistasRankeados));
                }
            }
            @Override
            public void onFailure(Call<ContainerArtistRank> call, Throwable t) {
                presenter.recibirMensajeDeError(context.getString(R.string.ErrorRetrofitArtistasRankeados));
            }
        });

    }

    @Override
    public void pedirListaDeTracksRank() {
        Call<ContainerTracksRank> call = serviceApi.getListTracksRank();
        call.enqueue(new Callback<ContainerTracksRank>() {
            @Override
            public void onResponse(Call<ContainerTracksRank> call, Response<ContainerTracksRank> response) {
                if(response.isSuccessful()){
                    presenter.recibirListaDeTracksRank(response.body());
                }
                else{
                    presenter.recibirMensajeDeError(context.getString(R.string.Error_Canciones_Rankeadas));
                }
            }

            @Override
            public void onFailure(Call<ContainerTracksRank> call, Throwable t) {
                presenter.recibirMensajeDeError(context.getString(R.string.Error_Retrofit_Canciones_Rankeadas));
            }
        });
    }
    @Override
    public void pedirListaPlaylistRank() {
        Call<ContainerPlaylistRank> call = serviceApi.getListPlaylistRank();
        call.enqueue(new Callback<ContainerPlaylistRank>() {
            @Override
            public void onResponse(Call<ContainerPlaylistRank> call, Response<ContainerPlaylistRank> response) {
                if(response.isSuccessful()){
                    presenter.recibirListaPlaylistRank(response.body());
                }else{
                    presenter.recibirMensajeDeError(context.getString(R.string.Error_Playlist_Rankeadas));
                }
            }
            @Override
            public void onFailure(Call<ContainerPlaylistRank> call, Throwable t) {
                presenter.recibirMensajeDeError(context.getString(R.string.Error_Retrofit_PlaylistRank));
            }
        });
    }

    @Override
    public void pedirTracksProfileArtist(int id) {
        Call<ContainerTracksRank> call = serviceApi.getTracksArtistProfile(id,10);
        call.enqueue(new Callback<ContainerTracksRank>() {
            @Override
            public void onResponse(Call<ContainerTracksRank> call, Response<ContainerTracksRank> response) {
                if(response.isSuccessful()){
                    presenter.recibirTracksProfileArtistDelInteractor(response.body());
                }else{
                    presenter.recibirMensajeDeError(context.getString(R.string.Error_Canciones_Artista));
                }
            }

            @Override
            public void onFailure(Call<ContainerTracksRank> call, Throwable t) {
                presenter.recibirMensajeDeError(context.getString(R.string.Error_Retrofit_CancionesArtista));
            }
        });
    }

    @Override
    public void pedirAlbumArtist(int id) {
        Call<ContainerAlbums> call = serviceApi.getAlbumsArtist(id);
        call.enqueue(new Callback<ContainerAlbums>() {
            @Override
            public void onResponse(Call<ContainerAlbums> call, Response<ContainerAlbums> response) {
                if(response.isSuccessful()){
                    presenter.recibirAlbumsArtist(response.body());
                }else{
                    presenter.recibirMensajeDeError(context.getString(R.string.Error_AlbumsArtistas));
                }
            }
            @Override
            public void onFailure(Call<ContainerAlbums> call, Throwable t) {
                presenter.recibirMensajeDeError(context.getString(R.string.Error_Retrofit_AlbumsArtista));
            }
        });
    }

    @Override
    public void pedirAlbumProfile(int id) {
        Call<ContainerAlbumProfile> call = serviceApi.getAlbumProfile(id);
        call.enqueue(new Callback<ContainerAlbumProfile>() {
            @Override
            public void onResponse(Call<ContainerAlbumProfile> call, Response<ContainerAlbumProfile> response) {
                if(response.isSuccessful()){
                    presenter.recibirAlbumProfile(response.body());
                }else{
                    presenter.recibirMensajeDeError(context.getString(R.string.Error_AlbumProfile));
                }
            }

            @Override
            public void onFailure(Call<ContainerAlbumProfile> call, Throwable t) {
                presenter.recibirMensajeDeError(context.getString(R.string.Error_Retrofit_AlbumProfile));
            }
        });
    }

    @Override
    public void pedirAlbumsAlbumProfile(int id) {
        Call<ContainerAlbums> call = serviceApi.getAlbumsArtist(id);
        call.enqueue(new Callback<ContainerAlbums>() {
            @Override
            public void onResponse(Call<ContainerAlbums> call, Response<ContainerAlbums> response) {
                if(response.isSuccessful()){
                    presenter.recibirAlbumsAlbumProfile(response.body());
                }else{
                    presenter.recibirMensajeDeError(context.getString(R.string.Error_Albums));
                }
            }

            @Override
            public void onFailure(Call<ContainerAlbums> call, Throwable t) {
                presenter.recibirMensajeDeError(context.getString(R.string.Error_Retrofit_Albums));
            }
        });
    }

    @Override
    public void pedirPlaylistProfileTracks(long id) {
        Call<ContainerPlaylistProfile> call = serviceApi.getPlaylistProfileTracks(id);
        call.enqueue(new Callback<ContainerPlaylistProfile>() {
            @Override
            public void onResponse(Call<ContainerPlaylistProfile> call, Response<ContainerPlaylistProfile> response) {
                if(response.isSuccessful()){
                    presenter.recibirPlaylistProfileTracks(response.body());
                }else{
                    presenter.recibirMensajeDeError(context.getString(R.string.Error_CancionesdePlaylist));
                }
            }

            @Override
            public void onFailure(Call<ContainerPlaylistProfile> call, Throwable t) {
                presenter.recibirMensajeDeError(context.getString(R.string.Error_Retrofit_CancionesPlaylist));
            }
        });
    }

    //Busquedas

    @Override
    public void pedirTrackABuscar(String s) {
        Call<ContainerTracksRank> call = serviceApi.getTracksSearch(s);
        call.enqueue(new Callback<ContainerTracksRank>() {
            @Override
            public void onResponse(Call<ContainerTracksRank> call, Response<ContainerTracksRank> response) {
                if(response.isSuccessful()){
                    presenter.recibirTrackABuscar(response.body());
                }else{
                    presenter.recibirMensajeDeError(context.getString(R.string.Error_CancionesSearch));
                }
            }

            @Override
            public void onFailure(Call<ContainerTracksRank> call, Throwable t) {
                presenter.recibirMensajeDeError(context.getString(R.string.Error_Retrofit_CancionesSearch));
            }
        });

    }

    @Override
    public void pedirAlbumABuscar(String s) {
        Call<ContainerAlbumSearch> call = serviceApi.getAlbumsSearch(s);
        call.enqueue(new Callback<ContainerAlbumSearch>() {
            @Override
            public void onResponse(Call<ContainerAlbumSearch> call, Response<ContainerAlbumSearch> response) {
                if(response.isSuccessful()){
                    presenter.recibirAlbumABuscar(response.body());
                }else{
                    presenter.recibirMensajeDeError(context.getString(R.string.Error_AlbumsSearch));
                }
            }

            @Override
            public void onFailure(Call<ContainerAlbumSearch> call, Throwable t) {
                presenter.recibirMensajeDeError(context.getString(R.string.Error_Retrofit_AlbumsSearch));
            }
        });

    }

    @Override
    public void pedirArtistaABuscar(String s) {
        Call<ContainerArtistSearch> call = serviceApi.getArtistSearch(s);
        call.enqueue(new Callback<ContainerArtistSearch>() {
            @Override
            public void onResponse(Call<ContainerArtistSearch> call, Response<ContainerArtistSearch> response) {
                if(response.isSuccessful()){
                    presenter.recibirArtistaABuscar(response.body());
                }else{
                    presenter.recibirMensajeDeError(context.getString(R.string.Error_ArtistaSearch));
                }
            }
            @Override
            public void onFailure(Call<ContainerArtistSearch> call, Throwable t) {
                presenter.recibirMensajeDeError(context.getString(R.string.Error_Retrofit_ArtistasSearch));
            }
        });

    }

    @Override
    public void pedirContainerMyPlaylists() {
        List<Playlist> playlistList = new ArrayList<>();
        ContainerMisPlaylist containerMisPlaylist = new ContainerMisPlaylist();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference(Users)
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(MyPlaylists);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                playlistList.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Playlist playlist = ds.getValue(Playlist.class);
                    playlistList.add(playlist);
                }
                containerMisPlaylist.setMiPlaylists(playlistList);
                presenter.recibirMisPlaylist(containerMisPlaylist);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                presenter.recibirMensajeDeError(context.getString(R.string.Error_Firebase_MisPlaylist));
            }
        });

    }

    @Override
    public void guardarContainerMyPlaylist(ContainerMisPlaylist containerMisPlaylist) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference(Users)
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(MyPlaylists);
        reference.setValue(containerMisPlaylist.getMiPlaylists())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            presenter.recibirRsptaGuardadoPlaylist(true);
                        }else{
                            presenter.recibirRsptaGuardadoPlaylist(false);
                        }
                    }
                });
    }

    @Override
    public void pedirContainerFavTracks() {
        List<FavTracks> favTrackstList = new ArrayList<>();
        ContainerTracksFav containerTracksFav = new ContainerTracksFav();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference(Users)
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(TracksFavoritos);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                favTrackstList.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    FavTracks favTracks = ds.getValue(FavTracks.class);
                    favTrackstList.add(favTracks);
                }
                containerTracksFav.setFavTracks(favTrackstList);
                presenter.recibirListaDeFavTracks(containerTracksFav);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                presenter.recibirMensajeDeError(context.getString(R.string.Error_Firebase_CancionesFavoritas));
            }
        });
    }

    @Override
    public void guardarContainerFavTracks(ContainerTracksFav containerTracksFav) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference(Users)
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(TracksFavoritos);
        reference.setValue(containerTracksFav.getFavTracks())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            presenter.recibirMensajeDeActualizacion(context.getString(R.string.Cancion_agregada_eliminada_a_favoritos_exitosamente));
                        }else{
                            presenter.recibirMensajeDeError(context.getString(R.string.Error_Firebase_GuardarCancionesFavoritas));
                        }
                    }
                });
    }
}
