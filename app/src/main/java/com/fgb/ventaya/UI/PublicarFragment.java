package com.fgb.ventaya.UI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.fgb.ventaya.Entity.Globales;
import com.fgb.ventaya.NuevasPublicacionesUI.PublicarDeportes;
import com.fgb.ventaya.NuevasPublicacionesUI.PublicarElectronica;
import com.fgb.ventaya.NuevasPublicacionesUI.PublicarHerramientas;
import com.fgb.ventaya.NuevasPublicacionesUI.PublicarIndumentaria;
import com.fgb.ventaya.NuevasPublicacionesUI.PublicarMuebles;
import com.fgb.ventaya.NuevasPublicacionesUI.PublicarMusica;
import com.fgb.ventaya.NuevasPublicacionesUI.PublicarServicios;
import com.fgb.ventaya.NuevasPublicacionesUI.PublicarVehiculos;
import com.fgb.ventaya.R;

public class PublicarFragment extends Fragment {

    ImageButton vehiculos;
    ImageButton indumentaria;
    ImageButton electronica;
    ImageButton musica;
    ImageButton muebles;
    ImageButton servicios;
    ImageButton deportes;
    ImageButton herramientas;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pantalla_publicar, container, false);
        vehiculos = view.findViewById(R.id.imageButton13);
        indumentaria = view.findViewById(R.id.imageButton9);
        electronica = view.findViewById(R.id.imageButton7);
        musica = view.findViewById(R.id.imageButton8);
        muebles = view.findViewById(R.id.imageButton10);
        servicios = view.findViewById(R.id.imageButton2);
        deportes = view.findViewById(R.id.imageButton3);
        herramientas = view.findViewById(R.id.imageButton4);



        vehiculos.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), PublicarVehiculos.class);
                getActivity().startActivity(i);
            }
        });

        indumentaria.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), PublicarIndumentaria.class);
                getActivity().startActivity(i);
            }
        });

        electronica.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), PublicarElectronica.class);
                getActivity().startActivity(i);

            }
        });

        musica.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), PublicarMusica.class);
                getActivity().startActivity(i);
            }
        });

        muebles.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), PublicarMuebles.class);
                getActivity().startActivity(i);
            }
        });

        servicios.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), PublicarServicios.class);
                getActivity().startActivity(i);
            }
        });

        deportes.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), PublicarDeportes.class);
                getActivity().startActivity(i);
            }
        });

        herramientas.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), PublicarHerramientas.class);
                getActivity().startActivity(i);
            }
        });

        // Inflate the layout for this fragment
        return view;

    }


    }
