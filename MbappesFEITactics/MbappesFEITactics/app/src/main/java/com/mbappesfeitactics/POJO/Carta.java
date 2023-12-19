package com.mbappesfeitactics.POJO;

import com.google.gson.annotations.SerializedName;

public class Carta {

    @SerializedName("IDCarta")
    private int idCarta;
    @SerializedName("Costo")
    private int costo;
    @SerializedName("Poder")
    private int poder;
    @SerializedName("Imagen")
    private byte[] imagen; // Representa el campo "Imagen" como un array de bytes

    // Constructor
    public Carta(int idCarta, int costo, int poder, byte[] imagen) {
        this.idCarta = idCarta;
        this.costo = costo;
        this.poder = poder;
        this.imagen = imagen;
    }

    public Carta() {

    }

    // Getters y setters

    public int getIdCarta() {
        return idCarta;
    }

    public void setIdCarta(int idCarta) {
        this.idCarta = idCarta;
    }

    public int getCosto() {
        return costo;
    }

    public void setCosto(int costo) {
        this.costo = costo;
    }

    public int getPoder() {
        return poder;
    }

    public void setPoder(int poder) {
        this.poder = poder;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }
}

