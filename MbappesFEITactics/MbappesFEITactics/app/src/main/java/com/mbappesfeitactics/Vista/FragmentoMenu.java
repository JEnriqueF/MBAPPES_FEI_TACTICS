package com.mbappesfeitactics.Vista;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mbappesfeitactics.R;


public class FragmentoMenu extends Fragment {

    public FragmentoMenu() {
        // Constructor por defecto requerido
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflar el diseño para este fragmento
        return inflater.inflate(R.layout.fragment1, container, false);
    }
}
