package com.fgb.ventaya.UI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.fgb.ventaya.Entity.Publicacion;
import com.fgb.ventaya.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;




public class  PerfilFragment extends Fragment {

    ImageView fotoPerfil;
    TextView nombreCompleto,favoritos,publicaciones,email,telefono,username,nombre;
    View layoutMisPublicaciones;
    View layoutFavoritos;
    private DatabaseReference dataBase;
    private int contFavoritos, contPublicaciones;
    private static final int CODIGO = 987;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pantalla_perfil, container, false);

        fotoPerfil = view.findViewById(R.id.imageViewPerfil);
        nombreCompleto = view.findViewById(R.id.nameCompleto);
        favoritos = view.findViewById(R.id.favoritos);
        publicaciones = view.findViewById(R.id.publiRealizadas);
        email = view.findViewById(R.id.mailUser);
        telefono = view.findViewById(R.id.phoneUser);
        username = view.findViewById(R.id.username);
        nombre = view.findViewById(R.id.nombrePila);
        layoutMisPublicaciones = view.findViewById(R.id.layoutMisPublicaciones);
        layoutFavoritos = view.findViewById(R.id.layoutFavoritos);
        dataBase = FirebaseDatabase.getInstance().getReference();
        contFavoritos=0;
        contPublicaciones=0;


        //obtenemos el id del usuario activo
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String idUsuario = user.getUid();


        dataBase.child("Users").child(idUsuario).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    if(snapshot.child("image").exists()) {
                        Glide.with(fotoPerfil.getContext()).load(snapshot.child("image").getValue().toString()).into(fotoPerfil);
                    }
                    nombreCompleto.setText(snapshot.child("name").getValue().toString()+" "+snapshot.child("apellido").getValue().toString());
                    email.setText(snapshot.child("mail").getValue().toString());
                    username.setText(snapshot.child("user").getValue().toString());
                    nombre.setText(snapshot.child("name").getValue().toString());
                    telefono.setText(snapshot.child("telefono").getValue().toString());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        dataBase.child("Publicacion").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Log.d("data", snapshot.getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        dataBase.child("Publicacion").addValueEventListener(new ValueEventListener() {
            @Override  public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot isnapshot : snapshot.getChildren()) {
                    if(isnapshot.hasChild(idUsuario)){
                        if (isnapshot.child(idUsuario).getValue().equals("Favorito")) {
                            contFavoritos++;
                        }
                    }
                    if(isnapshot.child("idUsuario").getValue().toString().equals(idUsuario)){
                        contPublicaciones++;
                    }
                }
                //Log.d("contador", String.valueOf(contFavoritos));
                favoritos.setText(""+contFavoritos);
                publicaciones.setText(""+contPublicaciones);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Log.d("datos", (dataBase.child("Publicacion").orderByChild(idUsuario).equalTo("Favorito")).toString());
        layoutMisPublicaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listenerCambiar.cambiarAPublicaciones();



            }
        });

        layoutFavoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listenerCambiar.cambiarAfavoritos();

            }
        });



        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CODIGO) {
                //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content, new HomeFragment()).commit();
            }
        }
    }

    public interface OnCambiarSeleccionListener{
        public void cambiarAfavoritos();
        public void cambiarAPublicaciones();
    }
    private OnCambiarSeleccionListener listenerCambiar;
    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        try {
            listenerCambiar = (OnCambiarSeleccionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + "must implement interface");
        }

    }
}