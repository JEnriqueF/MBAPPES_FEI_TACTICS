package com.mbappesfeitactics.POJO;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Jugador {
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
    @SerializedName("IdFoto")
    private int idFoto;

    public Jugador(String gamertag, String password, int partidasGanadas, int partidasPerdidas, String mazo, int idFoto){
        this.gamertag = gamertag;
        this.password = password;
        this.partidasGanadas = partidasGanadas;
        this.partidasPerdidas = partidasPerdidas;
        this.mazo = mazo;
        this.idFoto = idFoto;
    }

    public String getGamertag() {
        return gamertag;
    }

    public String getPassword(){
        return password;
    }

    public int getPartidasGanadas() { return partidasGanadas; }

    public int getPartidasPerdidas() { return partidasPerdidas; }

    public String getMazo() { return mazo; }

    public int getIdFoto() { return idFoto; }
}