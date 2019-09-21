package com.example.chani.logini;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/*
RegistroAcritivy
AppCompatActivity
Clase que maneja la actividad de registro
 */
public class Registro extends AppCompatActivity {

    EditText txtEmail, txtContra;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        txtContra = findViewById(R.id.txtContra);
        txtEmail = findViewById(R.id.txtEmail);
        firebaseAuth = FirebaseAuth.getInstance();

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser firebaseAuthCurrentUser = firebaseAuth.getCurrentUser();
        if(firebaseAuthCurrentUser != null){

        }
    }

    public void registrarse(View view) {
        crearUsuario();
    }

    /*
    crearUsuario
    Valida los datos y después crea un usuario con Email y contraseña
     */
    private void crearUsuario() {
        String correo, pass;
        correo = txtEmail.getText().toString();
        pass = txtContra.getText().toString();
        if(pass.isEmpty()){
            txtContra.setError("Falta contraseña");
            return;
        }
        if(correo.isEmpty()){
            txtEmail.setError("Falta correo electronico");
            return;
        }
        firebaseAuth.createUserWithEmailAndPassword(correo,pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Usuario creado de manera existosa", Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            Toast.makeText(getApplicationContext(), "Ha ocurrido un error", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }
}
