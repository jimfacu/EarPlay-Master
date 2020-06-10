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
import com.example.earplay.Core.Entities.AlbumsArtist.Albums;
import com.example.earplay.Core.Entities.TracksRank.Album;
import com.example.earplay.R;

import java.util.ArrayList;
import java.util.List;

public class Adapter_Albums_ArtistProfile extends RecyclerView.Adapter {

    private List<Albums> tankAlbumList;
    private List<Albums> albumList;
    private CellListenerAlbumsProfile cellListenerAlbumsProfile;


    public Adapter_Albums_ArtistProfile(CellListenerAlbumsProfile cellListenerAlbumsProfile) {
        this.tankAlbumList = new ArrayList<>();
        this.albumList = new ArrayList<>();
        this.cellListenerAlbumsProfile = cellListenerAlbumsProfile;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context =parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.cell_fragment_artist_profile_albums,parent,false);
        AlbumsArtistViewHolder viewHolder = new AlbumsArtistViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Albums album = albumList.get(position);
        AlbumsArtistViewHolder albumsArtistViewHolder = (AlbumsArtistViewHolder) holder;
        albumsArtistViewHolder.setAlbums(album);
    }

    public void insertAlbumsArtistProfile(List<Albums> listAlbums){
        if(listAlbums !=null){
            tankAlbumList.clear();
            for(Albums album:listAlbums){
                tankAlbumList.add(album);
            }
        }
        albumList.clear();
        albumList.addAll(tankAlbumList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }

    class AlbumsArtistViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView_Album;
        private TextView textView_nameAlbum;
        private TextView textView_ageAlbum;

        public AlbumsArtistViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView_Album = itemView.findViewById(R.id.imageView_AlbumProfileArtist);
            textView_nameAlbum = itemView.findViewById(R.id.textView_NameAlbumArtistProfile);
            textView_ageAlbum = itemView.findViewById(R.id.textView_YearAlbum);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Album album = new Album(Integer.parseInt(albumList.get(getAdapterPosition()).getId()),
                            albumList.get(getAdapterPosition()).getTitle(),albumList.get(getAdapterPosition()).getCover_medium());
                    cellListenerAlbumsProfile.goToAlbumProfile(album);
                }
            });
        }

        public void setAlbums(Albums album){
            Glide.with(itemView).load(album.getCover_medium()).into(imageView_Album);
            textView_nameAlbum.setText(album.getTitle());
            textView_ageAlbum.setText(album.getRelease_date().substring(0,4));
        }
    }

    public interface CellListenerAlbumsProfile{
        void goToAlbumProfile(Album albumGenerico);
    }
}
