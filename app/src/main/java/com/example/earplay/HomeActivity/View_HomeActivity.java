package com.example.earplay.HomeActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.earplay.HomeActivity.Entities.AlbumProfile.ContainerAlbumProfile;
import com.example.earplay.HomeActivity.Entities.AlbumSearch.ContainerAlbumSearch;
import com.example.earplay.HomeActivity.Entities.AlbumsArtist.ContainerAlbums;
import com.example.earplay.HomeActivity.Entities.ArtistRank.ContainerArtistRank;
import com.example.earplay.HomeActivity.Entities.ArtistSearch.ContainerArtistSearch;
import com.example.earplay.HomeActivity.Entities.Genericos.AlbumGenerico;
import com.example.earplay.HomeActivity.Entities.Genericos.ArtistGenerico;
import com.example.earplay.HomeActivity.Entities.Genericos.ContainerTracksFav;
import com.example.earplay.HomeActivity.Entities.Genericos.FavTracks;
import com.example.earplay.HomeActivity.Entities.Genericos.TrackGenerico;
import com.example.earplay.HomeActivity.Entities.MisPlaylist.ContainerMisPlaylist;
import com.example.earplay.HomeActivity.Entities.MisPlaylist.Playlist;
import com.example.earplay.HomeActivity.Entities.PlaylistProfile.ContainerPlaylistProfile;
import com.example.earplay.HomeActivity.Entities.PlaylistRank.ContainerPlaylistRank;
import com.example.earplay.HomeActivity.Entities.TracksRank.Album;
import com.example.earplay.HomeActivity.Entities.TracksRank.Artist;
import com.example.earplay.HomeActivity.Entities.TracksRank.ContainerTracksRank;
import com.example.earplay.HomeActivity.Entities.TracksRank.Track;
import com.example.earplay.HomeActivity.Fragments.Fragment_Home;
import com.example.earplay.HomeActivity.Fragments.Fragment_MyPlaylist;
import com.example.earplay.HomeActivity.Fragments.Fragment_PlaylistProfile;
import com.example.earplay.HomeActivity.Fragments.Fragment_ProfileArtist;
import com.example.earplay.HomeActivity.Fragments.Fragment_Reproductor;
import com.example.earplay.HomeActivity.Fragments.Fragment_Search;
import com.example.earplay.HomeActivity.Fragments.Fragment_TracksOfMyPlaylist;
import com.example.earplay.HomeActivity.Fragments.Fragnent_AlbumProfile;
import com.example.earplay.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class View_HomeActivity extends AppCompatActivity implements Contract_HomeActivity.View , Fragment_Home.ArtistProfile,Fragnent_AlbumProfile.AlbumProfile_Interface,Fragment_Search.Abuscar
                                                            ,Fragment_MyPlaylist.AddPlaylist,Fragment_ProfileArtist.ProfileArtist_Interface,Fragment_PlaylistProfile.PlaylistProfile_Interface
                                                            ,Fragment_TracksOfMyPlaylist.TracksMyPlaylis_Interface ,Fragment_Reproductor.ReproductorInterface {

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
    private BottomNavigationView bottomNavigationView;


    private ArtistGenerico artistActual;
    private AlbumGenerico albumActual;
    private List<TrackGenerico> trackGenericoList;
    private int positionActualTrackGenericoList;
    private HashMap<String,String> hp ;
    private int mostrarFragmentHome = 0;
    private boolean mostrarAlbumFragmentAFragment = false;

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
        setContentView(R.layout.activity_home);
        presenter = new Presenter_HomeActivity(this, this);
        init();
        pedirListas();
    }


    public void init() {
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(listenerBottomNavigation);
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
        mostrarFragmentHome++;
        if (mostrarFragmentHome == 3) {
            fragmentHome = Fragment_Home.buildFragmentHome(containerTracksRankV, containerPlaylistRankV, containerArtistRankV);
            mostrarFragmentHome = 0;
            setFragment(fragmentHome);
        }
    }

    @Override
    public void mostrarListaDeTracksRank(ContainerTracksRank containerTracksRank) {
        containerTracksRankV = containerTracksRank;
        mostrarFragmentHome++;
        if (mostrarFragmentHome == 3) {
            fragmentHome = Fragment_Home.buildFragmentHome(containerTracksRankV, containerPlaylistRankV, containerArtistRankV);
            mostrarFragmentHome = 0;
            setFragment(fragmentHome);
        }
    }

    @Override
    public void mostrarPlayListRank(ContainerPlaylistRank containerPlaylistRank) {
        containerPlaylistRankV = containerPlaylistRank;
        mostrarFragmentHome++;
        if (mostrarFragmentHome == 3) {
            fragmentHome = Fragment_Home.buildFragmentHome(containerTracksRankV, containerPlaylistRankV, containerArtistRankV);
            mostrarFragmentHome = 0;
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
    public void mostrarMisPlaylist(ContainerMisPlaylist containerMisPlaylist) {
        containerMisPlaylistGlobal = containerMisPlaylist;
        fragment_myPlaylist = Fragment_MyPlaylist.buildFragmentHome(containerMisPlaylist);
    }

    @Override
    public void mostrarMisTracksFav(ContainerTracksFav containerFavTracks) {
        if (containerFavTracks != null) {
            containerTracksFav = containerFavTracks;
            fragment_reproductor.cargarListaFavTrackReproductor(containerFavTracks);
            for(FavTracks track:containerFavTracks.getFavTracks()){
                hp.put(String.valueOf(track.getId()),String.valueOf(track.getId()));
            }
        }
    }

    @Override
    public void mostrarTracksOfMyPlaylist(Playlist playlist) {
        fragment_tracksOfMyPlaylist = Fragment_TracksOfMyPlaylist.buildFragmentArtistProfile(playlist);
        setFragment(fragment_tracksOfMyPlaylist);
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
                    menuItem.setIcon(R.drawable.ic_home_white_35dp);
                    setFragment(fragmentHome);
                    break;

                case R.id.searchIcon:
                    bottomNavigationView.setItemBackgroundResource(R.drawable.ic_search_white_24dp);
                    setFragment(new Fragment_Search());
                    break;

                case R.id.playlistIcon:
                    bottomNavigationView.setItemBackgroundResource(R.drawable.ic_playlist_play_white_24dp);
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
    public void playTrackSelected(Track track) {

    }

    @Override
    public void playTrack(List<TrackGenerico> trackList, int position) {
        if (trackList != null) {
            trackGenericoList = trackList;
            positionActualTrackGenericoList = position;
            fragment_reproductor = Fragment_Reproductor.buildFragmentReproductor(trackList, position);
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.Container_Reproductor, fragment_reproductor);
            fragmentTransaction.commit();
            fragment_reproductor.cargarListaFavTrackReproductor(containerTracksFav);
        }
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
    public void goToPlaylistRank(int id) {
        presenter.pedirTracksPlaylistProfile(id);
    }

    @Override
    public void goToAlbumFromAlbumProfile(AlbumGenerico albumGenerico) {
        presenter.pedirAlbumProfile(albumGenerico.getId());
        mostrarAlbumFragmentAFragment = true;
        //este null es para que no muestre el primer fragment , sino el mostrar album fragment a fragment
        containerOtrosAlbumsAlbumProfile = null;
        albumActual = albumGenerico;
    }


    @Override
    public void buscar(String categoria, String palabra) {
        switch (categoria) {
            case "Album":
                presenter.pedirAlbumABuscar(palabra);
                break;
            case "Artista":
                presenter.pedirArtistaABuscar(palabra);
                break;
            case "Cancion":
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

    @Override
    public void changeIconFavTrack(boolean inFavTrack) {
        if (inFavTrack) {
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.favoriteIcon:
                            menuItem.setIcon(R.drawable.ic_favorite_black_24dp);
                    }
                    return false;
                }
            };
        } else {
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.favoriteIcon:
                            menuItem.setIcon(R.drawable.ic_favorite_border_black_24dp);
                    }
                    return false;
                }
            };
        }
    }

    @Override
    public void saveTrackToFavTrack(TrackGenerico trackGenerico) {
        FavTracks favTracks = new FavTracks(trackGenerico.getId(),trackGenerico.getTitle_short(),trackGenerico.getPreview(),trackGenerico.getLink(),
                trackGenerico.getArtistGenerico(),trackGenerico.getAlbumGenerico());
        containerTracksFav.getFavTracks().add(favTracks);
        presenter.recibirListaConNuevoTrack(containerTracksFav);
        presenter.pedirListaDeFavTracks();
        Toast.makeText(this, "Cancion agregada a favoritos exitosamente", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void deleteTrackFavTrack(TrackGenerico trackFavDelete) {
        //containerTracksFav.getFavTracks().remove(new FavTracks(trackFavDelete.getId(),trackFavDelete.getTitle_short(),trackFavDelete.getPreview(),
          //      trackFavDelete.getLink(),trackFavDelete.getArtistGenerico(),trackFavDelete.getAlbumGenerico()));
        List<FavTracks> favTracksList= new ArrayList<>();
        List<FavTracks> tankfavTracksList = new ArrayList<>();
        for(FavTracks favTracks :containerTracksFav.getFavTracks()){
            if(favTracks.getId() != trackFavDelete.getId()) {
                tankfavTracksList.add(favTracks);
            }
        }
        favTracksList.addAll(tankfavTracksList);
        containerTracksFav.setFavTracks(tankfavTracksList);
        presenter.recibirListaConNuevoTrack(containerTracksFav);
        presenter.pedirListaDeFavTracks();
        Toast.makeText(this, "Cancion eliminada de favoritos exitosamente", Toast.LENGTH_SHORT).show();
    }
}
