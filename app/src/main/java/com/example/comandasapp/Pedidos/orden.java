package com.example.comandasapp.Pedidos;

import androidx.annotation.NonNull;

public class orden {
    private String platillo, cantidadPlatillo, bebida, cantidadBebida, mesa, total, id_orden;

    public String getPlatillo() {
        return platillo;
    }

    public void setPlatillo(String platillo) {
        this.platillo = platillo;
    }

    public String getCantidadPlatillo() {
        return cantidadPlatillo;
    }

    public void setCantidadPlatillo(String cantidadPlatillo) {
        this.cantidadPlatillo = cantidadPlatillo;
    }

    public String getBebida() {
        return bebida;
    }

    public void setBebida(String bebida) {
        this.bebida = bebida;
    }

    public String getCantidadBebida() {
        return cantidadBebida;
    }

    public void setCantidadBebida(String cantidadBebida) {
        this.cantidadBebida = cantidadBebida;
    }

    public String getMesa() {
        return mesa;
    }

    public void setMesa(String mesa) {
        this.mesa = mesa;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getId_orden() {
        return id_orden;
    }

    public void setId_orden(String id_orden) {
        this.id_orden = id_orden;
    }

    @NonNull
    @Override
    public String toString() {
        return "\n" + platillo + "\n" + "Cantidad: " + cantidadPlatillo + "\n" + bebida + "\n" + "Cantidad: " + cantidadBebida + "\n" + "Mesa: " + mesa + "\n" + "Total: " + total + "\n";
    }
}
