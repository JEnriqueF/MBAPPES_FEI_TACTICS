package com.mbappesfeitactics.Vista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.mbappesfeitactics.DAO.MatchmakingDAO;
import com.mbappesfeitactics.DAO.RespuestaMatchmaking;
import com.mbappesfeitactics.POJO.Jugador;
import com.mbappesfeitactics.R;
import com.mbappesfeitactics.Vista.ui.menu.MenuFragment;
import com.mbappesfeitactics.databinding.ActivityLobbyBinding;
import com.mbappesfeitactics.databinding.ActivityMainBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Lobby extends AppCompatActivity {

    Jugador jugador = RecursosCompartidosViewModel.obtenerInstancia().getJugador();

    ActivityLobbyBinding binding;

    int contadorEspera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLobbyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        contadorEspera = 0;

        binding.btnCancelar.setEnabled(true);

        solicitarPartida();

        binding.btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelarBusqueda();
            }
        });
    }

    private void cancelarBusqueda() {
        MatchmakingDAO.cancelarBusqueda(RecursosCompartidosViewModel.obtenerInstancia().getJugador().getGamertag(), new Callback<RespuestaMatchmaking>() {
            @Override
            public void onResponse(Call<RespuestaMatchmaking> call, Response<RespuestaMatchmaking> response) {
                Log.d("Exito", "Se hizo clic en el botón Cancelar");
                // Crear un Intent para iniciar la actividad MenuP
                Intent intent = new Intent(getApplicationContext(), MenuP.class);
                // Iniciar la actividad MenuP
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<RespuestaMatchmaking> call, Throwable t) {
                Log.d("Fallo", "Se hizo clic en el botón Cancelar");
            }
        });
    }

    private void solicitarPartida() {
        final Handler handler = new Handler();

        MatchmakingDAO.solicitarPartida(RecursosCompartidosViewModel.obtenerInstancia().getJugador().getGamertag(), new Callback<RespuestaMatchmaking>() {
            @Override
            public void onResponse(Call<RespuestaMatchmaking> call, Response<RespuestaMatchmaking> response) {
                Log.d("Exito", "");

                String respuesta = response.body().getRespuesta();

                if (respuesta != null && ("Ya se solicitó la partida".equals(respuesta) || "Partida Creada".equals(respuesta))) {
                    // Usar un Handler para introducir una pausa de 10 segundos
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (contadorEspera < 6) {
                                contadorEspera++;
                                solicitarPartida();
                            } else {
                                cancelarBusqueda();
                            }
                        }
                    }, 10000);
                } else if (response.body().getGamertag() != null) {
                    Jugador adversario = new Jugador();
                    adversario.setGamertag(response.body().getGamertag());
                    RecursosCompartidosViewModel.obtenerInstancia().setAdversario(adversario);
                    Intent intent = new Intent(getApplicationContext(), Partida.class);
                    startActivity(intent);
                    Log.d("Gamertag", response.body().getGamertag());
                }
            }

            @Override
            public void onFailure(Call<RespuestaMatchmaking> call, Throwable t) {
                Log.d("Fallo", "");
            }
        });
    }

}