package com.example.chani.logini;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.io.IOException;

//Clase MainActivity
//AppCompatActivity
//Esta es la clase del Login
public class MainActivity extends AppCompatActivity {

    //Declaración de variables constantes
    private FirebaseAuth firebaseAuth;
    EditText txtEmail, txtPass;
    GoogleSignInClient googleSignInClient;
    private static final int RC_SIGN_IN = 9001;


    //Inicialización de variables de vista
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();
        txtEmail = findViewById(R.id.txtMail);
        txtPass = findViewById(R.id.txtPass);
        //Opciones para API de autenticación con Google
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iniciarGoogle();
            }
        });
        googleSignInClient = GoogleSignIn.getClient(getApplicationContext(), gso);
        findViewById(R.id.btnIniciar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iniciarSesionCorreo();
            }
        });
    }

    //Metodo onStart
    //Verificar si existe una cuenta abierta
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser firebaseAuthCurrentUser = firebaseAuth.getCurrentUser();
        if(firebaseAuthCurrentUser != null){
            Intent intent = new Intent(getApplicationContext(), Menu.class);
            startActivity(intent);
        }
    }

    //Metodo registrar
    //Se envia a la actividad de Registro
    public void registrar(View view) {
        Intent intent = new Intent(getApplicationContext(), Registro.class);
        startActivity(intent);
    }


    //Metodo irMenu
    //Se envia a la actividad del Menu al iniciar sesión
    public void irMenu(){
        Intent intent = new Intent(getApplicationContext(), Menu.class);
        startActivity(intent);
    }

    //Metodo iniciarSesionCorreo
    //Recibe y valida los datos
    private void iniciarSesionCorreo() {
        String correo, pass;
        correo = txtEmail.getText().toString();
        pass = txtPass.getText().toString();
        if(pass.isEmpty()){
            txtPass.setError("Falta contraseña");
            return;
        }
        if(correo.isEmpty()){
            txtEmail.setError("Falta correo electronico");
            return;
        }
        firebaseAuth.signInWithEmailAndPassword(correo,pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            irMenu();
                        }else{
                            Toast.makeText(getApplicationContext(), "Ha ocurrido un error", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    //Metodo iniciarGoogle
    //Inicia el proceso para iniciar con la cuenta de Google
    public void iniciarGoogle() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    //Metodo onActivityResult
    //Se ejecuta cuando la actividad de autenticación de Google termina su ejecución
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN && resultCode == Activity.RESULT_OK) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            GoogleSignInAccount account = task.getResult();
            googleAuthFirebase(account);
        }
    }

    //Metodo googleAuthFirebase
    //Recibe una cuenta y con la cuenta inicia sesión en Firebase
    private void googleAuthFirebase(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            irMenu();
                        }else{
                            Toast.makeText(getApplicationContext(), "Ha ocurrido un error", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
