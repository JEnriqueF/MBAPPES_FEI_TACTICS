package com.mbappesfeitactics.Vista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.mbappesfeitactics.DAO.CartaDAO;
import com.mbappesfeitactics.DAO.EscenarioDAO;
import com.mbappesfeitactics.DAO.FotosPerfilDAO;
import com.mbappesfeitactics.DAO.JugadorDAO;
import com.mbappesfeitactics.DAO.MatchmakingDAO;
import com.mbappesfeitactics.DAO.RespuestaCartas;
import com.mbappesfeitactics.DAO.RespuestaEscenarios;
import com.mbappesfeitactics.DAO.RespuestaFotosPerfil;
import com.mbappesfeitactics.DAO.RespuestaMatchmaking;
import com.mbappesfeitactics.MainActivity;
import com.mbappesfeitactics.POJO.Carta;
import com.mbappesfeitactics.POJO.Escenario;
import com.mbappesfeitactics.POJO.FotosPerfil;
import com.mbappesfeitactics.POJO.Jugador;
import com.mbappesfeitactics.R;
import com.mbappesfeitactics.databinding.ActivityLobbyBinding;
import com.mbappesfeitactics.databinding.ActivityLobbyInvitadoBinding;

import java.util.List;
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

        obtenerCartas();
        obtenerFotosPerfil();

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


                if (respuesta != null && ("Ya se solicitó la partida".equals(respuesta) || "Partida Creada".equals(respuesta) || "Solicitud Guardada".equals(respuesta))) {
                    Log.d("Prueba","IF 1" );
                    // Usar un Handler para introducir una pausa de 10 segundos
                    handler1.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (contadorEspera < 6) {
                                Log.d("Prueba","IF 2" );
                                contadorEspera++;
                                solicitarPartida();
                            } else {
                                Log.d("Prueba","ELSE 2" );
                                cancelarBusqueda();
                            }
                        }
                    }, 10000);
                } else if (response.body().getGamertag() != null) {
                    Log.d("Prueba","ELSE 1" );
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

    private void obtenerCartas() {
        CartaDAO.recuperarCartas(new Callback<RespuestaCartas>() {
            @Override
            public void onResponse(Call<RespuestaCartas> call, Response<RespuestaCartas> response) {
                if (response.isSuccessful()) {
                    RespuestaCartas respuestaRecibida = response.body();

                    // Asigna la lista de cartas a tu MutableLiveData
                    if (respuestaRecibida != null) {
                        List<Carta> listaCartas = respuestaRecibida.getCartas();
                        Log.d("Recuperacion carta MenuP", "" + listaCartas.get(2));
                        RecursosCompartidosViewModel.obtenerInstancia().setCartas(listaCartas);
                    }
                }
            }

            @Override
            public void onFailure(Call<RespuestaCartas> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
            }
        });
    }

    private void obtenerFotosPerfil() {
        FotosPerfilDAO.recuperarFotosPerfil(new Callback<RespuestaFotosPerfil>() {
            @Override
            public void onResponse(Call<RespuestaFotosPerfil> call, Response<RespuestaFotosPerfil> response) {
                if (response.isSuccessful()) {
                    RespuestaFotosPerfil respuestaRecibida = response.body();

                    if (respuestaRecibida != null) {
                        List<FotosPerfil> listaFotosPerfil = respuestaRecibida.getFotosPerfil();

                        RecursosCompartidosViewModel.obtenerInstancia().setFotosPerfil(listaFotosPerfil);
                    }
                }
            }

            @Override
            public void onFailure(Call<RespuestaFotosPerfil> call, Throwable t) { Log.d("ERROR", t.getMessage()); }
        });
    }
}
