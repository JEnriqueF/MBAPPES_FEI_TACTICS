package com.mbappesfeitactics.Vista.ui.mazo;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.mbappesfeitactics.ConvertidorImagen;
import com.mbappesfeitactics.POJO.Carta;
import com.mbappesfeitactics.databinding.FragmentMazoBinding;

import java.util.List;

public class MazoFragment extends Fragment {

    private MazoViewModel mazoViewModel;
    private FragmentMazoBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MazoViewModel mazoViewModel = new ViewModelProvider(this).get(MazoViewModel.class);

        binding = FragmentMazoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Observa los cambios en el LiveData
        mazoViewModel.getCartas().observe(getViewLifecycleOwner(), new Observer<List<Carta>>() {
            @Override
            public void onChanged(List<Carta> listaCartas) {
                // Hacer algo con la lista de cartas en el fragmento
                Carta carta = listaCartas.get(2);
                Log.d("Recuperacion MazoFragment", "" + listaCartas.get(2));
                binding.carta2.setImageBitmap(ConvertidorImagen.convertirStringABitmap(carta.getImagen()));
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