package com.example.earplay.DetailActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.earplay.HomeActivity.Entities.Genericos.ContainerTracksFav;
import com.example.earplay.HomeActivity.Entities.Genericos.FavTracks;
import com.example.earplay.HomeActivity.Entities.Genericos.TrackGenerico;
import com.example.earplay.HomeActivity.Utils.Constants;
import com.example.earplay.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

public class View_DetailActivity extends AppCompatActivity implements Contract_DetailActivity.View{

    private Presenter_DetailActivity presenter;

    private static final String TrackList = "TrackList";
    private static final String Position = "Position";
    private static final String ArtistProfile= "ArtistProfile";
    private static final String AlbumProfile = "AlbumProfile";
    private static final String Normal = "Normal";
    private static final String Mod ="Mod";

    private List<TrackGenerico> trackGenericoList;
    private ContainerTracksFav containerFavTracks;
    private int position=0;

    private Context context;

    private double startTime;
    private double finalTime;

    private HashMap<String,String> hp ;


    public static int oneTimeOnly=0;
    private Handler myHandler = new Handler();

    private MediaPlayer mediaPlayer;

    @BindView(R.id.imageView_BackToHomeActivity)
    ImageView imageView_backToHomeActivity;

    @BindView(R.id.imageButton_config_Detail)
    ImageButton imageButton_options;

    @BindView(R.id.textView_endTime_DetailActivity)
    TextView textView_timeFinal;

    @BindView(R.id.textView_startTime_DetailActivity)
    TextView textView_timeStart;

    @BindView(R.id.imageView_savedeleteFavTrack)
    ImageView imageView_favTrack;

    @BindView(R.id.imageView_AlbumDetailActivity)
    ImageView imageView_ImageTrack;

    @BindView(R.id.textView_ArtistNameDetailActivity)
    TextView textView_ArtistName;

    @BindView(R.id.textView_NameTrackDetailActivity)
    TextView textView_NameTrack;

    @BindView(R.id.imageView_PreviousTrack)
    ImageView imageView_previousTrack;

    @BindView(R.id.imageView_PausePlayTrack)
    ImageView imageView_pausePlayTrack;

    @BindView(R.id.imageView_NextTrack)
    ImageView imageView_nextTrack;

