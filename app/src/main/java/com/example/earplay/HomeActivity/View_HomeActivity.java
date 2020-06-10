package com.example.earplay.HomeActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.earplay.DetailActivity.View_DetailActivity;
import com.example.earplay.Core.Entities.AlbumProfile.ContainerAlbumProfile;
import com.example.earplay.Core.Entities.AlbumSearch.ContainerAlbumSearch;
import com.example.earplay.Core.Entities.AlbumsArtist.ContainerAlbums;
import com.example.earplay.Core.Entities.ArtistRank.ContainerArtistRank;
import com.example.earplay.Core.Entities.ArtistSearch.ContainerArtistSearch;
import com.example.earplay.Core.Entities.Genericos.AlbumGenerico;
import com.example.earplay.Core.Entities.Genericos.ArtistGenerico;
import com.example.earplay.Core.Entities.Genericos.ContainerTracksFav;
import com.example.earplay.Core.Entities.Genericos.FavTracks;
import com.example.earplay.Core.Entities.Genericos.TrackGenerico;
import com.example.earplay.Core.Entities.MisPlaylist.ContainerMisPlaylist;
import com.example.earplay.Core.Entities.MisPlaylist.Playlist;
import com.example.earplay.Core.Entities.PlaylistProfile.ContainerPlaylistProfile;
import com.example.earplay.Core.Entities.PlaylistRank.ContainerPlaylistRank;
import com.example.earplay.Core.Entities.TracksRank.Album;
import com.example.earplay.Core.Entities.TracksRank.ContainerTracksRank;
import com.example.earplay.HomeActivity.Fragments.Fragment_Home;
import com.example.earplay.HomeActivity.Fragments.Fragment_MyPlaylist;
import com.example.earplay.HomeActivity.Fragments.Fragment_PlaylistProfile;
import com.example.earplay.HomeActivity.Fragments.Fragment_ProfileArtist;
import com.example.earplay.HomeActivity.Fragments.Fragment_Reproductor;
import com.example.earplay.HomeActivity.Fragments.Fragment_Search;
import com.example.earplay.HomeActivity.Fragments.Fragment_TracksOfMyPlaylist;
import com.example.earplay.HomeActivity.Fragments.Fragnent_AlbumProfile;
import com.example.earplay.HomeActivity.Utils.Constants;
import com.example.earplay.LoginActivity.View_LoginActivity;
import com.example.earplay.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class View_HomeActivity extends AppCompatActivity implements Contract_HomeActivity.View , Fragment_Home.ArtistProfile,Fragnent_AlbumProfile.AlbumProfile_Interface,Fragment_Search.Abuscar
                                                            ,Fragment_MyPlaylist.AddPlaylist,Fragment_ProfileArtist.ProfileArtist_Interface,Fragment_PlaylistProfile.PlaylistProfile_Interface
                                                            ,Fragment_TracksOfMyPlaylist.TracksMyPlaylist_Interface,Fragment_Reproductor.ReproductorInterface {

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private Fragment_Home fragmentHome = new Fragment_Home();
    private Fragment_ProfileArtist fragment_profileArtist = new Fragment_ProfileArtist();
    private Fragnent_AlbumProfile fragnentAlbumProfile = new Fragnent_AlbumProfile();
    private Fragment_PlaylistProfile fragment_playlistProfile = new Fragment_PlaylistProfile();
    private Fragment_Reproductor fragment_reproductor = new Fragment_Reproductor();
    private Fragment_Search fragment_search = new Fragment_Search();
    private Fragment_MyPlaylist fragment_myPlaylist = new Fragment_MyPlaylist();
    private Fragment_TracksOfMyPlaylist fragment_tracksOfMyPlaylist = new Fragment_TracksOfMyPlaylist();


    private ArtistGenerico artistActual;
    private AlbumGenerico albumActual;
    private List<TrackGenerico> trackGenericoList;
    private int positionActualTrackGenericoList;
    private HashMap<String,String> hp ;
    private BottomNavigationView bottomNavigationView;


    private boolean mostrarAlbumFragmentAFragment = false;
    private boolean refreshTracksMyPlaylist = false;
    private boolean refreshFavTracksMyPlaylist=false;

    private Contract_HomeActivity.Presenter presenter;

    private ContainerTracksRank containerTracksRankV;
    private ContainerArtistRank containerArtistRankV;
    private ContainerPlaylistRank containerPlaylistRankV;
    private ContainerMisPlaylist containerMisPlaylistGlobal;
    private ContainerTracksFav containerTracksFav;

    private ContainerAlbumProfile containerTracksAlbumProfile;
    private ContainerAlbums containerOtrosAlbumsAlbumProfile;
    // este tank container es para seguir teniendo el container otros albums album profile e ir de fragment a fragment
    private ContainerAlbums tankContainerOtrosAlbumsAlbumProfile;

    private ContainerAlbums containerAlbumsArtistProfile;
    private ContainerTracksRank containerTracksArtistProfile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_home);
        presenter = new Presenter_HomeActivity(this, this);
        init();
        pedirListas();
    }


    public void init() {
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(listenerBottomNavigation);
        Menu menu = bottomNavigationView.getMenu();
        menu.findItem(R.id.pausePlayIcon).setEnabled(false);
        menu.findItem(R.id.favoriteIcon).setEnabled(false);
        hp= new HashMap<>();
        trackGenericoList = new ArrayList<>();
    }

    private void pedirListas() {
        presenter.pedirArtistasRankAlInteractor();
        presenter.pedirTracksRankAlInteractor();
        presenter.pedirPlaylistRank();
        presenter.pedirListaDeMisPlaylist();
        presenter.pedirListaDeFavTracks();
    }


    @Override
    public void mostrarListaDeArtistasRank(ContainerArtistRank containerArtistRank) {
        containerArtistRankV = containerArtistRank;
        if (containerTracksRankV!= null && containerPlaylistRankV != null) {
            fragmentHome = Fragment_Home.buildFragmentHome(containerTracksRankV, containerPlaylistRankV, containerArtistRankV);
            setFragment(fragmentHome);
        }
    }

    @Override
    public void mostrarListaDeTracksRank(ContainerTracksRank containerTracksRank) {
        containerTracksRankV = containerTracksRank;
        if (containerArtistRankV != null && containerPlaylistRankV != null) {
            fragmentHome = Fragment_Home.buildFragmentHome(containerTracksRankV, containerPlaylistRankV, containerArtistRankV);
            setFragment(fragmentHome);
        }
    }

    @Override
    public void mostrarPlayListRank(ContainerPlaylistRank containerPlaylistRank) {
        containerPlaylistRankV = containerPlaylistRank;
        if (containerArtistRankV != null && containerTracksRankV != null) {
            fragmentHome = Fragment_Home.buildFragmentHome(containerTracksRankV, containerPlaylistRankV, containerArtistRankV);
            setFragment(fragmentHome);
        }
    }

    @Override
    public void mostrarAlbumProfileArtis(ContainerAlbums containerAlbums) {
        if (containerAlbums != null) {
            containerAlbumsArtistProfile = containerAlbums;
            if (containerTracksArtistProfile != null) {
                fragment_profileArtist = Fragment_ProfileArtist.buildFragmentArtistProfile(containerTracksArtistProfile, containerAlbumsArtistProfile, containerMisPlaylistGlobal, artistActual);
                setFragment(fragment_profileArtist);
            }
        }
    }

    @Override
    public void mostrarTracksProfileArtist(ContainerTracksRank containerTracks) {
        if (containerTracks != null) {
            containerTracksArtistProfile = containerTracks;
            if (containerAlbumsArtistProfile != null) {
                fragment_profileArtist = Fragment_ProfileArtist.buildFragmentArtistProfile(containerTracksArtistProfile, containerAlbumsArtistProfile, containerMisPlaylistGlobal, artistActual);
                setFragment(fragment_profileArtist);
            }
        }
    }

    @Override
    public void mostrarAlbumAlbumProfile(ContainerAlbumProfile containerAlbumProfile) {
        if (containerAlbumProfile != null) {
            containerTracksAlbumProfile = containerAlbumProfile;
            if (containerOtrosAlbumsAlbumProfile != null) {
                fragnentAlbumProfile = Fragnent_AlbumProfile.buildFragmentAlbumProfile(containerTracksAlbumProfile, containerOtrosAlbumsAlbumProfile, albumActual, artistActual, containerMisPlaylistGlobal);
                setFragment(fragnentAlbumProfile);
            } else {
                if (mostrarAlbumFragmentAFragment) {
                    mostrarAlbumFragmentAFragment = false;
                    fragnentAlbumProfile = Fragnent_AlbumProfile.buildFragmentAlbumProfile(containerTracksAlbumProfile, tankContainerOtrosAlbumsAlbumProfile, albumActual, artistActual, containerMisPlaylistGlobal);
                    setFragment(fragnentAlbumProfile);
                }
            }
        }
    }

    @Override
    public void mostrarOtrosAlbumsAlbumProfile(ContainerAlbums containerAlbums) {
        if (containerAlbums != null) {
            containerOtrosAlbumsAlbumProfile = containerAlbums;
            tankContainerOtrosAlbumsAlbumProfile = containerAlbums;
            if (containerTracksAlbumProfile != null) {
                fragnentAlbumProfile = Fragnent_AlbumProfile.buildFragmentAlbumProfile(containerTracksAlbumProfile, containerOtrosAlbumsAlbumProfile, albumActual, artistActual, containerMisPlaylistGlobal);
                setFragment(fragnentAlbumProfile);
            }
        }
    }

    @Override
    public void mostrarPlaylisProfileTracks(ContainerPlaylistProfile containerPlaylistProfile) {
        if (containerPlaylistProfile != null) {
            fragment_playlistProfile = Fragment_PlaylistProfile.buildFragmentAlbumProfile(containerPlaylistProfile, containerMisPlaylistGlobal);
            setFragment(fragment_playlistProfile);
        }
    }

    @Override
    public void mostrarBusquedaTracks(ContainerTracksRank containerTracksRank) {
        if (containerTracksRank != null) {
            fragment_search = Fragment_Search.buildFragmentTracks(containerTracksRank);
            setFragment(fragment_search);
        }
    }

    @Override
    public void mostrarBusquedaAlbums(ContainerAlbumSearch containerAlbumSearch) {
        if (containerAlbumSearch != null) {
            fragment_search = Fragment_Search.buildFragmentAlbum(containerAlbumSearch);
            setFragment(fragment_search);
        }
    }

    @Override
    public void mostrarBusquedaArtistas(ContainerArtistSearch containerArtistSearch) {
        if (containerArtistSearch != null) {
            fragment_search = Fragment_Search.buildFragmentArtist(containerArtistSearch);
            setFragment(fragment_search);
        }
    }

    @Override
    public void mostrarRsptaMyPlaylist(boolean guardado) {
        if(!guardado) {
            Toast.makeText(this, getString(R.string.Error_al_guardar_playlist), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void mostrarMisPlaylist(ContainerMisPlaylist containerMisPlaylist) {
        containerMisPlaylistGlobal = containerMisPlaylist;
        if(containerTracksFav != null){
            fragment_myPlaylist = Fragment_MyPlaylist.buildFragmentHome(containerMisPlaylist,containerTracksFav);
            if(refreshTracksMyPlaylist){
                fragment_tracksOfMyPlaylist.refreshListPlayList(containerMisPlaylistGlobal);
                refreshTracksMyPlaylist = false;
            }
        }
    }

    @Override
    public void mostrarMisTracksFav(ContainerTracksFav containerFavTracks) {
        if (containerFavTracks != null) {
            containerTracksFav = containerFavTracks;
            fragment_reproductor.cargarListaFavTrackReproductor(containerFavTracks);
            hp = new HashMap<>();
            for (FavTracks track : containerFavTracks.getFavTracks()) {
                hp.put(String.valueOf(track.getId()), String.valueOf(track.getId()));
            }
            if (containerMisPlaylistGlobal!= null) {
                fragment_myPlaylist = Fragment_MyPlaylist.buildFragmentHome(containerMisPlaylistGlobal, containerTracksFav);
            }
            if (refreshFavTracksMyPlaylist) {
                fragment_tracksOfMyPlaylist.refreshListFavTracks(containerFavTracks);
                refreshFavTracksMyPlaylist = false;
            }
        }
    }

    @Override
    public void mostrarTracksOfMyPlaylist(Playlist playlist,int position) {
        fragment_tracksOfMyPlaylist = Fragment_TracksOfMyPlaylist.buildFragmentArtistProfile(playlist,containerMisPlaylistGlobal,containerTracksFav,position);
        setFragment(fragment_tracksOfMyPlaylist);
    }

    @Override
    public void signOut() {
        FirebaseAuth.getInstance().signOut();
        Intent i = new Intent(this, View_LoginActivity.class);
        startActivity(i);
        finish();
    }

    private void setFragment(Fragment fragment) {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.Container_HomeActivity, fragment);
        fragmentTransaction.commit();
    }


    private BottomNavigationView.OnNavigationItemSelectedListener listenerBottomNavigation = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.homeIcon:
                   // menuItem.setIcon(R.drawable.ic_home_white_35dp);  PREGUNTAR POR ICONOS
                    setFragment(fragmentHome);
                    break;

                case R.id.searchIcon:
                   // bottomNavigationView.setItemBackgroundResource(R.drawable.ic_search_white_24dp);
                    setFragment(new Fragment_Search());
                    break;

                case R.id.playlistIcon:
                   // bottomNavigationView.setItemBackgroundResource(R.drawable.ic_playlist_play_white_24dp);
                    setFragment(fragment_myPlaylist);
                    break;
                case R.id.favoriteIcon:
                    if(String.valueOf(trackGenericoList.get(positionActualTrackGenericoList).getId()).equals(hp.get(String.valueOf(trackGenericoList.get(positionActualTrackGenericoList).getId())))){
                        fragment_reproductor.borrarTrackFavTrack();
                        menuItem.setIcon(R.drawable.ic_favorite_border_black_24dp);
                    }else{
                        fragment_reproductor.pedirTrackReproductor();
                        menuItem.setIcon(R.drawable.ic_favorite_black_24dp);
                    }
                    break;
                case R.id.pausePlayIcon:
                    fragment_reproductor.playPause();
            }
            return false;
        }
    };

    @Override
    public void goToArtistProfile(ArtistGenerico artist) {
        presenter.pedirTracksProfileArtist(artist.getId());
        presenter.pedirAlbumsArtistAlInteractor(artist.getId());
        containerTracksArtistProfile = null;
        containerAlbumsArtistProfile = null;
        artistActual = artist;
    }

    @Override
    public void playTrack(List<TrackGenerico> trackList, int position) {
        if (trackList != null) {
            trackGenericoList = trackList;
            positionActualTrackGenericoList = position;
            fragment_reproductor = Fragment_Reproductor.buildFragmentReproductor(trackList, position,containerTracksFav);
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.Container_Reproductor, fragment_reproductor);
            fragmentTransaction.commit();
            Menu menu = bottomNavigationView.getMenu();
            menu.findItem(R.id.pausePlayIcon).setEnabled(true);
            menu.findItem(R.id.favoriteIcon).setEnabled(true);
        }
    }

    @Override
    public void deleteTrackFromPlaylist(ContainerMisPlaylist containerMisPlaylist) {
        presenter.recibirNuevaListDePlaylist(containerMisPlaylist);
        positionActualTrackGenericoList--;
        fragment_reproductor.refreshPositionFromDeleteMyPlaylist();
        refreshTracksMyPlaylist = true;
    }

    @Override
    public void deleteFavTrackFromProfilePlaylist(ContainerTracksFav containerFavTracks) {
        presenter.recibirListaConNuevoTrack(containerFavTracks);
        presenter.pedirListaDeFavTracks();
        refreshFavTracksMyPlaylist = true;
        fragment_reproductor.refreshPositionFromDeleteMyPlaylist();
    }

    @Override
    public void backToHome() {
        setFragment(fragmentHome);
        presenter.pedirListaDeFavTracks();
        presenter.pedirListaDeMisPlaylist();
    }

    @Override
    public void goToAlbumProfile(Album album, ArtistGenerico artist) {
        presenter.pedirAlbumProfile(album.getId());
        presenter.pedirAlbumsAlbumProfile(artist.getId());
        containerOtrosAlbumsAlbumProfile = null;
        containerTracksAlbumProfile = null;
        mostrarAlbumFragmentAFragment = false;
        AlbumGenerico albumTank = new AlbumGenerico(album.getId(), album.getTitle(), album.getCover_medium());
        albumActual = albumTank;
        artistActual = artist;
    }

    @Override
    public void shareTrack(String share) {
        PackageManager pm=getPackageManager();
        try {
            Intent waIntent = new Intent(Intent.ACTION_SEND);
            waIntent.setType(getString(R.string.text_plain));
            String text = getString(R.string.Escuchate_este_temaiken)+share;

            PackageInfo info= pm.getPackageInfo(Constants.PackageWhatsapp, PackageManager.GET_META_DATA);
            waIntent.setPackage(Constants.PackageWhatsapp);

            waIntent.putExtra(Intent.EXTRA_TEXT, text);
            startActivity(Intent.createChooser(waIntent, getString(R.string.Share_with)));
        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(this, R.string.WhatsApp_not_Installed, Toast.LENGTH_SHORT)
                    .show();
        }
    };

    @Override
    public void goToPlaylistRank(long id) {
        presenter.pedirTracksPlaylistProfile(id);
    }

    @Override
    public void goToAlbumFromAlbumProfile(AlbumGenerico albumGenerico) {
        presenter.pedirAlbumProfile(albumGenerico.getId());
        mostrarAlbumFragmentAFragment = true;
        //este null es para no mostrar el primer fragment , sino el mostrar album fragment a fragment
        containerOtrosAlbumsAlbumProfile = null;
        albumActual = albumGenerico;
    }


    @Override
    public void buscar(String categoria, String palabra) {
        switch (categoria) {
            case Constants.Album:
                presenter.pedirAlbumABuscar(palabra);
                break;
            case Constants.Artista:
                presenter.pedirArtistaABuscar(palabra);
                break;
            case Constants.Cancion:
                presenter.pedirTrackABuscar(palabra);
                break;
        }
    }

    @Override
    public void savePlaylistOnFirebase(ContainerMisPlaylist containerMisPlaylist) {
        presenter.recibirNuevaListDePlaylist(containerMisPlaylist);
    }


    @Override
    public void saveNewSongFromPlaylist(ContainerMisPlaylist containerMisPlaylist) {
        presenter.recibirNuevaListDePlaylist(containerMisPlaylist);
        presenter.pedirListaDeMisPlaylist();
    }

    @SuppressLint("ResourceType")
    @Override
    public void changeIconFavTrack(boolean inFavTrack) {
        if (inFavTrack) {
            Menu menu = bottomNavigationView.getMenu();
            menu.findItem(R.id.favoriteIcon).setIcon(R.drawable.ic_favorite_black_24dp);
        } else {
            Menu menu = bottomNavigationView.getMenu();
         menu.findItem(R.id.favoriteIcon).setIcon(R.drawable.ic_favorite_border_black_24dp);
        }
    }

    @Override
    public void saveTrackToFavTrack(ContainerTracksFav containerFavTracks) {
        presenter.recibirListaConNuevoTrack(containerFavTracks);
        presenter.pedirListaDeFavTracks();
    }

    @Override
    public void deleteTrackFavTrack(ContainerTracksFav containerFavTracks) {
        presenter.recibirListaConNuevoTrack(containerFavTracks);
        presenter.pedirListaDeFavTracks();
    }

    @Override
    public void posicionActual(int position) {
        positionActualTrackGenericoList = position;
    }

    @Override
    public void isPlaying(boolean isplaying) {
        if(isplaying){
            Menu menu = bottomNavigationView.getMenu();
            menu.findItem(R.id.pausePlayIcon).setIcon(R.drawable.ic_pause_black_24dp);
        }else{
            Menu menu = bottomNavigationView.getMenu();
            menu.findItem(R.id.pausePlayIcon).setIcon(R.drawable.ic_baseline_play_arrow_24);
        }
    }

    @Override
    public void goToDetailActivity(List<TrackGenerico> list, int position) {
        Intent i = new Intent(this, View_DetailActivity.class);
        Bundle bundleActivity = new Bundle();
        bundleActivity.putParcelableArrayList(Constants.TrackList , (ArrayList<? extends Parcelable>) list);
        bundleActivity.putInt(Constants.Position,position);
        i.putExtras(bundleActivity);
        removeFragment();
        startActivityForResult(i,Constants.Uno);
    }
    private void removeFragment(){
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(fragment_reproductor);
        fragmentTransaction.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if(resultCode == View_HomeActivity.RESULT_OK){
                trackGenericoList = new ArrayList<>();
                trackGenericoList = data.getParcelableArrayListExtra(Constants.TrackList);
                positionActualTrackGenericoList = data.getIntExtra(Constants.Position,Constants.Cero);
                String mod = data.getStringExtra(Constants.Mod);
                presenter.pedirListaDeFavTracks();
                switch (mod){
                    case Constants.Normal:
                        this.playTrack(trackGenericoList,positionActualTrackGenericoList);
                        break;
                    case Constants.ArtistProfile:
                        this.goToArtistProfile(trackGenericoList.get(positionActualTrackGenericoList).getArtistGenerico());
                        this.playTrack(trackGenericoList,positionActualTrackGenericoList);
                        break;
                    case Constants.AlbumProfile:
                        Album album = new Album(trackGenericoList.get(positionActualTrackGenericoList).getAlbumGenerico().getId(),trackGenericoList.get(positionActualTrackGenericoList).getAlbumGenerico().getTitle(),
                                trackGenericoList.get(positionActualTrackGenericoList).getAlbumGenerico().getCover_medium());
                        this.goToAlbumProfile(album,
                                trackGenericoList.get(positionActualTrackGenericoList).getArtistGenerico());
                        this.playTrack(trackGenericoList,positionActualTrackGenericoList);
                        break;
                }
            }
            if (resultCode == View_HomeActivity.RESULT_CANCELED) {
                Toast.makeText(this, getString(R.string.Ocurrio_un_error), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void mostrarMensajeDeError(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void mostrarMensajeDeActualizacion(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void mostrarMensajeSinInternet() {
        Toast.makeText(this, getString(R.string.PorFavor_Conectarse_A_Internet), Toast.LENGTH_SHORT).show();
    }



}
