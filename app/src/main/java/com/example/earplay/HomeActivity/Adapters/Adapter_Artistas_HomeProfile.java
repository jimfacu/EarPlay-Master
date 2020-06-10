package com.example.earplay.HomeActivity.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.earplay.Core.Entities.ArtistRank.Artist;
import com.example.earplay.Core.Entities.Genericos.ArtistGenerico;
import com.example.earplay.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Adapter_Artistas_HomeProfile extends RecyclerView.Adapter {

    private List<Artist> listArtist;
    private List<Artist> tankListArtist;
    private CellListenerArtistRank cellListenerArtistRank;



    public Adapter_Artistas_HomeProfile(CellListenerArtistRank cellListenerArtistRank) {
        listArtist = new ArrayList<>();
        tankListArtist = new ArrayList<>();
        this.cellListenerArtistRank = cellListenerArtistRank;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context =parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.cell_fragment_home_artistas,parent,false);
        ArtistViewHolder viewHolder = new ArtistViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Artist artist = listArtist.get(position);
        ArtistViewHolder artistViewHolder = (ArtistViewHolder) holder;
        artistViewHolder.setArtist(artist);

    }

    public void insertListArtists(List<Artist> listaDeArtistasRank){
        if(listaDeArtistasRank != null){
            for(Artist artist : listaDeArtistasRank){
                tankListArtist.add(artist);
            }
        }
        listArtist.clear();
        listArtist.addAll(tankListArtist);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listArtist.size();
    }

    class ArtistViewHolder extends RecyclerView.ViewHolder{

        CircleImageView imageView_Artist;
        TextView nameArtist;

        public ArtistViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView_Artist = itemView.findViewById(R.id.circleImageView_ArtistRank);
            nameArtist = itemView.findViewById(R.id.textView_ArtistName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ArtistGenerico artist = new ArtistGenerico(listArtist.get(getAdapterPosition()).getId(),
                            listArtist.get(getAdapterPosition()).getName(),listArtist.get(getAdapterPosition()).getPicture_big());
                    cellListenerArtistRank.goToArtistProfile(artist);
                }
            });
        }

        public void setArtist(Artist artist){
            Glide.with(itemView).load(artist.getPicture_medium()).into(imageView_Artist);
            nameArtist.setText(artist.getName());
        }
    }

    public interface CellListenerArtistRank{
        void goToArtistProfile(ArtistGenerico artistGenerico);
    }
}
