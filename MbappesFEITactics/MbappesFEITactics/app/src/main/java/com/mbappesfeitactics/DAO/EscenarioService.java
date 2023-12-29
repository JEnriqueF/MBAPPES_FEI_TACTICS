package com.mbappesfeitactics.DAO;

import retrofit2.Call;
import retrofit2.http.GET;

public interface EscenarioService {
    @GET("/escenario/recuperarescenarios")
    Call<RespuestaEscenarios> recuperarEscenarios();
}
