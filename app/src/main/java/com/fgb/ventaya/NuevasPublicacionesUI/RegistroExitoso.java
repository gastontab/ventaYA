package com.fgb.ventaya.NuevasPublicacionesUI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.fgb.ventaya.R;
import com.fgb.ventaya.UI.PantallaPublicaciones;
import com.fgb.ventaya.UI.PantallaRegistro;

public class RegistroExitoso extends AppCompatActivity {
    private final int DURACION_CARGA_EXITOSA = 1500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_registro_exitoso);

        new Handler().postDelayed(new Runnable(){
            public void run(){
                Intent i = new Intent(RegistroExitoso.this, PantallaPublicaciones.class);
                startActivity(i);
                finish();
            };
        }, DURACION_CARGA_EXITOSA);

    }



}
