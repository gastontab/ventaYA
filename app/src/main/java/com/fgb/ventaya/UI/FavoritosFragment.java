package com.fgb.ventaya.UI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fgb.ventaya.Adapters.RecyclerAdapterPublicaciones;
import com.fgb.ventaya.Entity.Publicacion;
import com.fgb.ventaya.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class FavoritosFragment extends Fragment {
    RecyclerView recycler;
    RecyclerAdapterPublicaciones recyclerAdapterPublicaciones;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pantalla_home, container, false);
        recycler= (RecyclerView) view.findViewById(R.id.recyclerPublicacioness);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        getActivity().setTitle("Favoritos");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String idUsuario = user.getUid();

        FirebaseRecyclerOptions<Publicacion> options =
                new FirebaseRecyclerOptions.Builder<Publicacion>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Publicacion").orderByChild(idUsuario).equalTo("Favorito"),Publicacion.class)
                        .build();



        recyclerAdapterPublicaciones = new RecyclerAdapterPublicaciones(options);
        recycler.setAdapter(recyclerAdapterPublicaciones);

        return view;
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
}
