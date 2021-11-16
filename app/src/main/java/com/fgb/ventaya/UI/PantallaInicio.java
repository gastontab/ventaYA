package com.fgb.ventaya.UI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.fgb.ventaya.Entity.Globales;
import com.fgb.ventaya.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PantallaInicio extends AppCompatActivity {

    //creacion de elementos
    Button btnLogin, btnRegister;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            // User is signed in (getCurrentUser() will be null if not signed in)
            Intent i = new Intent(PantallaInicio.this, PantallaPublicaciones.class);
            i.putExtra("pantalla", "iniciosesion");
            startActivity(i);
            finish();
            Globales.sesionIniciada=1;
        }

        setTheme(R.style.ThemeVentaYa);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_inicio);

        // Inicializar Firebase Auth
      //  mAuth = FirebaseAuth.getInstance();
        // Iniciar Session como usuario anónimo
       // mAuth.signInAnonymously();
        //Log.d("iduser", mAuth.getCurrentUser().getUid());

        //inicializar elementos
        btnLogin = findViewById(R.id.buttonLogin);
        btnRegister = findViewById(R.id.buttonRegister);

        //intent hacia login
        btnLogin.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View view) {
                Globales.sesionIniciada=0;
                Intent i = new Intent(PantallaInicio.this, PantallaIniciarSesion.class);
                startActivity(i);
            }
        });

        //intent hacia registrarse
        btnRegister.setOnClickListener(new View.OnClickListener(){

            //intent hacia login
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PantallaInicio.this, PantallaRegistro.class);
                startActivity(i);
            }
        });

    }

   /* private void signInAnonymously() {
        mAuth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Exito
                            Log.d("Firebase", "signInAnonymously:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // Error
                            Log.w("Firebase", "signInAnonymously:failure", task.getException());
                            Toast.makeText(PantallaInicio.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }*/



}
