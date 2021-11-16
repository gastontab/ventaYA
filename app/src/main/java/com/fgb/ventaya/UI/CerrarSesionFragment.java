package com.fgb.ventaya.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.fgb.ventaya.NuevasPublicacionesUI.PantallaCargarImagenes;
import com.fgb.ventaya.R;
import com.fgb.ventaya.map.MapActivity;
import com.google.firebase.auth.FirebaseAuth;



public class CerrarSesionFragment extends Fragment {

    Button iniciarSesion;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pantalla_cerrar_sesion, container, false);
        iniciarSesion = view.findViewById(R.id.buttonIniciarSesionDeNuevo);

        FirebaseAuth.getInstance().signOut(); //cierra sesion

        iniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), PantallaIniciarSesion.class);
                startActivity(i);
            }
        });

        // Inflate the layout for this fragment
        return view;

    }
}
