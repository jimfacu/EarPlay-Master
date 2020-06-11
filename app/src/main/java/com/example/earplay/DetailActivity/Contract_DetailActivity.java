package com.example.earplay.DetailActivity;

import com.example.earplay.Core.Entities.Genericos.ContainerTracksFav;

public interface Contract_DetailActivity {


    interface View{
        void mostrarTracksFav(ContainerTracksFav containerTracksFav);
        void mostrarMensajeSinInternet();
        void mostrarMensaje(String s);
    }

    interface Presenter{
        void pedirTracksFavAlInteractor();
        void recibirTracksFavDelInteractor(ContainerTracksFav containerTracksFav);
        void guardarTracksFavNuevo(ContainerTracksFav containerTracksFav);
        void recibirMensajeDelInteractor(String s);
    }

    interface Interactor{
        void pedirTracksFav();
        void guardarTracksFav(ContainerTracksFav containerTracksFav);
    }
}
