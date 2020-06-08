package com.example.comandasapp.Pedidos;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.comandasapp.Bebidas.bebida;
import com.example.comandasapp.Platillos.platillo;
import com.example.comandasapp.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class dialogTomarOrden extends AppCompatDialogFragment implements View.OnClickListener {

   private String platillo, cantidadPlatillo, precioPlatillo, bebida, cantidadBebida, precioBebida, mesa, total;
   private int cantidad_platillo, precio_platillo, cantidad_bebida, precio_bebida, importe;
   private Button btnMasPlatillo, btnMenosPlatillo, btnMenosBebida, btnMasBebida;
   private Spinner spnPlatillo, spnBebida;
   private TextView c_platillo, c_bebida;
   private EditText edtMesa;

   private com.example.comandasapp.Platillos.platillo platilloSeleccionado;
    private com.example.comandasapp.Bebidas.bebida bebidaSeleccionado;

    private List<com.example.comandasapp.Platillos.platillo> listaPlatillos = new ArrayList<platillo>();
    ArrayAdapter<platillo> arrayAdapterPlatillo;

   private List<com.example.comandasapp.Bebidas.bebida> listaBebidas = new ArrayList<bebida>();
    ArrayAdapter<bebida> arrayAdapterBebida;


    private FirebaseDatabase fDB;
    private DatabaseReference rDB;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.tomar_orden, null);

        btnMenosPlatillo = (Button) view.findViewById(R.id.btnMenosPlatillo);
        btnMasPlatillo = (Button) view.findViewById(R.id.btnMasPlatillo);
        btnMenosBebida = (Button) view.findViewById(R.id.btnMenosBebida);
        btnMasBebida = (Button) view.findViewById(R.id.btnMasBebida);

        spnPlatillo = (Spinner) view.findViewById(R.id.spnPlatillo);
        spnBebida = (Spinner) view.findViewById(R.id.spnBebida);

        c_platillo = (TextView) view.findViewById(R.id.cantidadPlatillos);
        c_bebida = (TextView) view.findViewById(R.id.cantidadBebidas);

        btnMasPlatillo.setOnClickListener(this);
        btnMenosPlatillo.setOnClickListener(this);
        btnMenosBebida.setOnClickListener(this);
        btnMasBebida.setOnClickListener(this);

        edtMesa = (EditText) view.findViewById(R.id.mesa);

        iniciarFirebase();
        obtenerListaBebidas();
        obtenerListaPlatillos();

        spnPlatillo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                platilloSeleccionado = (platillo) parent.getItemAtPosition(position);
                platillo = platilloSeleccionado.getNombre_platillo();
                precioPlatillo = platilloSeleccionado.getPrecio_platillo();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spnBebida.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bebidaSeleccionado = (bebida) parent.getItemAtPosition(position);
                bebida = bebidaSeleccionado.getNombre_bebida();
                precioBebida = bebidaSeleccionado.getPrecio_bebida();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        builder.setView(view)
                .setTitle("Tomar orden")
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Enviar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        precio_platillo = Integer.parseInt(precioPlatillo);
                        precio_bebida = Integer.parseInt(precioBebida);


                        //cantidadPlatillo = c_platillo.getText().toString();
                        //cantidadBebida = c_bebida.getText().toString();
                        mesa = edtMesa.getText().toString();
                        importe = (precio_platillo * cantidad_platillo) + (precio_bebida * cantidad_bebida);
                        total = Integer.toString(importe);

                        orden o = new orden();
                        o.setId_orden(UUID.randomUUID().toString());
                        o.setPlatillo(platillo);
                        o.setCantidadPlatillo(cantidadPlatillo);
                        o.setBebida(bebida);
                        o.setCantidadBebida(cantidadBebida);
                        o.setMesa(mesa);
                        o.setTotal(total);
                        rDB .child("ordenes").child(o.getId_orden()).setValue(o);
                        Toast.makeText(getActivity(), "Se agrego correctamente", Toast.LENGTH_SHORT).show();
                    }
                });
        return builder.create();
    }

    public void iniciarFirebase(){
        FirebaseApp.initializeApp(getActivity());
        fDB = FirebaseDatabase.getInstance();
        rDB = fDB.getReference();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.btnMasPlatillo:
                    cantidad_platillo += 1;
                    cantidadPlatillo = Integer.toString(cantidad_platillo);
                    c_platillo.setText(cantidadPlatillo);
                break;
            case R.id.btnMenosPlatillo:
                cantidad_platillo -= 1;
                cantidadPlatillo = Integer.toString(cantidad_platillo);
                c_platillo.setText(cantidadPlatillo);
                break;
            case R.id.btnMasBebida:
                cantidad_bebida += 1;
                cantidadBebida = Integer.toString(cantidad_bebida);
                c_bebida.setText(cantidadBebida);
                break;
            case R.id.btnMenosBebida:
                cantidad_bebida -= 1;
                cantidadBebida = Integer.toString(cantidad_bebida);
                c_bebida.setText(cantidadBebida);
                break;
        }

    }

    private void obtenerListaBebidas(){
        rDB.child("bebidas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaBebidas.clear();
                for(DataSnapshot objSnapshot : dataSnapshot.getChildren())
                {

                    bebida b = objSnapshot.getValue(bebida.class);
                    b.getNombre_bebida();
                    listaBebidas.add(b);

                    arrayAdapterBebida = new ArrayAdapter<bebida>(requireContext(), android.R.layout.simple_spinner_item, listaBebidas);
                    spnBebida.setAdapter(arrayAdapterBebida);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void obtenerListaPlatillos(){
        rDB.child("platillos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaPlatillos.clear();
                for(DataSnapshot objSnapshot : dataSnapshot.getChildren())
                {

                    platillo p = objSnapshot.getValue(platillo.class);
                    listaPlatillos.add(p);

                    arrayAdapterPlatillo = new ArrayAdapter<platillo>(requireContext(), android.R.layout.simple_spinner_item, listaPlatillos);
                    spnPlatillo.setAdapter(arrayAdapterPlatillo);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
