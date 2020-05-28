package com.example.earplay.LoginActivity;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Interactor_LoginActivity implements Contract_LoginActivity.Interactor {

    private Contract_LoginActivity.Presenter presenter;
    private Context context;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    public Interactor_LoginActivity(Contract_LoginActivity.Presenter presenter, Context context) {
        this.presenter = presenter;
        this.context = context;
    }

    @Override
    public void verificacionDeDatos(String email, String password) {
     firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
         @Override
         public void onComplete(@NonNull Task<AuthResult> task) {
             if(task.isSuccessful()){
                 presenter.recibirVerificacionDeDatos(true);
             }else{
                 presenter.recibirVerificacionDeDatos(false);
             }
         }
     });

    }
}
