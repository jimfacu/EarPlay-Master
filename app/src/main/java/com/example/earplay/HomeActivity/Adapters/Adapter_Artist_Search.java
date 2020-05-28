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
import com.example.earplay.HomeActivity.Entities.ArtistSearch.ArtistSearch;
import com.example.earplay.HomeActivity.Entities.Genericos.ArtistGenerico;
import com.example.earplay.HomeActivity.Entities.TracksRank.Artist;
import com.example.earplay.R;

import java.util.ArrayList;
import java.util.List;

public class Adapter_Artist_Search extends RecyclerView.Adapter {

    private List<ArtistSearch> artistSearchList;
    private List<ArtistSearch> tankArtistSearchList;
    private ArtistSearchProfile artistSearchProfile;

    public Adapter_Artist_Search(ArtistSearchProfile artistSearchProfile) {
        this.artistSearchList = new ArrayList<>();
        this.tankArtistSearchList = new ArrayList<>();
        this.artistSearchProfile = artistSearchProfile;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context =parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.cell_fragment_search_albums_tracks_artist,parent,false);
        ArtistSearchViewHolder viewHolder = new ArtistSearchViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ArtistSearch artistSearch = artistSearchList.get(position);
        ArtistSearchViewHolder searchViewHolder = (ArtistSearchViewHolder) holder;
        searchViewHolder.setArtistSearch(artistSearch);
    }

    public void insertArtistSearch(List<ArtistSearch> artistSearches){
        if(artistSearches!= null){
            tankArtistSearchList.clear();
            for(ArtistSearch artistSearch:artistSearches){
                tankArtistSearchList.add(artistSearch);
            }
        }
        artistSearchList.clear();
        artistSearchList.addAll(tankArtistSearchList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return artistSearchList.size();
    }

    class ArtistSearchViewHolder extends RecyclerView.ViewHolder {

        private TextView textView_nameArtist;
        private ImageView imageView_Artist;
        private TextView textView_artista;


        public ArtistSearchViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_nameArtist = itemView.findViewById(R.id.textView_NameAlbumSearch);
            imageView_Artist = itemView.findViewById(R.id.imageView_imageBandTrackAlbum);
            textView_artista = itemView.findViewById(R.id.textView_nameBandSearch);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ArtistGenerico artist = new ArtistGenerico(Integer.parseInt(artistSearchList.get(getAdapterPosition()).getId()),
                            artistSearchList.get(getAdapterPosition()).getName(),artistSearchList.get(getAdapterPosition()).getPicture_big());
                    artistSearchProfile.goToArtistProfile(artist);
                }
            });
        }

        public void setArtistSearch(ArtistSearch artistSearch){
            Glide.with(itemView).load(artistSearch.getPicture_medium()).into(imageView_Artist);
            textView_nameArtist.setText(artistSearch.getName());
            textView_artista.setText("Artista");
        }
    }

    public interface ArtistSearchProfile{
        void goToArtistProfile(ArtistGenerico artist);
    }
}
