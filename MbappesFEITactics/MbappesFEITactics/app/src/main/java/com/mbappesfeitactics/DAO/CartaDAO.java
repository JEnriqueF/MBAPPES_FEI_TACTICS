package com.mbappesfeitactics.DAO;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mbappesfeitactics.POJO.Carta;
import com.mbappesfeitactics.POJO.Jugador;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CartaDAO {
    public static void recuperarCartas(final Callback<ArrayList<Carta>> callback) {
        Retrofit retrofit = APIClient.iniciarAPI();
        CartaService cartaService = retrofit.create(CartaService.class);

        Call<ArrayList<Carta>> call = cartaService.recuperarCartas();
        call.enqueue(new retrofit2.Callback<ArrayList<Carta>>() {
            @Override
            public void onResponse(Call<ArrayList<Carta>> call, Response<ArrayList<Carta>> response) {
                if (response.isSuccessful()) {
                    ArrayList<Carta> listaCartas = response.body();
                    Log.d("Cartas Recuperadas", response.toString());
                    callback.onResponse(call, Response.success(listaCartas));
                } else {
                    callback.onFailure(call, new Throwable("Error en la respuesta: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Carta>> call, Throwable t) {
                Log.d("PRUEBA MAL", "Error en la conexión: " + t.getMessage());
                callback.onFailure(call, t);
            }
        });
    }
}
