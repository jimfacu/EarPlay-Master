package com.example.earplay.LoginActivity;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;

import com.example.earplay.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Interactor_LoginActivity implements Contract_LoginActivity.Interactor {


    private Contract_LoginActivity.Presenter presenter;
    private Context context;
    private FirebaseAuth firebaseAuth;

    public Interactor_LoginActivity(Contract_LoginActivity.Presenter presenter, Context context) {
        this.presenter = presenter;
        this.context = context;
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void verificacionDeDatos(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    presenter.exitoDeInicioDeSesion();
                }else{
                    presenter.falloAlIniciarSesion(context.getString(R.string.Credenciales_Erroneas));
                }
            }
        });

    }
}
