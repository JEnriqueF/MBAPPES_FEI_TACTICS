package com.mbappesfeitactics.Vista;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mbappesfeitactics.DAO.JugadorDAO;
import com.mbappesfeitactics.POJO.Jugador;
import com.mbappesfeitactics.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CrearUsuario extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_usuario);

        EditText gamertagCrear = findViewById(R.id.gamertagCrear);
        EditText passwordCrear = findViewById(R.id.passwordCrear);
        Button btnCrearCuenta = findViewById(R.id.btnCrearCuenta);

        btnCrearCuenta.setOnClickListener(v -> {
            String gamertagBtn = gamertagCrear.getText().toString();
            String passwordBtn = passwordCrear.getText().toString();
            if(!gamertagBtn.isEmpty() || !passwordBtn.isEmpty()){
                Log.d("CrearUsuario.java",gamertagBtn);
                Log.d("CrearUsuario.java",passwordBtn);
                crearUser(gamertagBtn, passwordBtn, 1);
            }else{
                showMessage("Ingrese Datos para poder crear la cuenta");
            }
        });

    }

    private void crearUser(String gamertagCrear, String passwordCrear, int idFoto) {
        Log.d("CrearUsuario.java","Antes de llamar al método crearJugador del DAO");
        JugadorDAO.crearJugador(gamertagCrear, passwordCrear, idFoto, new Callback<Jugador>() {
            @Override
            public void onResponse(Call<Jugador> call, Response<Jugador> response) {
                Jugador jugador = response.body();
                if(jugador != null){
                    showMessage("Cuenta creada :)");
                }else {
                    showMessage("Operación no completada, intente más tarde");
                }
            }

            @Override
            public void onFailure(Call<Jugador> call, Throwable t) {
                t.printStackTrace();
                showMessage("Error en la conexión");
            }
        });
    }

    private void showMessage(String msj){
        Toast.makeText(this,msj, Toast.LENGTH_SHORT).show();
    }
}