package com.mbappesfeitactics.DAO;

import com.google.gson.annotations.SerializedName;
import com.mbappesfeitactics.POJO.Movimiento;

import java.util.ArrayList;
import java.util.List;

public class RespuestaPartida {

    @SerializedName("Respuesta")
    private String respuesta;

    @SerializedName("Movimientos")
    private List<Movimiento> listaMovimientos;

    // Constructor sin parámetros
    public RespuestaPartida() {
    }

    // Constructor con parámetros
    public RespuestaPartida(String respuesta, List<Movimiento> listaMovimientos) {
        this.respuesta = respuesta;
        this.listaMovimientos = listaMovimientos;
    }

    // Getter y Setter para respuesta
    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    // Getter y Setter para listaMovimientos
    public List<Movimiento> getListaMovimientos() {
        return listaMovimientos;
    }

    public void setListaMovimientos(List<Movimiento> listaMovimientos) {
        this.listaMovimientos = listaMovimientos;
    }
}
