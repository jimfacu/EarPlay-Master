package com.example.earplay.HomeActivity.Fragments;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.earplay.HomeActivity.Adapters.Adapter_Albums_ArtistProfile;
import com.example.earplay.HomeActivity.Adapters.Adapter_MyPlaylistPopUp;
import com.example.earplay.HomeActivity.Adapters.Adapter_Tracks_ArtistProfile;
import com.example.earplay.HomeActivity.Entities.AlbumsArtist.ContainerAlbums;
import com.example.earplay.HomeActivity.Entities.Genericos.AlbumGenerico;
import com.example.earplay.HomeActivity.Entities.Genericos.ArtistGenerico;
import com.example.earplay.HomeActivity.Entities.Genericos.TrackGenerico;
import com.example.earplay.HomeActivity.Entities.MisPlaylist.ContainerMisPlaylist;
import com.example.earplay.HomeActivity.Entities.MisPlaylist.TracksDeMiPlaylist;
import com.example.earplay.HomeActivity.Entities.TracksRank.Album;
import com.example.earplay.HomeActivity.Entities.TracksRank.ContainerTracksRank;
import com.example.earplay.HomeActivity.Entities.TracksRank.Track;
import com.example.earplay.R;

import java.util.ArrayList;
import java.util.List;

public class Fragment_ProfileArtist extends Fragment implements Adapter_Tracks_ArtistProfile.TracksArtistProfile_Interface,Adapter_MyPlaylistPopUp.PlaylistSelected
                                                                ,Adapter_Albums_ArtistProfile.CellListenerAlbumsProfile{

    private static final String ListTracksArtistProfile="listTracksArtistProfile";
    private static final String ListAlbumsArtistProfile="listAlbumsArtistProfile";
    private static final String ListMyPlaylists = "listMyPlaylists";
    private static final String Artist = "Artist";

    private RecyclerView recyclerViewTracksArtistProfile;
    private RecyclerView recyclerViewAlbumsArtistProfile;
    private RecyclerView recyclerView_PopUpMyPlaylist;
    private Adapter_Tracks_ArtistProfile adapterTracksArtistProfile;
    private Adapter_Albums_ArtistProfile adapterAlbumsArtistProfile;
    private Adapter_MyPlaylistPopUp adapterMyPlaylistPopUp;


    private ArtistGenerico artist;
    private int playlistActual = -1;
    private AppCompatButton btn_aceptAddTrackToPlaylist;
    private AppCompatButton btn_cancelAddTrackToPlaylist;

    private Dialog myDialog;

    private ImageView imageView_photoArtist;
    private TextView textView_nameArtist;

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
        setInformationArtist(view);
        return view;
    }

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
        imageView_photoArtist = view.findViewById(R.id.imageView_PhotoArtistProfile);
    }

    @Override
    public void goToMyPlaylists(TrackGenerico tracksDeMiPlaylist) {
        myDialog.setContentView(R.layout.custompopup_add_track_to_playlist);

        recyclerView_PopUpMyPlaylist = myDialog.findViewById(R.id.recyclerView_chosePlaylist);
        btn_aceptAddTrackToPlaylist = myDialog.findViewById(R.id.btn_AceptarAñadirTrack);
        btn_cancelAddTrackToPlaylist = myDialog.findViewById(R.id.btn_CancelarAñadirTrack);

        adapterMyPlaylistPopUp = new Adapter_MyPlaylistPopUp(this);
        @SuppressLint("WrongConstant") LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(myDialog.getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView_PopUpMyPlaylist.setLayoutManager(linearLayoutManager3);
        recyclerView_PopUpMyPlaylist.setAdapter(adapterMyPlaylistPopUp);
        adapterMyPlaylistPopUp.insertMyPlaylist(containerMisPlaylist.getMiPlaylists());


        btn_aceptAddTrackToPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tracksDeMiPlaylist.setArtistGenerico(artist);
                if(containerMisPlaylist.getMiPlaylists().get(playlistActual).getTracksDeMiPlaylists() != null) {
                    boolean checkTrack = false;
                    for(TrackGenerico track :containerMisPlaylist.getMiPlaylists().get(playlistActual).getTracksDeMiPlaylists()) {
                        if (track.getId()== tracksDeMiPlaylist.getId()) {
                            checkTrack = true;
                        }
                    }
                    if (!checkTrack){
                        containerMisPlaylist.getMiPlaylists().get(playlistActual).getTracksDeMiPlaylists().add(tracksDeMiPlaylist);
                        Toast.makeText(getContext(), "Cancion añadida Exitosamente !", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getContext(), "Esta cancion ya se encuentra en esta playlist", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    List<TrackGenerico> tankTracksDeMiPlaylist = new ArrayList<>();
                    tankTracksDeMiPlaylist.add(tracksDeMiPlaylist);
                    containerMisPlaylist.getMiPlaylists().get(playlistActual).setTracksDeMiPlaylists(tankTracksDeMiPlaylist);
                    Toast.makeText(getContext(), "Cancion añadida Exitosamente !", Toast.LENGTH_SHORT).show();
                }
                profileArtistInterface.saveNewSongFromPlaylist(containerMisPlaylist);
                myDialog.dismiss();
            }
        });
        myDialog.show();
    }

    @Override
    public void playTrack(List<TrackGenerico> trackGenericoList, int position) {
        profileArtistInterface.playTrack(trackGenericoList,position);
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
    }
}
