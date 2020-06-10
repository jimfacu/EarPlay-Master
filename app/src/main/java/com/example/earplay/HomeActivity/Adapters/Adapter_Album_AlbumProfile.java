package com.example.earplay.HomeActivity.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.earplay.Core.Entities.AlbumProfile.TracksFromAlbumProfile;
import com.example.earplay.Core.Entities.Genericos.AlbumGenerico;
import com.example.earplay.Core.Entities.Genericos.ArtistGenerico;
import com.example.earplay.Core.Entities.Genericos.TrackGenerico;
import com.example.earplay.R;

import java.util.ArrayList;
import java.util.List;

public class Adapter_Album_AlbumProfile extends RecyclerView.Adapter {

    private List<TracksFromAlbumProfile> tankListTracksFromAlbumProfile;
    private List<TracksFromAlbumProfile> listTracksFromAlbumProfile;
    private Context context;
    private ArtistProfileAdapter_Interface artistProfileAdapter_interface;
    private AlbumGenerico albumGenerico;
    private ArtistGenerico artistGenerico;


    public Adapter_Album_AlbumProfile(Context context, ArtistProfileAdapter_Interface artistProfileAdapter_interface, AlbumGenerico albumGenerico, ArtistGenerico artistGenerico) {
        this.tankListTracksFromAlbumProfile = new ArrayList<>();
        this.listTracksFromAlbumProfile = new ArrayList<>();
        this.context = context;
        this.artistProfileAdapter_interface = artistProfileAdapter_interface;
        this.albumGenerico = albumGenerico;
        this.artistGenerico = artistGenerico;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context =parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.cell_fragment_album_profile_tracks,parent,false);
        AlbumProfileViewHolder viewHolder = new AlbumProfileViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TracksFromAlbumProfile tracksFromAlbumProfile = listTracksFromAlbumProfile.get(position);
        AlbumProfileViewHolder albumProfileViewHolder = (AlbumProfileViewHolder) holder;
        albumProfileViewHolder.setAlbumProfile(tracksFromAlbumProfile);
    }

    public void insertAlbumProfile(List<TracksFromAlbumProfile> tracksFromAlbumProfileList){
        if(tracksFromAlbumProfileList != null){
            tankListTracksFromAlbumProfile.clear();
            for(TracksFromAlbumProfile tracksFromAlbumProfile : tracksFromAlbumProfileList){
                tankListTracksFromAlbumProfile.add(tracksFromAlbumProfile);
            }
        }
        listTracksFromAlbumProfile.clear();
        listTracksFromAlbumProfile.addAll(tankListTracksFromAlbumProfile);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listTracksFromAlbumProfile.size();
    }

    class AlbumProfileViewHolder extends RecyclerView.ViewHolder {

        private TextView textView_nameSongAlbumProfile;
        private TextView textView_NumberSongAlbumProfile;
        private ImageButton imageButton_options;

        public AlbumProfileViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_NumberSongAlbumProfile = itemView.findViewById(R.id.textView_numberSongAlbumProfile);
            textView_nameSongAlbumProfile = itemView.findViewById(R.id.textView_nameSongAlbumProfile);
            imageButton_options = itemView.findViewById(R.id.imageButton_config_AlbumProfile);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    List<TrackGenerico> trackGenericoList = new ArrayList<>();
                    for(TracksFromAlbumProfile track :listTracksFromAlbumProfile){
                        TrackGenerico trackGenerico = new TrackGenerico(Integer.parseInt(track.getId()),track.getTitle_short(),track.getPreview(),track.getLink(),artistGenerico,albumGenerico);
                        trackGenericoList.add(trackGenerico);
                    }
                    artistProfileAdapter_interface.playTrack(trackGenericoList, getAdapterPosition());
                }
            });
        }

        //Estaria bueno cambiar el nombre por tracksFromAlbumProfile
        public void setAlbumProfile(TracksFromAlbumProfile tracksFromAlbumProfile){
            textView_nameSongAlbumProfile.setText(tracksFromAlbumProfile.getTitle_short());
            textView_NumberSongAlbumProfile.setText(String.valueOf(getAdapterPosition()+1));
            imageButton_options.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popupMenu = new PopupMenu(context,imageButton_options);
                    popupMenu.inflate(R.menu.menu_options_tracks);
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {

                            switch (menuItem.getItemId()){
                                case R.id.addTrackToMyPlaylist:
                                    TrackGenerico tracksDeMiPlaylist =new TrackGenerico(Integer.parseInt(tracksFromAlbumProfile.getId()),
                                            tracksFromAlbumProfile.getTitle_short(),tracksFromAlbumProfile.getPreview(),
                                            tracksFromAlbumProfile.getLink(),null,null);
                                    artistProfileAdapter_interface.goToMyPlaylists(tracksDeMiPlaylist);
                                    break;

                                case R.id.shareTrack:
                                    artistProfileAdapter_interface.shareTrack(tracksFromAlbumProfile);
                                    break;

                                default:
                                    break;
                            }

                            return false;
                        }
                    });
                    popupMenu.show();
                }
            });
        }
    }

    public interface ArtistProfileAdapter_Interface{
        void goToMyPlaylists(TrackGenerico track);
        void playTrack(List<TrackGenerico> trackGenericoList,int position);
        void shareTrack(TracksFromAlbumProfile tracksFromAlbumProfile);
    }
}
