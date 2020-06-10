package com.example.earplay.HomeActivity.Fragments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.earplay.HomeActivity.Adapters.Adapter_Tracks_MyPlaylist;
import com.example.earplay.HomeActivity.Contract_HomeActivity;
import com.example.earplay.Core.Entities.Genericos.ContainerTracksFav;
import com.example.earplay.Core.Entities.Genericos.FavTracks;
import com.example.earplay.Core.Entities.Genericos.TrackGenerico;
import com.example.earplay.Core.Entities.MisPlaylist.ContainerMisPlaylist;
import com.example.earplay.Core.Entities.MisPlaylist.Playlist;
import com.example.earplay.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_TracksOfMyPlaylist extends Fragment implements Adapter_Tracks_MyPlaylist.AdapterTracks_Interface, Contract_HomeActivity.Refresh {

    private static final String PlayList = "PlayList";
    private static final String MyPlaylist = "MyPlaylist";
    private static final String PlaylistFavTracks = "FavTracks";
    private static final String Position = "Position";

    private int positionPlaylist;
    private Playlist playlist;
    private ContainerMisPlaylist containerMisPlaylist;
    private ContainerTracksFav containerTracksFav;

    private ImageView imageView_TracksOfMyPlaylist;
    private TextView textView_NameTracksOfMyPlaylist;
    private ImageView imageView_backHome;
    private CardView cardView_random;

    private boolean tracksFavoritos = false;

    private RecyclerView recyclerView_TracksOfMyPlaylist;
    private Adapter_Tracks_MyPlaylist adapterTracksMyPlaylist;

    private TracksMyPlaylist_Interface tracksMyPlaylist_interface;

    public static Fragment_TracksOfMyPlaylist buildFragmentArtistProfile(Playlist playlist, ContainerMisPlaylist containerMisPlaylist, ContainerTracksFav containerTracksFav,int position) {
        Fragment_TracksOfMyPlaylist fragmentTracksOfMyPlaylist = new Fragment_TracksOfMyPlaylist();
        if ( playlist != null && containerMisPlaylist!= null && containerTracksFav!= null) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(PlayList,playlist);
            bundle.putInt(Position,position);
            bundle.putParcelable(PlaylistFavTracks,containerTracksFav);
            bundle.putParcelable(MyPlaylist,containerMisPlaylist);
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
        imageView_backHome.setOnClickListener(backHomeListener);
        cardView_random.setOnClickListener(randomTracksListener);
        initAdapter(view);
        Bundle bundle = getArguments();
        if(bundle!= null){
            playlist = bundle.getParcelable(PlayList);
            containerMisPlaylist = bundle.getParcelable(MyPlaylist);
            containerTracksFav =  bundle.getParcelable(PlaylistFavTracks);
            positionPlaylist = bundle.getInt(Position);
            loadPlaylistProfile(view);
            adapterTracksMyPlaylist.insertTrackList(playlist.getTracksDeMiPlaylists());
        }
        if(playlist != null) {
            cardView_random.setOnClickListener(randomTracksListener);
        }
        imageView_backHome.setOnClickListener(backHomeListener);
        return view;

    }

    private View.OnClickListener backHomeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            tracksMyPlaylist_interface.backToHome();
        }
    };

    private void initViews(View view) {
        imageView_TracksOfMyPlaylist = view.findViewById(R.id.imageView_TracksOfMyPlaylist);
        textView_NameTracksOfMyPlaylist = view.findViewById(R.id.textView_NameTracksOfMyPlaylist);
        imageView_backHome = view.findViewById(R.id.back_FragmentTracksOfMyPlaylist);
        recyclerView_TracksOfMyPlaylist = view.findViewById(R.id.recycler_TracksOfMyPlaylist);
        cardView_random = view.findViewById(R.id.cardView_RandomTracksOfMyPlaylist);
    }

    private void initAdapter(View view) {
        adapterTracksMyPlaylist = new Adapter_Tracks_MyPlaylist(getContext(),this);
        @SuppressLint("WrongConstant") LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView_TracksOfMyPlaylist.setLayoutManager(linearLayoutManager1);
        recyclerView_TracksOfMyPlaylist.setAdapter(adapterTracksMyPlaylist);
    }

    private void loadPlaylistProfile(View view) {
        if(!playlist.getNamePlaylist().equals(getContext().getString(R.string.FavTracks))){
            Glide.with(view).load(playlist.getTracksDeMiPlaylists().get(playlist.getTracksDeMiPlaylists().size()-1).getAlbumGenerico().getCover_medium()).into(imageView_TracksOfMyPlaylist);
        }else{
            tracksFavoritos = true;
        }
        textView_NameTracksOfMyPlaylist.setText(playlist.getNamePlaylist());
    }

    private View.OnClickListener randomTracksListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            List<TrackGenerico> tracksRandom = playlist.getTracksDeMiPlaylists();
            Collections.shuffle(tracksRandom);
            tracksMyPlaylist_interface.playTrack(tracksRandom,0);
        }
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.tracksMyPlaylist_interface= (TracksMyPlaylist_Interface) context;
    }

    @Override
    public void playTrack(List<TrackGenerico> trackGenericoList, int position) {
        tracksMyPlaylist_interface.playTrack(trackGenericoList,position);
    }

    @Override
    public void deleteTrackFromPlaylist(TrackGenerico trackGenerico,int position) {
        if (tracksFavoritos) {
            containerTracksFav.getFavTracks().remove(position);
            List<FavTracks> favTracksList= new ArrayList<>();
            List<FavTracks> tankfavTracksList = new ArrayList<>();
            for(FavTracks favTracks :containerTracksFav.getFavTracks()){
                    tankfavTracksList.add(favTracks);
            }
            favTracksList.addAll(tankfavTracksList);
            containerTracksFav.setFavTracks(tankfavTracksList);
            tracksMyPlaylist_interface.deleteFavTrackFromProfilePlaylist(containerTracksFav);
        } else {
            if (position >0) {
                playlist.getTracksDeMiPlaylists().remove(position);
                containerMisPlaylist.getMiPlaylists().set(positionPlaylist, playlist);
                tracksMyPlaylist_interface.deleteTrackFromPlaylist(containerMisPlaylist);
            }else{
                Toast.makeText(getContext(), getContext().getString(R.string.La_playlist_no_puede_quedar_vacia), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void shareTrack(TrackGenerico trackGenerico) {
        tracksMyPlaylist_interface.shareTrack(trackGenerico.getLink());
    }

    @Override
    public void refreshListFavTracks(ContainerTracksFav containerTracksFavRefresh) {
        containerTracksFav = containerTracksFavRefresh;
        if (containerTracksFav != null) {
            List<TrackGenerico> trackGenericoList = new ArrayList<>();
            for (FavTracks favTracks : containerTracksFav.getFavTracks()) {
                TrackGenerico trackGenerico = new TrackGenerico(favTracks.getId(), favTracks.getTitle_short(), favTracks.getPreview()
                        , favTracks.getLink(), favTracks.getArtistGenerico(), favTracks.getAlbumGenerico());
                trackGenericoList.add(trackGenerico);
            }
            adapterTracksMyPlaylist.insertTrackList(trackGenericoList);
        }
    }

    @Override
    public void refreshListPlayList(ContainerMisPlaylist containerMisPlaylistRefresh) {
        containerMisPlaylist = containerMisPlaylistRefresh;
        adapterTracksMyPlaylist.insertTrackList(containerMisPlaylist.getMiPlaylists().get(positionPlaylist).getTracksDeMiPlaylists());
    }


    public interface TracksMyPlaylist_Interface{
        void playTrack(List<TrackGenerico> trackGenericoList,int position);
        void deleteTrackFromPlaylist(ContainerMisPlaylist containerMisPlaylist);
        void deleteFavTrackFromProfilePlaylist(ContainerTracksFav containerTracksFav);
        void backToHome();
        void shareTrack(String s);
    }
}
