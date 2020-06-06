package com.example.earplay.LoginActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

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
        if (internetAvalible()) {
            interactor.verificacionDeDatos(email, password);
        }else{
            Toast.makeText(context, context.getString(R.string.PorFavor_Conectarse_A_Internet), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void recibirVerificacionDeDatos(boolean loguearse) {
        if(view!=null){
            view.iniciarSesion(loguearse);
        }
    }

    private boolean internetAvalible(){
        boolean connected;
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            connected = true;
        }else{
            connected = false;
        }
        return connected;
    }
}
