package com.example.earplay.HomeActivity.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.earplay.HomeActivity.Entities.MisPlaylist.Playlist;
import com.example.earplay.HomeActivity.Utils.Common;
import com.example.earplay.HomeActivity.Utils.InterfaceUtils;
import com.example.earplay.R;

import java.util.ArrayList;
import java.util.List;


class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ImageView imageView_Playlist;
    private TextView textView_namePlaylist;


    InterfaceUtils.ItemClickListener interfaceUtils;

    public void setInterfaceUtils(InterfaceUtils.ItemClickListener interfaceUtils) {
        this.interfaceUtils = interfaceUtils;
    }

    public CustomViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView_Playlist = itemView.findViewById(R.id.imageView_MyPlaylist);
        textView_namePlaylist = itemView.findViewById(R.id.textView_NameMyPlaylist);
        itemView.setOnClickListener(this);
    }

    public void setPlaylist(Playlist playlist) {
        textView_namePlaylist.setText(playlist.getNamePlaylist());
        if(playlist.getTracksDeMiPlaylists()!= null) {
            Glide.with(itemView).load(playlist.getTracksDeMiPlaylists().get(playlist.getTracksDeMiPlaylists().size() - 1)).into(imageView_Playlist);
        }
    }

    @Override
    public void onClick(View view) {
        interfaceUtils.onClickItem(view, getAdapterPosition());
    }

}


    public class Adapter_MyPlaylistPopUp extends RecyclerView.Adapter<CustomViewHolder> {

        private List<Playlist> playlistList;
        private List<Playlist> tankPlaylistList;
        private int rox_index = -1;

        private PlaylistSelected playlistSelected;

        public Adapter_MyPlaylistPopUp(PlaylistSelected playlistSelected) {
            this.playlistList = new ArrayList<>();
            this.tankPlaylistList = new ArrayList<>();
            this.playlistSelected = playlistSelected;
        }

        @NonNull
        @Override
        public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            View view = layoutInflater.inflate(R.layout.cell_home_activity_myplaylist, parent, false);
            CustomViewHolder viewHolder = new CustomViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
            Playlist playlist = playlistList.get(position);
            CustomViewHolder customViewHolder = holder;
            customViewHolder.setPlaylist(playlist);
            holder.setInterfaceUtils(new InterfaceUtils.ItemClickListener() {
                @Override
                public void onClickItem(View view, int position) {
                    rox_index = position;
                    Common.playlistCurrent = playlistList.get(position);
                    playlistSelected.addToPlaylistSelected(position);
                    notifyDataSetChanged();
                }
            });

            if (rox_index == position) {
                holder.itemView.setBackgroundColor(Color.parseColor("#000000"));
            } else {
                holder.itemView.setBackgroundColor(Color.parseColor("#6f7a7b"));
            }
        }


        public void insertMyPlaylist(List<Playlist> listPlaylist) {
            if (listPlaylist != null) {
                tankPlaylistList.clear();
                for (Playlist playlist : listPlaylist) {
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

        public interface PlaylistSelected{
            void addToPlaylistSelected(int position);
        }
    }





