package com.mbappesfeitactics.Vista;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.mbappesfeitactics.R;

public class FragmentoMazo extends Fragment {

    public FragmentoMazo(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflar el diseño para este fragmento
        return inflater.inflate(R.layout.fragment2, container, false);
    }
}
