package com.example.earplay.RegisterActivity;

import android.content.Context;

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
        interactor.registrarAlUsuario(nombreUser,emailUser,passwordUser);
    }

    @Override
    public void recibirRespuestaDelInteractor(boolean regisrado) {
        if(view != null){
            view.recibirElRegistroCompletado(regisrado);
        }
    }
}
