package com.fgb.ventaya.UI;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fgb.ventaya.Adapters.RecyclerAdapterPublicaciones;
import com.fgb.ventaya.Entity.Publicacion;
import com.fgb.ventaya.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class HomeFragment extends Fragment {

     RecyclerView recycler;
     RecyclerAdapterPublicaciones  recyclerAdapterPublicaciones;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pantalla_home, container, false);
        recycler= (RecyclerView) view.findViewById(R.id.recyclerPublicacioness);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        String busqueda ="";
        if(this.getArguments()!=null){
            busqueda = this.getArguments().getString("busqueda");
        }


        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference();
        Query query = dbref.child("Publicacion");
        if(!busqueda.equals("")){
            query = query.orderByChild("title").startAt(busqueda).endAt(busqueda +"\uf8ff");
        }
        cargarPublicaciones(query);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String idUsuario = user.getUid().toString();
        Log.d("debug",idUsuario);

        // Inflate the layout for this fragment
        return view;
    }
    public  void  cargarPublicaciones(Query query){


        FirebaseRecyclerOptions<Publicacion> options =
                new FirebaseRecyclerOptions.Builder<Publicacion>()
                        .setQuery(query,Publicacion.class)
                        .build();
        recyclerAdapterPublicaciones = new RecyclerAdapterPublicaciones(options);
        options.getSnapshots();
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



}
