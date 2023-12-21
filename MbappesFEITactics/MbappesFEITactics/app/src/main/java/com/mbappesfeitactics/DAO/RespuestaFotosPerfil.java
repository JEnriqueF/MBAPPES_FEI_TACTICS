package com.mbappesfeitactics.DAO;

import com.google.gson.annotations.SerializedName;
import com.mbappesfeitactics.POJO.FotosPerfil;

import java.util.List;

public class RespuestaFotosPerfil {

    @SerializedName("imagenesPerfil")
    private List<FotosPerfil> fotosPerfil;
    public RespuestaFotosPerfil(List<FotosPerfil> fotosPerfil) { this.fotosPerfil = fotosPerfil; }
    public List<FotosPerfil> getFotosPerfil() { return fotosPerfil; }
    public void setFotosPerfil(List<FotosPerfil> fotosPerfil) { this.fotosPerfil = fotosPerfil; }
}
