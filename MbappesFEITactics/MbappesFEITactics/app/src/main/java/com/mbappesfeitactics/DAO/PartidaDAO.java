package com.mbappesfeitactics.DAO;

import java.util.Map;

import retrofit2.Callback;
import retrofit2.Retrofit;

public class PartidaDAO {

    public static void jugarTurno(PartidaRequest solicitudPartida, final Callback<RespuestaPartida> callback) {
        Retrofit retrofit = APIClient.iniciarAPI();
        MatchmakingService matchmakingService = retrofit.create(MatchmakingService.class);

    }
}
