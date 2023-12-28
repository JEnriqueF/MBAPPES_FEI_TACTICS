package com.mbappesfeitactics.POJO;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Jugador implements Parcelable {
    @SerializedName("Gamertag")
    private String gamertag;
    @SerializedName("contrasenia")
    private String password;
    @SerializedName("PartidasGanadas")
    private int partidasGanadas;
    @SerializedName("PartidasPerdidas")
    private int partidasPerdidas;
    @SerializedName("Mazo")
    private String mazo;
    @SerializedName("IDFoto")
    private int idFoto;

    public Jugador(String gamertag, int partidasGanadas, int partidasPerdidas, String mazo, int idFoto,  String password){
        this.gamertag = gamertag;
        this.partidasGanadas = partidasGanadas;
        this.partidasPerdidas = partidasPerdidas;
        this.mazo = mazo;
        this.idFoto = idFoto;
        this.password = password;
    }

    public Jugador() {

    }

    public String getGamertag() {
        return gamertag;
    }

    public void setGamertag(String gamertag) { this.gamertag = gamertag; }

    public String getPassword(){
        return password;
    }

    public int getPartidasGanadas() { return partidasGanadas; }

    public int getPartidasPerdidas() { return partidasPerdidas; }

    public String getMazo() { return mazo; }

    public void setMazo(String mazo) { this.mazo = mazo; }

    public int getIdFoto() { return idFoto; }

    public void setIdFoto(int idFoto){ this.idFoto = idFoto; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(gamertag);
        dest.writeInt(partidasGanadas);
        dest.writeInt(partidasPerdidas);
        dest.writeString(mazo);
        dest.writeInt(idFoto);
        dest.writeString(password);
    }

    protected Jugador(Parcel in){
        gamertag = in.readString();
        partidasGanadas = in.readInt();
        partidasPerdidas = in.readInt();
        mazo = in.readString();
        idFoto = in.readInt();
        password = in.readString();
    }

    public static final Creator<Jugador> CREATOR = new Creator<Jugador>() {
        @Override
        public Jugador createFromParcel(Parcel in) {
            return new Jugador(in);
        }

        @Override
        public Jugador[] newArray(int size) {
            return new Jugador[size];
        }
    };
}