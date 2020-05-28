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
import com.example.earplay.HomeActivity.Entities.AlbumsArtist.Albums;
import com.example.earplay.HomeActivity.Entities.Genericos.AlbumGenerico;
import com.example.earplay.HomeActivity.Entities.TracksRank.Album;
import com.example.earplay.R;

import java.util.ArrayList;
import java.util.List;

public class Adapter_Albums_AlbumProfile extends RecyclerView.Adapter {

    private List<Albums> tankAlbumsList;
    private List<Albums> albumsList;
    private CellListenerAlbumsProfile cellListenerAlbumsProfile;

    public Adapter_Albums_AlbumProfile(CellListenerAlbumsProfile cellListenerAlbumsProfile) {
        this.tankAlbumsList = new ArrayList<>();
        this.albumsList = new ArrayList<>();
        this.cellListenerAlbumsProfile = cellListenerAlbumsProfile;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context =parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.cell_fragment_album_profile_albums,parent,false);
        AlbumsAlbumProfileViewHolder viewHolder = new AlbumsAlbumProfileViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Albums albums = albumsList.get(position);
        AlbumsAlbumProfileViewHolder albumProfileViewHolder = (AlbumsAlbumProfileViewHolder) holder;
        albumProfileViewHolder.setAlbums(albums);
    }

    public void InsertAlbums(List<Albums> listAlbums){
        if(listAlbums != null){
            tankAlbumsList.clear();
            for(Albums albums:listAlbums){
                tankAlbumsList.add(albums);
            }
        }
        albumsList.clear();
        albumsList.addAll(tankAlbumsList);
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return albumsList.size();
    }

    class AlbumsAlbumProfileViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView_Album;
        private TextView textView_nameAlbum;
        private TextView textView_ageAlbum;

        public AlbumsAlbumProfileViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView_Album = itemView.findViewById(R.id.imageView_AlbumsProfileAlbum);
            textView_nameAlbum = itemView.findViewById(R.id.textView_NameAlbumAlbumProfile);
            textView_ageAlbum = itemView.findViewById(R.id.textView_YearAlbumProfile);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlbumGenerico albumTank = new AlbumGenerico(Integer.parseInt(albumsList.get(getAdapterPosition()).getId())
                    ,albumsList.get(getAdapterPosition()).getTitle(),albumsList.get(getAdapterPosition()).getCover_medium());
                    cellListenerAlbumsProfile.goToAlbumProfile(albumTank);
                }
            });
        }

        public void setAlbums(Albums albums){
            Glide.with(itemView).load(albums.getCover_medium()).into(imageView_Album);
            textView_nameAlbum.setText(albums.getTitle());
            textView_ageAlbum.setText(albums.getRelease_date().substring(0,4));
        }
    }

    public interface CellListenerAlbumsProfile{
        void goToAlbumProfile(AlbumGenerico albumGenerico);
    }
}
