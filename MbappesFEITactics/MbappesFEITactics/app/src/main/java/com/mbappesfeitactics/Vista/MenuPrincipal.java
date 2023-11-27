package com.mbappesfeitactics.Vista;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.mbappesfeitactics.R;

public class MenuPrincipal extends AppCompatActivity {
    public static final String GAMERTAG_KEY = "gamertag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
    }
}