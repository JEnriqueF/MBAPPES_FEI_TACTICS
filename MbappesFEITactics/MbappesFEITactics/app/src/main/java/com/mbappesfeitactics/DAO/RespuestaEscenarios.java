package com.mbappesfeitactics.DAO;

import com.mbappesfeitactics.POJO.Carta;
import com.mbappesfeitactics.POJO.Escenario;

import java.util.List;

public class RespuestaEscenarios {

    private List<Escenario> escenarios;

    public RespuestaEscenarios(List<Escenario> escenarios) {
        this.escenarios = escenarios;
    }

    public List<Escenario> getEscenarios() {
        return escenarios;
    }

    public void setEscenarios(List<Escenario> escenarios) {
        this.escenarios = escenarios;
    }
}
