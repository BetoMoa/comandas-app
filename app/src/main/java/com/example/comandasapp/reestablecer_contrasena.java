package com.example.comandasapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class reestablecer_contrasena extends AppCompatActivity {

    EditText txtCorreo;
    Button btnReset;
    private String email = "";
    private FirebaseAuth mAuth;
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reestablecer_contrasena);
        getSupportActionBar().hide();

        txtCorreo = (EditText) findViewById(R.id.edtCorreo);
        btnReset = (Button) findViewById(R.id.resPassword);
        mAuth = FirebaseAuth.getInstance();
        mDialog = new ProgressDialog(this);

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = txtCorreo.getText().toString();

                if (!email.isEmpty())
                {
                    mDialog.setMessage("Espere un momento...");
                    mDialog.setCanceledOnTouchOutside(false);
                    mDialog.show();
                    resetBtnReset();
                }
                else
                {
                    Toast.makeText(reestablecer_contrasena.this, "Ingrese su correo", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void resetBtnReset(){
        mAuth.setLanguageCode("es");
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(reestablecer_contrasena.this, "Se ha enviado un correo para reestablecer tu contraseña", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(reestablecer_contrasena.this, "No se pudo ejecutar esta accion", Toast.LENGTH_SHORT).show();
                }

                mDialog.dismiss();
            }
        });
    }
}
