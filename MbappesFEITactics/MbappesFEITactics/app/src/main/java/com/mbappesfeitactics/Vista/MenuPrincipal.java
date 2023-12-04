package com.mbappesfeitactics.Vista;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.mbappesfeitactics.POJO.Jugador;
import com.mbappesfeitactics.R;
import com.mbappesfeitactics.databinding.ActivityMenuPrincipalBinding;

public class MenuPrincipal extends AppCompatActivity {
    public static final String GAMERTAG_KEY = "gamertag";

    public static final String JUGADOR_KEY = "jugador_key";

    public static final String BUNDLE_KEY = "bundle";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Log.d("MenuPrincipal", GAMERTAG_KEY);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        ActivityMenuPrincipalBinding bin = ActivityMenuPrincipalBinding.inflate(getLayoutInflater());

        Bundle extras = getIntent().getBundleExtra(BUNDLE_KEY);
        Jugador jugador = extras.getParcelable(JUGADOR_KEY);
        Log.d("MENU PRINCIPAL ONCREATE", "onCreate: ." + jugador.getGamertag());
        bin.setGamertag(jugador.getGamertag());

    }
}