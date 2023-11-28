package com.mbappesfeitactics;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mbappesfeitactics.DAO.JugadorDAO;
import com.mbappesfeitactics.DAO.JugadorService;
import com.mbappesfeitactics.POJO.Jugador;
import com.mbappesfeitactics.Vista.MenuPrincipal;
import com.mbappesfeitactics.databinding.ActivityMainBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private JugadorService jugadorService;

    Jugador jugadorObtenido = new Jugador();

    boolean banderaConexion = false;

    boolean banderaUsuario = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater()); //Para accesar a los datos del layout
        setContentView(binding.getRoot());
        //Componentes de la vista
        //setContentView(R.layout.activity_main);
        EditText gamertagUsuario = findViewById(R.id.gamertag);
        EditText passwordUsuario = findViewById(R.id.password);
        Button btnInciarSesion = findViewById(R.id.btnIniciarSesion);
        Button btnIniciarInvitado = findViewById(R.id.btnIniciarInvitado);
        Button btnCrearCuenta = findViewById(R.id.btnCrearCuenta);
        TextView txtAlerta = findViewById(R.id.lbErrorInicio);

        //Configuración de retrofit

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://mk2m8b3x-3000.usw3.devtunnels.ms")
                .addConverterFactory(GsonConverterFactory.create()).build();

        //Instancia del JugadorDAO

        jugadorService = retrofit.create(JugadorService.class);


        btnInciarSesion.setOnClickListener(v->{
            jugadorObtenido=null;
            banderaConexion=true;
            banderaUsuario=false;
            String gamertag = gamertagUsuario.getText().toString();
            String password = passwordUsuario.getText().toString();
            Log.d("Test",gamertag);
            Log.d("Test",password);
            if(!gamertag.isEmpty() || !password.isEmpty()) {

                Log.d("PRUEBAGG","Evaluacion");

                loginUser(gamertag, password);

                if(!banderaConexion){
                    openMenuPrincipal(gamertag);
                }else{
                    Toast.makeText(this, "Error en la conexion", Toast.LENGTH_SHORT).show();
                }


            }else{
                Toast.makeText(this,"Error al iniciar sesión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openMenuPrincipal(String gamertag) {
        Intent intent = new Intent(this, MenuPrincipal.class);
        intent.putExtra(MenuPrincipal.GAMERTAG_KEY, gamertag);
        startActivity(intent);
    }

    private synchronized void loginUser(String gamertag, String password) {
        JugadorDAO.iniciarSesion(gamertag, password, new Callback<Jugador>() {
            @Override
            public void onResponse(Call<Jugador> call, Response<Jugador> response) {
                // Manejar la respuesta exitosa aquí
                Jugador jugador = response.body();
                if(jugador != null){
                    jugadorObtenido = jugador;
                    banderaUsuario = true;
                    Log.d("TodoBien", "Jugador Recuperado: "+jugadorObtenido.getGamertag()+" "+jugadorObtenido.getPassword()+" "+jugadorObtenido.getPartidasGanadas()+" "+jugadorObtenido.getPartidasPerdidas()+" "+jugadorObtenido.getMazo()+" "+jugadorObtenido.getIdFoto());
                }else{
                    jugadorObtenido = null;
                }

            }


            @Override
            public void onFailure(Call<Jugador> call, Throwable t) {
                // Manejar el fallo aquí
                banderaConexion = true;
            }
        });
    }
}