    @BindView(R.id.seekBar_DetailActivity)
    SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_detail);
        ButterKnife.bind(this);
        initView();
        context = this;
        presenter = new Presenter_DetailActivity(this,this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            trackGenericoList = bundle.getParcelableArrayList(TrackList);
            position = bundle.getInt(Position);
            loadView();
            pedirListas();
            imageView_favTrack.setOnClickListener(favTrackListener);
            imageView_backToHomeActivity.setOnClickListener(backToHomeListener);
            textView_ArtistName.setOnClickListener(goToArtistProfileListener);
            textView_NameTrack.setOnClickListener(goToAlbumProfileListener);
            imageButton_options.setOnClickListener(optionsListener);
            if (trackGenericoList.size() > 0) {
                try {
                    mediaPlayer.setDataSource(trackGenericoList.get(position).getPreview());
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    checkFavTrack();
                    seekBarTime();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void pedirListas() {
        presenter.pedirTracksFavAlInteractor();
    }

    private void loadView() {
        Glide.with(this).load(trackGenericoList.get(position).getAlbumGenerico().getCover_medium()).into(imageView_ImageTrack);
        textView_NameTrack.setText(trackGenericoList.get(position).getTitle_short());
        textView_ArtistName.setText(trackGenericoList.get(position).getArtistGenerico().getName());
        imageView_previousTrack.setOnClickListener(previousListener);
        imageView_nextTrack.setOnClickListener(nextListener);
        imageView_pausePlayTrack.setOnClickListener(playPauseListener);
    }

    private void initView() {
        trackGenericoList = new ArrayList<>();
        mediaPlayer = new MediaPlayer();
        hp=new HashMap<>();
    }

    private View.OnClickListener optionsListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            PopupMenu popupMenu = new PopupMenu(context,imageButton_options);
            popupMenu.inflate(R.menu.menu_options_tracks_detail);
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {

                    switch (menuItem.getItemId()) {
                        case R.id.shareTrack:
                            PackageManager pm=getPackageManager();
                            try {
                                Intent waIntent = new Intent(Intent.ACTION_SEND);
                                waIntent.setType(getString(R.string.text_plain));
                                String text = getString(R.string.Escuchate_este_temaiken)+trackGenericoList.get(position).getLink();

                                PackageInfo info= pm.getPackageInfo(Constants.PackageWhatsapp, PackageManager.GET_META_DATA);
                                waIntent.setPackage(Constants.PackageWhatsapp);

                                waIntent.putExtra(Intent.EXTRA_TEXT, text);
                                startActivity(Intent.createChooser(waIntent, getString(R.string.Share_with)));
                            } catch (PackageManager.NameNotFoundException e) {
                                Toast.makeText(getApplicationContext(), R.string.WhatsApp_not_Installed, Toast.LENGTH_SHORT)
                                        .show();
                            }
                            break;
                        default:
                            break;
                    }
                    return false;
                }
            });
            popupMenu.show();
        }};

    private View.OnClickListener goToArtistProfileListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.putExtra(TrackList, (ArrayList<? extends Parcelable>) trackGenericoList);
            intent.putExtra(Position,position);
            intent.putExtra(Mod,ArtistProfile);
            setResult(View_DetailActivity.RESULT_OK,intent);
            mediaPlayer.stop();
            mediaPlayer.release();
            myHandler.removeCallbacks(updateSongTime);
            finish();
        }
    };

    private View.OnClickListener goToAlbumProfileListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.putExtra(TrackList, (ArrayList<? extends Parcelable>) trackGenericoList);
            intent.putExtra(Position,position);
            intent.putExtra(Mod,AlbumProfile);
            setResult(View_DetailActivity.RESULT_OK,intent);
            mediaPlayer.stop();
            mediaPlayer.release();
            myHandler.removeCallbacks(updateSongTime);
            finish();
        }
    };

    private View.OnClickListener favTrackListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(String.valueOf(trackGenericoList.get(position).getId()).equals(hp.get(String.valueOf(trackGenericoList.get(position).getId())))){
                deleteTrackFavTrack();
                checkFavTrack();
            }else{
                saveTrackToFavTrack();
                checkFavTrack();
            }
        }
    };

    private View.OnClickListener backToHomeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.putExtra(TrackList, (ArrayList<? extends Parcelable>) trackGenericoList);
            intent.putExtra(Position,position);
            intent.putExtra(Mod,Normal);
            setResult(View_DetailActivity.RESULT_OK,intent);
            mediaPlayer.stop();
            mediaPlayer.release();
            myHandler.removeCallbacks(updateSongTime);
            finish();
        }
    };

    private View.OnClickListener previousListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(position>0){
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    position--;
                    checkFavTrack();
                    textView_NameTrack.setText(trackGenericoList.get(position).getTitle_short());
                    textView_ArtistName.setText(trackGenericoList.get(position).getArtistGenerico().getName());
                    Glide.with(context).load(trackGenericoList.get(position).getAlbumGenerico().getCover_medium()).into(imageView_ImageTrack);
                    Uri uri = Uri.parse(trackGenericoList.get(position).getPreview());
                    mediaPlayer = MediaPlayer.create(context,uri);
                    mediaPlayer.start();
                }else{
                    position--;
                    checkFavTrack();
                    textView_NameTrack.setText(trackGenericoList.get(position).getTitle_short());
                    textView_ArtistName.setText(trackGenericoList.get(position).getArtistGenerico().getName());
                    Glide.with(context).load(trackGenericoList.get(position).getAlbumGenerico().getCover_medium()).into(imageView_ImageTrack);
                    Uri uri = Uri.parse(trackGenericoList.get(position).getPreview());
                    mediaPlayer = MediaPlayer.create(context, uri);
                    mediaPlayer.start();
                }
            }else{
                position = trackGenericoList.size()-1;
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                    checkFavTrack();
                    textView_NameTrack.setText(trackGenericoList.get(position).getTitle_short());
                    textView_ArtistName.setText(trackGenericoList.get(position).getArtistGenerico().getName());
                    Glide.with(context).load(trackGenericoList.get(position).getAlbumGenerico().getCover_medium()).into(imageView_ImageTrack);
                    Uri uri = Uri.parse(trackGenericoList.get(position).getPreview());
                    mediaPlayer = MediaPlayer.create(context, uri);
                    mediaPlayer.start();
                }else{
                    position = trackGenericoList.size()-1;
                    checkFavTrack();
                    textView_NameTrack.setText(trackGenericoList.get(position).getTitle_short());
                    textView_ArtistName.setText(trackGenericoList.get(position).getArtistGenerico().getName());
                    Glide.with(context).load(trackGenericoList.get(position).getAlbumGenerico().getCover_medium()).into(imageView_ImageTrack);
                    Uri uri = Uri.parse(trackGenericoList.get(position).getPreview());
                    mediaPlayer = MediaPlayer.create(context, uri);
                    mediaPlayer.start();
                }
            }
        }
    };

    private View.OnClickListener nextListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (position < trackGenericoList.size()-1) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    position++;
                    checkFavTrack();
                    textView_NameTrack.setText(trackGenericoList.get(position).getTitle_short());
                    textView_ArtistName.setText(trackGenericoList.get(position).getArtistGenerico().getName());
                    Glide.with(context).load(trackGenericoList.get(position).getAlbumGenerico().getCover_medium()).into(imageView_ImageTrack);
                    Uri uri = Uri.parse(trackGenericoList.get(position).getPreview());
                    mediaPlayer = MediaPlayer.create(context, uri);
                    mediaPlayer.start();
                }else{
                    mediaPlayer.release();
                    position++;
                    checkFavTrack();
                    textView_NameTrack.setText(trackGenericoList.get(position).getTitle_short());
                    textView_ArtistName.setText(trackGenericoList.get(position).getArtistGenerico().getName());
                    Glide.with(context).load(trackGenericoList.get(position).getAlbumGenerico().getCover_medium()).into(imageView_ImageTrack);
                    Uri uri = Uri.parse(trackGenericoList.get(position).getPreview());
                    mediaPlayer = MediaPlayer.create(context, uri);
                    mediaPlayer.start();
                }
            } else {
                position = 0;
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    checkFavTrack();
                    textView_NameTrack.setText(trackGenericoList.get(position).getTitle_short());
                    textView_ArtistName.setText(trackGenericoList.get(position).getArtistGenerico().getName());
                    Glide.with(context).load(trackGenericoList.get(position).getAlbumGenerico().getCover_medium()).into(imageView_ImageTrack);
                    Uri uri = Uri.parse(trackGenericoList.get(position).getPreview());
                    mediaPlayer = MediaPlayer.create(context, uri);
                    mediaPlayer.start();
                }else{
                    position =0;
                    checkFavTrack();
                    textView_NameTrack.setText(trackGenericoList.get(position).getTitle_short());
                    textView_ArtistName.setText(trackGenericoList.get(position).getArtistGenerico().getName());
                    Glide.with(context).load(trackGenericoList.get(position).getAlbumGenerico().getCover_medium()).into(imageView_ImageTrack);
                    Uri uri = Uri.parse(trackGenericoList.get(position).getPreview());
                    mediaPlayer = MediaPlayer.create(context, uri);
                    mediaPlayer.start();
                }
            }
        }
    };

    private View.OnClickListener playPauseListener =new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(mediaPlayer.isPlaying()){
                mediaPlayer.pause();
                imageView_pausePlayTrack.setImageResource(R.drawable.ic_baseline_play_circle_outline_detail_activity_24);
            }else{
                mediaPlayer.start();
                imageView_pausePlayTrack.setImageResource(R.drawable.ic_baseline_pause_detail_activity_24);
            }
        }
    };

    @SuppressLint("DefaultLocale")
    private void seekBarTime(){
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
        seekBar.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
    }
    private Runnable updateSongTime= new Runnable() {
        @SuppressLint("DefaultLocale")
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

    @Override
    public void mostrarTracksFav(ContainerTracksFav containerTracksFav) {
        containerFavTracks = containerTracksFav;
        hp = new HashMap<>();
        for(FavTracks favTracks : containerTracksFav.getFavTracks()){
            hp.put(String.valueOf(favTracks.getId()),String.valueOf(favTracks.getId()));
        }
        checkFavTrack();
    }

    public void deleteTrackFavTrack() {
        //containerTracksFav.getFavTracks().remove(new FavTracks(trackFavDelete.getId(),trackFavDelete.getTitle_short(),trackFavDelete.getPreview(),
        //trackFavDelete.getLink(),trackFavDelete.getArtistGenerico(),trackFavDelete.getAlbumGenerico()));
        List<FavTracks> favTracksList= new ArrayList<>();
        List<FavTracks> tankfavTracksList = new ArrayList<>();
        for(FavTracks favTracks :containerFavTracks.getFavTracks()){
            if(favTracks.getId() != trackGenericoList.get(position).getId()) {
                tankfavTracksList.add(favTracks);
            }
        }
        favTracksList.addAll(tankfavTracksList);
        containerFavTracks.setFavTracks(tankfavTracksList);
        presenter.guardarTracksFavNuevo(containerFavTracks);
        presenter.pedirTracksFavAlInteractor();
        Toast.makeText(this, getString(R.string.Cancion_agregada_eliminada_a_favoritos_exitosamente), Toast.LENGTH_SHORT).show();
    }

    public void saveTrackToFavTrack() {
        FavTracks favTracks = new FavTracks(trackGenericoList.get(position).getId(),trackGenericoList.get(position).getTitle_short()
                ,trackGenericoList.get(position).getPreview(),trackGenericoList.get(position).getLink(),
                trackGenericoList.get(position).getArtistGenerico(),trackGenericoList.get(position).getAlbumGenerico());
        containerFavTracks.getFavTracks().add(favTracks);
        presenter.guardarTracksFavNuevo(containerFavTracks);
        presenter.pedirTracksFavAlInteractor();
        Toast.makeText(this, getString(R.string.Cancion_agregada_eliminada_a_favoritos_exitosamente), Toast.LENGTH_SHORT).show();
    }

    private void checkFavTrack (){
        if(String.valueOf(trackGenericoList.get(position).getId()).equals(hp.get(String.valueOf(trackGenericoList.get(position).getId())))){
            imageView_favTrack.setImageResource(R.drawable.ic_baseline_favorite_detail_activity_24);
        }else{
            imageView_favTrack.setImageResource(R.drawable.ic_baseline_favorite_border_detail_activity_24);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra(TrackList, (ArrayList<? extends Parcelable>) trackGenericoList);
        intent.putExtra(Position,position);
        intent.putExtra(Mod,Normal);
        setResult(View_DetailActivity.RESULT_OK,intent);
        mediaPlayer.stop();
        mediaPlayer.release();
        myHandler.removeCallbacks(updateSongTime);
        finish();
    }
}