package com.example.earplay.HomeActivity.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.earplay.HomeActivity.Entities.Genericos.TrackGenerico;
import com.example.earplay.HomeActivity.Entities.TracksRank.Track;
import com.example.earplay.R;

import java.util.ArrayList;
import java.util.List;

public class Adapter_Tracks_MyPlaylist extends RecyclerView.Adapter {

    private List<TrackGenerico> trackList;
    private List<TrackGenerico> tankTrackList;
    private AdapterTracks_Interface adapterTracksInterface;

    public Adapter_Tracks_MyPlaylist(AdapterTracks_Interface adapterTracks_interface) {
        this.trackList = new ArrayList<>();
        this.tankTrackList = new ArrayList<>();
        this.adapterTracksInterface = adapterTracks_interface;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.cell_fragment_myplaylist_profile_tracks,parent,false);
        TracksOfMyPlaylistViewHolder viewHolder = new TracksOfMyPlaylistViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TrackGenerico trackGenerico = trackList.get(position);
        TracksOfMyPlaylistViewHolder tracksOfMyPlaylistViewHolder = (TracksOfMyPlaylistViewHolder) holder;
        tracksOfMyPlaylistViewHolder.setTrack(trackGenerico);
    }

    public void insertTrackList(List<TrackGenerico> listTrack){
        if(listTrack != null){
            tankTrackList.clear();
            for(TrackGenerico track : listTrack){
                tankTrackList.add(track);
            }
        }
        trackList.clear();
        trackList.addAll(tankTrackList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return trackList.size();
    }

    class TracksOfMyPlaylistViewHolder extends RecyclerView.ViewHolder {

        private TextView textView_nameSongPlaylist;
        private TextView textView_numberSong;
        private TextView textView_nameBand;

        public TracksOfMyPlaylistViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_nameBand = itemView.findViewById(R.id.textView_nameBandTracksOfMyPlaylist);
            textView_numberSong = itemView.findViewById(R.id.textView_numberSongTracksOfMyPlaylist);
            textView_nameSongPlaylist = itemView.findViewById(R.id.textView_nameSongTracksOfMyPlaylist);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    adapterTracksInterface.playTrack(trackList,getAdapterPosition());
                }
            });
        }

        public void setTrack(TrackGenerico track){
            textView_nameSongPlaylist.setText(track.getTitle_short());
            textView_numberSong.setText(String.valueOf(getAdapterPosition()+1));
            textView_nameBand.setText(track.getArtistGenerico().getName());
        }
    }

    public interface AdapterTracks_Interface{
        void playTrack(List<TrackGenerico> trackGenericoList,int position);
    }
}
