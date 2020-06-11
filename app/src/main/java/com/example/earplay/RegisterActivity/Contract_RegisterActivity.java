package com.example.earplay.RegisterActivity;

public interface Contract_RegisterActivity {


    interface view{
        void recibirElRegistroCompletado(String s);
        void MostrarMensajeDeErrorAlRegistrar(String s);
        void mostrarMensajeSinInternet();
    }

    interface Presenter{

        void recibirInfoDelUserRegistrado(String nombreUser,String emailUser,String passwordUser);
        void recibirRespuestaDelInteractorExito(String s);
        void recibirRespuestaDelInteractorFallo(String s);
    }

    interface Interactor{

        void registrarAlUsuario(String nombreUser,String emailUser,String passwordUser);

    }
}
