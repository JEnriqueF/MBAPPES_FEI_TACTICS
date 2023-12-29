package com.mbappesfeitactics.Vista;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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
    private List<Carta> listaCartas;
    private ArrayList<ImageView> ivEscenarios = new ArrayList<>();
    private List<Escenario> listaEscenarios;
    private List<FotosPerfil> listaFotosPerfil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityPartidaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        listaCartas = RecursosCompartidosViewModel.obtenerInstancia().getCartas();
        listaEscenarios = RecursosCompartidosViewModel.obtenerInstancia().getEscenarios();
        jugador = RecursosCompartidosViewModel.obtenerInstancia().getJugador();
        adversario = RecursosCompartidosViewModel.obtenerInstancia().getAdversario();

        ivMazo.add(binding.fotoParaPoner1);
        ivMazo.add(binding.fotoParaPoner2);
        ivMazo.add(binding.fotoParaPoner3);
        ivMazo.add(binding.fotoParaPoner4);

        ivEscenarios.add(binding.imagenEscenario1);
        ivEscenarios.add(binding.imagenEscenario2);
        ivEscenarios.add(binding.imagenEscenario3);

        cargarAdversario();

        mostrarCartas();
        mostrarEscenarios();
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