package com.mbappesfeitactics.Vista;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.mbappesfeitactics.ConvertidorImagen;
import com.mbappesfeitactics.DAO.EscenarioDAO;
import com.mbappesfeitactics.DAO.RespuestaEscenarios;
import com.mbappesfeitactics.POJO.Carta;
import com.mbappesfeitactics.POJO.Escenario;
import com.mbappesfeitactics.POJO.FotosPerfil;
import com.mbappesfeitactics.POJO.Jugador;
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
    private ArrayList<ImageView> ivCartasTableroJugador = new ArrayList<>();
    private int[] idCartasTableroJugador = new int[3];
    private List<FotosPerfil> listaFotosPerfil;

    private ImageView imagenClicada;


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
                ArrayList<int[]> listaMovimientos = new ArrayList<>();
                for (int i = 0; i < idCartasTableroJugador.length; i++) {
                    if (idCartasTableroJugador[i] != -1) {
                        int[] movimiento = {i, idCartasTableroJugador[i] };
                        listaMovimientos.add(movimiento);
                    }
                }
            }
        });

        binding.btnAbandonarPartida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {  }
        });
    }

    private void moverCartasJugador(ImageView ivInvocadoraActual) {
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
                        for (int i = 0; i < mazoPartida.length; i++) {
                            if (idCartaClicadaPrimero == mazoPartida[i]) {
                                mazoPartida[i] = idCartaClicadaActual;
                            }
                        }
                        for (int i = 0; i < idCartasTableroJugador.length; i++) {
                            if (idCartaClicadaActual == idCartasTableroJugador[i]) {
                                idCartasTableroJugador[i] = idCartaClicadaPrimero;
                            }
                        }
                    } else if (clicadaActualEsMazo) {
                        for (int i = 0; i < mazoPartida.length; i++) {
                            if (idCartaClicadaActual == mazoPartida[i]) {
                                mazoPartida[i] = idCartaClicadaPrimero;
                            }
                        }
                        for (int i = 0; i < idCartasTableroJugador.length; i++) {
                            if (idCartaClicadaPrimero == idCartasTableroJugador[i]) {
                                idCartasTableroJugador[i] = idCartaClicadaActual;
                            }
                        }
                    }
                    ivInvocadoraActual.setImageBitmap(bitmapClicadaPrimero);
                    imagenClicada.setImageBitmap(bitmapClicadaActual);
                    imagenClicada = null;
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