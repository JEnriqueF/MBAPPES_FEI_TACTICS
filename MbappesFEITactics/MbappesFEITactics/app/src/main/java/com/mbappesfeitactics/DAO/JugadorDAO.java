package com.mbappesfeitactics.DAO;

import android.util.Log;

import com.mbappesfeitactics.POJO.Jugador;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class JugadorDAO {

    public static void iniciarSesion(String gamertag, String contrasenia, final Callback<Jugador> callback) {
        Retrofit retrofit = APIClient.iniciarAPI();
        JugadorService jugadorService = retrofit.create(JugadorService.class);

        Map<String, String> credentials = new HashMap<>();
        credentials.put("Gamertag", gamertag);
        credentials.put("contrasenia", contrasenia);
        Call<Jugador> call = jugadorService.loginUser(credentials);

        call.enqueue(new Callback<Jugador>() {
            @Override
            public void onResponse(Call<Jugador> call, Response<Jugador> response) {
                if (response.isSuccessful()) {
                    Jugador jugadorIniciado = response.body();
                    callback.onResponse(call, Response.success(jugadorIniciado));
                } else {
                    callback.onFailure(call, new Throwable("Error en la respuesta: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<Jugador> call, Throwable t) {
                Log.d("PRUEBAGG", "Error en la conexión: " + t.getMessage());
                callback.onFailure(call, t);
            }
        });
    }

    public static void crearJugador(String gamertag, String password, int idFoto, final Callback<Jugador> callback) {
        Retrofit retrofit = APIClient.iniciarAPI();
        JugadorService jugadorService = retrofit.create(JugadorService.class);

        Map<String, Object> credentials = new HashMap<>();
        credentials.put("gamertag", gamertag);
        credentials.put("password", password);
        credentials.put("idFoto", idFoto);
        Call<Jugador> call = jugadorService.createUser(credentials);

        call.enqueue(new Callback<Jugador>() {
            @Override
            public void onResponse(Call<Jugador> call, Response<Jugador> response) {
                if (response.isSuccessful()) {
                    Jugador jugadorRegistrado = response.body();
                    callback.onResponse(call, Response.success(jugadorRegistrado));
                } else {
                    callback.onFailure(call, new Throwable("Error en la respuesta: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<Jugador> call, Throwable t) {
                Log.d("PRUEBAGG", "Error en la conexión: " + t.getMessage());
                callback.onFailure(call, t);
            }
        });

    }

}
