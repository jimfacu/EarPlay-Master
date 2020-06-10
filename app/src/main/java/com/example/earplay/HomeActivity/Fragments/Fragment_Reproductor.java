package com.example.earplay.HomeActivity.Fragments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.earplay.HomeActivity.Contract_HomeActivity;
import com.example.earplay.Core.Entities.Genericos.ContainerTracksFav;
import com.example.earplay.Core.Entities.Genericos.FavTracks;
import com.example.earplay.Core.Entities.Genericos.TrackGenerico;
import com.example.earplay.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class Fragment_Reproductor extends Fragment implements Contract_HomeActivity.FavTrack {

    private static final String TrackList = "TrackList";
    private static final String Position = "Position";
    private static final String FavTrackList="FavTrackList";

    private List<TrackGenerico> trackList;
    private ContainerTracksFav containerTracksFav;
    private HashMap<String,String> hashMapFavTracks ;
    private int position;

    private ImageButton btn_NextSong;
    private ImageButton btn_PreviousSong;
    private TextView textViewSongNamePlay;
    private TextView textViewArtistNamePlay;
    private TextView textView_timeStart;
    private TextView textView_timeFinal;
    private ImageView imageViewSongPlay;
    private boolean segundaVez;

    private SeekBar seekBar;
    private double startTime;
    private double finalTime;
    private Handler myHandler = new Handler();

    private ReproductorInterface reproductorInterface;

    private View viewGlobal;

    private MediaPlayer mediaPlayer = new MediaPlayer();
    public static int oneTimeOnly=0;



    public static Fragment_Reproductor buildFragmentReproductor(List<TrackGenerico> trackList ,int position ,ContainerTracksFav containerTracksFav) {
        Fragment_Reproductor fragmentReproductor = new Fragment_Reproductor();
        if (trackList != null && containerTracksFav != null ) {
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(TrackList , (ArrayList<? extends Parcelable>) trackList);
            bundle.putInt(Position,position);
            bundle.putParcelable(FavTrackList,containerTracksFav);
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
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reproductorInterface.goToDetailActivity(trackList,position);
                mediaPlayer.stop();
                myHandler.removeCallbacks(updateSongTime);
            }
        });
        if(bundle != null) {
            trackList = bundle.getParcelableArrayList(TrackList);
            position = bundle.getInt(Position);
            containerTracksFav = bundle.getParcelable(FavTrackList);
            setHaspMap(containerTracksFav);
            loadTrackInformation(view);
            btn_NextSong.setOnClickListener(nextListener);
            btn_PreviousSong.setOnClickListener(previousListener);
            if (trackList.size() > 0) {
                try {
                    mediaPlayer.setDataSource(trackList.get(position).getPreview());
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    seekBarTime();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return view;
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
        seekBar = view.findViewById(R.id.seekBar_ReproductorFragment);
        textView_timeStart = view.findViewById(R.id.textView_startTime);
        textView_timeFinal = view.findViewById(R.id.textView_endTime);
        hashMapFavTracks = new HashMap<>();
        segundaVez =true;
    }

    @Override
    public void onStop() {
        super.onStop();
        if(mediaPlayer.isPlaying()){
            mediaPlayer.stop();
        }
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
                    chekTrackFavList();
                    reproductorInterface.posicionActual(position);
                    Uri uri = Uri.parse(trackList.get(position).getPreview());
                    mediaPlayer = MediaPlayer.create(getContext(), uri);
                    mediaPlayer.start();
                    reproductorInterface.isPlaying(true);
                }else{
                    mediaPlayer.release();
                    position++;
                    textViewSongNamePlay.setText(trackList.get(position).getTitle_short());
                    textViewArtistNamePlay.setText(trackList.get(position).getArtistGenerico().getName());
                    Glide.with(viewGlobal).load(trackList.get(position).getAlbumGenerico().getCover_medium()).into(imageViewSongPlay);
                    chekTrackFavList();
                    reproductorInterface.posicionActual(position);
                    Uri uri = Uri.parse(trackList.get(position).getPreview());
                    mediaPlayer = MediaPlayer.create(getContext(), uri);
                    mediaPlayer.start();
                    reproductorInterface.isPlaying(true);
                }
            } else {
                position = 0;
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    textViewSongNamePlay.setText(trackList.get(position).getTitle_short());
                    textViewArtistNamePlay.setText(trackList.get(position).getArtistGenerico().getName());
                    Glide.with(viewGlobal).load(trackList.get(position).getAlbumGenerico().getCover_medium()).into(imageViewSongPlay);
                    chekTrackFavList();
                    reproductorInterface.posicionActual(position);
                    Uri uri = Uri.parse(trackList.get(position).getPreview());
                    mediaPlayer = MediaPlayer.create(getContext(), uri);
                    mediaPlayer.start();
                    reproductorInterface.isPlaying(true);
                }else{
                    position =0;
                    textViewSongNamePlay.setText(trackList.get(position).getTitle_short());
                    textViewArtistNamePlay.setText(trackList.get(position).getArtistGenerico().getName());
                    Glide.with(viewGlobal).load(trackList.get(position).getAlbumGenerico().getCover_medium()).into(imageViewSongPlay);
                    chekTrackFavList();
                    reproductorInterface.posicionActual(position);
                    Uri uri = Uri.parse(trackList.get(position).getPreview());
                    mediaPlayer = MediaPlayer.create(getContext(), uri);
                    mediaPlayer.start();
                    reproductorInterface.isPlaying(true);
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
                    chekTrackFavList();
                    reproductorInterface.posicionActual(position);
                    Uri uri = Uri.parse(trackList.get(position).getPreview());
                    mediaPlayer = MediaPlayer.create(getContext(), uri);
                    mediaPlayer.start();
                    reproductorInterface.isPlaying(true);
                }else{
                    position--;
                    textViewSongNamePlay.setText(trackList.get(position).getTitle_short());
                    textViewArtistNamePlay.setText(trackList.get(position).getArtistGenerico().getName());
                    Glide.with(viewGlobal).load(trackList.get(position).getAlbumGenerico().getCover_medium()).into(imageViewSongPlay);
                    chekTrackFavList();
                    reproductorInterface.posicionActual(position);
                    Uri uri = Uri.parse(trackList.get(position).getPreview());
                    mediaPlayer = MediaPlayer.create(getContext(), uri);
                    mediaPlayer.start();
                    reproductorInterface.isPlaying(true);
                }
            }else{
                position = trackList.size()-1;
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                    textViewSongNamePlay.setText(trackList.get(position).getTitle_short());
                    textViewArtistNamePlay.setText(trackList.get(position).getArtistGenerico().getName());
                    Glide.with(viewGlobal).load(trackList.get(position).getAlbumGenerico().getCover_medium()).into(imageViewSongPlay);
                    Uri uri = Uri.parse(trackList.get(position).getPreview());
                    chekTrackFavList();
                    reproductorInterface.posicionActual(position);
                    mediaPlayer = MediaPlayer.create(getContext(), uri);
                    mediaPlayer.start();
                    reproductorInterface.isPlaying(true);
                }else{
                    position = trackList.size()-1;
                    textViewSongNamePlay.setText(trackList.get(position).getTitle_short());
                    textViewArtistNamePlay.setText(trackList.get(position).getArtistGenerico().getName());
                    Glide.with(viewGlobal).load(trackList.get(position).getAlbumGenerico().getCover_medium()).into(imageViewSongPlay);
                    Uri uri = Uri.parse(trackList.get(position).getPreview());
                    chekTrackFavList();
                    reproductorInterface.posicionActual(position);
                    mediaPlayer = MediaPlayer.create(getContext(), uri);
                    mediaPlayer.start();
                    reproductorInterface.isPlaying(true);
                }
            }
        }
    };

    private void setHaspMap(ContainerTracksFav containerTracksFav){
        if(containerTracksFav != null){
            hashMapFavTracks = new HashMap<>();
            for(FavTracks favTracks :containerTracksFav.getFavTracks()){
                hashMapFavTracks.put(String.valueOf(favTracks.getId()),String.valueOf(favTracks.getId()));
            }
            chekTrackFavList();
        }
    }

   private Runnable updateSongTime= new Runnable() {
        @Override
        public void run() {
            startTime = mediaPlayer.getCurrentPosition();
            textView_timeStart.setText(String.format("%d:%d", TimeUnit.MILLISECONDS.toMinutes((long)startTime),
                    TimeUnit.MILLISECONDS.toSeconds((long)startTime)-
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)startTime))));
            seekBar.setProgress((int) startTime);
            myHandler.postDelayed(this,100);
        }
    };

    @SuppressLint({"ClickableViewAccessibility", "DefaultLocale"})
    private void seekBarTime(){
        seekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });

        startTime = mediaPlayer.getCurrentPosition();
        finalTime = mediaPlayer.getDuration();
        if(oneTimeOnly ==0){
            seekBar.setMax((int) finalTime);
            oneTimeOnly = 1;
        }
        textView_timeFinal.setText(String.format("%d:%d", TimeUnit.MILLISECONDS.toMinutes((long)finalTime),
                TimeUnit.MILLISECONDS.toSeconds((long)finalTime)-
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)finalTime))));

        textView_timeStart.setText(String.format("%d:%d", TimeUnit.MILLISECONDS.toMinutes((long)startTime),
                TimeUnit.MILLISECONDS.toSeconds((long)startTime)-
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)startTime))));

        seekBar.setProgress((int)startTime);
        myHandler.postDelayed(updateSongTime,100);
    }

    private void chekTrackFavList(){
        if(String.valueOf(trackList.get(position).getId()).equals(hashMapFavTracks.get(String.valueOf(trackList.get(position).getId())))){
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
        FavTracks favTracks = new FavTracks(trackList.get(position).getId(),trackList.get(position).getTitle_short(),trackList.get(position).getPreview()
                ,trackList.get(position).getLink(),trackList.get(position).getArtistGenerico(),trackList.get(position).getAlbumGenerico());
        containerTracksFav.getFavTracks().add(favTracks);
        reproductorInterface.saveTrackToFavTrack(containerTracksFav
        );
    }

    @Override
    public void borrarTrackFavTrack() {
       // containerTracksFav.getFavTracks().remove(new FavTracks(trackList.get(position).getId(),trackList.get(position).getTitle_short(),trackList.get(position).getPreview(),
         //       trackList.get(position).getLink(),trackList.get(position).getArtistGenerico(),trackList.get(position).getAlbumGenerico()));
        List<FavTracks> favTracksList= new ArrayList<>();
        List<FavTracks> tankfavTracksList = new ArrayList<>();
        for(FavTracks favTracks :containerTracksFav.getFavTracks()){
            if(favTracks.getId() != trackList.get(position).getId()) {
                tankfavTracksList.add(favTracks);
            }
        }
        favTracksList.addAll(tankfavTracksList);
        containerTracksFav.setFavTracks(tankfavTracksList);
        reproductorInterface.deleteTrackFavTrack(containerTracksFav);
    }

    @Override
    public void refreshPositionFromDeleteMyPlaylist() {
        if(position >0) {
            position--;        }
    }

    @Override
    public void cargarListaFavTrackReproductor(ContainerTracksFav containerFavTracks) {
        containerTracksFav = containerFavTracks;
        if(segundaVez) {
            setHaspMap(containerTracksFav);
        }
    }

    @Override
    public void playPause() {
        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
            reproductorInterface.isPlaying(false);
        }else{
            mediaPlayer.start();
            reproductorInterface.isPlaying(true);
        }
    }

    public interface ReproductorInterface{
        void changeIconFavTrack(boolean inFavTrack);
        void saveTrackToFavTrack(ContainerTracksFav containerTracksFav);
        void deleteTrackFavTrack(ContainerTracksFav containerTracksFav);
        void posicionActual(int position);
        void isPlaying(boolean isplaying);
        void goToDetailActivity(List<TrackGenerico> list,int position);
    }
}
