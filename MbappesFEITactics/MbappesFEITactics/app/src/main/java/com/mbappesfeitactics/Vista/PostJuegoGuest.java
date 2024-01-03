package com.mbappesfeitactics.Vista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mbappesfeitactics.MainActivity;
import com.mbappesfeitactics.R;
import com.mbappesfeitactics.databinding.ActivityMainBinding;
import com.mbappesfeitactics.databinding.ActivityPostJuegoBinding;

public class PostJuegoGuest extends AppCompatActivity {

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
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}