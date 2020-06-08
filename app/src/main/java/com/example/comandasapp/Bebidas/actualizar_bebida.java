package com.example.comandasapp.Bebidas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.comandasapp.Platillos.platillo;
import com.example.comandasapp.R;
import com.example.comandasapp.home_page;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class actualizar_bebida extends AppCompatActivity {

    private FirebaseDatabase fDB;
    private DatabaseReference rDB;
    private EditText txtNombre_bebida, txtPrecio_bebida, txtCodigo_bebida;
    private String nombreBebida, precioBebida, codigoBebida, id_bebida;
    private Button btnActualizar, btnEliminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actualizar);
        getSupportActionBar().hide();

        txtNombre_bebida = (EditText) findViewById(R.id.edtNombre);
        txtPrecio_bebida = (EditText) findViewById(R.id.edtPrecio);
        txtCodigo_bebida = (EditText) findViewById(R.id.edtCodigo);
        btnActualizar = (Button) findViewById(R.id.acttualizar_producto);
        btnEliminar = (Button) findViewById(R.id.eliminar_producto);

        nombreBebida = getIntent().getStringExtra("nombre_bebida");
        precioBebida = getIntent().getStringExtra("precio_bebida");
        codigoBebida = getIntent().getStringExtra("codigo_bebida");
        id_bebida = getIntent().getStringExtra("id_bebida");

        txtNombre_bebida.setText(nombreBebida);
        txtPrecio_bebida.setText(precioBebida);
        txtCodigo_bebida.setText(codigoBebida);

        iniciarFirebase();

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(actualizar_bebida.this, home_page.class);
                bebida b = new bebida();
                b.setId_bebida(id_bebida);
                b.setNombre_bebida(txtNombre_bebida.getText().toString());
                b.setPrecio_bebida(txtPrecio_bebida.getText().toString());
                b.setCodigo_bebida(txtCodigo_bebida.getText().toString());
                rDB.child("bebidas").child(b.getId_bebida()).setValue(b);
                Toast.makeText(actualizar_bebida.this, "Se actualizo correctamente", Toast.LENGTH_SHORT).show();
                startActivity(i);
                finish();
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(actualizar_bebida.this, home_page.class);
                bebida b = new bebida();
                b.setId_bebida(id_bebida);
                rDB.child("bebidas").child(b.getId_bebida()).removeValue();
                Toast.makeText(actualizar_bebida.this, "Se actualizo correctamente", Toast.LENGTH_SHORT).show();
                startActivity(i);
                finish();
            }
        });
    }

    public void iniciarFirebase(){
        FirebaseApp.initializeApp(this);
        fDB = FirebaseDatabase.getInstance();
        rDB = fDB.getReference();
    }
}