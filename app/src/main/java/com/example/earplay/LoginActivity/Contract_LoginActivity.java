package com.example.earplay.LoginActivity;

public interface Contract_LoginActivity {

    interface View{
        void iniciarSesion(boolean loguearse);
    }
    interface Presenter{
        void recibirDatosDeInicioDeSesion(String email,String password);
        void recibirVerificacionDeDatos(boolean loguearse);
        }

    interface Interactor{
        void verificacionDeDatos(String email,String password);
    }
}
