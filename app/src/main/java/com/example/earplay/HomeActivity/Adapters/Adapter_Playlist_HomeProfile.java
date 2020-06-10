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
import com.example.earplay.Core.Entities.PlaylistRank.PlaylistRank;
import com.example.earplay.R;

import java.util.ArrayList;
import java.util.List;

public class Adapter_Playlist_HomeProfile extends RecyclerView.Adapter {

    private List<PlaylistRank> playlistRanksList;
    private List<PlaylistRank> tankPlaylistRankList;
    private CellListenerPlaylistRank cellListenerPlaylistRank;

    public Adapter_Playlist_HomeProfile(CellListenerPlaylistRank cellListenerPlaylistRank) {
        this.playlistRanksList = new ArrayList<>();
        this.tankPlaylistRankList = new ArrayList<>();
        this.cellListenerPlaylistRank = cellListenerPlaylistRank;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.cell_home_activity_playlistrank,parent,false);
        PlaylistRankViewHolder viewHolder = new PlaylistRankViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        PlaylistRank playlistRank = playlistRanksList.get(position);
        PlaylistRankViewHolder playlistRankViewHolder = (PlaylistRankViewHolder) holder;
        playlistRankViewHolder.setPlaylistRank(playlistRank);

    }

    public void insertPlaylistRank(List<PlaylistRank> playlistRanks){
        if(playlistRanks != null){
            for(PlaylistRank playlistRank :playlistRanks){
                tankPlaylistRankList.add(playlistRank);
            }
        }
        playlistRanksList.clear();
        playlistRanksList.addAll(tankPlaylistRankList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return playlistRanksList.size();
    }

    class PlaylistRankViewHolder extends  RecyclerView.ViewHolder {
        private ImageView imageView_PlaylistRank;
        private TextView textView_PlaylistRank;


        public PlaylistRankViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView_PlaylistRank = itemView.findViewById(R.id.imageView_PlaylistRank);
            textView_PlaylistRank = itemView.findViewById(R.id.textView_PlaylistNameRank);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cellListenerPlaylistRank.goToPlaylist(Long.parseLong(playlistRanksList.get(getAdapterPosition()).getId()));
                }
            });
        }

        public void setPlaylistRank(PlaylistRank playlistRank){
            Glide.with(itemView).load(playlistRank.getPicture()).into(imageView_PlaylistRank);
            textView_PlaylistRank.setText(playlistRank.getTitle());
        }
    }

    public interface CellListenerPlaylistRank{
        void goToPlaylist(long id);
        }
    }
