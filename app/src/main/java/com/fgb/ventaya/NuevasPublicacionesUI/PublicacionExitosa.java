package com.fgb.ventaya.NuevasPublicacionesUI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.fgb.ventaya.R;

public class PublicacionExitosa extends AppCompatActivity {
    private final int DURACION_CARGA_EXITOSA = 1500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.publicacion_exitosa);

        new Handler().postDelayed(new Runnable(){
            public void run(){
                finish();
            };
        }, DURACION_CARGA_EXITOSA);

    }



}
