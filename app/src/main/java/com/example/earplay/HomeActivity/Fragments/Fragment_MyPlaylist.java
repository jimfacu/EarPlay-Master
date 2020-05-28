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
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.earplay.HomeActivity.Adapters.Adapter_MyPlaylist;
import com.example.earplay.HomeActivity.Entities.Genericos.TrackGenerico;
import com.example.earplay.HomeActivity.Entities.MisPlaylist.ContainerMisPlaylist;
import com.example.earplay.HomeActivity.Entities.MisPlaylist.Playlist;
import com.example.earplay.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_MyPlaylist extends Fragment implements Adapter_MyPlaylist.PlaylistToAlbum{

    private static final String listPlaylistString= "ListPlaylistString";

    private ImageButton addPlaylist;
    private Dialog myDialog;
    private EditText namePlaylist;
    private AppCompatButton close;
    private AppCompatButton crearPlaylist;

    private List<Playlist> misPlaylists;
    private List<Playlist> tankPlaylist;
    private ContainerMisPlaylist containerMisPlaylist;

    private Adapter_MyPlaylist adapter_myPlaylist;
    private RecyclerView recyclerView_myPlaylists;

    private AddPlaylist addPlaylistInterface;


    public static Fragment_MyPlaylist buildFragmentHome(ContainerMisPlaylist containerMisPlaylist) {
        Fragment_MyPlaylist fragment_myPlaylist = new Fragment_MyPlaylist();
        if (containerMisPlaylist != null) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(listPlaylistString,containerMisPlaylist);
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
        Bundle bundle = getArguments();
        if(bundle != null){
            containerMisPlaylist = bundle.getParcelable(listPlaylistString);
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
        misPlaylists = new ArrayList<>();
        tankPlaylist = new ArrayList<>();
    }

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
        addPlaylistInterface.mostrarTracksOfMyPlaylist(containerMisPlaylist.getMiPlaylists().get(position));
    }

    public interface AddPlaylist{
        //Guardamos una nueva playlist creada recientemente
        void savePlaylistOnFirebase(ContainerMisPlaylist playlistList);
        //vamos al perfil de album para ver los tracks de mi playlist
        void mostrarTracksOfMyPlaylist(Playlist playlist);
    }

}
