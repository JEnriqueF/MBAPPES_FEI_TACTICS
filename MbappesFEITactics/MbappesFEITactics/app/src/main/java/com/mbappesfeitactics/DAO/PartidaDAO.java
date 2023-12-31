package com.mbappesfeitactics.DAO;

import android.util.Log;

import com.mbappesfeitactics.POJO.Jugador;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PartidaDAO {

    public static void jugarTurno(PartidaRequest solicitudPartida, final Callback<RespuestaPartida> callback) {
        Retrofit retrofit = APIClient.iniciarAPI();
        MatchmakingService matchmakingService = retrofit.create(MatchmakingService.class);

        Map<String, Object> solicitud = new HashMap<>();
        solicitud.put("Gamertag", solicitudPartida.getGamertag());
        solicitud.put("Movimientos", solicitudPartida.getListaMovimientos());
        Call<RespuestaPartida> call = matchmakingService.jugarTurno(solicitud);

        call.enqueue(new Callback<RespuestaPartida>() {
            @Override
            public void onResponse(Call<RespuestaPartida> call, Response<RespuestaPartida> response) {
                if (response.isSuccessful()) {
                    RespuestaPartida respuestaPartida = response.body();
                    callback.onResponse(call, Response.success(respuestaPartida));
                }else{
                    callback.onFailure(call, new Throwable("Error en la respuesta: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<RespuestaPartida> call, Throwable t) {
                Log.d("PRUEBAGG", "Error en la conexión: " + t.getMessage());
                callback.onFailure(call, t);
            }
        });
    }
}
