package com.example.earplay.DetailActivity;

import com.example.earplay.HomeActivity.Entities.Genericos.ContainerTracksFav;

public interface Contract_DetailActivity {

    interface View{
        void mostrarTracksFav(ContainerTracksFav containerTracksFav);
    }

    interface Presenter{
       void pedirTracksFavAlInteractor();
       void recibirTracksFavDelInteractor(ContainerTracksFav containerTracksFav);
       void guardarTracksFavNuevo(ContainerTracksFav containerTracksFav);
    }

    interface Interactor{
        void pedirTracksFav();
        void guardarTracksFav(ContainerTracksFav containerTracksFav);
    }
}
