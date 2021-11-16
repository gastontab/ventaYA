package com.fgb.ventaya.NuevasPublicacionesUI;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.fgb.ventaya.Entity.Globales;
import com.fgb.ventaya.Notifications.MyNotificationPublisher;
import com.fgb.ventaya.R;
import com.fgb.ventaya.UI.HomeFragment;
import com.fgb.ventaya.UI.PantallaIniciarSesion;
import com.fgb.ventaya.UI.PantallaInicio;
import com.fgb.ventaya.UI.PantallaPublicaciones;
import com.fgb.ventaya.map.MapActivity;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PantallaCargarImagenes extends AppCompatActivity {

    private int botonSeleccionado=0;
    static final int CAMARA_REQUEST = 1;
    static final int GALERIA_REQUEST = 2;
    private static final int CODIGO = 98;
    Uri imageUri;
    Uri downloadUri;
    Uri downloadUri2;
    Uri downloadUri3;
    Boolean tieneImagen=false;
    byte[] datas;
    byte[] datas1;
    byte[] datas2;
    byte[] datas3;
    ArrayList<byte[]> datos;
    private Boolean tipo= false;
    ImageView imagen1;
    ImageView imagen2;
    ImageView imagen3;
    ImageButton button1;
    ImageButton button2;
    ImageButton button3;
    Button publicar;
    Toolbar myToolbar;
    ImageButton abrirMapa;
    EditText direccion;
    private DatabaseReference db;
    ProgressBar progressBarPublicar;
    String latlong;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_cargar_imagenes);
        imagen1 = findViewById(R.id.imageView1);
        imagen2 = findViewById(R.id.imageView2);
        imagen3 = findViewById(R.id.imageView3);
        button1 = findViewById(R.id.imageButton1);
        button2 = findViewById(R.id.imageButton2);
        button3 = findViewById(R.id.imageButton3);
        publicar = findViewById(R.id.button);
        myToolbar = findViewById(R.id.toolbarImagenes);
        datos = new ArrayList<byte[]>();
        db = FirebaseDatabase.getInstance().getReference();
        abrirMapa = findViewById(R.id.buttonMapa);
        direccion = findViewById(R.id.textDirec);
        progressBarPublicar = findViewById(R.id.progressBarLogin);
        //clickListener(button1);
        //clickListener(button2);
        //clickListener(button3);

        setSupportActionBar(myToolbar);
        //para mostrar icono flecha atrás
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        abrirMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int LAUNCH_SECOND_ACTIVITY = 3;
                Intent i = new Intent(PantallaCargarImagenes.this, MapActivity.class);
                // i.putExtra("habilitar boton pedir" , "true");
                startActivityForResult(i, LAUNCH_SECOND_ACTIVITY);
            }
        });

        button1.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                botonSeleccionado=1;
                if (ActivityCompat.checkSelfPermission(PantallaCargarImagenes.this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(PantallaCargarImagenes.this,
                            new String[]{Manifest.permission.CAMERA},
                            9999);

                }

                AlertDialog.Builder builder= new AlertDialog.Builder(PantallaCargarImagenes.this);
                builder.setMessage("Seleccione desde donde desea cargar la imagen")
                        .setTitle("Cargar Imagen")
                        .setPositiveButton("Camara",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dlgInt, int i) {
                                        tipo=true;
                                        lanzarCamara();
                                    }
                                })
                        .setNegativeButton("Galeria",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dlgInt, int i) {
                                        abrirGaleria();
                                        //TODO: pedir permisos galeria
                                    }
                                });
                AlertDialog dialog= builder.create();
                dialog.show();


            }

        });

        button2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                botonSeleccionado=2;
                if (ActivityCompat.checkSelfPermission(PantallaCargarImagenes.this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(PantallaCargarImagenes.this,
                            new String[]{Manifest.permission.CAMERA},
                            9999);

                }

                AlertDialog.Builder builder= new AlertDialog.Builder(PantallaCargarImagenes.this);
                builder.setMessage("Seleccione desde donde desea cargar la imagen")
                        .setTitle("Cargar Imagen")
                        .setPositiveButton("Camara",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dlgInt, int i) {
                                        tipo=true;
                                        lanzarCamara();
                                    }
                                })
                        .setNegativeButton("Galeria",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dlgInt, int i) {
                                        abrirGaleria();
                                        //TODO: pedir permisos galeria
                                    }
                                });
                AlertDialog dialog= builder.create();
                dialog.show();


            }

        });

        button3.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                botonSeleccionado=3;
                if (ActivityCompat.checkSelfPermission(PantallaCargarImagenes.this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(PantallaCargarImagenes.this,
                            new String[]{Manifest.permission.CAMERA},
                            9999);

                }

                AlertDialog.Builder builder= new AlertDialog.Builder(PantallaCargarImagenes.this);
                builder.setMessage("Seleccione desde donde desea cargar la imagen")
                        .setTitle("Cargar Imagen")
                        .setPositiveButton("Camara",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dlgInt, int i) {
                                        tipo=true;
                                        lanzarCamara();
                                    }
                                })
                        .setNegativeButton("Galeria",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dlgInt, int i) {
                                        abrirGaleria();
                                        //TODO: pedir permisos galeria
                                    }
                                });
                AlertDialog dialog= builder.create();
                dialog.show();


            }

        });

        publicar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                int m =datos.size();
                if (m==0){
                    AlertDialog.Builder builder= new AlertDialog.Builder(PantallaCargarImagenes.this);
                    builder.setMessage("Cargue al menos una imagen para la portada")
                            .setTitle("Error")
                            .setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dlgInt, int i) {
                                        }
                                    });
                    AlertDialog dialog= builder.create();
                    dialog.show();
                }
                else{
                    boolean b=false;
                    publicar.setVisibility(View.GONE);
                    progressBarPublicar.setVisibility(View.VISIBLE);

                    for(int i=0;i<m;i++){
                        if (i==(m-1)){
                            b=true;
                        }
                        UUID ID = UUID.randomUUID();
                        //Toast.makeText(PantallaCargarImagenes.this, "Publicacion Creada",Toast.LENGTH_LONG).show();
                        subirImagen(ID,datos.get(i),b,i);

                    }
                }

            }
        });

    }
















    private void lanzarCamara() {
        Intent camaraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camaraIntent, CAMARA_REQUEST);
    }

    private void abrirGaleria() {
        Intent galeriaIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(galeriaIntent, GALERIA_REQUEST);
    }


    class TaskNotificacion extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            AlarmManager alarmManager;

            Intent intent = new Intent(PantallaCargarImagenes.this, MyNotificationPublisher.class);

            alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            PendingIntent pi = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);

            alarmManager.set(AlarmManager.RTC_WAKEUP, 0, pi);
            onBackPressed();

        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMARA_REQUEST  && resultCode == RESULT_OK) {
            tieneImagen=true;
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            float proporcion = 600 / (float) imageBitmap.getWidth();
            imageBitmap = Bitmap.createScaledBitmap(imageBitmap,600,(int) (imageBitmap.getHeight() * proporcion),false);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            switch(botonSeleccionado){
                case 1:
                    imagen1.setImageBitmap(imageBitmap);
                    button1.setVisibility(View.GONE);
                    imagen1.setVisibility(View.VISIBLE);
                    imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    datas1 = baos.toByteArray(); // Imagen en arreglo de bytes
                    datos.add(datas1);
                    break;
                case 2:
                    imagen2.setImageBitmap(imageBitmap);
                    button2.setVisibility(View.GONE);
                    imagen2.setVisibility(View.VISIBLE);
                    imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    datas2 = baos.toByteArray(); // Imagen en arreglo de bytes
                    datos.add(datas2);
                    break;
                case 3:
                    imagen3.setImageBitmap(imageBitmap);
                    button3.setVisibility(View.GONE);
                    imagen3.setVisibility(View.VISIBLE);
                    imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    datas3 = baos.toByteArray(); // Imagen en arreglo de bytes
                    datos.add(datas3);
                    break;
            }


        }
        if(requestCode == GALERIA_REQUEST && resultCode == RESULT_OK){
            tieneImagen=true;
            imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                float proporcion = 600 / (float) bitmap.getWidth();
                bitmap = Bitmap.createScaledBitmap(bitmap,600,(int) (bitmap.getHeight() * proporcion),false);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                switch(botonSeleccionado){
                    case 1:
                        imagen1.setImageBitmap(bitmap);
                        button1.setVisibility(View.GONE);
                        imagen1.setVisibility(View.VISIBLE);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        datas1 = baos.toByteArray(); // Imagen en arreglo de bytes
                        datos.add(datas1);
                        break;
                    case 2:
                        imagen2.setImageBitmap(bitmap);
                        button2.setVisibility(View.GONE);
                        imagen2.setVisibility(View.VISIBLE);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        datas2 = baos.toByteArray(); // Imagen en arreglo de bytes
                        datos.add(datas2);
                        break;
                    case 3:
                        imagen3.setImageBitmap(bitmap);
                        button3.setVisibility(View.GONE);
                        imagen3.setVisibility(View.VISIBLE);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        datas3 = baos.toByteArray(); // Imagen en arreglo de bytes
                        datos.add(datas3);
                        break;
                }



            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        if(requestCode==3){
            if (resultCode == RESULT_OK) {
                direccion.setText((String) data.getExtras().get("direccion"));
                latlong= (String) data.getExtras().get("latLongPosicion");

            }
        }
    }
    private Boolean subirImagen(UUID id, byte[] imagen, boolean b, int i) {
        final Boolean[] result = {false};
        // Creamos una referencia a nuestro Storage
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        // Creamos una referencia a 'images/plato_id.jpg'
        StorageReference platosImagesRef = storageRef.child("images/"+id.toString()+".jpg");

        // Cual quiera de los tres métodos tienen la misma implementación, se debe utilizar el que corresponda
        UploadTask uploadTask = platosImagesRef.putBytes(imagen);
        // UploadTask uploadTask = platosImagesRef.putFile(file);
        // UploadTask uploadTask = platosImagesRef.putStream(stream);

        // Registramos un listener para saber el resultado de la operación
        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    Toast.makeText(PantallaCargarImagenes.this, "error",Toast.LENGTH_LONG).show();
                    throw task.getException();
                }

                // Continuamos con la tarea para obtener la URL
                return platosImagesRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {

                if (task.isSuccessful()) {
                    // URL de descarga del archivo
                    switch (i){
                        case 0: downloadUri = task.getResult();
                        break;
                        case 1: downloadUri2 = task.getResult();
                        break;
                        case 2: downloadUri3 = task.getResult();
                        break;
                    }
                    //Toast.makeText(PantallaCargarImagenes.this, downloadUri.toString(),Toast.LENGTH_LONG).show();
                    //Log.d("DEBUG", downloadUri.toString());
                    result[0] =true;
                    if(b==true){
                        guardarPublicacion();

                    }
                }
                else{
                    progressBarPublicar.setVisibility(View.GONE);
                    publicar.setVisibility(View.VISIBLE);
                    Toast.makeText(PantallaCargarImagenes.this, "No se pudo cargar la imagen",Toast.LENGTH_LONG).show();

                }
            }
        });
        return result[0];
    }

    private void guardarPublicacion() {
        UUID id = UUID.randomUUID();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String idUsuario = user.getUid().toString();
        Map<String, Object> map= new HashMap<>();
        map.put("title", getIntent().getExtras().getString("titulo"));
        map.put("tipo",getIntent().getExtras().getString("tipo"));
        map.put("telefono",getIntent().getExtras().getString("telefono"));
        map.put("precio",getIntent().getExtras().getString("precio"));
        map.put("description",getIntent().getExtras().getString("comentario"));
        map.put("direccion", direccion.getText().toString());
        map.put("categoria",getIntent().getExtras().getString("categoria"));
        map.put("idUsuario",idUsuario);
        map.put("latlong",latlong);
        switch (getIntent().getExtras().getString("categoria")){
            case "Electronica":{
                map.put("marca",getIntent().getExtras().getString("marca"));
                map.put("modelo",getIntent().getExtras().getString("modelo"));
                break;

            }
            case "Musica":{
                map.put("marca",getIntent().getExtras().getString("marca"));
                map.put("color",getIntent().getExtras().getString("color"));
                break;

            }
            case "Indumentaria":{
                map.put("marca",getIntent().getExtras().getString("marca"));
                map.put("talle",getIntent().getExtras().getString("talle"));
                break;
            }
            case "Muebles":{
                map.put("peso",getIntent().getExtras().getString("peso"));
                break;

            }
        }





        if (downloadUri!=null){
            map.put("image",downloadUri.toString());
        }
        if (downloadUri2!=null){
            map.put("image2",downloadUri2.toString());
        }
        if (downloadUri3!=null){
            map.put("image3",downloadUri3.toString());
        }


        db.child("Publicacion").child(id.toString()).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
               // db.child("Users").child(idUsuario).child("Publicacion").child(id.toString())
            @Override
            public void onComplete(@NonNull Task<Void> task2) {
                if (task2.isSuccessful()) {
                    //Globales.publicacionRealizada=1;
                    Log.d("valorrrsi", String.valueOf(Globales.publicacionRealizada));
                    //Toast.makeText(PantallaCargarImagenes.this, "Publicacion creada con exito", Toast.LENGTH_LONG).show();
                    progressBarPublicar.setVisibility(View.GONE);
                    new TaskNotificacion().execute();
                    /*Intent intentResultado = new Intent();
                    intentResultado.putExtra("texto", "cerrar");
                    setResult(Activity.RESULT_OK,intentResultado);*/
                    Intent pubExitosa = new Intent(PantallaCargarImagenes.this, PublicacionExitosa.class);
                    //startActivityForResult(pubExitosa,CODIGO);
                    startActivity(pubExitosa);
                    Intent intentResultado = new Intent();
                    intentResultado.putExtra("texto", "cerrar");
                    setResult(Activity.RESULT_OK,intentResultado);
                    finish();



                } else {
                    Toast.makeText(PantallaCargarImagenes.this, "No se pudo crear la publicacion", Toast.LENGTH_LONG).show();
                    progressBarPublicar.setVisibility(View.GONE);
                    publicar.setVisibility(View.VISIBLE);
                }
            }
        });
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 9999 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                if (tipo==true){
                    lanzarCamara();
                }
                else{
                    abrirGaleria();
                }

            }

        }
    }

    //para aplicar funcionalidad flecha atrás
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
