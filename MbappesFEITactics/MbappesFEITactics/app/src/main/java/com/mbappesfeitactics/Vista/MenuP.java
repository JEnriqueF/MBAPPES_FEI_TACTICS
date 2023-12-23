package com.mbappesfeitactics.Vista;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.mbappesfeitactics.DAO.CartaDAO;
import com.mbappesfeitactics.DAO.FotosPerfilDAO;
import com.mbappesfeitactics.DAO.RespuestaCartas;
import com.mbappesfeitactics.DAO.RespuestaFotosPerfil;
import com.mbappesfeitactics.POJO.Carta;
import com.mbappesfeitactics.POJO.FotosPerfil;
import com.mbappesfeitactics.POJO.Jugador;
import com.mbappesfeitactics.R;
import com.mbappesfeitactics.Vista.ui.mazo.MazoViewModel;
import com.mbappesfeitactics.databinding.ActivityMenuPBinding;
import com.mbappesfeitactics.databinding.ActivityMenuPrincipalBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuP extends AppCompatActivity {

    private ActivityMenuPBinding binding;
    private MazoViewModel mazoViewModel;
    private MutableLiveData<List<Carta>> cartas = new MutableLiveData<>();
    private MutableLiveData<List<FotosPerfil>> fotosPerfil = new MutableLiveData<>();
    public static final String JUGADOR_KEY = "jugador_key";
    public static final String BUNDLE_KEY = "bundle";

    private boolean error;

    private MediaPlayer mediaPlayer;


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


        // Inicializar el reproductor de audio
        mediaPlayer = MediaPlayer.create(this, R.raw.musica_fei_tactics);

        // Configurar un OnCompletionListener para volver a reproducir cuando termine
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                // Reproducir el audio nuevamente cuando termine
                mediaPlayer.seekTo(0); // Reiniciar al principio
                mediaPlayer.start();
            }
        });

        // Reproducir el audio
        mediaPlayer.start();

        mazoViewModel = new ViewModelProvider(this).get(MazoViewModel.class);

        recuperarMedia();
    }

    public void recuperarMedia() {
        obtenerCartas();
        obtenerFotosPerfil();
    }


    private void obtenerCartas() {
        CartaDAO.recuperarCartas(new Callback<RespuestaCartas>() {
            @Override
            public void onResponse(Call<RespuestaCartas> call, Response<RespuestaCartas> response) {
                if (response.isSuccessful()) {
                    RespuestaCartas respuestaRecibida = response.body();

                    // Asigna la lista de cartas a tu MutableLiveData
                    if (respuestaRecibida != null) {
                        List<Carta> listaCartas = respuestaRecibida.getCartas();
                        Log.d("Recuperacion carta MenuP", "" + listaCartas.get(2));
                        RecursosCompartidosViewModel.obtenerInstancia().setCartas(listaCartas);
                    }
                }
            }

            @Override
            public void onFailure(Call<RespuestaCartas> call, Throwable t) {
                error = true;
            }
        });
    }

    private void obtenerFotosPerfil() {
        FotosPerfilDAO.recuperarFotosPerfil(new Callback<RespuestaFotosPerfil>() {
            @Override
            public void onResponse(Call<RespuestaFotosPerfil> call, Response<RespuestaFotosPerfil> response) {
                if (response.isSuccessful()) {
                    RespuestaFotosPerfil respuestaRecibida = response.body();

                    if (respuestaRecibida != null) {
                        List<FotosPerfil> listaFotosPerfil = respuestaRecibida.getFotosPerfil();

                        RecursosCompartidosViewModel.obtenerInstancia().setFotosPerfil(listaFotosPerfil);
                    }
                }
            }

            @Override
            public void onFailure(Call<RespuestaFotosPerfil> call, Throwable t) { error = true; }
        });
    }

    public void reanudarReproduccion() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    public void detenerReproduccion() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Liberar recursos del reproductor de audio
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_menu_p);
        return NavigationUI.navigateUp(navController, new AppBarConfiguration.Builder(R.id.navigation_menu, R.id.navigation_mazo, R.id.navigation_config, R.id.navigation_profile).build())
                || super.onSupportNavigateUp();
    }
}