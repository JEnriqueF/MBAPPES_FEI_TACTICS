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
import com.mbappesfeitactics.Vista.MenuP;
import com.mbappesfeitactics.Vista.MenuPrincipal;
import com.mbappesfeitactics.databinding.ActivityMainBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

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

    private void showMessage(String msj){
        Toast.makeText(this,msj, Toast.LENGTH_SHORT).show();
    }
}