package com.fgb.ventaya.NuevasPublicacionesUI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.fgb.ventaya.R;
import com.fgb.ventaya.UI.PantallaInicio;
import com.fgb.ventaya.UI.PantallaPublicaciones;
import com.fgb.ventaya.UI.PantallaRegistro;
import com.fgb.ventaya.map.MapActivity2;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PantallaInfoPublicacion extends AppCompatActivity implements OnMapReadyCallback {
    Toolbar myToolbar;

    Intent intent;
    TextView descripcion,precio,titulo,tipo;

    String concat;
    CarouselView carouselView;
    String idPublicacion;
    ImageView imagenPubli [];
    Boolean publicionFavorita = false;
    String idUser;
    String idUsuario;
    Boolean inicio = true;
    private MapView googleMap;
    public static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    TextView username, idusuarioInfo, mail, telefono;
    String latlong;
    ValueEventListener mValueEventListenerLatLong;
    DatabaseReference mPublicationRef;
    ImageView imgNoUbi;
    View LayoutIndumentaria,LayoutMuebles,LayoutMusica,LayoutElectronica;
    ArrayList<String> url = new ArrayList<String>();
    int[] sampleImages = {R.drawable.baloncesto, R.drawable.categoria_vehiculos, R.drawable.categoria_electronica};
    TextView marcaMusica,colorMusica;
    TextView marcaElectronica,modeloElectronica;
    TextView marcaIndumentaria,talleIndumentaria;
    TextView pesoMuebles;
    Button comprar;
    TextView compraRealizada;
    DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_info_publicacion);
        intent = getIntent();
        myToolbar = findViewById(R.id.toolbarPublicacion);

        //inicializamos datos que nos pasa la activity anterior
        idPublicacion = intent.getExtras().get("id").toString();
        titulo = findViewById(R.id.titPublicacion);
        titulo.setText(intent.getExtras().get("Titulo").toString());
        descripcion = findViewById(R.id.descPublicacion);
        descripcion.setText(intent.getExtras().get("Descripcion").toString());
        precio= findViewById(R.id.precPublicacion);
        concat = "$ " +intent.getExtras().get("Precio").toString();
        precio.setText(concat);
        tipo = findViewById(R.id.ContenidoTipo);
        //tipo.setText(intent.getExtras().get("Tipo").toString());
        comprar = findViewById(R.id.buttonComprar);
        compraRealizada = findViewById(R.id.prodVendido);
        imagenPubli = new ImageView[4];
        imagenPubli[0] = findViewById(R.id.imagenPubli);
        imagenPubli[1] = findViewById(R.id.imagenPubli2);
        imagenPubli[2] = findViewById(R.id.imagenPubli3);

        LayoutIndumentaria = findViewById(R.id.LayoutIndumentaria);
        marcaIndumentaria = findViewById(R.id.ContenidoMarcaIndum);
        talleIndumentaria = findViewById(R.id.ContenidoTalleIndum);

        LayoutMuebles = findViewById(R.id.LayoutMuebles);
        pesoMuebles = findViewById(R.id.ContenidoPesoMueb);

        LayoutMusica = findViewById(R.id.LayoutMusica);
        marcaMusica = findViewById(R.id.ContenidoMarcaMus);
        colorMusica = findViewById(R.id.ContenidoColorMus);

        LayoutElectronica = findViewById(R.id.LayoutElectronica);
        marcaElectronica = findViewById(R.id.ContenidoMarcaElec);
        modeloElectronica = findViewById(R.id.ContenidoModeloElec);

        googleMap = findViewById(R.id.mapaVendedor);
        username = findViewById(R.id.usernameInfo);
        mail = findViewById(R.id.mailInfo);
        telefono = findViewById(R.id.telInfo);
        idusuarioInfo = findViewById(R.id.idUser);
        imgNoUbi = findViewById(R.id.imagNoUbication);

        db = FirebaseDatabase.getInstance().getReference();
        mPublicationRef =db.child("Publicacion").child(idPublicacion);
        initGoogleMap(savedInstanceState);

        ImageSlider imageSlider = findViewById(R.id.slider);
        List<SlideModel> slideModels = new ArrayList<>();



        //imageSlider.setImageList(slideModels,true);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        idUsuario = user.getUid();
        Log.d("useractivo", idUsuario);
        //ahora para recuperar fotos de la publi


        mPublicationRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot isnapshot : snapshot.getChildren()) {

                    if (isnapshot.getValue().toString().contains("https://firebasestorage") && inicio) {
                        String urll = isnapshot.getValue().toString();
                        if (!url.contains(urll)) {
                            url.add(urll);
                        }




                    }

                    switch (isnapshot.getKey()) {
                        case "idUsuario": {

                            idusuarioInfo.setText(isnapshot.getValue().toString());
                            if (isnapshot.getValue().toString().equals(idUsuario)) {
                                comprar.setVisibility(View.GONE);
                            }

                            break;
                        }
                        case "latlong": {
                            latlong = isnapshot.getValue().toString();
                            Log.d("valorlatlong", latlong);
                            break;
                        }
                        case "categoria": {
                            switch (isnapshot.getValue().toString()) {
                                //TODOS los campos por defecto deben estar en gone y esto habilita los correspondientes
                                case "Electronica": {
                                    //TODO: Setear visible campos de electronica
                                    LayoutElectronica.setVisibility(View.VISIBLE);
                                    LayoutMusica.setVisibility(View.GONE);
                                    LayoutIndumentaria.setVisibility(View.GONE);
                                    LayoutMuebles.setVisibility(View.GONE);
                                    marcaElectronica.setText(snapshot.child("marca").getValue().toString());
                                    modeloElectronica.setText(snapshot.child("modelo").getValue().toString());


                                    break;
                                }
                                case "Musica": {
                                    //TODO: Setear visible campos de Musica
                                    LayoutMusica.setVisibility(View.VISIBLE);
                                    LayoutElectronica.setVisibility(View.GONE);
                                    LayoutIndumentaria.setVisibility(View.GONE);
                                    LayoutMuebles.setVisibility(View.GONE);
                                    marcaMusica.setText(snapshot.child("marca").getValue().toString());
                                    colorMusica.setText(snapshot.child("color").getValue().toString());


                                    break;
                                    //cargarCamposMusica();
                                }
                                case "Indumentaria": {
                                    //TODO: Setear visible campos de Indumentaria
                                    LayoutIndumentaria.setVisibility(View.VISIBLE);
                                    LayoutElectronica.setVisibility(View.GONE);
                                    LayoutMusica.setVisibility(View.GONE);
                                    LayoutMuebles.setVisibility(View.GONE);
                                    marcaIndumentaria.setText(snapshot.child("marca").getValue().toString());
                                    talleIndumentaria.setText(snapshot.child("talle").getValue().toString());


                                    break;
                                    //cargarCamposIndumentaria();

                                }
                                case "Muebles": {
                                    //TODO: Setear visible campos de Muebles
                                    LayoutMuebles.setVisibility(View.VISIBLE);
                                    LayoutElectronica.setVisibility(View.GONE);
                                    LayoutMusica.setVisibility(View.GONE);
                                    LayoutIndumentaria.setVisibility(View.GONE);
                                    pesoMuebles.setText(snapshot.child("peso").getValue().toString());
                                    break;

                                }
                            }
                            break;
                        }
                        case "tipo":{
                            tipo.setText(isnapshot.getValue().toString());
                            break;
                        }


                    /*if (isnapshot.getKey().equals("idUsuario")) {


                    }*/

                  /*  if (isnapshot.getKey().equals("latlong")) {

                    }*/
                  /*  if (isnapshot.getKey().equals("categoria")) {


                    }*/


                    }
                    if (snapshot.hasChild("estado")) {
                        comprar.setVisibility(View.GONE);
                        compraRealizada.setVisibility(View.VISIBLE);
                    }
                }
                    db.child("Users").child(idusuarioInfo.getText().toString()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                for (DataSnapshot isnapshot : snapshot.getChildren()) {
                                    if (isnapshot.getKey().equals("user")) {
                                        username.setText(isnapshot.getValue().toString());
                                    }
                                    if (isnapshot.getKey().equals("mail")) {
                                        mail.setText(isnapshot.getValue().toString());
                                    }
                                    if (isnapshot.getKey().equals("telefono")) {
                                        telefono.setText(isnapshot.getValue().toString());
                                    }
                                }
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });



                    //traemos imagenes con Glide para cada link

                    if (inicio) {
                        for (int i = 0; i < url.size(); i++) {
                            Glide.with(getApplicationContext())
                                    .load(url.get(i))
                                    .into(imagenPubli[i]);
                        }

                        for (int i = 0; i < url.size(); i++) {
                            slideModels.add(new SlideModel(url.get(i)));
                        }
                        imageSlider.setImageList(slideModels, true);

                    }

                inicio = false;


            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Log.d("valor", idusuarioInfo.getText().toString());
        idUser = idusuarioInfo.getText().toString();




        setSupportActionBar(myToolbar);

        //para mostrar icono flecha atrás
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        carouselView = (CarouselView) findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);

        carouselView.setImageListener(imageListener);

        comprar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder= new AlertDialog.Builder(PantallaInfoPublicacion.this);
                builder.setMessage("Seguro que que quieres comprar este producto?")
                        .setTitle("Confirmar compra")
                        .setPositiveButton("Si!",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dlgInt, int i) {
                                        comprar.setVisibility(View.GONE);
                                        compraRealizada.setVisibility(View.VISIBLE);
                                        Map<String, Object> mapa= new HashMap<>();
                                        mapa.put("estado","Vendido");
                                        db.child("Publicacion").child(idPublicacion).updateChildren(mapa).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task2) {
                                                if (task2.isSuccessful()) {
                                                    //item.setIcon(R.drawable.flecha);
                                                    Toast.makeText(PantallaInfoPublicacion.this, "Compra realizada, felicitaciones!", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(PantallaInfoPublicacion.this, "No se pudo realizar la compra, vuelva a intentarlo", Toast.LENGTH_LONG).show();
                                                    comprar.setVisibility(View.VISIBLE);
                                                    compraRealizada.setVisibility(View.GONE);
                                                }
                                            }
                                        });
                                    }
                                })
                        .setNegativeButton("Cancelar",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dlgInt, int i) {

                                    }
                                });
                AlertDialog dialog= builder.create();
                dialog.show();
            }
        });

    }



    private void initGoogleMap(Bundle savedInstanceState){
        // *** IMPORTANT ***
        // MapView requires that the Bundle you pass contain _ONLY_ MapView SDK
        // objects or sub-Bundles.
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }

        googleMap.onCreate(mapViewBundle);

        googleMap.getMapAsync(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }

        googleMap.onSaveInstanceState(mapViewBundle);
    }

    @Override
    public void onResume() {
        super.onResume();
        googleMap.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        googleMap.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        googleMap.onStop();
    }

    @Override
    public void onMapReady (GoogleMap map){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    9999);
            return;

        }


        Log.d("cambioData", "cambio");
        addCircleToMap(map);

    }

    private void addCircleToMap(GoogleMap map) {

        FirebaseDatabase.getInstance().getReference().child("Publicacion").child(idPublicacion).child("latlong").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue()==null){
                    googleMap.setVisibility(View.GONE);
                    imgNoUbi.setVisibility(View.VISIBLE);
                    //Seteo imagen ubicacion no disponible
                }else {

                    String ubicacion = snapshot.getValue().toString();
                    ubicacion = ubicacion.substring(10);
                    ubicacion = ubicacion.replaceFirst(".$","");
                    String[] ll =  ubicacion.split(",");
                    double latitude = Double.parseDouble(ll[0]);
                    double longitude = Double.parseDouble(ll[1]);

                    LatLng latlong = new LatLng(latitude,longitude); //bd obtener
                    //map.addMarker(new MarkerOptions().position(ubicacion).title("Vendedor"));
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(latlong, 15), 100, null);
                    CircleOptions circle = new CircleOptions()
                            .center(latlong)
                            .radius(300)
                            .fillColor(0x66FF0000)
                            .strokeColor(0x00000000);

                    map.addCircle(circle);
                    map.getUiSettings().setZoomControlsEnabled(false);
                    map.getUiSettings().setAllGesturesEnabled(false);
                    map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                        @Override
                        public void onMapClick(LatLng latLng) {
                            Intent i = new Intent(PantallaInfoPublicacion.this, MapActivity2.class);
                            i.putExtra("latlong",latlong);
                            startActivity(i);
                        }
                    });

                }}

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }




    @Override
    public void onPause() {
        googleMap.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        googleMap.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        googleMap.onLowMemory();
    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbarinfopublicaciones_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.favoritos:


                inicio = false;
                if(publicionFavorita){
                    DatabaseReference mPostReference =  db.child("Publicacion").child(idPublicacion).child(idUsuario);

                    mPostReference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {

                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if (task2.isSuccessful()) {
                                //item.setIcon(R.drawable.flecha);
                                Toast.makeText(PantallaInfoPublicacion.this, "Se quito como favorito!", Toast.LENGTH_LONG).show();
                                publicionFavorita = false;
                                ActualizarIconoToolbar(item);


                            } else {
                                Toast.makeText(PantallaInfoPublicacion.this, "No se pudo quitar favorito", Toast.LENGTH_LONG).show();
                                publicionFavorita = false;
                            }
                        }
                    });

                } else {
                    Map<String, Object> map= new HashMap<>();
                    map.put(idUsuario,"Favorito");
                    db.child("Publicacion").child(idPublicacion).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {

                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if (task2.isSuccessful()) {
                                //item.setIcon(R.drawable.flecha);
                                Toast.makeText(PantallaInfoPublicacion.this, "Agregado a favoritos!", Toast.LENGTH_LONG).show();
                                publicionFavorita = true;
                                ActualizarIconoToolbar(item);


                            } else {
                                Toast.makeText(PantallaInfoPublicacion.this, "No se pudo establecer como favorito", Toast.LENGTH_LONG).show();
                                publicionFavorita = false;
                            }
                        }
                    }); }

                return true;

            case R.id.compartir:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "**Descargá la app VentaYA y conseguí este artículo!**" + System.lineSeparator() +  System.lineSeparator() +
                        "Título: "+titulo.getText().toString() + System.lineSeparator() +
                        "Descripción: "+descripcion.getText().toString() + System.lineSeparator() +
                        "Precio: " + precio.getText().toString());


                sendIntent.setType("text/plain");
                startActivity(sendIntent);


        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        MenuItem settingsItem = menu.findItem(R.id.favoritos);


        db.child("Publicacion").child(idPublicacion).child(idUsuario).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
                        if (dataSnapshot.getValue() != null) {
                            if (dataSnapshot.getValue().equals("Favorito")) {
                                publicionFavorita = true;

                            } else {
                                publicionFavorita = false;
                            }
                        }else {
                            publicionFavorita = false;
                        }
                        ActualizarIconoToolbar(settingsItem);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        return super.onPrepareOptionsMenu(menu);
    }

    private void ActualizarIconoToolbar( MenuItem settingsItem){
        if(publicionFavorita){
            settingsItem.setIcon(ContextCompat.getDrawable(PantallaInfoPublicacion.this, R.drawable.ic_favoritos));
        }else {
            settingsItem.setIcon(ContextCompat.getDrawable(PantallaInfoPublicacion.this, R.drawable.ic_no_es_favorito));
        }

    }



}