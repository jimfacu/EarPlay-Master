package com.example.earplay.HomeActivity.Fragments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import androidx.appcompat.widget.SearchView;
import android.widget.Spinner;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.earplay.HomeActivity.Adapters.Adapter_Albums_Search;
import com.example.earplay.HomeActivity.Adapters.Adapter_Artist_Search;
import com.example.earplay.HomeActivity.Adapters.Adapter_Tracks_Search;
import com.example.earplay.Core.Entities.AlbumSearch.ContainerAlbumSearch;
import com.example.earplay.Core.Entities.ArtistSearch.ContainerArtistSearch;
import com.example.earplay.Core.Entities.Genericos.ArtistGenerico;
import com.example.earplay.Core.Entities.Genericos.TrackGenerico;
import com.example.earplay.Core.Entities.TracksRank.Album;
import com.example.earplay.Core.Entities.TracksRank.ContainerTracksRank;
import com.example.earplay.R;

import java.util.List;


public class Fragment_Search extends Fragment implements Adapter_Albums_Search.AlbumProfile ,Adapter_Artist_Search.ArtistSearchProfile
                                                            ,Adapter_Tracks_Search.TracksAdapter_Interface{

    private static final String listAlbumSearch = "ListAlbumSearch";
    private static final String listArtistSearch = "ListArtistSerch";
    private static final String listTracksSearch = "ListTracksSearch";

    private Toolbar toolbar;
    private Spinner spinner;
    private SearchView searchView;
    private String categoriaSeleccionda;

    private ContainerAlbumSearch containerAlbumSearch;
    private ContainerArtistSearch containerArtistSearch;
    private ContainerTracksRank containerTracksSearch;

    private Adapter_Albums_Search adapterAlbumsSearch;
    private RecyclerView recyclerViewAlbumsSearch;
    private Adapter_Artist_Search adapterArtistSearch;
    private RecyclerView recyclerViewArtistSearch;
    private Adapter_Tracks_Search adapterTracksSearch;
    private RecyclerView recyclerViewTracksSearch;

    private Abuscar abuscar;

    public static Fragment_Search buildFragmentArtist(ContainerArtistSearch containerArtist){
        Fragment_Search fragment_search = new Fragment_Search();
        Bundle bundle = new Bundle();
        if(containerArtist!= null){
            bundle.putParcelable(listArtistSearch,containerArtist);
            fragment_search.setArguments(bundle);
        }
        return fragment_search;
    }

    public static Fragment_Search buildFragmentTracks(ContainerTracksRank containerTracksRank){
        Fragment_Search fragment_search = new Fragment_Search();
        Bundle bundle = new Bundle();
        if(containerTracksRank != null){
            bundle.putParcelable(listTracksSearch,containerTracksRank);
            fragment_search.setArguments(bundle);
        }
        return fragment_search;
    }

    public static Fragment_Search buildFragmentAlbum(ContainerAlbumSearch containerAlbums){
        Fragment_Search fragment_search = new Fragment_Search();
        Bundle bundle = new Bundle();
        if(containerAlbums != null){
            bundle.putParcelable(listAlbumSearch,containerAlbums);
            fragment_search.setArguments(bundle);
        }
        return fragment_search;
    }

    public Fragment_Search() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        initViews(view);
        initAdapters(view);
        initSpinner();
        spinner.setOnItemSelectedListener(spinnerListener);
        searchView.setOnQueryTextListener(searchViewListener);
        Bundle bundle = getArguments();
        if(bundle !=null) {
            containerAlbumSearch = bundle.getParcelable(listAlbumSearch);
            containerArtistSearch = bundle.getParcelable(listArtistSearch);
            containerTracksSearch = bundle.getParcelable(listTracksSearch);
            if (containerArtistSearch != null) {
                adapterArtistSearch.insertArtistSearch(containerArtistSearch.getData());
                recyclerViewAlbumsSearch.setVisibility(View.GONE);
                recyclerViewTracksSearch.setVisibility(View.GONE);
            } else {
                if (containerAlbumSearch != null) {
                    adapterAlbumsSearch.insertAlbumsSearch(containerAlbumSearch.getData());
                    recyclerViewArtistSearch.setVisibility(View.GONE);
                    recyclerViewTracksSearch.setVisibility(View.GONE);
                } else {
                    if (containerTracksSearch != null)
                        adapterTracksSearch.insertTracksSearch(containerTracksSearch.getTracks());
                        recyclerViewArtistSearch.setVisibility(View.GONE);
                        recyclerViewAlbumsSearch.setVisibility(View.GONE);
                }
            }
        }


        return view;
    }

    private void initAdapters(View view) {
        adapterAlbumsSearch = new Adapter_Albums_Search(this);
        @SuppressLint("WrongConstant") LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerViewAlbumsSearch.setLayoutManager(linearLayoutManager1);
        recyclerViewAlbumsSearch.setAdapter(adapterAlbumsSearch);

        adapterArtistSearch = new Adapter_Artist_Search(getContext(),this);
        @SuppressLint("WrongConstant") LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerViewArtistSearch.setLayoutManager(linearLayoutManager2);
        recyclerViewArtistSearch.setAdapter(adapterArtistSearch);

        adapterTracksSearch = new Adapter_Tracks_Search(this);
        @SuppressLint("WrongConstant") LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerViewTracksSearch.setLayoutManager(linearLayoutManager3);
        recyclerViewTracksSearch.setAdapter(adapterTracksSearch);
    }

    private void initViews(View view){
        toolbar = view.findViewById(R.id.toolbar_Search);
        spinner = view.findViewById(R.id.spinner_Category);
        searchView = view.findViewById(R.id.searchCategory);
        recyclerViewAlbumsSearch = view.findViewById(R.id.recycler_AlbumSearch);
        recyclerViewArtistSearch = view.findViewById(R.id.recycler_ArtistSearch);
        recyclerViewTracksSearch = view.findViewById(R.id.recycler_TrackSearch);
    }

    private void initSpinner(){
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1
                ,getResources().getStringArray(R.array.categorys));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(myAdapter);
    }

        private Spinner.OnItemSelectedListener spinnerListener = new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                   categoriaSeleccionda = spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        };

        private SearchView.OnQueryTextListener searchViewListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                containerAlbumSearch = null;
                containerArtistSearch = null;
                containerTracksSearch = null;
                if(!s.isEmpty()) {
                    abuscar.buscar(categoriaSeleccionda, s);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.abuscar= (Abuscar) context;
    }

    @Override
    public void goToAlbumProfile(Album album, ArtistGenerico artist) {
        abuscar.goToAlbumProfile(album,artist);
    }

    @Override
    public void goToArtistProfile(ArtistGenerico artist) {
        abuscar.goToArtistProfile(artist);
    }

    @Override
    public void playTrack(List<TrackGenerico> trackGenericoList, int position) {
        abuscar.playTrack(trackGenericoList,position);
    }

    public interface Abuscar{
        void buscar(String categoria,String palabra);
        void goToAlbumProfile(Album album, ArtistGenerico artist);
        void goToArtistProfile(ArtistGenerico artist);
        void playTrack(List<TrackGenerico> trackGenericoList,int position);
    }

}
