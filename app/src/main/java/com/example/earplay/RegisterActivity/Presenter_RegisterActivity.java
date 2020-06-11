package com.example.earplay.RegisterActivity;

import android.content.Context;

import com.example.earplay.Core.Utils;

public class Presenter_RegisterActivity implements Contract_RegisterActivity.Presenter{

    private Contract_RegisterActivity.view view;
    private Contract_RegisterActivity.Interactor interactor;
    private Context context;

    public Presenter_RegisterActivity(Contract_RegisterActivity.view view,Context context) {
        this.view = view;
        this.context = context;
        interactor = new Interactor_RegisterActivity(this,context);
    }

    @Override
    public void recibirInfoDelUserRegistrado(String nombreUser, String emailUser, String passwordUser) {
        if(Utils.internetAvalible(context)){
            interactor.registrarAlUsuario(nombreUser,emailUser,passwordUser);
        }else{
            if(view!= null){
                view.mostrarMensajeSinInternet();
            }
        }

    }

    @Override
    public void recibirRespuestaDelInteractorExito(String s) {
        if(view!= null){
            view.recibirElRegistroCompletado(s);
        }
    }

    @Override
    public void recibirRespuestaDelInteractorFallo(String s) {
        if(view!= null){
            view.MostrarMensajeDeErrorAlRegistrar(s);
        }
    }
}
