package com.mbappesfeitactics.DAO;

import com.mbappesfeitactics.POJO.Carta;

import java.util.List;

public class RespuestaCartas {

    private List<Carta> cartas;

    public RespuestaCartas(List<Carta> cartas) {
        this.cartas = cartas;
    }

    public List<Carta> getCartas() {
        return cartas;
    }

    public void setCartas(List<Carta> cartas) {
        this.cartas = cartas;
    }
}

