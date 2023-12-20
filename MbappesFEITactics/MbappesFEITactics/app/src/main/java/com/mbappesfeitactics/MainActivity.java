package com.mbappesfeitactics;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mbappesfeitactics.DAO.CartaDAO;
import com.mbappesfeitactics.DAO.JugadorDAO;
import com.mbappesfeitactics.DAO.JugadorService;
import com.mbappesfeitactics.DAO.RespuestaCartas;
import com.mbappesfeitactics.POJO.Carta;
import com.mbappesfeitactics.POJO.Jugador;
import com.mbappesfeitactics.Vista.CrearUsuario;
import com.mbappesfeitactics.Vista.MenuP;
import com.mbappesfeitactics.Vista.MenuPrincipal;
import com.mbappesfeitactics.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private List<Carta> cartas;
    private boolean error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnIniciarSesion.setOnClickListener(v->{
            String gamertag = binding.gamertag.getText().toString();
            String password = binding.password.getText().toString();
            if(!gamertag.isEmpty() || !password.isEmpty()) {
                loginUser(gamertag, password);
            }else{
                showMessage("Ingrese credenciales de acceso");
            }
        });

        binding.btnCrearCuenta.setOnClickListener(v -> {
            Intent intent = new Intent(this, CrearUsuario.class);
            startActivity(intent);
        });

    }

    private void openMenuPrincipal(Jugador jugador) {
        Log.d("Probar OpenMp", "Intent this");
        Intent intent = new Intent(this, MenuP.class);

        Bundle b = new Bundle();
        b.putParcelable(MenuP.JUGADOR_KEY, jugador);

        Log.d("Probar OpenMp", "intent.putExtra");
        Log.d("Probar OpenMp", String.valueOf(jugador));

        intent.putExtra(MenuP.BUNDLE_KEY, b);

        Log.d("Probar OpenMp", "startActivity");
        startActivity(intent);
    }



    private void loginUser(String gamertag, String password) {
        JugadorDAO.iniciarSesion(gamertag, password, new Callback<Jugador>() {
            @Override
            public void onResponse(Call<Jugador> call, Response<Jugador> response) {
                // Manejar la respuesta exitosa aquí
                Jugador jugador = response.body();
                Log.d("MainActivity","Jugador: "+jugador);
                if(jugador != null){
                    openMenuPrincipal(jugador);
                }else{
                    showMessage("Credenciales de acceso incorrectas");
                }

            }

            @Override
            public void onFailure(Call<Jugador> call, Throwable t) {
                showMessage("Error en la conexión");
            }
        });
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
                        cartas = listaCartas;
                    }
                }
            }

            @Override
            public void onFailure(Call<RespuestaCartas> call, Throwable t) {
                error = true;
            }
        });
    }

    private void showMessage(String msj){
        Toast.makeText(this,msj, Toast.LENGTH_SHORT).show();
    }
}