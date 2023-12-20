package com.mbappesfeitactics.Vista;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.mbappesfeitactics.POJO.Carta;
import com.mbappesfeitactics.POJO.Jugador;
import com.mbappesfeitactics.R;
import com.mbappesfeitactics.databinding.ActivityMenuPBinding;
import com.mbappesfeitactics.databinding.ActivityMenuPrincipalBinding;

import java.util.ArrayList;
import java.util.List;

public class MenuP extends AppCompatActivity {

    private ActivityMenuPBinding binding;
    public MutableLiveData<List<Carta>> cartas = new MutableLiveData<>();
    public static final String JUGADOR_KEY = "jugador_key";
    public static final String BUNDLE_KEY = "bundle";

    ArrayList<Carta> listaCartas = new ArrayList<>();
    Jugador jugadorActual = new Jugador();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMenuPBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Configurar el Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BottomNavigationView navView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_menu, R.id.navigation_mazo, R.id.navigation_config, R.id.navigation_profile)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_menu_p);
        // Configurar la ActionBar con NavController
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        // Configurar el BottomNavigationView con NavController
        NavigationUI.setupWithNavController(navView, navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_menu_p);
        return NavigationUI.navigateUp(navController, new AppBarConfiguration.Builder(R.id.navigation_menu, R.id.navigation_mazo, R.id.navigation_config, R.id.navigation_profile).build())
                || super.onSupportNavigateUp();
    }
}