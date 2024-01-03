package com.mbappesfeitactics.Vista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mbappesfeitactics.ConvertidorImagen;
import com.mbappesfeitactics.DAO.EscenarioDAO;
import com.mbappesfeitactics.DAO.MatchmakingDAO;
import com.mbappesfeitactics.DAO.PartidaDAO;
import com.mbappesfeitactics.DAO.PartidaRequest;
import com.mbappesfeitactics.DAO.RespuestaEscenarios;
import com.mbappesfeitactics.DAO.RespuestaMatchmaking;
import com.mbappesfeitactics.DAO.RespuestaPartida;
import com.mbappesfeitactics.POJO.Carta;
import com.mbappesfeitactics.POJO.Escenario;
import com.mbappesfeitactics.POJO.FotosPerfil;
import com.mbappesfeitactics.POJO.Jugador;
import com.mbappesfeitactics.POJO.Movimiento;
import com.mbappesfeitactics.R;
import com.mbappesfeitactics.databinding.ActivityPartidaBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Partida extends AppCompatActivity {

    ActivityPartidaBinding binding;
    private Jugador jugador;
    private Jugador adversario;
    private ArrayList<ImageView> ivMazo = new ArrayList<>();
    private int[] mazoPartida = new int [4];
    private List<Carta> listaCartas;
    private ArrayList<ImageView> ivEscenarios = new ArrayList<>();
    private List<Escenario> listaEscenarios;
    private ArrayList<ImageView> ivCartasEnemigo = new ArrayList<>();
    private int[] idCartasTableroEnemigo = new int[3];
    private ArrayList<TextView> tvCartasTableroEnemigo = new ArrayList<>();
    private ArrayList<ImageView> ivCartasTableroJugador = new ArrayList<>();
    private int[] idCartasTableroJugador = new int[3];
    private ArrayList<TextView> tvCartasTableroJugador = new ArrayList<>();
    private List<FotosPerfil> listaFotosPerfil;

    private ImageView imagenClicada;
    private boolean movimientoConsultadoAnterior = false;
    private int turno = 1;
    int damageTotalEnemigo = 0;
    int damageTotalPropio = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityPartidaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        listaCartas = RecursosCompartidosViewModel.obtenerInstancia().getCartas();
        listaEscenarios = RecursosCompartidosViewModel.obtenerInstancia().getEscenarios();
        jugador = RecursosCompartidosViewModel.obtenerInstancia().getJugador();
        adversario = RecursosCompartidosViewModel.obtenerInstancia().getAdversario();

        mazoPartida = convertirMazo();
        imagenClicada = null;

        for (int i = 0; i < idCartasTableroJugador.length; i++) {
            idCartasTableroJugador[i] = -1;
        }
        for (int i = 0; i < idCartasTableroJugador.length; i++) {
            Log.d("ID'sCartasTableroJugador", String.valueOf(idCartasTableroJugador[i]));
        }

        for (int i = 0; i < idCartasTableroEnemigo.length; i++) {
            idCartasTableroEnemigo[i] = -1;
        }
        for (int i = 0; i < idCartasTableroEnemigo.length; i++) {
            Log.d("ID'sCartasTableroJugador", String.valueOf(idCartasTableroEnemigo[i]));
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //Arreglo Mazo
        ivMazo.add(binding.fotoParaPoner1);
        ivMazo.add(binding.fotoParaPoner2);
        ivMazo.add(binding.fotoParaPoner3);
        ivMazo.add(binding.fotoParaPoner4);

        //Arreglo Escenario
        ivEscenarios.add(binding.imagenEscenario1);
        ivEscenarios.add(binding.imagenEscenario2);
        ivEscenarios.add(binding.imagenEscenario3);

        //Arreglo TableroJugador
        ivCartasTableroJugador.add(binding.cartaPropia1);
        ivCartasTableroJugador.add(binding.cartaPropia2);
        ivCartasTableroJugador.add(binding.cartaPropia3);

        //Arreglo TableroEnemigo
        ivCartasEnemigo.add(binding.cartaEnemigo1);
        ivCartasEnemigo.add(binding.cartaEnemigo2);
        ivCartasEnemigo.add(binding.cartaEnemigo3);

        //Arreglo AtaqueJugador
        tvCartasTableroJugador.add(binding.lbNoAtaquePropio1);
        tvCartasTableroJugador.add(binding.lbNoAtaquePropio2);
        tvCartasTableroJugador.add(binding.lbNoAtaquePropio3);

        for (TextView textView : tvCartasTableroJugador) {
            textView.setText("0");
        }

        //Arreglo AtaqueEnemigo
        tvCartasTableroEnemigo.add(binding.lbNoAtaqueEnemigo1);
        tvCartasTableroEnemigo.add(binding.lbNoAtaqueEnemigo2);
        tvCartasTableroEnemigo.add(binding.lbNoAtaqueEnemigo3);

        for (TextView textView : tvCartasTableroEnemigo) {
            textView.setText("0");
        }

        binding.lbNumeroEnergia.setText(String.valueOf(turno+1));

        binding.lbNumTurno.setText(String.valueOf(turno));

        cargarAdversario();

        mostrarCartas();
        mostrarEscenarios();

        ivMazo.get(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { moverCartasJugador(ivMazo.get(0)); }
        });
        ivMazo.get(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { moverCartasJugador(ivMazo.get(1)); }
        });
        ivMazo.get(2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { moverCartasJugador(ivMazo.get(2)); }
        });
        ivMazo.get(3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { moverCartasJugador(ivMazo.get(3)); }
        });

        ivCartasTableroJugador.get(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { moverCartasJugador(ivCartasTableroJugador.get(0)); }
        });
        ivCartasTableroJugador.get(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { moverCartasJugador(ivCartasTableroJugador.get(1)); }
        });
        ivCartasTableroJugador.get(2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { moverCartasJugador(ivCartasTableroJugador.get(2)); }
        });

        binding.btnTerminarTurno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Movimiento> listaMovimientos = new ArrayList<>();
                for (int i = 0; i < idCartasTableroJugador.length; i++) {
                    Log.d("idCartaTableroJugador"+i, String.valueOf(idCartasTableroJugador[i]));
                    if (idCartasTableroJugador[i] != -1) {
                        Movimiento movimiento = new Movimiento(i, idCartasTableroJugador[i]);
                        listaMovimientos.add(movimiento);
                    }
                }

                PartidaRequest partidaRequest = new PartidaRequest(jugador.getGamertag(), listaMovimientos);

                jugarTurno(partidaRequest);
            }
        });

        binding.btnAbandonarPartida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("PRUEBA", RecursosCompartidosViewModel.obtenerInstancia().getJugador().getGamertag());
                MatchmakingDAO.cancelarPartida(RecursosCompartidosViewModel.obtenerInstancia().getJugador().getGamertag(), new Callback<RespuestaMatchmaking>() {
                    @Override
                    public void onResponse(Call<RespuestaMatchmaking> call, Response<RespuestaMatchmaking> response) {
                        RespuestaMatchmaking respuestaMatchmaking = response.body();

                        if (respuestaMatchmaking.getRespuesta().equals("Partida cancelada correctamente")) {
                            RecursosCompartidosViewModel.obtenerInstancia().setEstadoFinalPartida("Derrota");
                            Intent intent = new Intent(getApplicationContext(), PostJuego.class);
                            // Iniciar la actividad MenuP
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<RespuestaMatchmaking> call, Throwable t) {
                        Log.d("PRUEBA", t.getMessage());
                        Log.d("Fallo", "No se canceló correctamente");
                    }
                });
            }
        });
    }

    private void moverCartasJugador(ImageView ivInvocadoraActual) {
        int energia = Integer.parseInt((String) binding.lbNumeroEnergia.getText());
        if (imagenClicada == ivInvocadoraActual) {
            imagenClicada = null;
        } else {
            if (imagenClicada == null) {
                imagenClicada = ivInvocadoraActual;
            } else {

                //Clicada Primero
                Bitmap bitmapClicadaPrimero = convertirDrawableABitmap(imagenClicada.getDrawable());
                int idCartaClicadaPrimero = 0;
                boolean clicadaPrimeroEsMazo = false;

                for (ImageView ivClicadaPrimero : ivMazo) {
                    if (imagenClicada == ivClicadaPrimero) {
                        int index = ivMazo.indexOf(ivClicadaPrimero);
                        idCartaClicadaPrimero = mazoPartida[index];
                        clicadaPrimeroEsMazo = true;
                    }
                }

                for (ImageView ivClicadaPrimero : ivCartasTableroJugador) {
                    if (imagenClicada == ivClicadaPrimero) {
                        int index = ivCartasTableroJugador.indexOf(ivClicadaPrimero);
                        idCartaClicadaPrimero = idCartasTableroJugador[index];
                    }
                }

                //Clicada Actual
                Bitmap bitmapClicadaActual = convertirDrawableABitmap(ivInvocadoraActual.getDrawable());
                int idCartaClicadaActual = 0;
                boolean clicadaActualEsMazo = false;

                for (ImageView ivClicadaActual : ivMazo) {
                    if (ivInvocadoraActual == ivClicadaActual) {
                        int index = ivMazo.indexOf(ivClicadaActual);
                        idCartaClicadaActual = mazoPartida[index];
                        clicadaActualEsMazo = true;
                    }
                }

                for (ImageView ivClicadaActual : ivCartasTableroJugador) {
                    if (ivInvocadoraActual == ivClicadaActual) {
                        int index = ivCartasTableroJugador.indexOf(ivClicadaActual);
                        idCartaClicadaActual = idCartasTableroJugador[index];
                    }
                }

                //Evaluar
                if ((clicadaPrimeroEsMazo && clicadaActualEsMazo) || (!clicadaPrimeroEsMazo && !clicadaActualEsMazo)) {
                    imagenClicada = null;
                } else {
                    //Cambio de ID´s
                    if (clicadaPrimeroEsMazo) {

                        int indexMazo = -1;
                        for (int i = 0; i < mazoPartida.length; i++) {
                            if (imagenClicada == ivMazo.get(i)) {
                                indexMazo = i;
                            }
                        }

                        int indexTableroJugador = -1;
                        for (int i = 0; i < idCartasTableroJugador.length; i++) {
                            if (ivInvocadoraActual == ivCartasTableroJugador.get(i)) {
                                indexTableroJugador = i;
                            }
                        }

                        //

                        int indexCarta = -1;
                        for (Carta carta : listaCartas) {
                            if (carta.getIdCarta() == idCartaClicadaPrimero) {
                                indexCarta = listaCartas.indexOf(carta);
                            }
                        }

                        if (indexCarta == -1) {

                            int indexCartaPrimero = -1;
                            if (idCartaClicadaPrimero != -1) {
                                for (Carta carta : listaCartas) {
                                    if (carta.getIdCarta() == idCartaClicadaPrimero) {
                                        indexCartaPrimero = listaCartas.indexOf(carta);
                                    }
                                }
                                energia = energia - listaCartas.get(indexCartaPrimero).getCosto();

                                tvCartasTableroJugador.get(indexTableroJugador).setText(String.valueOf(listaCartas.get(indexCartaPrimero).getPoder()));
                            }

                            if (idCartaClicadaActual != -1) {
                                int indexCartaActual = -1;
                                for (Carta carta : listaCartas) {
                                    if (carta.getIdCarta() == idCartaClicadaActual) {
                                        indexCartaActual = listaCartas.indexOf(carta);
                                    }
                                }
                                energia = energia + listaCartas.get(indexCartaActual).getCosto();

                                tvCartasTableroJugador.get(indexTableroJugador).setText("0");
                            }

                            if (idCartaClicadaActual != -1 && idCartaClicadaPrimero != -1) {
                                tvCartasTableroJugador.get(indexTableroJugador).setText(String.valueOf(listaCartas.get(indexCartaPrimero).getPoder()));
                            }

                            mazoPartida[indexMazo] = idCartaClicadaActual;
                            idCartasTableroJugador[indexTableroJugador] = idCartaClicadaPrimero;
                            binding.lbNumeroEnergia.setText(String.valueOf(energia));

                            ivInvocadoraActual.setImageBitmap(bitmapClicadaPrimero);
                            imagenClicada.setImageBitmap(bitmapClicadaActual);
                            imagenClicada = null;

                            Log.d("ID's TableroJugador", idCartasTableroJugador[0] + " " + idCartasTableroJugador[1] + " " + idCartasTableroJugador[2]);
                            Log.d("ID´s Mazo", mazoPartida[0] + " " + mazoPartida[1] + " " + mazoPartida[2] + " " + mazoPartida[3]);

                        } else if (listaCartas.get(indexCarta).getCosto() <= energia) {

                            int indexCartaPrimero = -1;
                            if (idCartaClicadaPrimero != -1) {
                                for (Carta carta : listaCartas) {
                                    if (carta.getIdCarta() == idCartaClicadaPrimero) {
                                        indexCartaPrimero = listaCartas.indexOf(carta);
                                    }
                                }
                                energia = energia - listaCartas.get(indexCartaPrimero).getCosto();

                                tvCartasTableroJugador.get(indexTableroJugador).setText(String.valueOf(listaCartas.get(indexCartaPrimero).getPoder()));
                            }

                            if (idCartaClicadaActual != -1) {
                                int indexCartaActual = -1;
                                for (Carta carta : listaCartas) {
                                    if (carta.getIdCarta() == idCartaClicadaActual) {
                                        indexCartaActual = listaCartas.indexOf(carta);
                                    }
                                }
                                energia = energia + listaCartas.get(indexCartaActual).getCosto();

                                tvCartasTableroJugador.get(indexTableroJugador).setText("0");
                            }

                            if (idCartaClicadaActual != -1 && idCartaClicadaPrimero != -1) {
                                tvCartasTableroJugador.get(indexTableroJugador).setText(String.valueOf(listaCartas.get(indexCartaPrimero).getPoder()));
                            }

                            mazoPartida[indexMazo] = idCartaClicadaActual;
                            idCartasTableroJugador[indexTableroJugador] = idCartaClicadaPrimero;
                            binding.lbNumeroEnergia.setText(String.valueOf(energia));

                            ivInvocadoraActual.setImageBitmap(bitmapClicadaPrimero);
                            imagenClicada.setImageBitmap(bitmapClicadaActual);
                            imagenClicada = null;

                            Log.d("ID's TableroJugador", idCartasTableroJugador[0] + " " + idCartasTableroJugador[1] + " " + idCartasTableroJugador[2]);
                            Log.d("ID´s Mazo", mazoPartida[0] + " " + mazoPartida[1] + " " + mazoPartida[2] + " " + mazoPartida[3]);

                        }


                    } else if (clicadaActualEsMazo) {

                        int indexMazo = -1;
                        for (int i = 0; i < mazoPartida.length; i++) {
                            if (ivInvocadoraActual == ivMazo.get(i)) {
                                indexMazo = i;
                            }
                        }

                        int indexTableroJugador = -1;
                        for (int i = 0; i < idCartasTableroJugador.length; i++) {
                            if (imagenClicada == ivCartasTableroJugador.get(i)) {
                                indexTableroJugador = i;
                            }
                        }

                        //

                        int indexCarta = -1;
                        for (Carta carta : listaCartas) {
                            if (carta.getIdCarta() == idCartaClicadaActual) {
                                indexCarta = listaCartas.indexOf(carta);
                            }
                        }

                        if (indexCarta == -1) {

                            int indexCartaActual = -1;
                            if (idCartaClicadaActual != -1) {
                                for (Carta carta : listaCartas) {
                                    if (carta.getIdCarta() == idCartaClicadaActual) {
                                        indexCartaActual = listaCartas.indexOf(carta);
                                    }
                                }
                                energia = energia - listaCartas.get(indexCartaActual).getCosto();

                                tvCartasTableroJugador.get(indexTableroJugador).setText(String.valueOf(listaCartas.get(indexCartaActual).getPoder()));
                            }

                            if (idCartaClicadaPrimero != -1) {
                                int indexCartaPrimero = -1;
                                for (Carta carta : listaCartas) {
                                    if (carta.getIdCarta() == idCartaClicadaPrimero) {
                                        indexCartaPrimero = listaCartas.indexOf(carta);
                                    }
                                }
                                energia = energia + listaCartas.get(indexCartaPrimero).getCosto();

                                tvCartasTableroJugador.get(indexTableroJugador).setText("0");
                            }

                            if (idCartaClicadaActual != -1 && idCartaClicadaPrimero != -1) {
                                tvCartasTableroJugador.get(indexTableroJugador).setText(String.valueOf(listaCartas.get(indexCartaActual).getPoder()));
                            }

                            mazoPartida[indexMazo] = idCartaClicadaPrimero;
                            idCartasTableroJugador[indexTableroJugador] = idCartaClicadaActual;
                            binding.lbNumeroEnergia.setText(String.valueOf(energia));

                            ivInvocadoraActual.setImageBitmap(bitmapClicadaPrimero);
                            imagenClicada.setImageBitmap(bitmapClicadaActual);
                            imagenClicada = null;

                            Log.d("ID's TableroJugador", idCartasTableroJugador[0] + " " + idCartasTableroJugador[1] + " " + idCartasTableroJugador[2]);
                            Log.d("ID´s Mazo", mazoPartida[0] + " " + mazoPartida[1] + " " + mazoPartida[2] + " " + mazoPartida[3]);

                        } else if (listaCartas.get(indexCarta).getCosto() <= energia) {

                            int indexCartaActual = -1;
                            if (idCartaClicadaActual != -1) {
                                for (Carta carta : listaCartas) {
                                    if (carta.getIdCarta() == idCartaClicadaActual) {
                                        indexCartaActual = listaCartas.indexOf(carta);
                                    }
                                }
                                energia = energia - listaCartas.get(indexCartaActual).getCosto();

                                tvCartasTableroJugador.get(indexTableroJugador).setText(String.valueOf(listaCartas.get(indexCartaActual).getPoder()));
                            }

                            if (idCartaClicadaPrimero != -1) {
                                int indexCartaPrimero = -1;
                                for (Carta carta : listaCartas) {
                                    if (carta.getIdCarta() == idCartaClicadaPrimero) {
                                        indexCartaPrimero = listaCartas.indexOf(carta);
                                    }
                                }
                                energia = energia + listaCartas.get(indexCartaPrimero).getCosto();

                                tvCartasTableroJugador.get(indexTableroJugador).setText("0");
                            }

                            if (idCartaClicadaActual != -1 && idCartaClicadaPrimero != -1) {
                                tvCartasTableroJugador.get(indexTableroJugador).setText(String.valueOf(listaCartas.get(indexCartaActual).getPoder()));
                            }

                            mazoPartida[indexMazo] = idCartaClicadaPrimero;
                            idCartasTableroJugador[indexTableroJugador] = idCartaClicadaActual;
                            binding.lbNumeroEnergia.setText(String.valueOf(energia));

                            ivInvocadoraActual.setImageBitmap(bitmapClicadaPrimero);
                            imagenClicada.setImageBitmap(bitmapClicadaActual);
                            imagenClicada = null;

                            Log.d("ID's TableroJugador", idCartasTableroJugador[0] + " " + idCartasTableroJugador[1] + " " + idCartasTableroJugador[2]);
                            Log.d("ID´s Mazo", mazoPartida[0] + " " + mazoPartida[1] + " " + mazoPartida[2] + " " + mazoPartida[3]);

                        }

                    }
                }
            }
        }
    }

    private void cargarAdversario() {
        //Poner foto del adversario
        int idFotoAdversario = adversario.getIdFoto();
        listaFotosPerfil = RecursosCompartidosViewModel.obtenerInstancia().getFotosPerfil();
        String fotoAdversario = "";

        for (FotosPerfil foto : listaFotosPerfil) {
            if (foto.getIdFoto() == idFotoAdversario) {
                fotoAdversario = foto.getFoto();
            }
        }

        if (idFotoAdversario != 0) {
            binding.fotoEnemigo.setImageBitmap(ConvertidorImagen.convertirStringABitmap(fotoAdversario));
        } else {
            binding.fotoEnemigo.setImageResource(R.drawable.user_default);
        }
        //Poner gamertag del adversario
        binding.textGamertagEnemigo.setText(adversario.getGamertag());
    }

    private void mostrarCartas(){
        int[] mazo = convertirMazo();
        int contadorMazo = 0;

        for (Carta carta : listaCartas) {

            boolean estaEnMazo = false;
            for(int id: mazo){
                if(id == carta.getIdCarta()){
                    estaEnMazo = true;
                    break;
                }
            }

            if(estaEnMazo){
                for(int i = 0; i < mazo.length; i++){
                    if (carta.getIdCarta() == mazo[i]) {
                        ivMazo.get(contadorMazo).setImageBitmap(ConvertidorImagen.convertirStringABitmap(carta.getImagen()));
                        contadorMazo++;
                    }
                }
            }
        }
    }

    private void mostrarEscenarios(){
        int contadorEscenario = 0;
        for (Escenario escenario : listaEscenarios) {
            ivEscenarios.get(contadorEscenario).setImageBitmap(ConvertidorImagen.convertirStringABitmap(escenario.getImagen()));
            contadorEscenario++;
        }
    }

    private void jugarTurno(PartidaRequest partidaRequest) {
        final Handler handler = new Handler();
        final int[] contadorEspera = {0};
        PartidaDAO.jugarTurno(partidaRequest, new Callback<RespuestaPartida>() {
            @Override
            public void onResponse(Call<RespuestaPartida> call, Response<RespuestaPartida> response) {
                RespuestaPartida respuestaPartida = response.body();

                if (respuestaPartida.getRespuesta() != null) {
                    Log.d("Respuesta", respuestaPartida.getRespuesta());
                }
                if (respuestaPartida.getListaMovimientos() != null) {
                    for (Movimiento movimiento : respuestaPartida.getListaMovimientos()) {
                        Log.d("Movimiento Recibido", movimiento.getIdEscenario()+" "+ movimiento.getIdCarta());
                    }
                    Log.d("Respuesta", respuestaPartida.getListaMovimientos().toString());
                }

                Log.d("TURNO:", String.valueOf(turno));

                if(turno < 4){
                    turno++;
                }

                if (respuestaPartida.getRespuesta() != null && (respuestaPartida.getRespuesta().equals("Turno Jugado") || respuestaPartida.getRespuesta().equals("Ya se jugó un movimiento para Jugador en este turno"))) {
                    Log.d("IF 1", "");

                    bloquearTurno();

                    mostrarAtaquesEscenarioJugador();

                    //Mandar jugarTurno otra vez cada 10 segs maximo 1 min
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (contadorEspera[0] < 6) {
                                contadorEspera[0]++;
                                jugarTurno(partidaRequest);
                            } else {
                                //controlar la condición
                            }
                        }
                    }, 5000);
                } else if (respuestaPartida.getListaMovimientos() != null) {
                    Log.d("IF 2", "");
                    //Poner las cartas recibidas y cambiar turno
                    //Bloquear las cartas que y se jugaron
                    movimientoConsultadoAnterior = true;
                     List<Movimiento> listaMovimientos = respuestaPartida.getListaMovimientos();
                     colocarCartasEnemigo(listaMovimientos);
                     
                    if (turno == 4) {
                        jugarTurno(partidaRequest);
                    }

                    binding.lbNumTurno.setText(String.valueOf(turno));
                    binding.lbNumeroEnergia.setText(String.valueOf(turno));
                    habilitarTurno();
                } else if (respuestaPartida.getRespuesta() != null && (respuestaPartida.getRespuesta().equals("Juego terminado") || respuestaPartida.getRespuesta().equals("Jugador no encontrado en la partida")) && turno == 4) {
                    Log.d("IF 3", "");
                    //Terminar Juego
                    if(jugador.getGamertag().startsWith("guest")){
                        try{
                            damageTotalEnemigo = Integer.parseInt(binding.lbNoAtaqueEnemigo1.getText().toString()) + Integer.parseInt(binding.lbNoAtaqueEnemigo2.getText().toString()) +Integer.parseInt(binding.lbNoAtaqueEnemigo3.getText().toString());
                            damageTotalPropio = Integer.parseInt(binding.lbNoAtaquePropio1.getText().toString()) + Integer.parseInt(binding.lbNoAtaquePropio2.getText().toString()) + Integer.parseInt(binding.lbNoAtaquePropio3.getText().toString());

                            if(damageTotalPropio < damageTotalEnemigo){
                                RecursosCompartidosViewModel.obtenerInstancia().setEstadoFinalPartida("Derrota");
                                Intent intent = new Intent(getApplicationContext(), PostJuegoGuest.class);
                                startActivity(intent);
                            }else if(damageTotalPropio > damageTotalEnemigo){
                                RecursosCompartidosViewModel.obtenerInstancia().setEstadoFinalPartida("Victoria");
                                Intent intent = new Intent(getApplicationContext(), PostJuegoGuest.class);
                                startActivity(intent);
                            }else{
                                RecursosCompartidosViewModel.obtenerInstancia().setEstadoFinalPartida("Empate");
                                Intent intent = new Intent(getApplicationContext(), PostJuegoGuest.class);
                                startActivity(intent);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }else{
                        try{
                            damageTotalEnemigo = Integer.parseInt(binding.lbNoAtaqueEnemigo1.getText().toString()) + Integer.parseInt(binding.lbNoAtaqueEnemigo2.getText().toString()) +Integer.parseInt(binding.lbNoAtaqueEnemigo3.getText().toString());
                            damageTotalPropio = Integer.parseInt(binding.lbNoAtaquePropio1.getText().toString()) + Integer.parseInt(binding.lbNoAtaquePropio2.getText().toString()) + Integer.parseInt(binding.lbNoAtaquePropio3.getText().toString());

                            if(damageTotalPropio < damageTotalEnemigo){
                                RecursosCompartidosViewModel.obtenerInstancia().setEstadoFinalPartida("Derrota");
                                Intent intent = new Intent(getApplicationContext(), PostJuego.class);
                                startActivity(intent);
                            }else if(damageTotalPropio > damageTotalEnemigo){
                                RecursosCompartidosViewModel.obtenerInstancia().setEstadoFinalPartida("Victoria");
                                Intent intent = new Intent(getApplicationContext(), PostJuego.class);
                                startActivity(intent);
                            }else{
                                RecursosCompartidosViewModel.obtenerInstancia().setEstadoFinalPartida("Empate");
                                Intent intent = new Intent(getApplicationContext(), PostJuego.class);
                                startActivity(intent);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                } else if (respuestaPartida.getRespuesta() != null && respuestaPartida.getRespuesta().equals("Jugador no encontrado en la partida") && turno < 4) {
                    Log.d("IF 4", "");
                    //El otro jugador cancelo la partida
                    Intent intent = new Intent(getApplicationContext(), PostJuego.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<RespuestaPartida> call, Throwable t) {
                //Fallo
            }
        });
    }

    private void colocarCartasEnemigo(List<Movimiento> listaMovimientos) {
        for (Movimiento movimiento : listaMovimientos) {
            Log.d("IdEscenario", String.valueOf(movimiento.getIdEscenario()));
            int indexCarta = -1;
            for (int i = 0; i < listaCartas.size(); i++) {
                if (listaCartas.get(i).getIdCarta() == movimiento.getIdCarta()) {
                    indexCarta = i;
                }
            }
            tvCartasTableroEnemigo.get(movimiento.getIdEscenario()).setText(String.valueOf(listaCartas.get(indexCarta).getPoder()));
            ivCartasEnemigo.get(movimiento.getIdEscenario()).setImageBitmap(ConvertidorImagen.convertirStringABitmap(listaCartas.get(indexCarta).getImagen()));
            idCartasTableroEnemigo[movimiento.getIdEscenario()] = movimiento.getIdCarta();
        }
    }

    public void habilitarTurno() {
        binding.btnTerminarTurno.setEnabled(true);
        for (ImageView ivCartaMazo : ivMazo) {
            ivCartaMazo.setEnabled(true);
        }
        for (ImageView ivCartaTableroJugador : ivCartasTableroJugador) {
            ivCartaTableroJugador.setEnabled(true);
        }
        deshabilitarIVJugado();
    }

    public void deshabilitarIVJugado() {
        for (int i = 0; i < idCartasTableroJugador.length; i++) {
            if (idCartasTableroJugador[i] != -1) {
                ivCartasTableroJugador.get(i).setEnabled(false);
            }
        }
    }

    public void bloquearTurno() {
        binding.btnTerminarTurno.setEnabled(false);
        for (ImageView ivCartaMazo : ivMazo) {
            ivCartaMazo.setEnabled(false);
        }
        for (ImageView ivCartaTableroJugador : ivCartasTableroJugador) {
            ivCartaTableroJugador.setEnabled(false);
        }
    }

    private void mostrarAtaquesEscenarioJugador() {
        for (int i = 0; i < idCartasTableroJugador.length; i++) {
            if (idCartasTableroJugador[i] != -1) {
                for (Carta carta: listaCartas) {
                    if (carta.getIdCarta() == idCartasTableroJugador[i]) {
                        tvCartasTableroJugador.get(i).setText(String.valueOf(carta.getPoder()));
                    }
                }
            }
        }
    }


    public int[] convertirMazo() {
        // Dividir el string en substrings usando la coma como separador
        String[] numerosArray = jugador.getMazo().split(",");

        // Crear un array de enteros para almacenar los números
        int[] mazo = new int[numerosArray.length];

        // Convertir cada substring a un entero y almacenarlo en el array mazo
        for (int i = 0; i < numerosArray.length; i++) {
            mazo[i] = Integer.parseInt(numerosArray[i]);
        }

        return mazo;
    }

    private Bitmap convertirDrawableABitmap(Drawable drawable) {
        // Convierte el Drawable en Bitmap
        Bitmap bitmap;

        if (drawable instanceof BitmapDrawable) {
            bitmap = ((BitmapDrawable) drawable).getBitmap();
        } else {
            // Si el Drawable no es un BitmapDrawable, crea un nuevo Bitmap
            int width = drawable.getIntrinsicWidth();
            int height = drawable.getIntrinsicHeight();
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
        }
        return bitmap;
    }
}