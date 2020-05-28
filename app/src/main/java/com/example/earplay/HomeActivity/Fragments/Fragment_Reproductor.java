package com.example.earplay.HomeActivity.Fragments;


import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.earplay.HomeActivity.Contract_HomeActivity;
import com.example.earplay.HomeActivity.Entities.Genericos.ContainerTracksFav;
import com.example.earplay.HomeActivity.Entities.Genericos.FavTracks;
import com.example.earplay.HomeActivity.Entities.Genericos.TrackGenerico;
import com.example.earplay.HomeActivity.Entities.TracksRank.Track;
import com.example.earplay.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Reproductor extends Fragment implements Contract_HomeActivity.FavTrack {

    private static final String TrackList = "TrackList";
    private static final String Position = "Position";
    private static final String FavTrackList="FavTrackList";



    private List<TrackGenerico> trackList;
    private ContainerTracksFav containerTracksFav;
    private HashMap<String,String> hashMapTracksFav ;
    private int position;

    private ImageButton btn_NextSong;
    private ImageButton btn_PreviousSong;
    private TextView textViewSongNamePlay;
    private TextView textViewArtistNamePlay;
    private ImageView imageViewSongPlay;
    private int segundaVez =0;

    private ReproductorInterface reproductorInterface;

    private View viewGlobal;

    private MediaPlayer mediaPlayer = new MediaPlayer();



    public static Fragment_Reproductor buildFragmentReproductor(List<TrackGenerico> trackList ,int position) {
        Fragment_Reproductor fragmentReproductor = new Fragment_Reproductor();
        if (trackList != null  ) {
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(TrackList , (ArrayList<? extends Parcelable>) trackList);
            bundle.putInt(Position,position);
            fragmentReproductor.setArguments(bundle);
        }
        return fragmentReproductor;
    }


    public Fragment_Reproductor() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reproductor, container, false);
        viewGlobal = view;
        initViews(view);
        Bundle bundle = getArguments();
        if(bundle != null) {
            trackList = bundle.getParcelableArrayList(TrackList);
            setHaspMap(containerTracksFav);
            position = bundle.getInt(Position);
            loadTrackInformation(view);
            btn_NextSong.setOnClickListener(nextListener);
            btn_PreviousSong.setOnClickListener(previousListener);
            if (trackList.size() > 0) {
                try {
                    mediaPlayer.setDataSource(trackList.get(position).getPreview());
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }



    private void loadTrackInformation(View view) {
        textViewSongNamePlay.setText(trackList.get(position).getTitle_short());
        textViewArtistNamePlay.setText(trackList.get(position).getArtistGenerico().getName());
        Glide.with(view).load(trackList.get(position).getAlbumGenerico().getCover_medium()).into(imageViewSongPlay);
    }

    private void initViews(View view) {
        textViewArtistNamePlay = view.findViewById(R.id.textView_ArtistNameReproductor);
        textViewSongNamePlay = view.findViewById(R.id.textView_TrackNameReproductor);
        imageViewSongPlay = view.findViewById(R.id.imageView_TrackReproductor);
        btn_NextSong = view.findViewById(R.id.button_nextSong);
        btn_PreviousSong = view.findViewById(R.id.button_previousSong);
        hashMapTracksFav = new HashMap<>();
    }

    View.OnClickListener nextListener= new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (position < trackList.size()-1) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    position++;
                    textViewSongNamePlay.setText(trackList.get(position).getTitle_short());
                    textViewArtistNamePlay.setText(trackList.get(position).getArtistGenerico().getName());
                    Glide.with(viewGlobal).load(trackList.get(position).getAlbumGenerico().getCover_medium()).into(imageViewSongPlay);
                    Uri uri = Uri.parse(trackList.get(position).getPreview());
                    mediaPlayer = MediaPlayer.create(getContext(), uri);
                    mediaPlayer.start();
                }
            } else {
                position = 0;
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    textViewSongNamePlay.setText(trackList.get(position).getTitle_short());
                    textViewArtistNamePlay.setText(trackList.get(position).getArtistGenerico().getName());
                    Glide.with(viewGlobal).load(trackList.get(position).getAlbumGenerico().getCover_medium()).into(imageViewSongPlay);
                    Uri uri = Uri.parse(trackList.get(position).getPreview());
                    mediaPlayer = MediaPlayer.create(getContext(), uri);
                    mediaPlayer.start();
                }
            }
        }
    };

    View.OnClickListener previousListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(position>0){
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    position--;
                    textViewSongNamePlay.setText(trackList.get(position).getTitle_short());
                    textViewArtistNamePlay.setText(trackList.get(position).getArtistGenerico().getName());
                    Glide.with(viewGlobal).load(trackList.get(position).getAlbumGenerico().getCover_medium()).into(imageViewSongPlay);
                    Uri uri = Uri.parse(trackList.get(position).getPreview());
                    mediaPlayer = MediaPlayer.create(getContext(), uri);
                    mediaPlayer.start();
                }
            }else{
                position = trackList.size()-1;
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                    textViewSongNamePlay.setText(trackList.get(position).getTitle_short());
                    textViewArtistNamePlay.setText(trackList.get(position).getArtistGenerico().getName());
                    Glide.with(viewGlobal).load(trackList.get(position).getAlbumGenerico().getCover_medium()).into(imageViewSongPlay);
                    Uri uri = Uri.parse(trackList.get(position).getPreview());
                    mediaPlayer = MediaPlayer.create(getContext(), uri);
                    mediaPlayer.start();
                }
            }
        }
    };

    private void setHaspMap(ContainerTracksFav containerTracksFav){
        if(containerTracksFav != null){
            for(FavTracks favTracks :containerTracksFav.getFavTracks()){
                hashMapTracksFav.put(String.valueOf(favTracks.getId()),String.valueOf(favTracks.getId()));
            }
        }
        if(String.valueOf(trackList.get(position).getId()).equals(hashMapTracksFav.get(String.valueOf(trackList.get(position).getId())))){
            reproductorInterface.changeIconFavTrack(true);
        }else {
            reproductorInterface.changeIconFavTrack(false);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.reproductorInterface = (ReproductorInterface) context;
    }

    @Override
    public void pedirTrackReproductor() {
        reproductorInterface.saveTrackToFavTrack(trackList.get(position));
    }

    @Override
    public void borrarTrackFavTrack() {
        reproductorInterface.deleteTrackFavTrack(trackList.get(position));
    }

    @Override
    public void cargarListaFavTrackReproductor(ContainerTracksFav containerFavTracks) {
        containerTracksFav = containerFavTracks;
        if(segundaVez>0) {
            setHaspMap(containerTracksFav);
            }
        segundaVez++;
    }

    public interface ReproductorInterface{
        void changeIconFavTrack(boolean inFavTrack);
        void saveTrackToFavTrack(TrackGenerico trackGenerico);
        void deleteTrackFavTrack(TrackGenerico trackGenerico);
    }

}
