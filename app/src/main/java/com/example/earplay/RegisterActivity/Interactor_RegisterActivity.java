package com.example.earplay.RegisterActivity;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.earplay.R;
import com.example.earplay.Core.Entities.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Interactor_RegisterActivity implements Contract_RegisterActivity.Interactor{

    private static final String Users = "Users";

    private Contract_RegisterActivity.Presenter presenter;
    private Context context;
    private FirebaseAuth firebaseAuth ;
    private DatabaseReference reference;


    public Interactor_RegisterActivity(Contract_RegisterActivity.Presenter presenter, Context context) {
        this.presenter = presenter;
        this.context = context;
        firebaseAuth =  FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference(Users);
    }



    @Override
    public void registrarAlUsuario(String nombreUser, String emailUser, String passwordUser) {
        firebaseAuth.createUserWithEmailAndPassword(emailUser,passwordUser).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    User user = new User(nombreUser,emailUser,passwordUser,null,null);
                    reference.child(firebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                presenter.recibirRespuestaDelInteractorExito(context.getString(R.string.Usuario_Registrado));
                            }else{
                                presenter.recibirRespuestaDelInteractorFallo(context.getString(R.string.Intentar_De_Nuevo));
                            }
                        }
                    });
                }
            }
        });
    }
}
