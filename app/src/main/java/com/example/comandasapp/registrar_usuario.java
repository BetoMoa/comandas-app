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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class registrar_usuario extends AppCompatActivity {

    private EditText txtNombre, txtCorreo, txtPassword;
    private Button btnRegistrar;

    //Variables para almacenar los datos

    private String nombre = "";
    private String email = "";
    private String password = "";

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro_usuarios);
        getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        txtNombre = findViewById(R.id.txUsuario);
        txtCorreo = findViewById(R.id.txCorreo);
        txtPassword = findViewById(R.id.txPassword);
        btnRegistrar = findViewById(R.id.registrar_usuario);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombre = txtNombre.getText().toString();
                email = txtCorreo.getText().toString();
                password = txtPassword.getText().toString();

                if(!nombre.isEmpty() &&  !email.isEmpty() && !password.isEmpty())
                {
                    if (password.length() >= 6)
                    {
                        registrarUsuario();
                        Intent intent = new Intent(registrar_usuario.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        Toast.makeText(registrar_usuario.this, "La contraseña debe contener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(registrar_usuario.this, "Hay campos vacios por llenar", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void registrarUsuario()
    {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    Map<String, Object> map = new HashMap<>();
                    map.put("nombre", nombre);
                    map.put("email", email);
                    map.put("password", password);

                    String id = mAuth.getCurrentUser().getUid();

                    mDatabase.child("usuarios").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if(task2.isSuccessful())
                            {
                                Toast.makeText(registrar_usuario.this, "Se registro correctamente", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(registrar_usuario.this, "No se crearon los datos correctamente", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
                else
                {
                    Toast.makeText(registrar_usuario.this, "No se pudo registrar este usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
