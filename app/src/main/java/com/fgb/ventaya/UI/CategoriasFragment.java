package com.fgb.ventaya.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.fgb.ventaya.NuevasPublicacionesUI.PantallaInfoPublicacion;
import com.fgb.ventaya.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;
import java.util.Map;

public class CategoriasFragment extends Fragment {

    Button buttonElectronica,buttonMusica,buttonMuebles,buttonIndumentaria;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pantalla_categorias, container, false);


        buttonElectronica = view.findViewById(R.id.buttonElectronica);
        buttonIndumentaria = view.findViewById(R.id.buttonIndumentaria);
        buttonMuebles = view.findViewById(R.id.buttonMuebles);
        buttonMusica = view.findViewById(R.id.buttonMusica);
        buttonIndumentaria.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                cargarCategoria("Indumentaria");
            }

        });

        buttonMuebles.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                cargarCategoria("Muebles");
            }

        });

        buttonElectronica.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                cargarCategoria("Electronica");
            }

        });
        buttonMusica.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                cargarCategoria("Musica");
            }

        });

        // Inflate the layout for this fragment
        return view;

    }

    public void cargarCategoria(String categoria) {
        Intent i = new Intent(getActivity(), PantallaCategoriaSeleccionada.class);
        i.putExtra("categoria", categoria);
        startActivity(i);
    }


}
