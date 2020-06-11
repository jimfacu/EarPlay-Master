package com.example.earplay.LoginActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.example.earplay.Core.Utils;
import com.example.earplay.R;

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
        if (Utils.internetAvalible(context)) {
            interactor.verificacionDeDatos(email, password);
        }else{
            if(view!= null){
                view.mostrarMensajeSinInternet();
            }
        }
    }

    @Override
    public void exitoDeInicioDeSesion() {
        if(view!= null){
            view.iniciarSesion();
        }
    }

    @Override
    public void falloAlIniciarSesion(String s) {
        if(view!= null){
            view.falloAlInciarSesion(s);
        }
    }
}
