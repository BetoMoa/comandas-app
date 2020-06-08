package com.example.comandasapp.Platillos;

import androidx.annotation.NonNull;

public class platillo {

    private String id, nombre_platillo, precio_platillo, codigo_platillo;

    public platillo() {
    }

    public platillo(String id, String nombre_platillo, String precio_platillo, String codigo_platillo) {
        this.id = id;
        this.nombre_platillo = nombre_platillo;
        this.precio_platillo = precio_platillo;
        this.codigo_platillo = codigo_platillo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre_platillo() {
        return nombre_platillo;
    }

    public void setNombre_platillo(String nombre_platillo) {
        this.nombre_platillo = nombre_platillo;
    }

    public String getPrecio_platillo() {
        return precio_platillo;
    }

    public void setPrecio_platillo(String precio_platillo) {
        this.precio_platillo = precio_platillo;
    }

    public String getCodigo_platillo() {
        return codigo_platillo;
    }

    public void setCodigo_platillo(String codigo_platillo) {
        this.codigo_platillo = codigo_platillo;
    }

    @NonNull
    @Override
    public String toString() {
        return "\n" + nombre_platillo + "\n\n" + precio_platillo + " $" + "\n" + "Codigo: " + codigo_platillo + "\n";
    }
}
