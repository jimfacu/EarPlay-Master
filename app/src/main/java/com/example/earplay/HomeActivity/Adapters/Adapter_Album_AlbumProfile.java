package com.example.earplay.HomeActivity.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.earplay.HomeActivity.Entities.AlbumProfile.TracksFromAlbumProfile;
import com.example.earplay.HomeActivity.Entities.Genericos.AlbumGenerico;
import com.example.earplay.HomeActivity.Entities.Genericos.ArtistGenerico;
import com.example.earplay.HomeActivity.Entities.Genericos.TrackGenerico;
import com.example.earplay.HomeActivity.Entities.MisPlaylist.TracksDeMiPlaylist;
import com.example.earplay.HomeActivity.Entities.TracksRank.Album;
import com.example.earplay.HomeActivity.Entities.TracksRank.Track;
import com.example.earplay.HomeActivity.Utils.InterfaceUtils;
import com.example.earplay.R;

import java.util.ArrayList;
import java.util.List;

public class Adapter_Album_AlbumProfile extends RecyclerView.Adapter {

    private List<TracksFromAlbumProfile> tankListTracksFromAlbumProfile;
    private List<TracksFromAlbumProfile> listTracksFromAlbumProfile;
    private Context context;
    private ShowMyPlaylists showMyPlaylists;
    private AlbumGenerico albumGenerico;
    private ArtistGenerico artistGenerico;


    public Adapter_Album_AlbumProfile(Context context, ShowMyPlaylists showMyPlaylists, AlbumGenerico albumGenerico, ArtistGenerico artistGenerico) {
        this.tankListTracksFromAlbumProfile = new ArrayList<>();
        this.listTracksFromAlbumProfile = new ArrayList<>();
        this.context = context;
        this.showMyPlaylists = showMyPlaylists;
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
        private TextView textView_options;

        public AlbumProfileViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_NumberSongAlbumProfile = itemView.findViewById(R.id.textView_numberSongAlbumProfile);
            textView_nameSongAlbumProfile = itemView.findViewById(R.id.textView_nameSongAlbumProfile);
            textView_options = itemView.findViewById(R.id.textView_Options);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    List<TrackGenerico> trackGenericoList = new ArrayList<>();
                    for(TracksFromAlbumProfile track :listTracksFromAlbumProfile){
                        TrackGenerico trackGenerico = new TrackGenerico(Integer.parseInt(track.getId()),track.getTitle_short(),track.getPreview(),track.getLink(),artistGenerico,albumGenerico);
                        trackGenericoList.add(trackGenerico);
                    }
                    showMyPlaylists.playTrack(trackGenericoList, getAdapterPosition());
                }
            });
        }

        //Estaria bueno cambiar el nombre por tracksFromAlbumProfile
        public void setAlbumProfile(TracksFromAlbumProfile tracksFromAlbumProfile){
            textView_nameSongAlbumProfile.setText(tracksFromAlbumProfile.getTitle_short());
            textView_NumberSongAlbumProfile.setText(String.valueOf(getAdapterPosition()+1));
            textView_options.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popupMenu = new PopupMenu(context,textView_options);
                    popupMenu.inflate(R.menu.menu_options_tracks);
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {

                            switch (menuItem.getItemId()){
                                case R.id.addTrackToMyPlaylist:
                                    TrackGenerico tracksDeMiPlaylist =new TrackGenerico(Integer.parseInt(tracksFromAlbumProfile.getId()),
                                            tracksFromAlbumProfile.getTitle_short(),tracksFromAlbumProfile.getPreview(),
                                            tracksFromAlbumProfile.getLink(),null,null);
                                    showMyPlaylists.goToMyPlaylists(tracksDeMiPlaylist);
                                    break;

                                case R.id.shareTrack:
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

    public interface ShowMyPlaylists{
        void goToMyPlaylists(TrackGenerico track);
        void playTrack(List<TrackGenerico> trackGenericoList,int position);
    }
}
