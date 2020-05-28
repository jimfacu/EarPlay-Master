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
import com.example.earplay.HomeActivity.Entities.AlbumSearch.AlbumsSearch;
import com.example.earplay.HomeActivity.Entities.Genericos.ArtistGenerico;
import com.example.earplay.HomeActivity.Entities.TracksRank.Album;
import com.example.earplay.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class Adapter_Albums_Search extends RecyclerView.Adapter {

    private List<AlbumsSearch> albumsSearchList;
    private List<AlbumsSearch> tankAlbumSearchList;
    private AlbumProfile albumProfile;

    public Adapter_Albums_Search(AlbumProfile albumProfile) {
        this.albumsSearchList = new ArrayList<>();
        this.tankAlbumSearchList = new ArrayList<>();
        this.albumProfile= albumProfile;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context =parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.cell_fragment_search_albums_tracks_artist,parent,false);
        AlbumSearchViewHolder viewHolder = new AlbumSearchViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        AlbumsSearch albumsSearch = albumsSearchList.get(position);
        AlbumSearchViewHolder searchViewHolder = (AlbumSearchViewHolder) holder;
        searchViewHolder.setAlbumsSearch(albumsSearch);
    }

    public void insertAlbumsSearch(List<AlbumsSearch> albumsSearches){
        if(albumsSearches != null){
            tankAlbumSearchList.clear();
            for(AlbumsSearch albumsSearch :albumsSearches){
                tankAlbumSearchList.add(albumsSearch);
            }
        }
        albumsSearchList.clear();
        albumsSearchList.addAll(tankAlbumSearchList);
        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return albumsSearchList.size();
    }

    class AlbumSearchViewHolder extends RecyclerView.ViewHolder {

        private TextView textView_nameAlbum;
        private TextView textView_nameBand;
        private ImageView imageView_Album;

        public AlbumSearchViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_nameAlbum = itemView.findViewById(R.id.textView_NameAlbumSearch);
            textView_nameBand = itemView.findViewById(R.id.textView_nameBandSearch);
            imageView_Album = itemView.findViewById(R.id.imageView_imageBandTrackAlbum);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ArtistGenerico artist = new ArtistGenerico(albumsSearchList.get(getAdapterPosition()).getArtist().getId(),albumsSearchList.get(getAdapterPosition()).getArtist().getName()
                                                ,albumsSearchList.get(getAdapterPosition()).getArtist().getPicture_big());
                    Album album = new Album(Integer.parseInt(albumsSearchList.get(getAdapterPosition()).getId()),albumsSearchList.get(getAdapterPosition()).getTitle()
                                            ,albumsSearchList.get(getAdapterPosition()).getCover_medium());
                    albumProfile.goToAlbumProfile(album,artist);
                }
            });
        }

        public void setAlbumsSearch(AlbumsSearch albumsSearch){
            Glide.with(itemView).load(albumsSearch.getCover_medium()).into(imageView_Album);
            textView_nameBand.setText(albumsSearch.getArtist().getName());
            textView_nameAlbum.setText(albumsSearch.getTitle());
        }
    }
    public interface AlbumProfile{
        void goToAlbumProfile(Album album , ArtistGenerico artist);
    }
}
