package com.example.earplay.RegisterActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.earplay.LoginActivity.View_LoginActivity;
import com.example.earplay.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class View_RegisterActivity extends AppCompatActivity implements Contract_RegisterActivity.view {

    private Contract_RegisterActivity.Presenter presenter;


    @BindView(R.id.input_NameUser)
    EditText nameUser;

    @BindView(R.id.input_email)
    EditText emailUser;

    @BindView(R.id.input_password)
    EditText passwordUser;

    @BindView(R.id.btn_Register)
    AppCompatButton btn_confirm;

    @BindView(R.id.link_signup)
    TextView textView_signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_register);
        ButterKnife.bind(this);
        presenter = new Presenter_RegisterActivity(this,this);
        btn_confirm.setOnClickListener(confirmListener);
        textView_signUp.setOnClickListener(signUpListener);
    }

    @Override
    public void recibirElRegistroCompletado(boolean registrado) {
        if(registrado){
            Toast.makeText(this, getString(R.string.Usuario_Registrado), Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, View_LoginActivity.class);
            startActivity(i);
            finish();
        }else{
            Toast.makeText(this, getString(R.string.Intentar_De_Nuevo), Toast.LENGTH_SHORT).show();
        }
    }

    private void recibirDatos(){
        boolean mandarInfo =true;
        if(emailUser.getText().toString().trim().isEmpty()){
            mandarInfo = false;
            Toast.makeText(this, getString(R.string.Completar_Datos), Toast.LENGTH_SHORT).show();
        }
        if(passwordUser.getText().toString().trim().isEmpty()){
            mandarInfo = false;
            Toast.makeText(this, getString(R.string.Completar_Datos), Toast.LENGTH_SHORT).show();
        }
        if(nameUser.getText().toString().trim().isEmpty()){
            mandarInfo = false;
            Toast.makeText(this, getString(R.string.Completar_Datos), Toast.LENGTH_SHORT).show();
        }

        if(mandarInfo){
            presenter.recibirInfoDelUserRegistrado(nameUser.getText().toString().trim(),emailUser.getText().toString().trim(),passwordUser.getText().toString().trim());
        }
    }
    private View.OnClickListener confirmListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            recibirDatos();
        }
    };

    private View.OnClickListener signUpListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(getApplicationContext(),View_LoginActivity.class);
            startActivity(i);
            finish();
        }
    };
}
