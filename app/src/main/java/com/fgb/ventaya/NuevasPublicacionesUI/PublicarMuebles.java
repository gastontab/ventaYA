package com.fgb.ventaya.NuevasPublicacionesUI;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.fgb.ventaya.R;

public class PublicarMuebles extends AppCompatActivity {

    Toolbar myToolbar;
    Button botonSiguiente;
    EditText titulo, tipo, telefono, peso, comentario, precio;
    private static final int CODIGO = 987;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_publicar_muebles);
        myToolbar = findViewById(R.id.toolbarMuebles);
        botonSiguiente = findViewById(R.id.buttonRegistrar);
        titulo = findViewById(R.id.titulo);
        tipo = findViewById(R.id.tipoMueble);
        telefono = findViewById(R.id.textTelefono);
        peso = findViewById(R.id.pesoMueble);
        comentario = findViewById(R.id.textDescripcion);
        precio = findViewById(R.id.precioMueble);

        setSupportActionBar(myToolbar);
        //para mostrar icono flecha atrás
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        botonSiguiente.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                if(!validarCampos()) {
                    Intent i = new Intent(PublicarMuebles.this, PantallaCargarImagenes.class);
                    i.putExtra("titulo", titulo.getText().toString());
                    i.putExtra("tipo", tipo.getText().toString());
                    i.putExtra("telefono", telefono.getText().toString());
                    i.putExtra("peso", peso.getText().toString());
                    i.putExtra("precio", precio.getText().toString());
                    i.putExtra("comentario", comentario.getText().toString());
                    i.putExtra("categoria", "Muebles");
                    startActivityForResult(i, CODIGO);
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

    public boolean validarCampos(){
        boolean validar=false;
        if (titulo.getText().toString().isEmpty()) {
            titulo.setError("El titulo no puede estar vacio!");
            validar=true;
        }
        if (tipo.getText().toString().isEmpty()) {
            tipo.setError("Por favor, dinos de que tipo es!");
            validar=true;
        }
        if (peso.getText().toString().isEmpty()) {
            peso.setError("Necesitamos el peso en Kg!");
            validar=true;
        }
        if (telefono.getText().toString().isEmpty()) {
            telefono.setError("Necesitamos un telefono para que te contacten!");
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