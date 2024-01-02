package com.mbappesfeitactics.Vista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mbappesfeitactics.R;
import com.mbappesfeitactics.databinding.ActivityPartidaBinding;
import com.mbappesfeitactics.databinding.ActivityPostJuegoBinding;

public class PostJuego extends AppCompatActivity {

    ActivityPostJuegoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPostJuegoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.lbVictoriaDerrota.setText(RecursosCompartidosViewModel.obtenerInstancia().getEstadoFinalPartida());

        binding.btnTerminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MenuP.class);
                startActivity(intent);
            }
        });
    }
}