package com.mbappesfeitactics.DAO;

import android.util.Log;

import com.mbappesfeitactics.POJO.Carta;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CartaDAO {
    public static void recuperarCartas(final Callback<RespuestaCartas> callback) {
        Retrofit retrofit = APIClient.iniciarAPI();
        CartaService cartaService = retrofit.create(CartaService.class);

        Call<RespuestaCartas> call = cartaService.recuperarCartas();
        call.enqueue(new retrofit2.Callback<RespuestaCartas>() {
            @Override
            public void onResponse(Call<RespuestaCartas> call, Response<RespuestaCartas> response) {
                if (response.isSuccessful()) {
                    RespuestaCartas respuesta = response.body();
                    Log.d("Cartas Recuperadas", "Cantidad: " + respuesta.getCartas().size());
                    for (Carta carta: respuesta.getCartas()) {
                        Log.d("Carta Recibida", carta.toString());
                    }
                    callback.onResponse(call, Response.success(respuesta));
                } else {
                    callback.onFailure(call, new Throwable("Error en la respuesta: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<RespuestaCartas> call, Throwable t) {
                Log.d("Error en la conexión", "Error en la conexión: " + t.getMessage());
                callback.onFailure(call, t);
            }
        });
    }
}
