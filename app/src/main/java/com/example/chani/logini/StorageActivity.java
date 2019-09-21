package com.example.chani.logini;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.chani.logini.Modelos.Upload;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

/*
StorageActivity
AppCompatActivity
Clase que maneja la actividad de el almacenamiento en la nube de google
 */
public class StorageActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int PICK_IMAGE_REQUEST = 9001;
    //Declaración de variables de vista
    Button btnChooseFile, btnUpload, btnShow;
    EditText txtNombre;
    ImageView ivFoto;
    ProgressBar progressBar;

    private Uri mImageUri;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;

    private StorageTask mUploadTask;

    /*
    metodo onCreate
    Inicializamos todas las variables de vista
    Y la referencia de la base de datos y de el storage
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);

        btnChooseFile = findViewById(R.id.btnBuscar);
        btnUpload = findViewById(R.id.btnSubir);
        btnUpload.setOnClickListener(this);
        btnShow=findViewById(R.id.btnShow);
        btnShow.setOnClickListener(this);
        btnChooseFile.setOnClickListener(this);
        txtNombre = findViewById(R.id.txtNombre);
        ivFoto = findViewById(R.id.ivFoto);
        progressBar= findViewById(R.id.progress_bar);

        mStorageRef= FirebaseStorage.getInstance().getReference("Upload");

        mDatabaseRef= FirebaseDatabase.getInstance().getReference("Upload");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnBuscar:
                seleccionarImagen();
                break;
            case R.id.btnSubir:

                if(mUploadTask!= null && mUploadTask.isInProgress()){
                    Toast.makeText(getApplicationContext(),getString(R.string.msgInProgress),Toast.LENGTH_SHORT).show();
                }

                subirArchivo();
                break;
            case R.id.btnShow:
                Intent intent = new Intent(getApplicationContext(), GaleriaStorageActivity.class);
                startActivity(intent);
                break;
        }
    }

    /*
    Metodo para subir el archivo guardado a el cloud storage
     */
    private void subirArchivo() {
        //Validar que tengamos imagen cargada
        if(mImageUri != null){
            //subimos archivo
            final StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()+ "."+getFileExtension(mImageUri));

            //abrir conexion o tarea para subir el archivo
            mUploadTask=fileReference.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    //abrir el hilo de conexion
                    Handler handler= new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(0);

                        }
                    }, 5000);

                    Toast.makeText(getApplicationContext(), getString(R.string.msgSuccess), Toast.LENGTH_SHORT) .show();

                    fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            //usamos nuestro modelo de datos para crear la estructura que subiremos a firebase dentro de la base de datos

                            Upload upload= new Upload(
                                txtNombre.getText().toString().trim(), uri.toString());

                    String uploadId= mDatabaseRef.push().getKey();
                    mDatabaseRef.child(uploadId).setValue(upload);
                            }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                    int p= (int) (100* (taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount()));
                    progressBar.setProgress(p);
                }
            });

        }else{
            Toast.makeText(getApplicationContext(), getString(R.string.msgNotFileSelected), Toast.LENGTH_SHORT) .show();
        }
    }

    /*
    getFileExtension
    Regresa la extensión del archivo a partir de una dirección tipo URI

     */
    private String getFileExtension(Uri uri) {
        ContentResolver cR= getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));

    }

    /*
    Lanza el intent para requerír una imagen
     */
    private void seleccionarImagen() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST );
    }

    /*
    onActivityResult que captura el resultado de escoger una imagen y guarda la data en mImageUri
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode==RESULT_OK
                && data!= null
                && data.getData()!= null){
            //consultamos la informacion que regresa el chooser de android
            mImageUri=data.getData();
            //previsualización de la imagen
            ivFoto.setImageURI(mImageUri);

        }

    }
}
