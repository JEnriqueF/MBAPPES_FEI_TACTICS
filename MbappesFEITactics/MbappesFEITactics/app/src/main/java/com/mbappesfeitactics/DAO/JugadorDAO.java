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

    public static void editarMazo(String gamertag, String MazoEditado, final Callback<Jugador> callback){
        Retrofit retrofit = APIClient.iniciarAPI();
        JugadorService jugadorService = retrofit.create(JugadorService.class);

        Map<String, String> datosEditar = new HashMap<>();
        datosEditar.put("Gamertag", gamertag);
        datosEditar.put("Mazo", MazoEditado);
        Call<Jugador> call = jugadorService.editarMazo(datosEditar);

        call.enqueue(new Callback<Jugador>() {
            @Override
            public void onResponse(Call<Jugador> call, Response<Jugador> response) {
                if(response.isSuccessful()){
                    Jugador jugadorMazoEditado = response.body();
                    callback.onResponse(call, Response.success(jugadorMazoEditado));
                }else{
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

    public static void editarFotoPerfil(String gamertag, Integer idFotoPerfil, final Callback<Jugador> callback){
        Retrofit retrofit = APIClient.iniciarAPI();
        JugadorService jugadorService = retrofit.create(JugadorService.class);

        Map<String, Object> datosEnviar = new HashMap<>();
        datosEnviar.put("Gamertag", gamertag);
        datosEnviar.put("idFoto", Integer.valueOf(idFotoPerfil));
        Call<Jugador> call = jugadorService.editarFotoPerfil(datosEnviar);

        call.enqueue(new Callback<Jugador>() {
            @Override
            public void onResponse(Call<Jugador> call, Response<Jugador> response) {
                if(response.isSuccessful()){
                    Jugador jugadorFotoEditado = response.body();
                    callback.onResponse(call, Response.success(jugadorFotoEditado));
                }else{
                    callback.onFailure(call, new Throwable("Error en la respuesta: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<Jugador> call, Throwable t) {
                callback.onFailure(call, t);
            }
        });
    }

    public static void recuperarOponente(String gamertagRecibido, final Callback<Jugador> callback) {
        Log.d("Entró a DAO", "");
        Retrofit retrofit = APIClient.iniciarAPI();
        JugadorService jugadorService = retrofit.create(JugadorService.class);

        Map<String, String> gamertag = new HashMap<>();
        gamertag.put("Gamertag", gamertagRecibido);
        Call<Jugador> call = jugadorService.recuperarOponente(gamertag);
        Log.d("Map", gamertag.toString());

        call.enqueue(new Callback<Jugador>() {
            @Override
            public void onResponse(Call<Jugador> call, Response<Jugador> response) {
                Log.d("Si hay respuesta", "");
                if (response.isSuccessful()) {
                    Jugador jugadorOponente = response.body();
                    callback.onResponse(call, Response.success(jugadorOponente));
                } else {
                    callback.onFailure(call, new Throwable("Error en la respuesta: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<Jugador> call, Throwable t) {
                callback.onFailure(call, t);
            }
        });
    }

}
