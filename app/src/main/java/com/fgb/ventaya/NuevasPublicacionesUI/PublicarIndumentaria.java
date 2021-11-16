package com.fgb.ventaya.NuevasPublicacionesUI;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.fgb.ventaya.R;

public class PublicarIndumentaria extends AppCompatActivity {

    Toolbar myToolbar;
    Button botonSiguiente;
    EditText titulo, tipo, talle,telefono, marca, comentario, precio;
    private static final int CODIGO = 987;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_publicar_indumentaria);
        myToolbar = findViewById(R.id.toolbarIndumentaria);
        botonSiguiente = findViewById(R.id.buttonRegistrar);
        titulo = findViewById(R.id.titulo);
        tipo = findViewById(R.id.tipoIndumentaria);
        talle = findViewById(R.id.talleIndumentaria);
        telefono = findViewById(R.id.textTelefono);
        marca = findViewById(R.id.marcaIndumentaria);
        precio = findViewById(R.id.precioIndumentaria);
        comentario = findViewById(R.id.textDescripcion);
        setSupportActionBar(myToolbar);
        //para mostrar icono flecha atrás
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        botonSiguiente.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if(!validarCampos()){
                    Intent i = new Intent(PublicarIndumentaria.this, PantallaCargarImagenes.class);
                    i.putExtra("titulo", titulo.getText().toString());
                    i.putExtra("tipo", tipo.getText().toString());
                    i.putExtra("talle",talle.getText().toString());
                    i.putExtra("telefono", telefono.getText().toString());
                    i.putExtra("marca",marca.getText().toString());
                    i.putExtra("precio", precio.getText().toString());
                    i.putExtra("comentario", comentario.getText().toString());
                    i.putExtra("categoria","Indumentaria");
                    startActivityForResult(i,CODIGO);
                }


            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CODIGO) {
                finish();
            }
        }
    }

    //para aplicar funcionalidad flecha atrás
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    //funcion para validar campos para registrar el user
    public boolean validarCampos(){
        boolean validar=false;

        if (titulo.getText().toString().isEmpty()) {
            titulo.setError("El titulo no puede estar vacio!");
            validar=true;
        }
        if (tipo.getText().toString().isEmpty()) {
            tipo.setError("El tipo de indumentaria esta vacio!");
            validar=true;
        }
        if (talle.getText().toString().isEmpty()) {
            talle.setError("Necesitamos el talle del articulo!");
            validar=true;
        }
        if (telefono.getText().toString().isEmpty()) {
            telefono.setError("Necesitamos un telefono para que te contacten!");
            validar=true;
        }
        if (marca.getText().toString().isEmpty()) {
            marca.setError("Por favor, dinos la marca de la indumentaria!");
            validar=true;
        }
        if (comentario.getText().toString().isEmpty()) {
            comentario.setError("Describe con tus palabras el articulo!");
            validar=true;
        }

        if (precio.getText().toString().isEmpty()) {
            precio.setError("El precio es obligatorio para la publicacion!");
            validar=true;
        }
        return validar;
    }



}