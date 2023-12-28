package com.mbappesfeitactics.DAO;

import com.google.gson.annotations.SerializedName;

public class RespuestaMatchmaking {
    @SerializedName("Respuesta")
    private String respuesta;

    @SerializedName("Gamertag")
    private String gamertag;

    public RespuestaMatchmaking() {
        // Puedes inicializar valores predeterminados aquí si es necesario.
    }

    public RespuestaMatchmaking(String respuesta, String gamertag) {
        this.respuesta = respuesta;
        this.gamertag = gamertag;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public String getGamertag() {
        return gamertag;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public void setGamertag(String gamertag) {
        this.gamertag = gamertag;
    }

    // Puedes implementar equals, hashCode, y toString aquí si es necesario.
}