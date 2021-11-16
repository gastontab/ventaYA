package com.fgb.ventaya.NuevasPublicacionesUI;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.fgb.ventaya.R;

public class PublicarMusica extends AppCompatActivity {

    Toolbar myToolbar;
    Button botonSiguiente;
    EditText titulo, tipoInstrumento, textTelefono, marcaInstrumento, precio, colorInstrumento, descripcion;
    private static final int CODIGO = 987;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_publicar_musica);
        myToolbar = findViewById(R.id.toolbarMusica);
        botonSiguiente = findViewById(R.id.buttonRegistrar);
        titulo = findViewById(R.id.titulo);
        tipoInstrumento = findViewById(R.id.tipoInstrumento);
        textTelefono = findViewById(R.id.textTelefono);
        marcaInstrumento = findViewById(R.id.marcaInstrumento);
        colorInstrumento = findViewById(R.id.colorInstrumento);
        precio = findViewById(R.id.precioInstrumento);
        descripcion = findViewById(R.id.textDescripcion);



        setSupportActionBar(myToolbar);
        //para mostrar icono flecha atrás
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        botonSiguiente.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                if(!validarCampos()) {
                    Intent i = new Intent(PublicarMusica.this, PantallaCargarImagenes.class);
                    i.putExtra("titulo", titulo.getText().toString());
                    i.putExtra("tipo", tipoInstrumento.getText().toString());
                    i.putExtra("telefono", textTelefono.getText().toString());
                    i.putExtra("precio", precio.getText().toString());
                    i.putExtra("comentario", descripcion.getText().toString());
                    i.putExtra("marca", marcaInstrumento.getText().toString());
                    i.putExtra("color", colorInstrumento.getText().toString());
                    i.putExtra("categoria", "Musica");
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
        if (tipoInstrumento.getText().toString().isEmpty()) {
            tipoInstrumento.setError("Por favor, dinos de que tipo es!");
            validar=true;
        }
        if (colorInstrumento.getText().toString().isEmpty()) {
            colorInstrumento.setError("Necesitamos el color del articulo!");
            validar=true;
        }
        if (textTelefono.getText().toString().isEmpty()) {
            textTelefono.setError("Necesitamos un telefono para que te contacten!");
            validar=true;
        }
        if (marcaInstrumento.getText().toString().isEmpty()) {
            marcaInstrumento.setError("Por favor, dinos la marca del articulo");
            validar=true;
        }
        if (descripcion.getText().toString().isEmpty()) {
            descripcion.setError("Describe con tus palabras el articulo!");
            validar=true;
        }

        if (precio.getText().toString().isEmpty()) {
            precio.setError("El precio es obligatorio para la publicacion!");
            validar=true;
        }
        return validar;
    }



}