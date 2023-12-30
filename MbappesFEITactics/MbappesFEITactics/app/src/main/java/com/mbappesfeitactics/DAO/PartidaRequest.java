package com.mbappesfeitactics.DAO;

import com.google.gson.annotations.SerializedName;
import com.mbappesfeitactics.POJO.Movimiento;

import java.util.List;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class PartidaRequest {

    @SerializedName("Gamertag")
    private String gamertag;

    @SerializedName("Movimientos")
    private List<Movimiento> listaMovimientos;

    // Constructor sin parámetros
    public PartidaRequest() {
    }

    // Constructor con parámetros
    public PartidaRequest(String gamertag, List<Movimiento> listaMovimientos) {
        this.gamertag = gamertag;
        this.listaMovimientos = listaMovimientos;
    }

    // Getter y Setter para gamertag
    public String getGamertag() {
        return gamertag;
    }

    public void setGamertag(String gamertag) {
        this.gamertag = gamertag;
    }

    // Getter y Setter para listaMovimientos
    public List<Movimiento> getListaMovimientos() {
        return listaMovimientos;
    }

    public void setListaMovimientos(List<Movimiento> listaMovimientos) {
        this.listaMovimientos = listaMovimientos;
    }
}

