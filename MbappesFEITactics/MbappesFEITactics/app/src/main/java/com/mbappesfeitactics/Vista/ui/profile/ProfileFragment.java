package com.mbappesfeitactics.Vista.ui.profile;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.mbappesfeitactics.ConvertidorImagen;
import com.mbappesfeitactics.DAO.JugadorDAO;
import com.mbappesfeitactics.POJO.Carta;
import com.mbappesfeitactics.POJO.FotosPerfil;
import com.mbappesfeitactics.POJO.Jugador;
import com.mbappesfeitactics.Vista.RecursosCompartidosViewModel;
import com.mbappesfeitactics.databinding.FragmentProfileBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment{

    private FragmentProfileBinding binding;

    private List<FotosPerfil> listaFotosPerfil;
    private Jugador jugador;

    ImageView imagenClicada = null;

    int[] idFotosDisponibles = new int[5];
    int idFotoPerfil;


    private ArrayList<ImageView> ivDisponibles = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ProfileViewModel profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        listaFotosPerfil = RecursosCompartidosViewModel.obtenerInstancia().getFotosPerfil();
        jugador = RecursosCompartidosViewModel.obtenerInstancia().getJugador();

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ivDisponibles.add(binding.fotoParaUsar1);
        ivDisponibles.add(binding.fotoParaUsar2);
        ivDisponibles.add(binding.fotoParaUsar3);
        ivDisponibles.add(binding.fotoParaUsar4);
        ivDisponibles.add(binding.fotoParaUsar5);

        mostrarFotosPerfil();

        binding.textProfile.setText(jugador.getGamertag());
        binding.lbNoPartidasGanadas.setText(String.valueOf(jugador.getPartidasGanadas()));

        ivDisponibles.get(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moverFotoPerfil(ivDisponibles.get(0));
            }
        });

        ivDisponibles.get(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moverFotoPerfil(ivDisponibles.get(1));
            }
        });

        ivDisponibles.get(2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moverFotoPerfil(ivDisponibles.get(2));
            }
        });

        ivDisponibles.get(3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moverFotoPerfil(ivDisponibles.get(3));
            }
        });

        ivDisponibles.get(4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moverFotoPerfil(ivDisponibles.get(4));
            }
        });


        binding.btnEditarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JugadorDAO.editarFotoPerfil(jugador.getGamertag(), idFotoPerfil, new Callback<Jugador>() {
                    @Override
                    public void onResponse(Call<Jugador> call, Response<Jugador> response) {
                        jugador.setIdFoto(idFotoPerfil);
                        RecursosCompartidosViewModel.obtenerInstancia().setJugador(jugador);
                        Log.d("Prueba","Todo ok");
                    }

                    @Override
                    public void onFailure(Call<Jugador> call, Throwable t) {
                        Log.d("Prueba","Todo no ok :(");
                    }
                });
            }
        });

        return root;
    }

    private void moverFotoPerfil(ImageView ivImagenSeleccionada){
        Bitmap bitmapCircular = convertirDrawableABitmap(binding.imagenCircular.getDrawable());
        int idFotoCircular = idFotoPerfil;

        Bitmap seleccionada = convertirDrawableABitmap(ivImagenSeleccionada.getDrawable());
        int idFotoClicada = 0;

        for (ImageView ivClicadaActual: ivDisponibles) {
            if(ivImagenSeleccionada == ivClicadaActual){
                int index = ivDisponibles.indexOf(ivClicadaActual);
                idFotoClicada = idFotosDisponibles[index];
            }
        }

        idFotoPerfil = idFotoClicada;

        for(int i = 0; i < idFotosDisponibles.length; i++){
            if (idFotoClicada == idFotosDisponibles[i]) {
                idFotosDisponibles[i] = idFotoCircular;
            }
        }

        Log.d("IdFotoCircular", String.valueOf(idFotoPerfil));
        Log.d("IdFotosDisponibles",idFotosDisponibles[0] + " " + idFotosDisponibles[1] + " " + idFotosDisponibles[2] + " " +idFotosDisponibles[3] + " " + idFotosDisponibles[4]);
        ivImagenSeleccionada.setImageBitmap(bitmapCircular);
        binding.imagenCircular.setImageBitmap(seleccionada);
    }

    private void mostrarFotosPerfil() {
        int contadorIVDisponible = 0;

        Log.d("ID Foto Jugador", String.valueOf(jugador.getIdFoto()));

        for (FotosPerfil fotoPerfil : listaFotosPerfil) {

            Log.d("ID Foto Lista", String.valueOf(fotoPerfil.getIdFoto()));

            if (fotoPerfil.getIdFoto() == jugador.getIdFoto()) {
                binding.imagenCircular.setImageBitmap(ConvertidorImagen.convertirStringABitmap(fotoPerfil.getFoto()));
                idFotoPerfil = fotoPerfil.getIdFoto();
            } else {
                if (contadorIVDisponible < ivDisponibles.size()) {
                    ivDisponibles.get(contadorIVDisponible).setImageBitmap(ConvertidorImagen.convertirStringABitmap(fotoPerfil.getFoto()));
                    idFotosDisponibles [contadorIVDisponible] = fotoPerfil.getIdFoto();
                    contadorIVDisponible++;
                }
            }
        }
    }


    private Bitmap convertirDrawableABitmap(Drawable drawable) {
        // Convierte el Drawable en Bitmap
        Bitmap bitmap;

        if (drawable instanceof BitmapDrawable) {
            bitmap = ((BitmapDrawable) drawable).getBitmap();
        } else {
            // Si el Drawable no es un BitmapDrawable, crea un nuevo Bitmap
            int width = drawable.getIntrinsicWidth();
            int height = drawable.getIntrinsicHeight();
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
        }
        return bitmap;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
