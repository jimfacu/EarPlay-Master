package com.example.earplay.LoginActivity;

public interface Contract_LoginActivity {

    interface View{
        void iniciarSesion();
        void falloAlInciarSesion(String s);
        void mostrarMensajeSinInternet();
    }
    interface Presenter{
        void recibirDatosDeInicioDeSesion(String email,String password);
        void exitoDeInicioDeSesion();
        void falloAlIniciarSesion(String s);
    }

    interface Interactor{
        void verificacionDeDatos(String email,String password);
    }
}
