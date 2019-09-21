package com.example.chani.logini;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.chani.logini.Modelos.Upload;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

/*
GaleriaStorageActivity
AppCompatActivity
Maneja la galería de Storage e imagenes
 */
public class GaleriaStorageActivity extends AppCompatActivity {

    DatabaseReference mDatabaseRef;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galeria_storage);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Upload");
        recyclerView = findViewById(R.id.recyclerViewGaleria);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),3,
                LinearLayoutManager.VERTICAL,false));

    }

    @Override
    protected void onStart() {
        super.onStart();
        //Recycler adapter para las imagenes de la base de datos
        FirebaseRecyclerAdapter<Upload, ViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Upload, ViewHolder>(
                        Upload.class, R.layout.image_item,
                        ViewHolder.class, mDatabaseRef
                ) {
                    @Override
                    protected void populateViewHolder(ViewHolder viewHolder, Upload imageModel, int i) {
                        viewHolder.setImage(getApplicationContext(), imageModel.getImageUrl(), imageModel.getName());
                    }
                };

        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }
}
