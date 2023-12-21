package com.mbappesfeitactics.DAO;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FotosPerfilService {
    @GET("/jugador/recuperarfotosperfil")
    Call<RespuestaFotosPerfil> recuperarImagenesPerfil();
}
