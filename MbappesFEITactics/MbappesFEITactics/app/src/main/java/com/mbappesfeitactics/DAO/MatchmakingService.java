package com.mbappesfeitactics.DAO;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface MatchmakingService {
    @POST("/matchmaking/solicitarpartida")
    Call<RespuestaMatchmaking> solicitarPartida(@Body String jugadorSolicitado);

    @POST("/matchmaking/cancelarpartida")
    Call<RespuestaMatchmaking> cancelarPartida(@Body String jugadorCancelado);

}
