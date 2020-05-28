package com.example.earplay.HomeActivity.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.earplay.HomeActivity.Entities.Genericos.TrackGenerico;
import com.example.earplay.HomeActivity.Entities.MisPlaylist.Playlist;
import com.example.earplay.HomeActivity.Entities.MisPlaylist.TracksDeMiPlaylist;
import com.example.earplay.R;

import java.util.ArrayList;
import java.util.List;

public class Adapter_MyPlaylist extends RecyclerView.Adapter {

    private List<Playlist> playlistList ;
    private List<Playlist> tankPlaylistList;
    private PlaylistToAlbum playlistToAlbum;
    private Context context;

    public Adapter_MyPlaylist(Context context,PlaylistToAlbum playlistToAlbum) {
        playlistList = new ArrayList<>();
        tankPlaylistList = new ArrayList<>();
        this.playlistToAlbum = playlistToAlbum;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context =parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.cell_home_activity_myplaylist,parent,false);
        MyPlaylistsViewHolder viewHolder = new MyPlaylistsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Playlist playlist = playlistList.get(position);
        MyPlaylistsViewHolder myPlaylistsViewHolder = (MyPlaylistsViewHolder) holder;
        myPlaylistsViewHolder.setPlaylist(playlist);


    }

    public void insertMisPlaylist(List<Playlist> listPlaylist){
        if(listPlaylist!= null){
            tankPlaylistList.clear();
            for(Playlist playlist : listPlaylist){
                tankPlaylistList.add(playlist);
            }
        }
        playlistList.clear();
        playlistList.addAll(tankPlaylistList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return playlistList.size();
    }

    class MyPlaylistsViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView_Playlist;
        private TextView texView_namePlaylist;

        public MyPlaylistsViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView_Playlist = itemView.findViewById(R.id.imageView_MyPlaylist);
            texView_namePlaylist = itemView.findViewById(R.id.textView_NameMyPlaylist);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(playlistList.get(getAdapterPosition()).getTracksDeMiPlaylists() != null) {
                        playlistToAlbum.goToAlbumProfile(getAdapterPosition());
                    }else{
                        Toast.makeText(context, "Playlist sin canciones ", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        public void setPlaylist(Playlist playlist){
            if(playlist.getTracksDeMiPlaylists()!= null) {
                Glide.with(itemView).load(playlist.getTracksDeMiPlaylists().get(playlist.getTracksDeMiPlaylists().size()-1).getAlbumGenerico().getCover_medium()).into(imageView_Playlist);
            }
            texView_namePlaylist.setText(playlist.getNamePlaylist());
        }
    }


    public interface PlaylistToAlbum{
        //pasamos la posicion de la playlist seleccionada
        void goToAlbumProfile(int position);
    }

}
