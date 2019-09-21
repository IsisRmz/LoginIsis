package com.example.chani.logini;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

//Clase Menu
//AppCompatActivity
//Esta clase es del menu
public class Menu extends AppCompatActivity {

    //Declaración de variables constantes
    FirebaseAuth firebaseAuth;
    TextView lblCorreo;

    //Inicialización de variables de vista
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        firebaseAuth = FirebaseAuth.getInstance();
        lblCorreo = findViewById(R.id.lblCorreo);

    }

    //Metodo onStart
    //Se guarda el correo del usuario actual en la etiqueta
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser firebaseAuthCurrentUser = firebaseAuth.getCurrentUser();
        if(firebaseAuthCurrentUser != null){
            lblCorreo.setText(firebaseAuthCurrentUser.getEmail());
        }
    }

    //Metodo irMultimedia
    //Manda a la actividad de Multimedia
    public void irMultimedia(View view) {
        Intent intent = new Intent(getApplicationContext(), Multimedia.class);
        startActivity(intent);
    }

    //Metodo cerrarSesion
    //Cierra la sesión en firebase y lanza la actividad de login
    public void cerrarSesion(View view) {
        firebaseAuth.signOut();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);

    }

    /*
     Metodo irBaseDatos
     El metodo nos permite ir a la actividad de la base de datos
     */
    public void irBaseDatos(View view) {
        Intent intent = new Intent(getApplicationContext(), DatabaseActivity.class);
        startActivity(intent);
    }

    /*
    metodo storage
    Lanza el intent para ir a la actividad del storage
     */
    public void storage(View view) {
        Intent intent = new Intent(getApplicationContext(), StorageActivity.class);
        startActivity(intent);
    }
}
