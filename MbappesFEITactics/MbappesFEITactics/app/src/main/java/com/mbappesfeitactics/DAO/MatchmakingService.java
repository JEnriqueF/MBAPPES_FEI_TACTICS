package com.mbappesfeitactics.DAO;

import com.mbappesfeitactics.POJO.Jugador;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.PATCH;
import retrofit2.http.POST;

public interface MatchmakingService {
    @POST("/matchmaking/solicitarpartida")
    Call<RespuestaMatchmaking> solicitarPartida(@Body Map<String, String> jugadorSolicitado);

    @PATCH("/matchmaking/cancelarbusqueda")
    Call<RespuestaMatchmaking> cancelarBusqueda(@Body Map<String, String> jugadorSolicitado);

    @PATCH("/matchmaking/cancelarpartida")
    Call<RespuestaMatchmaking> cancelarPartida(@Body Map<String, String> jugadorCancelado);

    @POST("/matchmaking/jugarturno")
    Call<RespuestaPartida> jugarTurno(@Body Map<String, Object> movimientos);

    @POST("/matchmaking/guardarresultado")
    Call<String> guardarResultado(@Body Map<String, Object> datosEnviar);

}
