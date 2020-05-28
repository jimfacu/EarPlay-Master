package com.example.earplay.LoginActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.earplay.HomeActivity.View_HomeActivity;
import com.example.earplay.R;
import com.example.earplay.RegisterActivity.View_RegisterActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class View_LoginActivity extends AppCompatActivity implements Contract_LoginActivity.View {

    private Contract_LoginActivity.Presenter presenter;
    private ProgressDialog progressDialog;

    @BindView(R.id.input_emailLogin)
    EditText emailUser;

    @BindView(R.id.input_passwordLogin)
    EditText passwordUser;

    @BindView(R.id.btn_login)
    AppCompatButton btnLogin;

    @BindView(R.id.link_register)
    TextView textView_Register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_login);
        ButterKnife.bind(this);
        presenter = new Presenter_LoginActivity(this,this);
        progressDialog = new ProgressDialog(getApplicationContext());

        btnLogin.setOnClickListener(loginListener);
        textView_Register.setOnClickListener(registerListener);

    }

    private void verificarDatos(){
        boolean mandarInfo = true;
        if(emailUser.getText().toString().trim().isEmpty()){
            mandarInfo = false;
            Toast.makeText(this, "Por favor , completar los campos solicitados", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }
        if(passwordUser.getText().toString().trim().isEmpty()){
            mandarInfo = false;
            Toast.makeText(this, "Por favor , completar los campos solicitados", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }

        if(mandarInfo){
            presenter.recibirDatosDeInicioDeSesion(emailUser.getText().toString().trim(),passwordUser.getText().toString().trim());
        }
    }

    private View.OnClickListener loginListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            progressDialog.setMessage("Logueandose...");
            progressDialog.dismiss();
            verificarDatos();
        }
    };



    @Override
    public void iniciarSesion(boolean loguearse) {
        if(loguearse){
            progressDialog.dismiss();
            Intent i = new Intent(this, View_HomeActivity.class);
            startActivity(i);
        }else{
            progressDialog.dismiss();
            Toast.makeText(this, "Credenciales errones, por favor intentar nuevamente", Toast.LENGTH_SHORT).show();
        }
    }

    private View.OnClickListener registerListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(getApplicationContext(),View_RegisterActivity.class);
            startActivity(i);
        }
    };
}
