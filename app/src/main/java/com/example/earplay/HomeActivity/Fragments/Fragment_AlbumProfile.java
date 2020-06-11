package com.example.earplay.HomeActivity.Fragments;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.earplay.HomeActivity.Adapters.Adapter_Album_AlbumProfile;
import com.example.earplay.HomeActivity.Adapters.Adapter_Albums_AlbumProfile;
import com.example.earplay.HomeActivity.Adapters.Adapter_MyPlaylistPopUp;
import com.example.earplay.Core.Entities.AlbumProfile.ContainerAlbumProfile;
import com.example.earplay.Core.Entities.AlbumProfile.TracksFromAlbumProfile;
import com.example.earplay.Core.Entities.AlbumsArtist.ContainerAlbums;
import com.example.earplay.Core.Entities.Genericos.AlbumGenerico;
import com.example.earplay.Core.Entities.Genericos.ArtistGenerico;
import com.example.earplay.Core.Entities.Genericos.TrackGenerico;
import com.example.earplay.Core.Entities.MisPlaylist.ContainerMisPlaylist;
import com.example.earplay.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_AlbumProfile extends Fragment implements Adapter_Albums_AlbumProfile.CellListenerAlbumsProfile,Adapter_Album_AlbumProfile.ArtistProfileAdapter_Interface
                                                                ,Adapter_MyPlaylistPopUp.PlaylistSelected{

    private static final String ListAlbumProfile ="listAlbumProfile";
    private static final String ListAlbumsAlbumProfile="listAlbumsAlbumProfile";
    private static final String AlbumNow = "AlbumNow";
    private static final String ArtistNow = "ArtistNow";
    private static final String ListMyPlaylist = "MyPlaylist";

    private ContainerAlbumProfile  containerAlbumProfile;
    private ContainerAlbums containerAlbums;
    private ContainerMisPlaylist tankContainerMisPlaylist;
    private ContainerMisPlaylist containerMisPlaylist;

    private ImageView imageView_AlbumProfile;
    private ImageView imageView_backHome;
    private TextView textView_cantidadDeCanciones;
    private TextView textView_NameAlbum;
    private CardView cardView_random;

    private ArtistGenerico artist;
    private AlbumGenerico album;
    private int playlistActual=-1;


    private RecyclerView recyclerView_TracksAlbumProfile;
    private Adapter_Album_AlbumProfile adapterAlbumProfile;
    private RecyclerView recyclerView_AlbumsAlbumProfile;
    private Adapter_Albums_AlbumProfile adapterAlbumsAlbumProfile;

    private Dialog myDialog;

    private AlbumProfile_Interface albumProfileInterface;

    public static Fragment_AlbumProfile buildFragmentAlbumProfile(ContainerAlbumProfile containerAlbumProfile, ContainerAlbums containerAlbums,
                                                                  AlbumGenerico album, ArtistGenerico artist, ContainerMisPlaylist containerMisPlaylist) {
        Fragment_AlbumProfile fragmentAlbumProfiler = new Fragment_AlbumProfile();
        if (containerAlbumProfile != null && containerAlbums != null && album!= null && artist!= null && containerMisPlaylist != null) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(ListAlbumProfile,containerAlbumProfile);
            bundle.putParcelable(ListAlbumsAlbumProfile,containerAlbums);
            bundle.putParcelable(ListMyPlaylist,containerMisPlaylist);
            bundle.putParcelable(AlbumNow,album);
            bundle.putParcelable(ArtistNow,artist);
            fragmentAlbumProfiler.setArguments(bundle);
        }
        return fragmentAlbumProfiler;
    }


    public Fragment_AlbumProfile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_album_profile, container, false);
        initViews(view);
        myDialog = new Dialog(getContext());
        imageView_backHome.setOnClickListener(backToHomeListener);
        cardView_random.setOnClickListener(randomTracksListener);
        Bundle bundle = getArguments();
        if(bundle != null) {
            containerAlbumProfile = bundle.getParcelable(ListAlbumProfile);
            containerAlbums = bundle.getParcelable(ListAlbumsAlbumProfile);
            containerMisPlaylist = bundle.getParcelable(ListMyPlaylist);
            album = bundle.getParcelable(AlbumNow);
            artist = bundle.getParcelable(ArtistNow);
            initAdapter(view);
            loadInfoAlbumProfile(view);
            adapterAlbumProfile.insertAlbumProfile(containerAlbumProfile.getData());
            adapterAlbumsAlbumProfile.InsertAlbums(containerAlbums.getData());
        }
        if(containerAlbumProfile != null) {
            cardView_random.setOnClickListener(randomTracksListener);
        }
        imageView_backHome.setOnClickListener(backToHomeListener);
        return view;
    }

    private View.OnClickListener backToHomeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            albumProfileInterface.backToHome();
        }
    };

    private void loadInfoAlbumProfile(View view) {
        Glide.with(view).load(album.getCover_medium()).into(imageView_AlbumProfile);
        textView_cantidadDeCanciones.setText(String.valueOf(containerAlbumProfile.getTotal())+" "+getContext().getString(R.string.Canciones));
        textView_NameAlbum.setText(album.getTitle());
    }

    private void initAdapter(View view) {
        adapterAlbumProfile = new Adapter_Album_AlbumProfile(getContext(),this,album,artist);
        @SuppressLint("WrongConstant") LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView_TracksAlbumProfile.setLayoutManager(linearLayoutManager1);
        recyclerView_TracksAlbumProfile.setAdapter(adapterAlbumProfile);

        adapterAlbumsAlbumProfile = new Adapter_Albums_AlbumProfile(this);
        @SuppressLint("WrongConstant") LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView_AlbumsAlbumProfile.setLayoutManager(linearLayoutManager2);
        recyclerView_AlbumsAlbumProfile.setAdapter(adapterAlbumsAlbumProfile);
    }

    private void initViews(View view) {
        imageView_AlbumProfile = view.findViewById(R.id.imageView_AlbumProfile);
        textView_cantidadDeCanciones = view.findViewById(R.id.textView_CantidadDeCanciones);
        textView_NameAlbum = view.findViewById(R.id.textView_NameAlbumProfile);
        imageView_backHome = view.findViewById(R.id.back_FragmentAlbumProfile);
        myDialog = new Dialog(getContext());
        recyclerView_TracksAlbumProfile = view.findViewById(R.id.recycler_TracksAlbumProfile);
        recyclerView_AlbumsAlbumProfile = view.findViewById(R.id.recycler_AlbumsAlbumProfile);
        cardView_random = view.findViewById(R.id.cardView_RandomAlbumProfile);
    }



    @Override
    public void goToMyPlaylists(TrackGenerico tracksDeMiPlaylist) {

        myDialog.setContentView(R.layout.custompopup_add_track_to_playlist);

        RecyclerView recyclerView_PopUpMyPlaylist = myDialog.findViewById(R.id.recyclerView_chosePlaylist);
        AppCompatButton btn_aceptAddTrackToPlaylist = myDialog.findViewById(R.id.btn_AceptarAñadirTrack);
        AppCompatButton btn_cancelAddTrackToPlaylist = myDialog.findViewById(R.id.btn_CancelarAñadirTrack);

        Adapter_MyPlaylistPopUp adapterMyPlaylistPopUp = new Adapter_MyPlaylistPopUp(this);
        @SuppressLint("WrongConstant") LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(myDialog.getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView_PopUpMyPlaylist.setLayoutManager(linearLayoutManager3);
        recyclerView_PopUpMyPlaylist.setAdapter(adapterMyPlaylistPopUp);
        adapterMyPlaylistPopUp.insertMyPlaylist(containerMisPlaylist.getMiPlaylists());

        btn_cancelAddTrackToPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });


        btn_aceptAddTrackToPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tracksDeMiPlaylist.setAlbumGenerico(album);
                tracksDeMiPlaylist.setArtistGenerico(artist);
                if(containerMisPlaylist.getMiPlaylists().get(playlistActual).getTracksDeMiPlaylists() != null) {
                    boolean checkTrack = false;
                    for(TrackGenerico track :containerMisPlaylist.getMiPlaylists().get(playlistActual).getTracksDeMiPlaylists()) {
                        if (track.getId()==(tracksDeMiPlaylist.getId())) {
                            checkTrack = true;
                        }
                    }
                    if (!checkTrack){
                        containerMisPlaylist.getMiPlaylists().get(playlistActual).getTracksDeMiPlaylists().add(tracksDeMiPlaylist);
                        Toast.makeText(getContext(), getContext().getString(R.string.Cancion_añadida_Exitosamente), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getContext(), getContext().getString(R.string.Esta_cancion_ya_se_encuentra_en_esta_playlist), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    List<TrackGenerico> tankTracksDeMiPlaylist = new ArrayList<>();
                    tankTracksDeMiPlaylist.add(tracksDeMiPlaylist);
                    containerMisPlaylist.getMiPlaylists().get(playlistActual).setTracksDeMiPlaylists(tankTracksDeMiPlaylist);
                    Toast.makeText(getContext(), getContext().getString(R.string.Cancion_añadida_Exitosamente), Toast.LENGTH_SHORT).show();
                }
                albumProfileInterface.saveNewSongFromPlaylist(containerMisPlaylist);
                myDialog.dismiss();
            }
        });
        myDialog.show();
    }

    private View.OnClickListener randomTracksListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            List<TrackGenerico> tankTrackGenericoList = new ArrayList<>();
            List<TrackGenerico> trackGenericoList = new ArrayList<>();
            for(TracksFromAlbumProfile track : containerAlbumProfile.getData()){
                TrackGenerico trackGenerico = new TrackGenerico(Integer.parseInt(track.getId()),track.getTitle_short(),track.getPreview(),track.getLink()
                        ,artist,album);
                tankTrackGenericoList.add(trackGenerico);
            }
            Collections.shuffle(tankTrackGenericoList);
            trackGenericoList.addAll(tankTrackGenericoList);
            albumProfileInterface.playTrack(tankTrackGenericoList,0);
        }
    };

    @Override
    public void playTrack(List<TrackGenerico> trackGenericoList, int position) {
        albumProfileInterface.playTrack(trackGenericoList,position);
    }

    @Override
    public void shareTrack(TracksFromAlbumProfile tracksFromAlbumProfile) {
        albumProfileInterface.shareTrack(tracksFromAlbumProfile.getLink());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.albumProfileInterface= (AlbumProfile_Interface) context;
    }

    @Override
    public void goToAlbumProfile(AlbumGenerico albumGenerico) {
        albumProfileInterface.goToAlbumFromAlbumProfile(albumGenerico);
    }

    @Override
    public void addToPlaylistSelected(int position) {
        playlistActual = position;
    }

    public interface AlbumProfile_Interface{
        void goToAlbumFromAlbumProfile(AlbumGenerico albumGenerico);
        void saveNewSongFromPlaylist(ContainerMisPlaylist containerMisPlaylist);
        void playTrack(List<TrackGenerico> trackList ,int position);
        void shareTrack(String s);
        void backToHome();
    }
}
