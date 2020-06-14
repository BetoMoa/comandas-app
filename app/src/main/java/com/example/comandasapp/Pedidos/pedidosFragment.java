package com.example.comandasapp.Pedidos;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

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
public class pedidosFragment extends Fragment {

    Button btnTomar;
    ListView lv_ordenesPendientes, lv_ordenesListas;
    private List<orden> listaOrdenesPendientes = new ArrayList<orden>();
    ArrayAdapter<orden> arrayAdapterOrdenesPendientes;
    private FirebaseDatabase fDB;
    private DatabaseReference rDB;

    public void iniciarFirebase(){
        FirebaseApp.initializeApp(getActivity());
        fDB = FirebaseDatabase.getInstance();
        rDB = fDB.getReference();
    }

    public pedidosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_pedidos, container, false);

        lv_ordenesPendientes = (ListView) v.findViewById(R.id.orden_pendiente);

        iniciarFirebase();

        obtenerListaOrdenesPendientes();

        return v;
    }

    private void obtenerListaOrdenesPendientes(){
        rDB.child("ordenes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaOrdenesPendientes.clear();
                for(DataSnapshot objSnapshot : dataSnapshot.getChildren())
                {

                    orden o = objSnapshot.getValue(orden.class);
                    listaOrdenesPendientes.add(o);

                    arrayAdapterOrdenesPendientes = new ArrayAdapter<orden>(requireContext(), android.R.layout.simple_list_item_1, listaOrdenesPendientes);
                    lv_ordenesPendientes.setAdapter(arrayAdapterOrdenesPendientes);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
