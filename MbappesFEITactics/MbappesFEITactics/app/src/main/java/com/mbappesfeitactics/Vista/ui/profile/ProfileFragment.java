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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.mbappesfeitactics.ConvertidorImagen;
import com.mbappesfeitactics.POJO.Carta;
import com.mbappesfeitactics.POJO.FotosPerfil;
import com.mbappesfeitactics.POJO.Jugador;
import com.mbappesfeitactics.Vista.RecursosCompartidosViewModel;
import com.mbappesfeitactics.databinding.FragmentProfileBinding;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment{

    private FragmentProfileBinding binding;

    private List<FotosPerfil> listaFotosPerfil;
    private Jugador jugador;

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

        return root;
    }

    private void mostrarFotosPerfil() {
        int contadorIVDisponible = 0;

        Log.d("ID Foto Jugador", String.valueOf(jugador.getIdFoto()));

        for (FotosPerfil fotoPerfil : listaFotosPerfil) {

            Log.d("ID Foto Lista", String.valueOf(fotoPerfil.getIdFoto()));

            if (fotoPerfil.getIdFoto() == jugador.getIdFoto()) {
                binding.imagenCircular.setImageBitmap(ConvertidorImagen.convertirStringABitmap(fotoPerfil.getFoto()));
            } else {
                if (contadorIVDisponible < ivDisponibles.size()) {
                    ivDisponibles.get(contadorIVDisponible).setImageBitmap(ConvertidorImagen.convertirStringABitmap(fotoPerfil.getFoto()));
                    contadorIVDisponible++;
                }
            }
        }
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
