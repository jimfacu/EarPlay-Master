package com.example.earplay.HomeActivity.Utils;

import android.view.View;

import com.example.earplay.HomeActivity.Entities.AlbumProfile.TracksFromAlbumProfile;
import com.example.earplay.HomeActivity.Entities.MisPlaylist.TracksDeMiPlaylist;
import com.example.earplay.HomeActivity.Entities.TracksRank.Track;

public interface InterfaceUtils {

    interface ItemClickListener {
        void onClickItem(View view, int position);
    }

    interface ShowMyPlaylists{
        void goToShowMyPlaylists(TracksDeMiPlaylist tracksFromAlbumProfile);
    }
}
