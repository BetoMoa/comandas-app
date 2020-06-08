package com.example.comandasapp.Bebidas;

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

public class dialogBebida extends AppCompatDialogFragment {

    private EditText txtNombre_bebida, txtPrecio_bebida, txtCodigo_bebida;
    private String nombreBebida, precioBebida, codigoBebida;
    private FirebaseDatabase fDB;
    private DatabaseReference rDB;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.agregar_bebida, null);

        txtNombre_bebida = (EditText) view.findViewById(R.id.edtBebida);
        txtPrecio_bebida = (EditText) view.findViewById(R.id.edtPrecioBebida);
        txtCodigo_bebida = (EditText) view.findViewById(R.id.edtCodigoBebida);

        builder.setView(view)
                .setTitle("Agregar bebida")
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        nombreBebida = txtNombre_bebida.getText().toString();
                        precioBebida = txtPrecio_bebida.getText().toString();
                        codigoBebida = txtCodigo_bebida.getText().toString();

                        if (!nombreBebida.isEmpty() && !precioBebida.isEmpty() && !codigoBebida.isEmpty())
                        {
                            iniciarFirebase();
                            bebida b = new bebida();
                            b.setId_bebida(UUID.randomUUID().toString());
                            b.setNombre_bebida(nombreBebida);
                            b.setPrecio_bebida(precioBebida);
                            b.setCodigo_bebida(codigoBebida);
                            rDB .child("bebidas").child(b.getId_bebida()).setValue(b);
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