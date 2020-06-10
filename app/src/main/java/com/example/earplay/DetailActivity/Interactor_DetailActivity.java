package com.example.earplay.DetailActivity;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.earplay.Core.Entities.Genericos.ContainerTracksFav;
import com.example.earplay.Core.Entities.Genericos.FavTracks;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Interactor_DetailActivity implements Contract_DetailActivity.Interactor {

    private Contract_DetailActivity.Presenter presenter;
    private Context context;


    public Interactor_DetailActivity(Presenter_DetailActivity presenter, Context context) {
        this.presenter = presenter;
        this.context = context;
    }

    @Override
    public void pedirTracksFav() {
        List<FavTracks> favTrackstList = new ArrayList<>();
        ContainerTracksFav containerTracksFav = new ContainerTracksFav();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("favoriteTracks");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                favTrackstList.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    FavTracks favTracks = ds.getValue(FavTracks.class);
                    favTrackstList.add(favTracks);
                }
                containerTracksFav.setFavTracks(favTrackstList);
                presenter.recibirTracksFavDelInteractor(containerTracksFav);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void guardarTracksFav(ContainerTracksFav containerTracksFav) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("favoriteTracks");
        reference.setValue(containerTracksFav.getFavTracks())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                           // presenter.(true);
                        }else{
                           // presenter.recibirRsptaGuardadoPlaylist(false);
                            Toast.makeText(context, "Fallo al agregar cancion a favoritos", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
