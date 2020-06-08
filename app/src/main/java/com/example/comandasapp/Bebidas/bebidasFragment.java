package com.example.comandasapp.Bebidas;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.comandasapp.Platillos.actualizar_platillo;
import com.example.comandasapp.Platillos.dialogPlatillo;
import com.example.comandasapp.Platillos.platillo;
import com.example.comandasapp.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class bebidasFragment extends Fragment {

    private String id_bebida, nombre_bebida, precio_bebida, codigo_bebida;
    private FirebaseDatabase fDB;
    private DatabaseReference rDB;
    private ListView lv_bebidas;

    private List<bebida> listaBebidas = new ArrayList<bebida>();
    ArrayAdapter<bebida> arrayAdapterBebida;

    Button btnAgregar;

    bebida bebidaSeleccionada;


    public bebidasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View main = inflater.inflate(R.layout.fragment_bebidas, container, false);

        lv_bebidas = (ListView) main.findViewById(R.id.lv_bebidas);
        btnAgregar = (Button) main.findViewById(R.id.btnAgregar_bebida);

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        iniciarFirebase();
        obtenerLista();

        lv_bebidas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                bebidaSeleccionada = (bebida) parent.getItemAtPosition(position);
                id_bebida = bebidaSeleccionada.getId_bebida();
                nombre_bebida = bebidaSeleccionada.getNombre_bebida();
                precio_bebida = bebidaSeleccionada.getPrecio_bebida();
                codigo_bebida = bebidaSeleccionada.getCodigo_bebida();
                Intent i = new Intent(getActivity(), actualizar_bebida.class);
                i.putExtra("nombre_bebida", nombre_bebida);
                i.putExtra("precio_bebida", precio_bebida);
                i.putExtra("codigo_bebida", codigo_bebida);
                i.putExtra("id_bebida", id_bebida);
                startActivity(i);
            }
        });

        return main;
    }

    private void openDialog(){
        dialogBebida DialogoBebida = new dialogBebida();
        DialogoBebida.show(getActivity().getSupportFragmentManager(), "Agregar bebida");
    }

    public void iniciarFirebase(){
        FirebaseApp.initializeApp(getActivity());
        fDB = FirebaseDatabase.getInstance();
        rDB = fDB.getReference();
    }

    private void obtenerLista(){
        rDB.child("bebidas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaBebidas.clear();
                for(DataSnapshot objSnapshot : dataSnapshot.getChildren())
                {

                    bebida b = objSnapshot.getValue(bebida.class);
                    listaBebidas.add(b);

                    arrayAdapterBebida = new ArrayAdapter<bebida>(requireContext(), android.R.layout.simple_list_item_1, listaBebidas);
                    lv_bebidas.setAdapter(arrayAdapterBebida);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
