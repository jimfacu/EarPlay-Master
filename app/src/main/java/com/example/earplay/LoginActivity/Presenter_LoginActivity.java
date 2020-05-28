package com.example.earplay.LoginActivity;

import android.content.Context;

public class Presenter_LoginActivity implements Contract_LoginActivity.Presenter {

    private Contract_LoginActivity.View view;
    private Contract_LoginActivity.Interactor interactor;
    private Context context;

    public Presenter_LoginActivity(Contract_LoginActivity.View view, Context context) {
        this.view = view;
        this.context = context;
        interactor = new Interactor_LoginActivity(this,context);
    }

    @Override
    public void recibirDatosDeInicioDeSesion(String email, String password) {
        interactor.verificacionDeDatos(email,password);
    }

    @Override
    public void recibirVerificacionDeDatos(boolean loguearse) {
        if(view!=null){
            view.iniciarSesion(loguearse);
        }
    }
}
