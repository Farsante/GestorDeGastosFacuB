package com.example.gestordegastosfacub.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gestordegastosfacub.MainActivity;
import com.example.gestordegastosfacub.R;
import com.example.gestordegastosfacub.databinding.ActivityLoginBinding;
import com.example.gestordegastosfacub.helpers.SessionPersistence;
import com.example.gestordegastosfacub.models.User;
import com.example.gestordegastosfacub.viewModels.LoginViewModel;

import java.util.Observable;
import com.example.gestordegastosfacub.*;

public class LoginActivity extends AppCompatActivity {
    /*private EditText etnombre, etcontrasña;
    private Button sesion;
    private User user;*/
    private ActivityLoginBinding binding;
    private LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (SessionPersistence.getUser()!=null){
            goToHomeActivity();
        }else{
            /*setContentView(R.layout.activity_login);
            etnombre = findViewById(R.id.usuario);
            etcontrasña = findViewById(R.id.contraseña);
            sesion = findViewById(R.id.btnSesion);---------- ACA SE REEMPLAZA POR BINDING QUE PERMITE MINIMIZAR LA CARGA DE CODIGO */
            binding = DataBindingUtil.setContentView(this,R.layout.activity_login);
            viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
            binding.setLoginViewModel(viewModel);
            setupObservers();
        }

        /*etnombre = findViewById(R.id.usuario);
        etcontrasña = findViewById(R.id.contraseña);*/

        /*sesion = (Button) findViewById(R.id.btnSesion);*/
        /*  sesion.setOnClickListener(new View.OnClickListener(){
            @Override
            public void  onClick(View view){
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
            }
        });------------------------------------------*/
    }

    public void goToHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }



    private void setupObservers() {
        viewModel.getUsername().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                viewModel.getUser().setUsername(s);
            }
        });
        viewModel.getPassword().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                viewModel.getUser().setPassword(s);
            }
        });

        viewModel.getLoginButton().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (validateField()) {
                    viewModel.saveUser();
                    goToHomeActivity();
                }
            }
        });
    }



    public String getUserName(){
        /*return etnombre.getText().toString();*/
        return viewModel.getUsername().getValue();
    }

    public String getPassword(){
            //return etcontrasña.getText().toString();
        return viewModel.getPassword().getValue();
    }

    public boolean validateField() {
        if (getUserName() == null || getUserName().isEmpty()) {
           /* etnombre.requestFocus();
            etnombre.setError("Debe completar nombre de usuario");*/
            binding.etusuario.requestFocus();
            binding.etusuario.setError("Debe completar nombre de usuario");
            return false;
        }
        if (getPassword() == null || getPassword().isEmpty()) {
           /* etpassword.requestFocus();
            etpassword.setError("Debe completar contraseña");*/
            binding.etpassword.requestFocus();
            binding.etpassword.setError("Debe completar contraseña");
            return false;
        }
        return true;
    }
    //Metodo para el boton
    /*public void sesion (View view){
        nuevas validaciones  -------------
        etnombre.setError(null);
        etpassword.setError(null);
    // ---------------------------------
        String nombre = etnombre.getText().toString();
        String pass = etpassword.getText().toString();


        if(nombre.length() == 0 ){
            Toast.makeText(this," Debe ingresar un nombre por favor ", Toast.LENGTH_LONG).show();
        }
        if(pass.length() == 0){
            Toast.makeText(this," Debes ingresar una contraseña por favor ", Toast.LENGTH_LONG).show();
        }
        if(nombre.length() !=0 && pass.length() !=0 ){
            Toast.makeText(this," Iniciando Sesion ... ", Toast.LENGTH_LONG).show();

        }
    //}
        // nuevas validaciones
        if(TextUtils.isEmpty(nombre)){
            etnombre.setError(getString(R.string.error_campo_obligatorio));
            etnombre.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(contraseña)){
            etpassword.setError(getString(R.string.error_campo_obligatorio));
            etpassword.requestFocus();
            return;
        }
    }*/


}
