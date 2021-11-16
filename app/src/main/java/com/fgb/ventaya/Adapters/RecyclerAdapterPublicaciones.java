package com.fgb.ventaya.Adapters;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fgb.ventaya.Entity.Publicacion;
import com.fgb.ventaya.NuevasPublicacionesUI.PantallaInfoPublicacion;
import com.fgb.ventaya.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RecyclerAdapterPublicaciones extends FirebaseRecyclerAdapter<Publicacion,RecyclerAdapterPublicaciones.viewHolder> {

    public RecyclerAdapterPublicaciones(@NonNull FirebaseRecyclerOptions<Publicacion> options) {

        super(options);

    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }


    @Override
    protected void onBindViewHolder(@NonNull viewHolder viewHolder, int i, @NonNull Publicacion publicacion) {
        viewHolder.titulo.setText(publicacion.getTitle());
        viewHolder.descripcion.setText(publicacion.getDescription());
        viewHolder.precio.setText("$ "+publicacion.getPrecio());
        if(publicacion.getEstado()!=null){
            viewHolder.estado.setVisibility(View.VISIBLE);
        } else {
            viewHolder.estado.setVisibility(View.GONE);
        }
        Glide.with(viewHolder.imgPublicacion.getContext()).load(publicacion.getImage()).into(viewHolder.imgPublicacion);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Click","Se hizo click");
                String id = getRef(getItemViewType(i)).getKey();
                Intent i = new Intent(v.getContext(), PantallaInfoPublicacion.class);
                i.putExtra("id",id);
                i.putExtra("Titulo",publicacion.getTitle());
                i.putExtra("Descripcion",publicacion.getDescription());
                i.putExtra("Precio",publicacion.getPrecio());
               // i.putExtra("Tipo",publicacion.getTipo());


                v.getContext().startActivity(i);

            }
        });

    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fila_recycler_publicaciones,parent,false);

        return new viewHolder(view);
    }

    class viewHolder extends RecyclerView.ViewHolder{
        ImageView imgPublicacion;
        TextView titulo,descripcion,precio,estado;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            imgPublicacion =(ImageView) itemView.findViewById(R.id.imagePublication);
            titulo =(TextView) itemView.findViewById(R.id.tituloPublicacion);
            descripcion =(TextView) itemView.findViewById(R.id.descripcionPublicacion);
            precio =(TextView) itemView.findViewById(R.id.precioPublicacion);
            estado = (TextView) itemView.findViewById(R.id.publiNoDisponible);

        }
    }

}