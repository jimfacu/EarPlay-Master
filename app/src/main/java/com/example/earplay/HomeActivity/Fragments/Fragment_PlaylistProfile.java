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
import com.example.earplay.HomeActivity.Adapters.Adapter_MyPlaylistPopUp;
import com.example.earplay.HomeActivity.Adapters.Adapter_Tracks_PlaylistProfile;
import com.example.earplay.HomeActivity.Entities.Genericos.TrackGenerico;
import com.example.earplay.HomeActivity.Entities.MisPlaylist.ContainerMisPlaylist;
import com.example.earplay.HomeActivity.Entities.MisPlaylist.TracksDeMiPlaylist;
import com.example.earplay.HomeActivity.Entities.PlaylistProfile.ContainerPlaylistProfile;
import com.example.earplay.HomeActivity.Entities.TracksRank.Track;
import com.example.earplay.R;

import java.util.ArrayList;
import java.util.List;


public class Fragment_PlaylistProfile extends Fragment implements Adapter_Tracks_PlaylistProfile.ShowPlaylists ,Adapter_MyPlaylistPopUp.PlaylistSelected {

    private static final String listPlaylistProfileTracks = "listPlaylistProfileTracks";
    private static final String listMyPlaylist = "listMyPlaylist";

    private ContainerPlaylistProfile containerPlaylistProfileTracks;
    private ContainerMisPlaylist containerMisPlaylist;

    private ImageView imageView_Playlistrofile;
    private TextView textView_cantidadDeCanciones;
    private TextView textView_NamePlaylist;
    private int playlistActual=-1;

    private Dialog myDialog;
    private AppCompatButton btn_aceptAddTrackToPlaylist;
    private AppCompatButton btn_cancelAddTrackToPlaylist;

    private PlaylistProfile_Interface playlistProfileInterface;

    private RecyclerView recyclerView_PlaylistProfile;
    private RecyclerView recyclerView_PopUpMyPlaylist;
    private Adapter_Tracks_PlaylistProfile adapterTracksPlaylistProfile;
    private Adapter_MyPlaylistPopUp adapterMyPlaylistPopUp;




    public static Fragment_PlaylistProfile buildFragmentAlbumProfile(ContainerPlaylistProfile containerPlaylistProfile ,ContainerMisPlaylist containerMisPlaylist) {
        Fragment_PlaylistProfile fragmentPlaylistProfile = new Fragment_PlaylistProfile();
        if ( containerPlaylistProfile!= null) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(listMyPlaylist,containerMisPlaylist);
            bundle.putParcelable(listPlaylistProfileTracks,containerPlaylistProfile);
            fragmentPlaylistProfile.setArguments(bundle);
        }
        return fragmentPlaylistProfile;
    }


    public Fragment_PlaylistProfile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_playlist_profile, container, false);
        initViews (view);
        initAdapter(view);
        Bundle bundle = getArguments();
        if(bundle != null){
            containerPlaylistProfileTracks = bundle.getParcelable(listPlaylistProfileTracks);
            containerMisPlaylist = bundle.getParcelable(listMyPlaylist);
            loadPlaylistProfile(view);
            adapterTracksPlaylistProfile.insetPlaylistTracks(containerPlaylistProfileTracks.getTracks().getData());
        }

        return view;
    }

    private void loadPlaylistProfile(View view) {
        Glide.with(view).load(containerPlaylistProfileTracks.getPicture_big()).into(imageView_Playlistrofile);
        textView_cantidadDeCanciones.setText(String.valueOf(containerPlaylistProfileTracks.getTracks().getData().size()));
        textView_NamePlaylist.setText(containerPlaylistProfileTracks.getTitle());
        myDialog = new Dialog(getContext());
    }

    private void initAdapter(View view) {
        adapterTracksPlaylistProfile = new Adapter_Tracks_PlaylistProfile(getContext(),this);
        @SuppressLint("WrongConstant") LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView_PlaylistProfile.setLayoutManager(linearLayoutManager1);
        recyclerView_PlaylistProfile.setAdapter(adapterTracksPlaylistProfile);
    }

    private void initViews(View view) {
        imageView_Playlistrofile = view.findViewById(R.id.imageView_PlaylistProfile);
        textView_NamePlaylist = view.findViewById(R.id.textView_NamePlaylistProfile);
        textView_cantidadDeCanciones = view.findViewById(R.id.textView_CantidadDeCancionesPlaylistProfile);
        recyclerView_PlaylistProfile = view.findViewById(R.id.recycler_TracksPlaylistProfile);
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

                if(containerMisPlaylist.getMiPlaylists().get(playlistActual).getTracksDeMiPlaylists() != null) {
                    boolean checkTrack = false;
                    for(TrackGenerico track :containerMisPlaylist.getMiPlaylists().get(playlistActual).getTracksDeMiPlaylists()) {
                        if (track.getId()==(tracksDeMiPlaylist.getId())) {
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
                playlistProfileInterface.saveNewSongFromPlaylist(containerMisPlaylist);
                myDialog.dismiss();
            }
        });
        myDialog.show();
    }

    @Override
    public void playTrack(List<TrackGenerico> trackList, int position) {
        playlistProfileInterface.playTrack(trackList,position);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.playlistProfileInterface= (PlaylistProfile_Interface) context;
    }

    @Override
    public void addToPlaylistSelected(int position) {
        playlistActual = position;
    }

    public interface PlaylistProfile_Interface{
        void saveNewSongFromPlaylist(ContainerMisPlaylist containerMisPlaylist);
        void playTrack(List<TrackGenerico> trackGenericoList,int position);
    }
}
