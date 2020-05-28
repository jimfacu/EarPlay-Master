package com.example.earplay.RegisterActivity.Entities;

import com.example.earplay.HomeActivity.Entities.Genericos.FavTracks;
import com.example.earplay.HomeActivity.Entities.MisPlaylist.Playlist;

import java.util.List;

public class User {

    private String userName;
    private String email;
    private String password;
    private List<FavTracks> favTracks;
    private List<Playlist> myPlaylists;

    public User(String userName, String email, String password, List<FavTracks> favTracks, List<Playlist> myPlaylists) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.favTracks = favTracks;
        this.myPlaylists = myPlaylists;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<FavTracks> getFavTracks() {
        return favTracks;
    }

    public void setFavTracks(List<FavTracks> favTracks) {
        this.favTracks = favTracks;
    }

    public List<Playlist> getMyPlaylists() {
        return myPlaylists;
    }

    public void setMyPlaylists(List<Playlist> myPlaylists) {
        this.myPlaylists = myPlaylists;
    }
}
