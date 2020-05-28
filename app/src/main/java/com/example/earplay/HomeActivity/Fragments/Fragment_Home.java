package com.example.earplay.HomeActivity.Fragments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.earplay.HomeActivity.Adapters.Adapter_Artistas_HomeProfile;
import com.example.earplay.HomeActivity.Adapters.Adapter_Playlist_HomeProfile;
import com.example.earplay.HomeActivity.Adapters.Adapter_Tracks_HomeProfile;
import com.example.earplay.HomeActivity.Entities.ArtistRank.ContainerArtistRank;
import com.example.earplay.HomeActivity.Entities.Genericos.ArtistGenerico;
import com.example.earplay.HomeActivity.Entities.PlaylistRank.ContainerPlaylistRank;
import com.example.earplay.HomeActivity.Entities.TracksRank.Album;
import com.example.earplay.HomeActivity.Entities.TracksRank.Artist;
import com.example.earplay.HomeActivity.Entities.TracksRank.ContainerTracksRank;
import com.example.earplay.HomeActivity.Entities.TracksRank.Track;
import com.example.earplay.R;

public class Fragment_Home extends Fragment implements Adapter_Tracks_HomeProfile.CellListenerTracksRank , Adapter_Playlist_HomeProfile.CellListenerPlaylistRank , Adapter_Artistas_HomeProfile.CellListenerArtistRank  {

    private static final String ListTracksRank="listTracksRank";
    private static final String ListArtistRank="listArtistRank";
    private static final String ListPlaylistRank="listPlaylistRank";

    private Adapter_Tracks_HomeProfile adapterTracksRank;
    private Adapter_Artistas_HomeProfile adapterArtistRank;
    private Adapter_Playlist_HomeProfile adapterPlaylistRank;

    private RecyclerView recyclerViewTracksRank;
    private RecyclerView recyclerViewArtist;
    private RecyclerView recyclerViewPlaylistRank;

    private ContainerPlaylistRank containerPlaylistRank;
    private ContainerArtistRank containerArtistRank;
    private ContainerTracksRank containerTracksRank ;

    private ArtistProfile artistProfile;

    public static Fragment_Home buildFragmentHome(ContainerTracksRank containerTracksRank,ContainerPlaylistRank containerPlaylistRank,ContainerArtistRank containerArtistRank) {
        Fragment_Home fragmentHome = new Fragment_Home();
        if (containerPlaylistRank != null & containerArtistRank != null & containerTracksRank != null) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(ListTracksRank, containerTracksRank);
            bundle.putParcelable(ListPlaylistRank,containerPlaylistRank);
            bundle.putParcelable(ListArtistRank,containerArtistRank);
            fragmentHome.setArguments(bundle);
        }
        return fragmentHome;
    }

    public Fragment_Home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view =inflater.inflate(R.layout.fragment_fragment__home, container, false);
        recyclerViewTracksRank = view.findViewById(R.id.recycler_Tracks);
        recyclerViewArtist = view.findViewById(R.id.recycler_Artistas);
        recyclerViewPlaylistRank = view.findViewById(R.id.recycler_PlaylistRank);
       initAdapters(view);
       Bundle bundle = getArguments();
       if(bundle != null) {
           containerArtistRank = bundle.getParcelable(ListArtistRank);
           containerTracksRank = bundle.getParcelable(ListTracksRank);
           containerPlaylistRank = bundle.getParcelable(ListPlaylistRank);
           adapterArtistRank.insertListArtists(containerArtistRank.getData());
           adapterPlaylistRank.insertPlaylistRank(containerPlaylistRank.getData());
           adapterTracksRank.insertListTracks(containerTracksRank.getTracks());
       }
       return view;
    }



    private void initAdapters(View view){
        adapterPlaylistRank = new Adapter_Playlist_HomeProfile(this);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(view.getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerViewPlaylistRank.setLayoutManager(linearLayoutManager2);
        recyclerViewPlaylistRank.setAdapter(adapterPlaylistRank);

        adapterArtistRank = new Adapter_Artistas_HomeProfile(this);
        @SuppressLint("WrongConstant") LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewArtist.setLayoutManager(linearLayoutManager);
        recyclerViewArtist.setAdapter(adapterArtistRank);

        adapterTracksRank = new Adapter_Tracks_HomeProfile(this);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewTracksRank.setLayoutManager(linearLayoutManager1);
        recyclerViewTracksRank.setAdapter(adapterTracksRank);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.artistProfile = (ArtistProfile) context;
    }




    //Interfaces de los adapters
    @Override
    public void play(Track track) {
        artistProfile.playTrackSelected(track);
    }

    @Override
    public void goToAlbum(Album album,ArtistGenerico artist) {
        artistProfile.goToAlbumProfile(album,artist);
    }

    @Override
    public void goToPlaylist(int id) {
        artistProfile.goToPlaylistRank(id);
    }

    @Override
    public void goToArtistProfile(ArtistGenerico artist) {
        artistProfile.goToArtistProfile(artist);
    }

    public interface ArtistProfile{
        void goToArtistProfile(ArtistGenerico artist);
        void playTrackSelected(Track track);
        void goToAlbumProfile(Album album,ArtistGenerico artist);
        void goToPlaylistRank(int id);
    }
}
