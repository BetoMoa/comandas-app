package com.example.comandasapp.Platillos;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.comandasapp.Platillos.platillo;
import com.example.comandasapp.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class dialogPlatillo extends AppCompatDialogFragment {

    private EditText txtNombre_platillo, txtPrecio_platillo, txtCodigo_platillo;
    private String nombrePlatillo, precioPlatillo, codigoPlatillo;
    private FirebaseDatabase fDB;
    private DatabaseReference rDB;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.agregar_platillo, null);

        txtNombre_platillo = (EditText) view.findViewById(R.id.edtPlatillo);
        txtPrecio_platillo = (EditText) view.findViewById(R.id.edtPrecioPlatillo);
        txtCodigo_platillo = (EditText) view.findViewById(R.id.edtCodigoPlatillo);

        builder.setView(view)
                .setTitle("Agregar platillo")
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        nombrePlatillo = txtNombre_platillo.getText().toString();
                        precioPlatillo = txtPrecio_platillo.getText().toString();
                        codigoPlatillo = txtCodigo_platillo.getText().toString();

                        if (!nombrePlatillo.isEmpty() && !precioPlatillo.isEmpty() && !codigoPlatillo.isEmpty())
                        {
                            iniciarFirebase();
                            platillo p = new platillo();
                            p.setId(UUID.randomUUID().toString());
                            p.setNombre_platillo(nombrePlatillo);
                            p.setPrecio_platillo(precioPlatillo);
                            p.setCodigo_platillo(codigoPlatillo);
                            rDB .child("platillos").child(p.getId()).setValue(p);
                            Toast.makeText(getActivity(), "Se agrego correctamente", Toast.LENGTH_SHORT).show();

                        }
                        else
                        {
                            Toast.makeText(getActivity(), "No puede haber campos vacios", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
        return builder.create();
    }

    public void iniciarFirebase(){
        FirebaseApp.initializeApp(getActivity());
        fDB = FirebaseDatabase.getInstance();
        rDB = fDB.getReference();
    }
}
