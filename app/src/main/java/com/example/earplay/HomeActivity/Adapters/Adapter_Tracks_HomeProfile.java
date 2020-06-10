package com.example.earplay.HomeActivity.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.earplay.Core.Entities.Genericos.ArtistGenerico;
import com.example.earplay.Core.Entities.TracksRank.Album;
import com.example.earplay.Core.Entities.TracksRank.Track;
import com.example.earplay.R;

import java.util.ArrayList;
import java.util.List;

public class Adapter_Tracks_HomeProfile extends RecyclerView.Adapter {

    private List<Track> trackList;
    private CellListenerTracksRank cellListener;

    public Adapter_Tracks_HomeProfile(CellListenerTracksRank cellListener) {
        this.trackList = new ArrayList<>();
        this.cellListener = cellListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.cell_home_activity_tracks,parent,false);
        TracksRankViewHolder viewHolder = new TracksRankViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Track track = trackList.get(position);
        TracksRankViewHolder tracksRankViewHolder = (TracksRankViewHolder) holder;
        tracksRankViewHolder.setTrack(track);
    }

    public void insertListTracks(List<Track> listTrack){
        if(listTrack != null){
            for(Track track : listTrack){
                trackList.add(track);
            }
        }
    }

    @Override
    public int getItemCount() {
        return trackList.size();
    }

    class TracksRankViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageView_Track;
        private TextView textView_TrackName;
        private TextView textView_TrackArtistName;

        public TracksRankViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView_Track = itemView.findViewById(R.id.imageView_trackImage);
            textView_TrackName = itemView.findViewById(R.id.textView_TrackName);
            textView_TrackArtistName = itemView.findViewById(R.id.textView_TrackNameArtists);

            imageView_Track.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cellListener.play(trackList.get(getAdapterPosition()));
                }
            });

            textView_TrackArtistName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ArtistGenerico artist = new ArtistGenerico(trackList.get(getAdapterPosition()).getArtist().getId(),
                            trackList.get(getAdapterPosition()).getArtist().getName(),trackList.get(getAdapterPosition()).getArtist().getPicture_big());
                    cellListener.goToAlbum(trackList.get(getAdapterPosition()).getAlbum(),artist);
                }
            });

        }
        public void setTrack(Track track){
            Glide.with(itemView).load(track.getAlbum().getCover_medium()).into(imageView_Track);
            textView_TrackName.setText(track.getTitle());
            textView_TrackArtistName.setText(track.getArtist().getName());
        }

    }

    public interface CellListenerTracksRank{
        void play(Track track);
        void goToAlbum(Album album , ArtistGenerico artist);
    }


}
