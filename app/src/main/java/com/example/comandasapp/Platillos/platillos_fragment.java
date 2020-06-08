package com.example.comandasapp.Platillos;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

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
public class platillos_fragment extends Fragment {

    private FirebaseDatabase fDB;
    private DatabaseReference rDB;
    private ListView lv_platillos;
    private String nombre_platillo, precio_platillo, codigo_platillo, id_platillo;

    private List<platillo> listaPlatillo = new ArrayList<platillo>();
    ArrayAdapter<platillo> arrayAdapterPlatillo;

    Button btnAgregar;

    platillo platilloSeleccionado;

    public platillos_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View main = inflater.inflate(R.layout.fragment_platillos_fragment, container, false);

        lv_platillos = (ListView) main.findViewById(R.id.lv_platillos);
        btnAgregar = (Button) main.findViewById(R.id.btnAgregar_platillo);

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        iniciarFirebase();
        obtenerLista();

        lv_platillos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                platilloSeleccionado = (platillo) parent.getItemAtPosition(position);
                id_platillo = platilloSeleccionado.getId();
                nombre_platillo = platilloSeleccionado.getNombre_platillo();
                precio_platillo = platilloSeleccionado.getPrecio_platillo();
                codigo_platillo = platilloSeleccionado.getCodigo_platillo();
                Intent i = new Intent(getActivity(), actualizar_platillo.class);
                i.putExtra("nombre_platillo", nombre_platillo);
                i.putExtra("precio_platillo", precio_platillo);
                i.putExtra("codigo_platillo", codigo_platillo);
                i.putExtra("id_platillo", id_platillo);
                startActivity(i);
            }
        });

        return main;
    }


    private void openDialog(){
        dialogPlatillo DialogoPlatillo = new dialogPlatillo();
        DialogoPlatillo.show(getActivity().getSupportFragmentManager(), "Agregar platillo");
    }

    public void iniciarFirebase(){
        FirebaseApp.initializeApp(getActivity());
        fDB = FirebaseDatabase.getInstance();
        rDB = fDB.getReference();
    }

    private void obtenerLista(){
        rDB.child("platillos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaPlatillo.clear();
                for(DataSnapshot objSnapshot : dataSnapshot.getChildren())
                {

                    platillo p = objSnapshot.getValue(platillo.class);
                    listaPlatillo.add(p);

                    arrayAdapterPlatillo = new ArrayAdapter<platillo>(requireContext(), android.R.layout.simple_list_item_1, listaPlatillo);
                    lv_platillos.setAdapter(arrayAdapterPlatillo);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
