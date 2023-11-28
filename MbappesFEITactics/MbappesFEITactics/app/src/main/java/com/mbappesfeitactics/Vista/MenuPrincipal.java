package com.mbappesfeitactics.Vista;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mbappesfeitactics.R;

public class MenuPrincipal extends AppCompatActivity {
    public static final String GAMERTAG_KEY = "gamertag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        NavController navController = Navigation.findNavController(this, R.id.navHostFragment);

        NavigationUI.setupWithNavController(bottomNavigationView, navController);

    }
}