package com.fgb.ventaya.UI;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fgb.ventaya.Adapters.RecyclerAdapterPublicaciones;
import com.fgb.ventaya.Entity.Publicacion;
import com.fgb.ventaya.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class PantallaCategoriaSeleccionada extends AppCompatActivity {
    RecyclerView recycler;
    RecyclerAdapterPublicaciones recyclerAdapterPublicaciones;
    Toolbar myToolbar;
    String categoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_categoria_seleccionada);
        //inicializar elementos
        myToolbar = findViewById(R.id.toolbarCategoriaSeleccionada);
        setSupportActionBar(myToolbar);
        recycler= (RecyclerView) findViewById(R.id.recyclerPublicacionesCategorias);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        //para mostrar icono flecha atrás
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

       categoria = getIntent().getExtras().getString("categoria");

        this.setTitle("Categoria "+categoria);
        FirebaseRecyclerOptions<Publicacion> options =
                new FirebaseRecyclerOptions.Builder<Publicacion>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Publicacion").orderByChild("categoria").equalTo(categoria),Publicacion.class)
                        .build();

        recyclerAdapterPublicaciones = new RecyclerAdapterPublicaciones(options);
        recycler.setAdapter(recyclerAdapterPublicaciones);





    }


    @Override
    public void onStart() {
        super.onStart();
        recyclerAdapterPublicaciones.startListening();
    }
    @Override
    public  void onStop() {
        super.onStop();
        recyclerAdapterPublicaciones.stopListening();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;


        }

        return super.onOptionsItemSelected(item);

    }
}
