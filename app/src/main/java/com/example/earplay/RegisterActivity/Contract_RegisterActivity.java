package com.example.earplay.RegisterActivity;

public interface Contract_RegisterActivity {

    interface view{
        void recibirElRegistroCompletado(boolean registrado);
    }

    interface Presenter{

        void recibirInfoDelUserRegistrado(String nombreUser,String emailUser,String passwordUser);
        void recibirRespuestaDelInteractor(boolean registrado);
    }

    interface Interactor{

        void registrarAlUsuario(String nombreUser,String emailUser,String passwordUser);

    }

}
