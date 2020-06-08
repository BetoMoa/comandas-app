package com.example.comandasapp.Platillos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.comandasapp.R;
import com.example.comandasapp.home_page;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class actualizar_platillo extends AppCompatActivity {

    private FirebaseDatabase fDB;
    private DatabaseReference rDB;
    private EditText txtNombre_platillo, txtPrecio_platillo, txtCodigo_platillo;
    private String nombre_platillo, precio_platillo, codigo_platillo, id_platillo;
    private Button btnActualizar, btnEliminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actualizar);
        getSupportActionBar().hide();

        txtNombre_platillo = (EditText) findViewById(R.id.edtNombre);
        txtPrecio_platillo = (EditText) findViewById(R.id.edtPrecio);
        txtCodigo_platillo = (EditText) findViewById(R.id.edtCodigo);
        btnActualizar = (Button) findViewById(R.id.acttualizar_producto);
        btnEliminar = (Button) findViewById(R.id.eliminar_producto);

        nombre_platillo = getIntent().getStringExtra("nombre_platillo");
        precio_platillo = getIntent().getStringExtra("precio_platillo");
        codigo_platillo = getIntent().getStringExtra("codigo_platillo");
        id_platillo = getIntent().getStringExtra("id_platillo");

        txtNombre_platillo.setText(nombre_platillo);
        txtPrecio_platillo.setText(precio_platillo);
        txtCodigo_platillo.setText(codigo_platillo);

        iniciarFirebase();

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent i = new Intent(actualizar_platillo.this, home_page.class);
                    platillo p = new platillo();
                    p.setId(id_platillo);
                    p.setNombre_platillo(txtNombre_platillo.getText().toString());
                    p.setPrecio_platillo(txtPrecio_platillo.getText().toString());
                    p.setCodigo_platillo(txtCodigo_platillo.getText().toString());
                    rDB.child("platillos").child(p.getId()).setValue(p);
                    Toast.makeText(actualizar_platillo.this, "Se actualizo correctamente", Toast.LENGTH_SHORT).show();
                    startActivity(i);
                    finish();
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(actualizar_platillo.this, home_page.class);
                platillo p = new platillo();
                p.setId(id_platillo);
                rDB.child("platillos").child(p.getId()).removeValue();
                Toast.makeText(actualizar_platillo.this, "Se actualizo correctamente", Toast.LENGTH_SHORT).show();
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
