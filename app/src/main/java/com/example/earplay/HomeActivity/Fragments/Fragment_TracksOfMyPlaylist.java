package com.example.earplay.HomeActivity.Fragments;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.earplay.HomeActivity.Adapters.Adapter_MyPlaylist;
import com.example.earplay.HomeActivity.Adapters.Adapter_Tracks_MyPlaylist;
import com.example.earplay.HomeActivity.Entities.Genericos.TrackGenerico;
import com.example.earplay.HomeActivity.Entities.MisPlaylist.Playlist;
import com.example.earplay.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_TracksOfMyPlaylist extends Fragment implements Adapter_Tracks_MyPlaylist.AdapterTracks_Interface {

    private static String PlayList = "PlayList";

    private Playlist playlist;

    private ImageView imageView_TracksOfMyPlaylist;
    private TextView textView_NameTracksOfMyPlaylist;
    private TextView textView_cantidadDeCancionesTracksOfMyPlaylist;

    private RecyclerView recyclerView_TracksOfMyPlaylist;
    private Adapter_Tracks_MyPlaylist adapterTracksMyPlaylist;

    private TracksMyPlaylis_Interface tracksMyPlaylis_interface;

    public static Fragment_TracksOfMyPlaylist buildFragmentArtistProfile(Playlist playlist) {
        Fragment_TracksOfMyPlaylist fragmentTracksOfMyPlaylist = new Fragment_TracksOfMyPlaylist();
        if ( playlist != null) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(PlayList,playlist);
            fragmentTracksOfMyPlaylist.setArguments(bundle);
        }
        return fragmentTracksOfMyPlaylist;
    }


    public Fragment_TracksOfMyPlaylist() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tracks_of_my_playlist, container, false);
        initViews(view);
        initAdapter(view);
        Bundle bundle = getArguments();
        if(bundle!= null){
            playlist = bundle.getParcelable(PlayList);
            loadPlaylistProfile(view);
            adapterTracksMyPlaylist.insertTrackList(playlist.getTracksDeMiPlaylists());
        }

        return view;

    }

    private void initViews(View view) {
        imageView_TracksOfMyPlaylist = view.findViewById(R.id.imageView_TracksOfMyPlaylist);
        textView_NameTracksOfMyPlaylist = view.findViewById(R.id.textView_NameTracksOfMyPlaylist);
        textView_cantidadDeCancionesTracksOfMyPlaylist = view.findViewById(R.id.textView_CantidadDeCancionesTracksOfMyPlaylist);
        recyclerView_TracksOfMyPlaylist = view.findViewById(R.id.recycler_TracksOfMyPlaylist);
    }

    private void initAdapter(View view) {
        adapterTracksMyPlaylist = new Adapter_Tracks_MyPlaylist(this);
        @SuppressLint("WrongConstant") LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView_TracksOfMyPlaylist.setLayoutManager(linearLayoutManager1);
        recyclerView_TracksOfMyPlaylist.setAdapter(adapterTracksMyPlaylist);
    }

    private void loadPlaylistProfile(View view) {
        Glide.with(view).load(playlist.getTracksDeMiPlaylists().get(playlist.getTracksDeMiPlaylists().size()-1)).into(imageView_TracksOfMyPlaylist);
        textView_NameTracksOfMyPlaylist.setText(playlist.getNamePlaylist());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.tracksMyPlaylis_interface= (TracksMyPlaylis_Interface) context;
    }

    @Override
    public void playTrack(List<TrackGenerico> trackGenericoList, int position) {
        tracksMyPlaylis_interface.playTrack(trackGenericoList,position);
    }

    public interface TracksMyPlaylis_Interface{
        void playTrack(List<TrackGenerico> trackGenericoList,int position);
    }
}
