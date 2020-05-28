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
import com.example.earplay.HomeActivity.Entities.Genericos.AlbumGenerico;
import com.example.earplay.HomeActivity.Entities.Genericos.ArtistGenerico;
import com.example.earplay.HomeActivity.Entities.Genericos.TrackGenerico;
import com.example.earplay.HomeActivity.Entities.TracksRank.Track;
import com.example.earplay.R;

import java.util.ArrayList;
import java.util.List;

public class Adapter_Tracks_Search extends RecyclerView.Adapter {

    private List<Track> trackList;
    private List<Track> tankTrackList;
    private TracksAdapter_Interface tracksAdapterInterface;

    public Adapter_Tracks_Search(TracksAdapter_Interface tracksAdapterInterface) {
        this.trackList = new ArrayList<>();
        this.tankTrackList = new ArrayList<>();
        this.tracksAdapterInterface = tracksAdapterInterface;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context =parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.cell_fragment_search_albums_tracks_artist,parent,false);
        TracksSearchViewHolder viewHolder = new TracksSearchViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Track track = trackList.get(position);
        TracksSearchViewHolder tracksSearchViewHolder = (TracksSearchViewHolder) holder;
        tracksSearchViewHolder.setTracksSearch(track);
    }

    public void insertTracksSearch(List<Track> listTracksSearch){
        if(listTracksSearch!= null){
            tankTrackList.clear();
            for(Track track:listTracksSearch){
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

    class TracksSearchViewHolder extends RecyclerView.ViewHolder {

        private TextView textView_nameTracks;
        private TextView textView_nameBand;
        private ImageView imageView_Track;

        public TracksSearchViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_nameTracks = itemView.findViewById(R.id.textView_NameAlbumSearch);
            textView_nameBand = itemView.findViewById(R.id.textView_nameBandSearch);
            imageView_Track = itemView.findViewById(R.id.imageView_imageBandTrackAlbum);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    List<TrackGenerico> trackGenericoList = new ArrayList<>();
                    ArtistGenerico artistGenerico = new ArtistGenerico(trackList.get(getAdapterPosition()).getArtist().getId(),trackList.get(getAdapterPosition()).getArtist().getName(),
                            trackList.get(getAdapterPosition()).getArtist().getPicture_big());
                    AlbumGenerico albumGenerico = new AlbumGenerico(trackList.get(getAdapterPosition()).getAlbum().getId(),trackList.get(getAdapterPosition()).getAlbum().getTitle(),
                            trackList.get(getAdapterPosition()).getAlbum().getCover_medium());
                    TrackGenerico trackGenerico = new TrackGenerico(trackList.get(getAdapterPosition()).getId(),trackList.get(getAdapterPosition()).getTitle_short(),
                            trackList.get(getAdapterPosition()).getPreview(),trackList.get(getAdapterPosition()).getLink(),artistGenerico,albumGenerico);
                    trackGenericoList.add(trackGenerico);
                    tracksAdapterInterface.playTrack(trackGenericoList,0);
                }
            });
        }

        public void setTracksSearch(Track tracksSearch){
            Glide.with(itemView).load(tracksSearch.getAlbum().getCover_medium()).into(imageView_Track);
            textView_nameTracks.setText(tracksSearch.getTitle_short());
            textView_nameBand.setText(tracksSearch.getArtist().getName());
        }
    }

    public interface TracksAdapter_Interface{
        void playTrack(List<TrackGenerico> trackGenericoList,int position);
    }
}
