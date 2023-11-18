package com.mbappesfeitactics;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mbappesfeitactics.DAO.JugadorDAO;
import com.mbappesfeitactics.POJO.Jugador;
import com.mbappesfeitactics.Vista.MenuPrincipal;
import com.mbappesfeitactics.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater()); //Para accesar a los datos del layout
        setContentView(binding.getRoot());
        //Componentes de la vista
        setContentView(R.layout.activity_main);
        EditText gamertagUsuario = findViewById(R.id.gamertag);
        EditText passwordUsuario = findViewById(R.id.password);
        Button btnInciarSesion = findViewById(R.id.btnIniciarSesion);
        Button btnIniciarInvitado = findViewById(R.id.btnIniciarInvitado);
        Button btnCrearCuenta = findViewById(R.id.btnCrearCuenta);
        TextView txtAlerta = findViewById(R.id.lbErrorInicio);

        btnInciarSesion.setOnClickListener(v->{
            String gamertag = binding.gamertag.getText().toString();
            String password = passwordUsuario.getText().toString();

            if(!gamertag.isEmpty() || !password.isEmpty()) {
                try{

                    Jugador jugadorObtenido = JugadorDAO.inciarSesion(gamertag, password);

                    if(jugadorObtenido !=null){
                        String alerta = "Hola " + gamertag.toUpperCase()
                                + "! bienvenido a FEI Tactics";
                        txtAlerta.setText(alerta);
                        openMenuPrincipal(gamertag); //Llamar al Menú Principal
                    }else{
                        Toast.makeText(this,"Error al iniciar sesión", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    e.getMessage();
                }
            }else{
                Toast.makeText(this,"Error al iniciar sesión",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openMenuPrincipal(String gamertag) {
        Intent intent = new Intent(this, MenuPrincipal.class);
        intent.putExtra(MenuPrincipal.GAMERTAG_KEY, gamertag);
        startActivity(intent);
    }
}