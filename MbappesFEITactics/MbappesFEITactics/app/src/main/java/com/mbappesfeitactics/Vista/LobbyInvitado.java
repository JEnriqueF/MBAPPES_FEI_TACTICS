package com.mbappesfeitactics.Vista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.mbappesfeitactics.DAO.EscenarioDAO;
import com.mbappesfeitactics.DAO.JugadorDAO;
import com.mbappesfeitactics.DAO.MatchmakingDAO;
import com.mbappesfeitactics.DAO.RespuestaEscenarios;
import com.mbappesfeitactics.DAO.RespuestaMatchmaking;
import com.mbappesfeitactics.MainActivity;
import com.mbappesfeitactics.POJO.Jugador;
import com.mbappesfeitactics.R;
import com.mbappesfeitactics.databinding.ActivityLobbyBinding;
import com.mbappesfeitactics.databinding.ActivityLobbyInvitadoBinding;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LobbyInvitado extends AppCompatActivity {

    ActivityLobbyInvitadoBinding binding;
    Jugador jugadorOponente;
    int contadorEspera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLobbyInvitadoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Bloque para creación de guest
        Random random = new Random();
        int numeroEntero = random.nextInt();
        int numeroEnRango = random.nextInt(1000);

        String gamertagGuest = "guest" + String.valueOf(numeroEnRango);
        String mazoGuest = "9,10,11,12";
        Jugador guest = new Jugador();
        guest.setGamertag(gamertagGuest);
        guest.setIdFoto(1);
        guest.setMazo(mazoGuest);
        RecursosCompartidosViewModel.obtenerInstancia().setJugador(guest);

        contadorEspera = 0;
        jugadorOponente = null;
        binding.btnCancelar.setEnabled(true);

        binding.lbGamertagGuest.setText(RecursosCompartidosViewModel.obtenerInstancia().getJugador().getGamertag());

        iniciarBusqueda();

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
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                // Iniciar la actividad MenuP
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<RespuestaMatchmaking> call, Throwable t) {
                Log.d("Fallo", "Se hizo clic en el botón Cancelar");
            }
        });
    }

    private void iniciarBusqueda() {
        final Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                solicitarPartida();
            }
        }, 10000);
    }

    private void solicitarPartida() {
        final Handler handler1 = new Handler();

        MatchmakingDAO.solicitarPartida(RecursosCompartidosViewModel.obtenerInstancia().getJugador().getGamertag(), new Callback<RespuestaMatchmaking>() {
            @Override
            public void onResponse(Call<RespuestaMatchmaking> call, Response<RespuestaMatchmaking> response) {
                Log.d("Exito", "");

                String respuesta = response.body().getRespuesta();

                Log.d("Prueba",respuesta );

                if (respuesta != null && ("Ya se solicitó la partida".equals(respuesta) || "Partida Creada".equals(respuesta))) {
                    // Usar un Handler para introducir una pausa de 10 segundos
                    handler1.postDelayed(new Runnable() {
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
                    Log.d("Gamertag", response.body().getGamertag());
                    abrirVentanaJuego(response.body().getGamertag());
                }
            }

            @Override
            public void onFailure(Call<RespuestaMatchmaking> call, Throwable t) {
                Log.d("Fallo", "");
            }
        });
    }

    private void recuperarOponente(String gamertag) {
        Log.d("Entró a recuperar oponente", "");
        JugadorDAO.recuperarOponente(gamertag, new Callback<Jugador>() {
            @Override
            public void onResponse(Call<Jugador> call, Response<Jugador> response) {
                jugadorOponente = response.body();
                RecursosCompartidosViewModel.obtenerInstancia().setAdversario(jugadorOponente);
                Log.d("JugadorAdversario", RecursosCompartidosViewModel.obtenerInstancia().getAdversario().getGamertag()+" "+RecursosCompartidosViewModel.obtenerInstancia().getAdversario().getIdFoto());
            }

            @Override
            public void onFailure(Call<Jugador> call, Throwable t) {
                //error
            }
        });
    }

    private void recuperarEscenarios() {
        EscenarioDAO.recuperarEscenarios(new Callback<RespuestaEscenarios>() {
            @Override
            public void onResponse(Call<RespuestaEscenarios> call, Response<RespuestaEscenarios> response) {
                RecursosCompartidosViewModel.obtenerInstancia().setEscenarios(response.body().getEscenarios());
            }

            @Override
            public void onFailure(Call<RespuestaEscenarios> call, Throwable t) {

            }
        });
    }

    private void abrirVentanaJuego(String gamertag) {
        if(!gamertag.startsWith("guest")){
            recuperarOponente(gamertag);
        }else {
            Jugador guest = new Jugador();
            guest.setGamertag(gamertag);
            RecursosCompartidosViewModel.obtenerInstancia().setAdversario(guest);
        }
        //recuperarOponente(gamertag);
        recuperarEscenarios();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        Intent intent = new Intent(getApplicationContext(), Partida.class);
        startActivity(intent);
    }
}