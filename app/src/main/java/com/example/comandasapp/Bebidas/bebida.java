package com.example.comandasapp.Bebidas;

import androidx.annotation.NonNull;

public class bebida {
    private String id_bebida, nombre_bebida, precio_bebida, codigo_bebida;

    public String getId_bebida() {
        return id_bebida;
    }

    public void setId_bebida(String id_bebida) {
        this.id_bebida = id_bebida;
    }

    public String getNombre_bebida() {
        return nombre_bebida;
    }

    public void setNombre_bebida(String nombre_bebida) {
        this.nombre_bebida = nombre_bebida;
    }

    public String getPrecio_bebida() {
        return precio_bebida;
    }

    public void setPrecio_bebida(String precio_bebida) {
        this.precio_bebida = precio_bebida;
    }

    public String getCodigo_bebida() {
        return codigo_bebida;
    }

    public void setCodigo_bebida(String codigo_bebida) {
        this.codigo_bebida = codigo_bebida;
    }

    @NonNull
    @Override
    public String toString() {
        return "\n" + nombre_bebida + "\n\n" + precio_bebida + " $" + "\n" + "Codigo: " + codigo_bebida + "\n";
    }
}
