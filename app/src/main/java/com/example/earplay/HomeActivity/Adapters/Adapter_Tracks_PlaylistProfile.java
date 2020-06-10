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

import com.example.earplay.Core.Entities.Genericos.AlbumGenerico;
import com.example.earplay.Core.Entities.Genericos.ArtistGenerico;
import com.example.earplay.Core.Entities.Genericos.TrackGenerico;
import com.example.earplay.Core.Entities.TracksRank.Track;
import com.example.earplay.R;

import java.util.ArrayList;
import java.util.List;

public class Adapter_Tracks_PlaylistProfile extends RecyclerView.Adapter {

    private List<Track> tankTracksPlaylistList;
    private List<Track> tracksPlaylistList;
    private ShowPlaylists showPlaylists;
    private Context context;

    public Adapter_Tracks_PlaylistProfile(Context context,ShowPlaylists showPlaylists) {
        this.tankTracksPlaylistList = new ArrayList<>();
        this.tracksPlaylistList = new ArrayList<>();
        this.showPlaylists = showPlaylists;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context =parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.cell_fragment_playlist_profile_tracks,parent,false);
        PlaylistProfileViewHolder viewHolder = new PlaylistProfileViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Track tracksPlaylist = tracksPlaylistList.get(position);
        PlaylistProfileViewHolder profileViewHolder = (PlaylistProfileViewHolder) holder;
        profileViewHolder.setTracksPlaylist(tracksPlaylist);
    }

    public void insetPlaylistTracks(List<Track> tracksPlaylistsProfile){
        if(tracksPlaylistsProfile != null){
            tankTracksPlaylistList.clear();
            for(Track tracksPlaylist:tracksPlaylistsProfile){
                tankTracksPlaylistList.add(tracksPlaylist);
            }
        }
        tracksPlaylistList.clear();
        tracksPlaylistList.addAll(tankTracksPlaylistList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return tracksPlaylistList.size();
    }

    class PlaylistProfileViewHolder extends RecyclerView.ViewHolder {

        private TextView textView_numberSong;
        private TextView textView_nameSong;
        private TextView textView_nameArtist;
        private ImageButton imageButton_options;

        public PlaylistProfileViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_numberSong = itemView.findViewById(R.id.textView_numberSongPlaylistProfile);
            textView_nameSong = itemView.findViewById(R.id.textView_nameSongProfilePlaylist);
            textView_nameArtist = itemView.findViewById(R.id.textView_nameBandPlaylistProfile);
            imageButton_options = itemView.findViewById(R.id.imageButton_config_PlaylistProfile);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    List<TrackGenerico> trackList = new ArrayList<>();
                    for(Track track : tracksPlaylistList){
                        AlbumGenerico album = new AlbumGenerico(track.getAlbum().getId(),track.getAlbum().getTitle(),track.getAlbum().getCover_medium());
                        ArtistGenerico artist = new ArtistGenerico(track.getArtist().getId(),track.getArtist().getName(),track.getArtist().getPicture_big());
                        TrackGenerico trackGenerico = new TrackGenerico(track.getId(),track.getTitle_short(),track.getPreview(),track.getLink(),artist,album);
                        trackList.add(trackGenerico);
                    }
                    showPlaylists.playTrack(trackList,getAdapterPosition());
                }
            });
        }

        public void setTracksPlaylist(Track tracksPlaylist){

            textView_numberSong.setText(String.valueOf(getAdapterPosition()+1));
            textView_nameSong.setText(tracksPlaylist.getTitle());
            textView_nameArtist.setText(tracksPlaylist.getArtist().getName());
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
                                    AlbumGenerico album = new AlbumGenerico(tracksPlaylist.getAlbum().getId(),tracksPlaylist.getAlbum().getTitle(),
                                            tracksPlaylist.getAlbum().getCover_medium());
                                    //El constructor de este artist es diferente al normal , ya que solo solicita un id y un nombre por que la listatracksPlaylist no tiene la informacion completa del artista;
                                    ArtistGenerico artist = new ArtistGenerico(tracksPlaylist.getArtist().getId(),tracksPlaylist.getArtist().getName());
                                    TrackGenerico trackPlaylist = new TrackGenerico(tracksPlaylist.getId(),tracksPlaylist.getTitle_short()
                                    ,tracksPlaylist.getPreview(),tracksPlaylist.getLink(),artist,album);
                                    showPlaylists.goToMyPlaylists(trackPlaylist);
                                    break;

                                case R.id.shareTrack:
                                    showPlaylists.shareTrack(tracksPlaylist);
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
    public interface ShowPlaylists{
        void goToMyPlaylists(TrackGenerico track);
        void playTrack(List<TrackGenerico> trackList,int position);
        void shareTrack(Track trackGenerico);
    }
    }
