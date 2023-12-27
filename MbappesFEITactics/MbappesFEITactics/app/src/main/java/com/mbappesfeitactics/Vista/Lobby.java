package com.mbappesfeitactics.Vista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.mbappesfeitactics.POJO.Jugador;
import com.mbappesfeitactics.R;
import com.mbappesfeitactics.Vista.ui.menu.MenuFragment;
import com.mbappesfeitactics.databinding.ActivityLobbyBinding;
import com.mbappesfeitactics.databinding.ActivityMainBinding;

public class Lobby extends AppCompatActivity {

    Jugador jugador = RecursosCompartidosViewModel.obtenerInstancia().getJugador();

    ActivityLobbyBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLobbyBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_lobby);

        binding.btnCancelar.setEnabled(true);

        binding.btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Botón Cancelar", "Se hizo clic en el botón Cancelar");
                // Crear un Intent para iniciar la actividad MenuP
                Intent intent = new Intent(getApplicationContext(), MenuP.class);
                // Iniciar la actividad MenuP
                startActivity(intent);
            }
        });
    }
}