package com.mbappesfeitactics.POJO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Jugador {
    private String gamertag;
    private String password;
    private int partidasGanadas;
    private int partidasPerdidas;
    private String mazo;
    private int idFoto;

    public Jugador(String gamertag, String password){
        this.gamertag = gamertag;
        this.password = password;
    }
}