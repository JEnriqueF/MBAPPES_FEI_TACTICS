package com.mbappesfeitactics.Vista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.mbappesfeitactics.DAO.JugadorDAO;
import com.mbappesfeitactics.POJO.Jugador;
import com.mbappesfeitactics.R;
import com.mbappesfeitactics.databinding.ActivityPartidaBinding;
import com.mbappesfeitactics.databinding.ActivityPostJuegoBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostJuego extends AppCompatActivity {

    ActivityPostJuegoBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPostJuegoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.lbVictoriaDerrota.setText(RecursosCompartidosViewModel.obtenerInstancia().getEstadoFinalPartida());

        if(RecursosCompartidosViewModel.obtenerInstancia().getEstadoFinalPartida() == "Victoria"){
            JugadorDAO.guardarResultado(RecursosCompartidosViewModel.obtenerInstancia().getJugador().getGamertag(), 1, new Callback<Jugador>() {
                @Override
                public void onResponse(Call<Jugador> call, Response<Jugador> response) {
                    Jugador jugador = response.body();
                    if(jugador != null){
                        showMessage("Resultado guardado");
                    }else {
                        showMessage("Operación no completada, intente más tarde");
                    }
                }

                @Override
                public void onFailure(Call<Jugador> call, Throwable t) {

                }
            });
        }else{
            JugadorDAO.guardarResultado(RecursosCompartidosViewModel.obtenerInstancia().getJugador().getGamertag(), 0, new Callback<Jugador>() {
                @Override
                public void onResponse(Call<Jugador> call, Response<Jugador> response) {
                    Jugador jugador = response.body();
                    if(jugador != null){
                        showMessage("Resultado guardado");
                    }else {
                        showMessage("Operación no completada, intente más tarde");
                    }
                }

                @Override
                public void onFailure(Call<Jugador> call, Throwable t) {

                }
            });
        }
        binding.btnTerminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MenuP.class);
                startActivity(intent);
            }
        });
    }

    private void showMessage(String msj){
        Toast.makeText(this,msj, Toast.LENGTH_SHORT).show();
    }
}