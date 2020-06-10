package com.example.earplay.HomeActivity.Fragments;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.earplay.HomeActivity.Adapters.Adapter_MyPlaylist;
import com.example.earplay.Core.Entities.Genericos.ContainerTracksFav;
import com.example.earplay.Core.Entities.Genericos.FavTracks;
import com.example.earplay.Core.Entities.Genericos.TrackGenerico;
import com.example.earplay.Core.Entities.MisPlaylist.ContainerMisPlaylist;
import com.example.earplay.Core.Entities.MisPlaylist.Playlist;
import com.example.earplay.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_MyPlaylist extends Fragment implements Adapter_MyPlaylist.PlaylistToAlbum{

    private static final String listPlaylistString= "ListPlaylistString";
    private static final String TracksFav = "TracksFavourites";

    private ImageButton addPlaylist;
    private ImageButton imageButton_signOut;
    private ImageView imageView_FavTracks;
    private Dialog myDialog;
    private EditText namePlaylist;
    private AppCompatButton close;
    private AppCompatButton crearPlaylist;

    private ContainerMisPlaylist containerMisPlaylist;
    private ContainerTracksFav containerTracksFav;

    private Adapter_MyPlaylist adapter_myPlaylist;
    private RecyclerView recyclerView_myPlaylists;

    private AddPlaylist addPlaylistInterface;


    public static Fragment_MyPlaylist buildFragmentHome(ContainerMisPlaylist containerMisPlaylist,ContainerTracksFav containerTracksFav) {
        Fragment_MyPlaylist fragment_myPlaylist = new Fragment_MyPlaylist();
        if (containerMisPlaylist != null && containerTracksFav != null) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(listPlaylistString,containerMisPlaylist);
            bundle.putParcelable(TracksFav,containerTracksFav);
            fragment_myPlaylist.setArguments(bundle);
        }
        return fragment_myPlaylist;
    }

    public Fragment_MyPlaylist() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_playlist, container, false);
        initViews(view);
        initAdapter(view);
        myDialog = new Dialog(getContext());
        addPlaylist.setOnClickListener(addPlaylistListener);
        imageButton_signOut.setOnClickListener(signOutListener);
        imageView_FavTracks.setOnClickListener(goToFavTracks);
        Bundle bundle = getArguments();
        if(bundle != null){
            containerMisPlaylist = bundle.getParcelable(listPlaylistString);
            containerTracksFav =bundle.getParcelable(TracksFav);
            adapter_myPlaylist.insertMisPlaylist(containerMisPlaylist.getMiPlaylists());
        }
        return view;
    }

    private void initAdapter(View view) {
        adapter_myPlaylist = new Adapter_MyPlaylist(getContext(),this);
        @SuppressLint("WrongConstant") LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView_myPlaylists.setLayoutManager(linearLayoutManager1);
        recyclerView_myPlaylists.addItemDecoration(new DividerItemDecoration(recyclerView_myPlaylists.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView_myPlaylists.setAdapter(adapter_myPlaylist);
    }

    private void initViews(View view) {
        addPlaylist = view.findViewById(R.id.addPlaylist);
        recyclerView_myPlaylists = view.findViewById(R.id.recycler_MyPlaylists);
        imageView_FavTracks = view.findViewById(R.id.imageView_TracksFav);
        imageButton_signOut = view.findViewById(R.id.imageButton_config);
    }

    private View.OnClickListener signOutListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Context context = getContext();
            PopupMenu popupMenu = new PopupMenu(context,imageButton_signOut);
            popupMenu.inflate(R.menu.menu_sign_out);
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {

                    switch (menuItem.getItemId()) {
                        case R.id.signOut:
                            addPlaylistInterface.signOut();
                            break;
                        default:
                            break;
                    }
                    return false;
                }
            });
            popupMenu.show();
        }
    };


    private View.OnClickListener goToFavTracks = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (containerTracksFav != null) {
                List<TrackGenerico> trackGenericoList = new ArrayList<>();
                for (FavTracks favTracks : containerTracksFav.getFavTracks()) {
                    TrackGenerico trackGenerico = new TrackGenerico(favTracks.getId(), favTracks.getTitle_short(), favTracks.getPreview()
                            ,favTracks.getLink(), favTracks.getArtistGenerico(), favTracks.getAlbumGenerico());
                    trackGenericoList.add(trackGenerico);
                }
                if(containerTracksFav.getFavTracks().size()>0) {
                    Playlist playlist = new Playlist(trackGenericoList, getContext().getString(R.string.FavTracks));
                    addPlaylistInterface.mostrarTracksOfMyPlaylist(playlist,-1);
                }else{
                    Toast.makeText(getContext(), getContext().getString(R.string.Album_de_favoritos_sin_canciones), Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    private View.OnClickListener addPlaylistListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            myDialog.setContentView(R.layout.custompopup_newplaylist);
            close = myDialog.findViewById(R.id.btn_CancelarMyPlaylist);
            crearPlaylist = myDialog.findViewById(R.id.btn_AceptarMyPlaylist);
            namePlaylist = myDialog.findViewById(R.id.editText_TituloMyPlaylist);
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    myDialog.dismiss();
                }
            });
            crearPlaylist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Playlist newPlaylist = new Playlist();
                    if(containerMisPlaylist == null){
                        containerMisPlaylist = new ContainerMisPlaylist();
                    }
                    if (!namePlaylist.getText().toString().isEmpty()) {
                        newPlaylist.setNamePlaylist(namePlaylist.getText().toString().trim());
                        containerMisPlaylist.getMiPlaylists().add(newPlaylist);
                        addPlaylistInterface.savePlaylistOnFirebase(containerMisPlaylist);
                        adapter_myPlaylist.insertMisPlaylist(containerMisPlaylist.getMiPlaylists());
                        myDialog.dismiss();
                    }
                }
            });
            myDialog.show();
        }
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.addPlaylistInterface= (AddPlaylist) context;
    }

    @Override
    public void goToAlbumProfile(int position) {
        addPlaylistInterface.mostrarTracksOfMyPlaylist(containerMisPlaylist.getMiPlaylists().get(position),position);
    }

    public interface AddPlaylist{
        void savePlaylistOnFirebase(ContainerMisPlaylist playlistList);
        void mostrarTracksOfMyPlaylist(Playlist playlist, int position);
        void signOut();
    }
}
