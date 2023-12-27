package com.mbappesfeitactics.Vista.ui.menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.mbappesfeitactics.Vista.CrearUsuario;
import com.mbappesfeitactics.Vista.Lobby;
import com.mbappesfeitactics.databinding.FragmentMenuBinding;

public class MenuFragment extends Fragment {

    private FragmentMenuBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MenuViewModel menuViewModel = new ViewModelProvider(this).get(MenuViewModel.class);

        binding = FragmentMenuBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();

        final TextView textView = binding.textMenu;
        menuViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        binding.btnJugar.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), Lobby.class);
            requireActivity().startActivity(intent);
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}