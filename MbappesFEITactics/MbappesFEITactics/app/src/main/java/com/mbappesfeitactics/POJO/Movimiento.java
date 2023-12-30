package com.mbappesfeitactics.POJO;

import com.google.gson.annotations.SerializedName;

public class Movimiento {
    @SerializedName("Escenario")
    private int idEscenario;

    @SerializedName("Carta")
    private int idCarta;

    // Constructor sin parámetros
    public Movimiento() {
    }

    // Constructor con parámetros
    public Movimiento(int idEscenario, int idCarta) {
        this.idEscenario = idEscenario;
        this.idCarta = idCarta;
    }

    // Getter y Setter para idEscenario
    public int getIdEscenario() {
        return idEscenario;
    }

    public void setIdEscenario(int idEscenario) {
        this.idEscenario = idEscenario;
    }

    // Getter y Setter para idCarta
    public int getIdCarta() {
        return idCarta;
    }

    public void setIdCarta(int idCarta) {
        this.idCarta = idCarta;
    }
}
