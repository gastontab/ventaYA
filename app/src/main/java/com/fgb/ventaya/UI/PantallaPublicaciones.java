package com.fgb.ventaya.UI;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ColorSpace;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fgb.ventaya.Adapters.RecyclerAdapterPublicaciones;
import com.fgb.ventaya.Entity.Globales;
import com.fgb.ventaya.Entity.Publicacion;
import com.fgb.ventaya.NuevasPublicacionesUI.PantallaCargarImagenes;
import com.fgb.ventaya.R;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;

public class PantallaPublicaciones extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SearchView.OnQueryTextListener, PerfilFragment.OnCambiarSeleccionListener {

    DrawerLayout drawerLayout;
    public NavigationView navigationView;
    Toolbar myToolbar;
    ActionBarDrawerToggle toggle;
    ImageView ip;
    DatabaseReference storage;
    SearchView searchView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_publicaciones);

        //inicializacion elementos
        myToolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.nav_view);
        drawerLayout = findViewById(R.id.drawer_layout);
        storage = FirebaseDatabase.getInstance().getReference();


        getSupportFragmentManager().beginTransaction().add(R.id.content, new HomeFragment()).commit();
        setTitle("Home");

        //Setup toolbar
        setSupportActionBar(myToolbar);

        toggle = new ActionBarDrawerToggle(this, drawerLayout,myToolbar,R.string.drawer_open,R.string.drawer_close);
        drawerLayout.addDrawerListener(toggle);


        navigationView.setNavigationItemSelectedListener(this);


        ip = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.imageViewPerfil);
        /*if(getIntent().getExtras().getString("pantalla").equals("registro")){
            String s= getIntent().getExtras().getString("image");
            traerImagenPerfil(s);
        }*/
        //else{
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String idUsuario = user.getUid();

            storage.child("Users").child(idUsuario).child("image").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    //TODO: bucle infinito por aqui
                    if(snapshot.exists()){
                        traerImagenPerfil(snapshot.getValue().toString());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        //}

    }



    public SearchView getSearchView() {
        return searchView;
    }


    @Override //agrega la funcionalidad de búsqueda en la toolbar!
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        MenuItem.OnActionExpandListener onActionExpandListener=new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                Toast.makeText(PantallaPublicaciones.this, "expandido", Toast.LENGTH_SHORT).show();
                return true;
            }
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                Toast.makeText(PantallaPublicaciones.this, "colapsado", Toast.LENGTH_SHORT).show();
                return true;
            }
        };
        menu.findItem(R.id.search).setOnActionExpandListener(onActionExpandListener);
        searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setQueryHint("¿Qué querés buscar?");
        searchView.setOnQueryTextListener(this);


        return true;
    }

    private void traerImagenPerfil(String number) {
        Glide.with(ip.getContext()).load(number).into(ip);
    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        selectItemNav(item);
        return true;
    }

    private void selectItemNav(MenuItem item) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        searchView.setVisibility(View.GONE);
        switch (item.getItemId()){
            case R.id.nav_home:
                searchView.setVisibility(View.VISIBLE);
                ft.replace(R.id.content, new HomeFragment(),"Home").commit();

                break;
            case R.id.nav_profile:
                ft.replace(R.id.content, new PerfilFragment(),"Perfil").commit();

                break;
            case R.id.nav_publicar:
                ft.replace(R.id.content, new PublicarFragment(),"Publicar").commit();

                break;
            case R.id.nav_mis_publicaciones:
                ft.replace(R.id.content, new MisPublicacionesFragment(),"Mis Publicaciones").commit();

                break;
            case R.id.nav_categorias:
                ft.replace(R.id.content, new CategoriasFragment(),"Categorias").commit();

                break;
            case R.id.nav_favoritos:
                ft.replace(R.id.content, new FavoritosFragment(),"Favoritos").commit();

                break;
            case R.id.nav_closeSesion:
                //ft.replace(R.id.content, new CerrarSesionFragment(),"Cerrar sesion").commit();
                AlertDialog.Builder builder= new AlertDialog.Builder(PantallaPublicaciones.this);
                builder.setMessage("Seguro que desea cerrar sesion?")
                        .setTitle("Cerrar Sesion")
                        .setPositiveButton("Si!",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dlgInt, int i) {
                                        FirebaseAuth.getInstance().signOut();
                                        Toast.makeText(PantallaPublicaciones.this, "Se ha cerrado la sesion", Toast.LENGTH_SHORT).show();
                                        if(Globales.sesionIniciada==1){
                                            Intent j = new Intent(PantallaPublicaciones.this, PantallaInicio.class);
                                            startActivity(j);
                                        }
                                        finish();
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
                break;
        }
        setTitle(item.getTitle());
        drawerLayout.closeDrawers();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Bundle bundle = new Bundle();
        bundle.putString("busqueda", newText );
        HomeFragment fragInfo = new HomeFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        fragInfo.setArguments(bundle);
        transaction.replace(R.id.content,fragInfo);
        transaction.addToBackStack(null);
        transaction.commit();

        return false;
    }


    @Override
    public void cambiarAfavoritos() {
        MenuItem menuItem =  navigationView.getMenu().getItem(4);
        onNavigationItemSelected(menuItem);
        menuItem.setChecked(true);
        selectItemNav(menuItem);
    }

    @Override
    public void cambiarAPublicaciones() {
        MenuItem menuItem =  navigationView.getMenu().getItem(2);
        onNavigationItemSelected(menuItem);
        menuItem.setChecked(true);
        selectItemNav(menuItem);
    }
}

