package com.example.comandasapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private Button btnRegistro, btnIniciarSesion, btnResetPassword;
    private EditText txtUusuario, txtPassword;
    private String email, password;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();

        btnRegistro = (Button) findViewById(R.id.registrarse);
        txtUusuario = (EditText) findViewById(R.id.txUsuario);
        txtPassword = (EditText) findViewById(R.id.txPassword);
        btnIniciarSesion = (Button) findViewById(R.id.iniciar_sesion);
        btnResetPassword = (Button) findViewById(R.id.recuperarPassword);

        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = txtUusuario.getText().toString();
                password = txtPassword.getText().toString();

                if(!email.isEmpty() && !password.isEmpty())
                {
                    loginUsuario();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "El usuario o la contraseña es incorrecta", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, registrar_usuario.class);
                startActivity(intent);
            }
        });

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, reestablecer_contrasena.class));
            }
        });
    }

    public void  loginUsuario()
    {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    startActivity(new Intent(MainActivity.this, home_page.class));
                }
                else
                {
                    Toast.makeText(MainActivity.this, "No se pudo iniciar sesión comprueba tus datos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() != null)
        {
            startActivity(new Intent(MainActivity.this, home_page.class));
            finish();
        }
    }
}
