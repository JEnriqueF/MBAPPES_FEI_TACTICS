package com.mbappesfeitactics.Vista.ui.mazo;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mbappesfeitactics.DAO.CartaDAO;
import com.mbappesfeitactics.DAO.RespuestaCartas;
import com.mbappesfeitactics.POJO.Carta;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MazoViewModel extends ViewModel {

    private MutableLiveData<List<Carta>> cartas;
    private boolean error;
    private final MutableLiveData<String> mazo;

    public MazoViewModel() {
        cartas = new MutableLiveData<>();
        mazo = new MutableLiveData<>();
        error = true;

        // Inicializa la lista de cartas con un ejemplo
        obtenerCartas();

        // Establece un nombre de mazo de ejemplo
        mazo.setValue("Mazo Ejemplo");
    }

    public LiveData<List<Carta>> getCartas() {
        return cartas;
    }

    public LiveData<String> getMazo() {
        return mazo;
    }

    // Método para obtener una lista de cartas de ejemplo
    private void obtenerCartas() {
        CartaDAO.recuperarCartas(new Callback<RespuestaCartas>() {
            @Override
            public void onResponse(Call<RespuestaCartas> call, Response<RespuestaCartas> response) {
                if (response.isSuccessful()) {
                    RespuestaCartas respuestaRecibida = response.body();

                    // Asigna la lista de cartas a tu MutableLiveData
                    if (respuestaRecibida != null) {
                        List<Carta> listaCartas = respuestaRecibida.getCartas();
                        cartas.postValue(listaCartas);  // O usa setValue si estás en el hilo principal
                    }
                }
            }

            @Override
            public void onFailure(Call<RespuestaCartas> call, Throwable t) {
                error = true;
            }
        });
    }

}