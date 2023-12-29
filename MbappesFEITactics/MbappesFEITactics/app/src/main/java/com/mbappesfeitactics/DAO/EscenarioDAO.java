package com.mbappesfeitactics.DAO;

import android.util.Log;

import com.mbappesfeitactics.POJO.Carta;
import com.mbappesfeitactics.POJO.Escenario;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class EscenarioDAO {
    public static void recuperarEscenarios(final Callback<RespuestaEscenarios> callback) {
        Retrofit retrofit = APIClient.iniciarAPI();
        EscenarioService escenarioService = retrofit.create(EscenarioService.class);

        Call<RespuestaEscenarios> call = escenarioService.recuperarEscenarios();
        call.enqueue(new retrofit2.Callback<RespuestaEscenarios>() {
            @Override
            public void onResponse(Call<RespuestaEscenarios> call, Response<RespuestaEscenarios> response) {
                if (response.isSuccessful()) {
                    RespuestaEscenarios respuesta = response.body();
                    for (Escenario escenario: respuesta.getEscenarios()) {
                        Log.d("Escenario Recibid", escenario.toString());
                    }
                    callback.onResponse(call, Response.success(respuesta));
                }else {
                    callback.onFailure(call, new Throwable("Error en la respuesta: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<RespuestaEscenarios> call, Throwable t) {
                Log.d("Error en la conexión", "Error en la conexión: " + t.getMessage());
                callback.onFailure(call, t);
            }
        });
    }
}
