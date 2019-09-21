package com.example.chani.logini;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chani.logini.Modelos.ModeloActividades;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;

/*
Adaptador para las lecturas
Basado en el elemento FirebaseRecyclerAdapter
 */
class AdaptadorLecturasFirebase extends FirebaseRecyclerAdapter<ModeloActividades, LecturaHolder> {

        Context context;

        public AdaptadorLecturasFirebase(Class<ModeloActividades> modelClass, int modelLayout, Class<LecturaHolder> viewHolderClass, DatabaseReference ref, Context c)
        {
            super(modelClass, modelLayout, viewHolderClass, ref);
            context = c;
        }

        @Override
        protected void populateViewHolder(LecturaHolder viewHolder, final ModeloActividades model, int position) {
            //Llenamos el viewholder con los datos de el modelo
            viewHolder.lblLectura.setText(model.getLecture());
            viewHolder.lblGrupo.setText(model.getGroup());
            viewHolder.lblActividad.setText(model.getActivity());
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //metodo onClick para cada item en el recycler
                    Toast.makeText(context, model.getId(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, LecturaEditActivity.class);
                    intent.putExtra("id", model.getId());
                    context.startActivity(intent);

                }
            });
        }

    public static class StorageActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_storage);
        }
    }
}

