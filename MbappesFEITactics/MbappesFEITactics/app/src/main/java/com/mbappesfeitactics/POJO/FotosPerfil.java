package com.mbappesfeitactics.POJO;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class FotosPerfil implements Parcelable {

    @SerializedName("IDFoto")
    private int idFoto;
    @SerializedName("Foto")
    private String foto;

    public FotosPerfil() {
    }

    public FotosPerfil(int idFoto, String foto) {
        this.idFoto = idFoto;
        this.foto = foto;
    }

    public int getIdFoto() {
        return idFoto;
    }

    public void setIdFoto(int idFoto) {
        this.idFoto = idFoto;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(idFoto);
        dest.writeString(foto);
    }

    public static final Parcelable.Creator<FotosPerfil> CREATOR = new Parcelable.Creator<FotosPerfil>() {
        @Override
        public FotosPerfil createFromParcel(Parcel in) {
            return new FotosPerfil(in);
        }

        @Override
        public FotosPerfil[] newArray(int size) {
            return new FotosPerfil[size];
        }
    };

    protected FotosPerfil(Parcel in) {
        idFoto = in.readInt();
        foto = in.readString();
    }
}