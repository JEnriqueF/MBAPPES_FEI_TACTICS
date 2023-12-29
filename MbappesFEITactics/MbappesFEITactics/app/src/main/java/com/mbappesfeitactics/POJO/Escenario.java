package com.mbappesfeitactics.POJO;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Escenario implements Parcelable {
    @SerializedName("IDEscenario")
    private int idEscenario;

    @SerializedName("Imagen")
    private String imagen;

    public Escenario(int idEscenario, String imagen){
        this.idEscenario = idEscenario;
        this.imagen = imagen;
    }

    public Escenario(){

    }

    public int getIdEscenario(){
        return idEscenario;
    }

    public void setIdEscenario(int idEscenario){
        this.idEscenario = idEscenario;
    }

    public String getImagen(){
        return imagen;
    }

    public void setImagen(String imagen){
        this.imagen = imagen;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(idEscenario);
        dest.writeString(imagen);
    }
}
