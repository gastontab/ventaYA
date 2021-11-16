package com.fgb.ventaya.map;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import com.fgb.ventaya.NuevasPublicacionesUI.PantallaCargarImagenes;
import com.fgb.ventaya.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.maps.android.SphericalUtil;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap myMap;
    private Button botonubi;
    private LatLng latlong = null;
    private LatLng posicionGPS = null;
    private LatLng nuevaPosicionResto = null;
    private Boolean marcador = false;
    private FusedLocationProviderClient fusedLocationClient;
    private Address address = null;
    private String direccionGPS = null;
    private String direccionMarcador = null;
    private List<Address> addresses;
    private Geocoder geocoder;
    private Marker m = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_mapa);
        geocoder = new Geocoder(this, Locale.getDefault());
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        botonubi = findViewById(R.id.botonConfirmarUbicacion);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        botonubi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent returnIntent = new Intent();
                if(direccionMarcador==null){
                    returnIntent.putExtra("direccion", direccionGPS);
                    returnIntent.putExtra("latLongPosicion",posicionGPS.toString());
                }
                else{
                    returnIntent.putExtra("direccion", direccionMarcador);
                    returnIntent.putExtra("latLongPosicion", latlong.toString());
                }
                setResult(PantallaCargarImagenes.RESULT_OK,returnIntent);
                finish();


            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        myMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    9999);
            return;
        }

        inicializarMapa();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 9999 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                inicializarMapa();
            }

        }
    }
    @SuppressLint("MissingPermission")
    private void inicializarMapa(){
        myMap.setMyLocationEnabled(true);
        myMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        myMap.getUiSettings().setZoomControlsEnabled(true);
        myMap.getUiSettings().setCompassEnabled(true);
        //Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {

                        if (location != null) {
                            //latlong = new LatLng(location.getLatitude(),location.getLongitude());
                            posicionGPS = new LatLng(location.getLatitude(),location.getLongitude());
                            try {
                                addresses= (List<Address>) geocoder.getFromLocation(posicionGPS.latitude, posicionGPS.longitude, 1);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            direccionGPS = addresses.get(0).getAddressLine(0);
                            String result = direccionGPS.split(",")[0];
                            direccionGPS=result;

                        }
                    }
                });

        myMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {

            @Override
            public void onMapLongClick(LatLng latLng) {
                latlong = latLng;
                marcador = true;
                try {
                    addresses= (List<Address>) geocoder.getFromLocation(latlong.latitude, latlong.longitude, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                direccionMarcador = addresses.get(0).getAddressLine(0);
                String result = direccionMarcador.split(",")[0];
                direccionMarcador=result;
                if(m != null){
                    m.remove();
                }

                MarkerOptions marcador = new MarkerOptions()
                        .alpha(1f)
                        .position(latLng)
                        .draggable(false)
                        .title("Ubicacion vendedor")
                        .snippet("El producto se encuentra aquí");
                m = myMap.addMarker(marcador);

            }
        });


    }




}
