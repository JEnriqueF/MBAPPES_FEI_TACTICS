package com.mbappesfeitactics.Vista.ui.mazo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.mbappesfeitactics.databinding.FragmentMazoBinding;

public class MazoFragment extends Fragment {

    private FragmentMazoBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MazoViewModel mazoViewModel =
                new ViewModelProvider(this).get(MazoViewModel.class);

        binding = FragmentMazoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}