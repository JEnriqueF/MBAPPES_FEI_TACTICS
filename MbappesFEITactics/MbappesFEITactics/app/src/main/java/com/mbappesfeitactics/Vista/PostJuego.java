package com.mbappesfeitactics.Vista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.mbappesfeitactics.DAO.JugadorDAO;
import com.mbappesfeitactics.DAO.MatchmakingDAO;
import com.mbappesfeitactics.POJO.Jugador;
import com.mbappesfeitactics.R;
import com.mbappesfeitactics.databinding.ActivityPartidaBinding;
import com.mbappesfeitactics.databinding.ActivityPostJuegoBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostJuego extends AppCompatActivity {

    Jugador jugador = RecursosCompartidosViewModel.obtenerInstancia().getJugador();
    ActivityPostJuegoBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPostJuegoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.lbVictoriaDerrota.setText(RecursosCompartidosViewModel.obtenerInstancia().getEstadoFinalPartida());

        if(RecursosCompartidosViewModel.obtenerInstancia().getEstadoFinalPartida().equals("Victoria")){
            jugador.setPartidasGanadas(jugador.getPartidasGanadas()+1);
            MatchmakingDAO.guardarResultado(RecursosCompartidosViewModel.obtenerInstancia().getJugador().getGamertag(), 1, new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    String respuesta = response.body();
                    if(respuesta != null){
                        showMessage("Resultado guardado");
                    }else {
                        showMessage("Operación no completada, intente más tarde");
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            });
        }else{
            jugador.setPartidasPerdidas(jugador.getPartidasPerdidas()+1);
            MatchmakingDAO.guardarResultado(RecursosCompartidosViewModel.obtenerInstancia().getJugador().getGamertag(), 0, new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    String respuesta = response.body();
                    if(respuesta != null){
                        showMessage("Resultado guardado");
                    }else {
                        showMessage("Operación no completada, intente más tarde");
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

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