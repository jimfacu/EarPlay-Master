package com.example.earplay.DetailActivity;

import android.content.Context;

import com.example.earplay.Core.Entities.Genericos.ContainerTracksFav;
import com.example.earplay.Core.Utils;

public class Presenter_DetailActivity  implements Contract_DetailActivity.Presenter {

    private Contract_DetailActivity.View view;
    private Contract_DetailActivity.Interactor interactor;
    private Context context;

    public Presenter_DetailActivity(Contract_DetailActivity.View view, Context context) {
        this.view = view;
        this.context = context;
        interactor = new Interactor_DetailActivity(this,context);
    }


    @Override
    public void pedirTracksFavAlInteractor() {
        if(Utils.internetAvalible(context)){
            interactor.pedirTracksFav();
        }else{
            if(view!= null){
                view.mostrarMensajeSinInternet();
            }
        }
    }

    @Override
    public void recibirTracksFavDelInteractor(ContainerTracksFav containerTracksFav) {
        if(view!= null){
            view.mostrarTracksFav(containerTracksFav);
        }
    }

    @Override
    public void guardarTracksFavNuevo(ContainerTracksFav containerTracksFav) {
        interactor.guardarTracksFav(containerTracksFav);
    }

    @Override
    public void recibirMensajeDelInteractor(String s) {
        if(view!= null){
            view.mostrarMensaje(s);
        }
    }
}
