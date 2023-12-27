package com.mbappesfeitactics.Vista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.mbappesfeitactics.R;
import com.mbappesfeitactics.databinding.ActivityLobbyBinding;
import com.mbappesfeitactics.databinding.ActivityMainBinding;

public class Lobby extends AppCompatActivity {

    ActivityLobbyBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLobbyBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_lobby);

        binding.btnCancelar.setOnClickListener(v -> {
            Intent intent = new Intent(this, MenuP.class);
            startActivity(intent);
        });
    }
}