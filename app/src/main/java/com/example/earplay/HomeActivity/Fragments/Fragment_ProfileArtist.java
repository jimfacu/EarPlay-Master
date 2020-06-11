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
import com.example.earplay.HomeActivity.Adapters.Adapter_Albums_ArtistProfile;
import com.example.earplay.HomeActivity.Adapters.Adapter_MyPlaylistPopUp;
import com.example.earplay.HomeActivity.Adapters.Adapter_Tracks_ArtistProfile;
import com.example.earplay.Core.Entities.AlbumsArtist.ContainerAlbums;
import com.example.earplay.Core.Entities.Genericos.AlbumGenerico;
import com.example.earplay.Core.Entities.Genericos.ArtistGenerico;
import com.example.earplay.Core.Entities.Genericos.TrackGenerico;
import com.example.earplay.Core.Entities.MisPlaylist.ContainerMisPlaylist;
import com.example.earplay.Core.Entities.TracksRank.Album;
import com.example.earplay.Core.Entities.TracksRank.ContainerTracksRank;
import com.example.earplay.Core.Entities.TracksRank.Track;
import com.example.earplay.Core.Constants;
import com.example.earplay.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Fragment_ProfileArtist extends Fragment implements Adapter_Tracks_ArtistProfile.TracksArtistProfile_Interface,Adapter_MyPlaylistPopUp.PlaylistSelected
                                                                ,Adapter_Albums_ArtistProfile.CellListenerAlbumsProfile{

    private static final String ListTracksArtistProfile ="listTracksArtistProfile";
    private static final String ListAlbumsArtistProfile="listAlbumsArtistProfile";
    private static final String ListMyPlaylists = "listMyPlaylists";
    private static final String Artist = "Artist";

    private RecyclerView recyclerViewTracksArtistProfile;
    private RecyclerView recyclerViewAlbumsArtistProfile;
    private Adapter_Tracks_ArtistProfile adapterTracksArtistProfile;
    private Adapter_Albums_ArtistProfile adapterAlbumsArtistProfile;


    private ArtistGenerico artist;
    private int playlistActual = -1;

    private Dialog myDialog;

    private ImageView imageView_photoArtist;
    private TextView textView_nameArtist;
    private ImageView imageView_backHome;
    private CardView cardView_random;

    private ContainerTracksRank containerTracksRank;
    private ContainerAlbums containerAlbumsArtistProfile;
    private ContainerMisPlaylist containerMisPlaylist;

    private ProfileArtist_Interface profileArtistInterface;


    public static Fragment_ProfileArtist buildFragmentArtistProfile(ContainerTracksRank containerTracksArtistProfile,
                                                                    ContainerAlbums containerAlbums, ContainerMisPlaylist containerMisPlaylist, ArtistGenerico artist) {
        Fragment_ProfileArtist fragmentArtistProfile = new Fragment_ProfileArtist();
        if ( containerTracksArtistProfile != null && containerAlbums!= null && containerMisPlaylist != null && artist!=  null) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(ListTracksArtistProfile,containerTracksArtistProfile);
            bundle.putParcelable(ListAlbumsArtistProfile,containerAlbums);
            bundle.putParcelable(ListMyPlaylists,containerMisPlaylist);
            bundle.putParcelable(Artist ,artist);
            fragmentArtistProfile.setArguments(bundle);
        }
        return fragmentArtistProfile;
    }


    public Fragment_ProfileArtist() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.fragment_profile_artist, container, false);
         initViews(view);
         Bundle bundle = getArguments();
         if(bundle != null){
            artist = bundle.getParcelable(Artist);
            containerTracksRank = bundle.getParcelable(ListTracksArtistProfile);
            containerAlbumsArtistProfile = bundle.getParcelable(ListAlbumsArtistProfile);
            containerMisPlaylist = bundle.getParcelable(ListMyPlaylists);
            initAdapters(view);
            adapterTracksArtistProfile.insertTracksArtistProfile(containerTracksRank.getTracks());
            adapterAlbumsArtistProfile.insertAlbumsArtistProfile(containerAlbumsArtistProfile.getData());
        }
        if(containerTracksRank != null) {
            cardView_random.setOnClickListener(randomTracksListener);
        }
        imageView_backHome.setOnClickListener(backHomeListener);
        setInformationArtist(view);

         return view;
    }

    private View.OnClickListener backHomeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            profileArtistInterface.backToHome();
        }
    };

    private void setInformationArtist(View view) {
        Glide.with(view).load(artist.getPicture_big()).into(imageView_photoArtist);
        textView_nameArtist.setText(artist.getName());
        myDialog = new Dialog(getContext());
    }

    private void initAdapters (View view){
        adapterTracksArtistProfile = new Adapter_Tracks_ArtistProfile(getContext(),this,artist);
        @SuppressLint("WrongConstant") LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerViewTracksArtistProfile.setLayoutManager(linearLayoutManager);
        recyclerViewTracksArtistProfile.setAdapter(adapterTracksArtistProfile);

        adapterAlbumsArtistProfile = new Adapter_Albums_ArtistProfile(this);
        @SuppressLint("WrongConstant") LinearLayoutManager linearLayout1 = new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerViewAlbumsArtistProfile.setLayoutManager(linearLayout1);
        recyclerViewAlbumsArtistProfile.setAdapter(adapterAlbumsArtistProfile);
    }
    private void initViews(View view){
        recyclerViewTracksArtistProfile = view.findViewById(R.id.recycler_TracksArtistProfile);
        recyclerViewAlbumsArtistProfile = view.findViewById(R.id.recycler_AlbumsProfileArtist);
        textView_nameArtist = view.findViewById(R.id.textView_NameArtistProfile);
        imageView_backHome = view.findViewById(R.id.back_FragmentArtistProfile);
        imageView_photoArtist = view.findViewById(R.id.imageView_PhotoArtistProfile);
        cardView_random = view.findViewById(R.id.cardView_RandomArtistProfile);
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
                tracksDeMiPlaylist.setArtistGenerico(artist);
                if(playlistActual != -1) {
                    if (containerMisPlaylist.getMiPlaylists().get(playlistActual).getTracksDeMiPlaylists() != null) {
                        boolean checkTrack = false;
                        for (TrackGenerico track : containerMisPlaylist.getMiPlaylists().get(playlistActual).getTracksDeMiPlaylists()) {
                            if (track.getId() == tracksDeMiPlaylist.getId()) {
                                checkTrack = true;
                            }
                        }
                        if (!checkTrack) {
                            containerMisPlaylist.getMiPlaylists().get(playlistActual).getTracksDeMiPlaylists().add(tracksDeMiPlaylist);
                            Toast.makeText(getContext(), getContext().getString(R.string.Cancion_añadida_Exitosamente), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), getContext().getString(R.string.Esta_cancion_ya_se_encuentra_en_esta_playlist), Toast.LENGTH_SHORT).show();
                        }
                        //Else por si en la playlist no hay cancion , creamos e inicializamos una para agregar El track
                    } else {
                        List<TrackGenerico> tankTracksDeMiPlaylist = new ArrayList<>();
                        tankTracksDeMiPlaylist.add(tracksDeMiPlaylist);
                        containerMisPlaylist.getMiPlaylists().get(playlistActual).setTracksDeMiPlaylists(tankTracksDeMiPlaylist);
                        Toast.makeText(getContext(), getContext().getString(R.string.Cancion_añadida_Exitosamente), Toast.LENGTH_SHORT).show();
                    }
                }
                profileArtistInterface.saveNewSongFromPlaylist(containerMisPlaylist);
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
            for(Track track :containerTracksRank.getTracks()){
                AlbumGenerico albumGenerico = new AlbumGenerico(track.getAlbum().getId(),track.getAlbum().getTitle(),track.getAlbum().getCover_medium());
                ArtistGenerico artistGenerico = new ArtistGenerico(track.getArtist().getId(),track.getArtist().getName(),track.getArtist().getPicture_big());
                TrackGenerico trackGenerico = new TrackGenerico(track.getId(),track.getTitle_short(),track.getPreview(),track.getLink()
                        ,artistGenerico,albumGenerico);
                tankTrackGenericoList.add(trackGenerico);
            }
            Collections.shuffle(tankTrackGenericoList);
            trackGenericoList.addAll(tankTrackGenericoList);
            profileArtistInterface.playTrack(tankTrackGenericoList, Constants.Cero);
            }
        };

    @Override
    public void playTrack(List<TrackGenerico> trackGenericoList, int position) {
        profileArtistInterface.playTrack(trackGenericoList,position);
    }

    @Override
    public void shareTrack(Track track) {
        profileArtistInterface.shareTrack(track.getLink());
    }

    @Override
    public void addToPlaylistSelected(int position) {
        playlistActual = position;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.profileArtistInterface= (ProfileArtist_Interface) context;
    }

    @Override
    public void goToAlbumProfile(Album albumGenerico) {
        profileArtistInterface.goToAlbumProfile(albumGenerico,artist);
    }

    public interface ProfileArtist_Interface{
        void saveNewSongFromPlaylist(ContainerMisPlaylist containerMisPlaylist);
        void playTrack(List<TrackGenerico> trackGenericoList,int position);
        void goToAlbumProfile(Album albumGenerico, ArtistGenerico artist);
        void shareTrack(String share);
        void backToHome();
    }
}
