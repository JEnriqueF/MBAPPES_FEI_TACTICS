package com.mbappesfeitactics.POJO;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Carta implements Parcelable {

    @SerializedName("IDCarta")
    private int idCarta;
    @SerializedName("Costo")
    private int costo;
    @SerializedName("Poder")
    private int poder;
    @SerializedName("Imagen")
    private String imagen; // Representa el campo "Imagen" como un array de bytes

    // Constructor
    public Carta(int idCarta, int costo, int poder, String imagen) {
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

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(idCarta);
        dest.writeInt(costo);
        dest.writeInt(poder);
        dest.writeString(imagen);
    }
}

