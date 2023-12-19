package com.mbappesfeitactics.Vista.ui.mazo;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mbappesfeitactics.DAO.CartaDAO;
import com.mbappesfeitactics.POJO.Carta;

import java.util.ArrayList;
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
        CartaDAO.recuperarCartas(new Callback<ArrayList<Carta>>() {
            @Override
            public void onResponse(Call<ArrayList<Carta>> call, Response<ArrayList<Carta>> response) {
                if (response.isSuccessful()) {
                    ArrayList<Carta> listaCartas = response.body();
                    cartas.setValue(listaCartas);  // Asigna la lista de cartas a tu MutableLiveData

                    for (Carta carta : listaCartas) {
                        Log.d("Elemento Carta", "IDCarta: " + carta.getIdCarta() +
                                ", Costo: " + carta.getCosto() +
                                ", Poder: " + carta.getPoder() +
                                ", Imagen: " + carta.getImagen());
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Carta>> call, Throwable t) {
                error = true;
            }
        });
    }


}