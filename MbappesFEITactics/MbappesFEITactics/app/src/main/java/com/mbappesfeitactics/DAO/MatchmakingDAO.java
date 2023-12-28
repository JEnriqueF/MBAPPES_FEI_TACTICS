package com.mbappesfeitactics.DAO;

import android.util.Log;

import com.mbappesfeitactics.POJO.Jugador;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MatchmakingDAO {

    public static void solicitarPartida(String gamertag, final Callback<RespuestaMatchmaking> callback){
        Retrofit retrofit = APIClient.iniciarAPI();
        MatchmakingService matchmakingService = retrofit.create(MatchmakingService.class);

        Map<String, String> jugador = new HashMap<>();
        jugador.put("Gamertag", gamertag);
        Call<RespuestaMatchmaking> call = matchmakingService.solicitarPartida(jugador);

        call.enqueue(new Callback<RespuestaMatchmaking>() {
            @Override
            public void onResponse(Call<RespuestaMatchmaking> call, Response<RespuestaMatchmaking> response) {
                if(response.isSuccessful()){
                    RespuestaMatchmaking respuestaMatchmakingSolicitada = response.body();
                    callback.onResponse(call, Response.success(respuestaMatchmakingSolicitada));
                }else{
                    callback.onFailure(call, new Throwable("Error en la respuesta: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<RespuestaMatchmaking> call, Throwable t) {
                Log.d("Prueba","Error en la conexión");
                callback.onFailure(call, t);
            }
        });
    }

    public static void cancelarBusqueda(String gamertag, final Callback<RespuestaMatchmaking> callback) {
        Retrofit retrofit = APIClient.iniciarAPI();
        MatchmakingService matchmakingService = retrofit.create(MatchmakingService.class);
        Map<String, String> jugador = new HashMap<>();
        jugador.put("Gamertag", gamertag);
        Call<RespuestaMatchmaking> call = matchmakingService.cancelarBusqueda(jugador);

        call.enqueue(new Callback<RespuestaMatchmaking>() {
            @Override
            public void onResponse(Call<RespuestaMatchmaking> call, Response<RespuestaMatchmaking> response) {
                if (response.isSuccessful()) {
                    RespuestaMatchmaking respuestaMatchmakingSolicitada = response.body();
                    callback.onResponse(call, Response.success(respuestaMatchmakingSolicitada));
                } else {
                    callback.onFailure(call, new Throwable("Error en la respuesta: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<RespuestaMatchmaking> call, Throwable t) {
                Log.d("Prueba","Error en la conexión");
                callback.onFailure(call, t);
            }
        });
    }

    public static void cancelarPartida(String gamertag, final Callback<RespuestaMatchmaking> callback){
        Retrofit retrofit = APIClient.iniciarAPI();
        MatchmakingService matchmakingService = retrofit.create(MatchmakingService.class);

        Call<RespuestaMatchmaking> call = matchmakingService.cancelarPartida(gamertag);

        call.enqueue(new Callback<RespuestaMatchmaking>() {
            @Override
            public void onResponse(Call<RespuestaMatchmaking> call, Response<RespuestaMatchmaking> response) {
                if(response.isSuccessful()){
                    RespuestaMatchmaking respuestaMatchmakingSolicitada = response.body();
                    callback.onResponse(call, Response.success(respuestaMatchmakingSolicitada));
                }else{
                    callback.onFailure(call, new Throwable("Error en la respuesta: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<RespuestaMatchmaking> call, Throwable t) {
                Log.d("Prueba","Error en la conexión");
                callback.onFailure(call, t);
            }
        });
    }
}
