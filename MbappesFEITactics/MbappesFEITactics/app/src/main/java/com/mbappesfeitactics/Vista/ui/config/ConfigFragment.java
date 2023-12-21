package com.mbappesfeitactics.Vista.ui.config;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.mbappesfeitactics.R;
import com.mbappesfeitactics.Vista.MenuP;
import com.mbappesfeitactics.databinding.FragmentConfigBinding;

public class ConfigFragment extends Fragment {

    private FragmentConfigBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ConfigViewModel configViewModel =
                new ViewModelProvider(this).get(ConfigViewModel.class);

        binding = FragmentConfigBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textConfig;
        configViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        // Encuentra el Switch por su ID
        Switch miSwitchSonido = root.findViewById(R.id.miSwitchSonido);
        miSwitchSonido.setChecked(true);

        // Asigna un OnCheckedChangeListener al Switch
        miSwitchSonido.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Aquí puedes manejar el cambio de estado
                if (isChecked) {
                    // El Switch está activado
                    // Llama a la función de reanudar en el MainActivity
                    ((MenuP) getActivity()).reanudarReproduccion();
                } else {
                    // El Switch está desactivado
                    // Llama a la función de detener en el MainActivity
                    ((MenuP) getActivity()).detenerReproduccion();
                }
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