package com.example.earplay.HomeActivity.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.earplay.HomeActivity.Entities.Genericos.AlbumGenerico;
import com.example.earplay.HomeActivity.Entities.Genericos.ArtistGenerico;
import com.example.earplay.HomeActivity.Entities.Genericos.TrackGenerico;
import com.example.earplay.HomeActivity.Entities.MisPlaylist.TracksDeMiPlaylist;
import com.example.earplay.HomeActivity.Entities.TracksRank.Album;
import com.example.earplay.HomeActivity.Entities.TracksRank.Artist;
import com.example.earplay.HomeActivity.Entities.TracksRank.Track;
import com.example.earplay.HomeActivity.Utils.InterfaceUtils;
import com.example.earplay.R;

import java.util.ArrayList;
import java.util.List;

public class Adapter_Tracks_ArtistProfile extends RecyclerView.Adapter  {

    private List<Track> trackList;
    private List<Track> tankTrackList;
    private Context context;
    private TracksArtistProfile_Interface tracksArtistProfileInterface;
    private ArtistGenerico artistGenerico;

    public Adapter_Tracks_ArtistProfile(Context context, TracksArtistProfile_Interface tracksArtistProfileInterface, ArtistGenerico artistGenerico) {
        this.trackList = new ArrayList<>();
        this.tankTrackList = new ArrayList<>();
        this.tracksArtistProfileInterface = tracksArtistProfileInterface;
        this.context = context;
        this.artistGenerico = artistGenerico;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.cell_fragment_artist_profile_tracks,parent,false);
        ArtistProfileViewHolder viewHolder = new ArtistProfileViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Track track = trackList.get(position);
        ArtistProfileViewHolder artistProfileViewHolder = (ArtistProfileViewHolder) holder;
        artistProfileViewHolder.setTracks(track);

    }

    public void insertTracksArtistProfile(List<Track> listTracks){
        if(listTracks!= null){
            tankTrackList.clear();
            for(Track track:listTracks){
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



    class ArtistProfileViewHolder extends RecyclerView.ViewHolder {

        private TextView textView_numberSong;
        private TextView textView_nameSong;
        private TextView textView_options;

        public ArtistProfileViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_nameSong = itemView.findViewById(R.id.textView_nameSongProfileArtist);
            textView_numberSong = itemView.findViewById(R.id.textView_numberSongArtistProfile);
            textView_options = itemView.findViewById(R.id.textView_Options);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    List<TrackGenerico> tracksToPlay = new ArrayList<>();
                    for(Track t : trackList) {
                        AlbumGenerico album = new AlbumGenerico(t.getAlbum().getId(),t.getAlbum().getTitle(),t.getAlbum().getCover_medium());
                        TrackGenerico trackGenerico = new TrackGenerico(t.getId(),t.getTitle_short(),t.getPreview(),t.getLink(),artistGenerico,album);
                        tracksToPlay.add(trackGenerico);
                    }
                    tracksArtistProfileInterface.playTrack(tracksToPlay,getAdapterPosition());

                }
            });
        }

        public void setTracks(Track tracks){
            textView_nameSong.setText(tracks.getTitle());
            textView_numberSong.setText(String.valueOf(getAdapterPosition()+1));
            textView_options.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popupMenu = new PopupMenu(context,textView_options);
                    popupMenu.inflate(R.menu.menu_options_tracks);
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {

                            switch (menuItem.getItemId()){
                                case R.id.addTrackToMyPlaylist:
                                    AlbumGenerico albumGenerico = new AlbumGenerico(trackList.get(getAdapterPosition()).getAlbum().getId()
                                    ,trackList.get(getAdapterPosition()).getAlbum().getTitle(),trackList.get(getAdapterPosition()).getAlbum().getCover_medium());
                                    TrackGenerico trackMyPlaylist = new TrackGenerico(trackList.get(getAdapterPosition()).getId()
                                    ,trackList.get(getAdapterPosition()).getTitle_short(),trackList.get(getAdapterPosition()).getPreview()
                                    ,trackList.get(getAdapterPosition()).getLink(),null,albumGenerico);
                                    tracksArtistProfileInterface.goToMyPlaylists(trackMyPlaylist);
                                    break;

                                case R.id.shareTrack:
                                    break;

                                default:
                                    break;
                            }

                            return false;
                        }
                    });
                    popupMenu.show();
                }
            });
        }
    }

    public interface TracksArtistProfile_Interface{
        void goToMyPlaylists(TrackGenerico track);
        void playTrack(List<TrackGenerico> trackGenericoList,int position);
    }
}
