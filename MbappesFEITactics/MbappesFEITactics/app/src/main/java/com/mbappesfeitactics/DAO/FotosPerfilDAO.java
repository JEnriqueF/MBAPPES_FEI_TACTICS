package com.mbappesfeitactics.DAO;

import android.util.Log;

import com.mbappesfeitactics.POJO.Carta;
import com.mbappesfeitactics.POJO.FotosPerfil;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FotosPerfilDAO {
    public static void recuperarFotosPerfil(final Callback<RespuestaFotosPerfil> callback) {
        Retrofit retrofit = APIClient.iniciarAPI();
        FotosPerfilService fotosPerfilService = retrofit.create(FotosPerfilService.class);

        // Llamada para recuperar fotos de perfil
        Call<RespuestaFotosPerfil> call = fotosPerfilService.recuperarImagenesPerfil();

        // Realizar la llamada asíncrona
        call.enqueue(new Callback<RespuestaFotosPerfil>() {
            @Override
            public void onResponse(Call<RespuestaFotosPerfil> call, Response<RespuestaFotosPerfil> response) {
                if (response.isSuccessful()) {
                    // La solicitud fue exitosa, puedes manejar la respuesta en el objeto response.body()
                    RespuestaFotosPerfil respuesta = response.body();
                    Log.d("Fotos Perfil Recuperadas", "Cantidad: " + respuesta.getFotosPerfil().size());
                    for (FotosPerfil fotosPerfil: respuesta.getFotosPerfil()) {
                        Log.d("Foto Perfil Recibida", fotosPerfil.toString());
                    }
                    callback.onResponse(call, response);
                } else {
                    // La solicitud no fue exitosa, puedes manejar el error en response.errorBody()
                    callback.onFailure(call, new Throwable("Error en la respuesta: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<RespuestaFotosPerfil> call, Throwable t) {
                // Manejar errores de red u otros tipos de errores
                callback.onFailure(call, t);
            }
        });
    }
}
