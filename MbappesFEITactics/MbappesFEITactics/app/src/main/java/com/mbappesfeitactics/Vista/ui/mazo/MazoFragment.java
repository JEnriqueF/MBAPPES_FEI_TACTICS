package com.mbappesfeitactics.Vista.ui.mazo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.mbappesfeitactics.ConvertidorImagen;
import com.mbappesfeitactics.POJO.Carta;
import com.mbappesfeitactics.databinding.FragmentMazoBinding;

import java.util.List;

public class MazoFragment extends Fragment {

    private FragmentMazoBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MazoViewModel mazoViewModel = new ViewModelProvider(this).get(MazoViewModel.class);

        binding = FragmentMazoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        LiveData<List<Carta>> listaCartas = mazoViewModel.getCartas();
        listaCartas.observe(getViewLifecycleOwner(), cartas -> {
            if (cartas != null && cartas.size() > 1) {
                Carta carta = cartas.get(2);
                binding.carta1.setImageBitmap(ConvertidorImagen.convertirStringABitmap(carta.getImagen()));
            }
        });

        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}