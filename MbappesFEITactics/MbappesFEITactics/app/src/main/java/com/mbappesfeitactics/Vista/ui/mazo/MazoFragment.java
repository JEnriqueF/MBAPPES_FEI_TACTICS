package com.mbappesfeitactics.Vista.ui.mazo;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.mbappesfeitactics.ConvertidorImagen;
import com.mbappesfeitactics.POJO.Carta;
import com.mbappesfeitactics.POJO.Jugador;
import com.mbappesfeitactics.Vista.RecursosCompartidosViewModel;
import com.mbappesfeitactics.databinding.FragmentMazoBinding;
import java.util.ArrayList;
import java.util.List;

public class MazoFragment extends Fragment {

    private MazoViewModel mazoViewModel;
    private FragmentMazoBinding binding;
    private ArrayList<ImageView> ivMazo = new ArrayList<>();
    private ArrayList<ImageView> ivDisponible = new ArrayList<>();

    private List<Carta> listaCartas;
    private Jugador jugador;

    private int[] mazoEditado;

    private ImageView imagenClicada;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MazoViewModel mazoViewModel = new ViewModelProvider(this).get(MazoViewModel.class);

        binding = FragmentMazoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        listaCartas = RecursosCompartidosViewModel.obtenerInstancia().getCartas();
        jugador = RecursosCompartidosViewModel.obtenerInstancia().getJugador();
        mazoEditado = convertirMazo();
        imagenClicada = null;

        ivMazo.add(binding.carta1);
        ivMazo.add(binding.carta2);
        ivMazo.add(binding.carta3);
        ivMazo.add(binding.carta4);

        ivDisponible.add(binding.cartaParaUsar1);
        ivDisponible.add(binding.cartaParaUsar2);

        mostrarCartas();

        // Estado anterior de las cartas en el mazo y disponibles
        List<Integer> estadoAnteriorMazo = obtenerEstadoCartas(ivMazo);
        List<Integer> estadoAnteriorDisponible = obtenerEstadoCartas(ivDisponible);

        ivMazo.get(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (imagenClicada == ivMazo.get(0)) {
                    imagenClicada = null;
                } else {

                    if (imagenClicada == null) {
                        imagenClicada = ivMazo.get(0);
                    } else {
                        Bitmap bitmapClicadaPrimero = convertirDrawableABitmap(imagenClicada.getDrawable());
                        String imagenClicadaPrimero = ConvertidorImagen.convertirBitmapAString(bitmapClicadaPrimero);
                        int idCartaClicadaPrimero = 0;
                        for (Carta carta : listaCartas) {
                            if (carta.getImagen() == imagenClicadaPrimero) {
                                idCartaClicadaPrimero = carta.getIdCarta();
                            }
                        }

                        Bitmap bitmapClicadaActual = convertirDrawableABitmap(ivMazo.get(0).getDrawable());
                        String imagenClicadaActual = ConvertidorImagen.convertirBitmapAString(bitmapClicadaActual);
                        int idCartaClicadaActual = 0;
                        for (Carta carta : listaCartas) {
                            if (carta.getImagen() == imagenClicadaActual) {
                                idCartaClicadaActual = carta.getIdCarta();
                            }
                        }

                        //Aqui debe controlar las idCarta en el mazoEditado

                        imagenClicada.setImageBitmap(bitmapClicadaActual);
                        ivMazo.get(0).setImageBitmap(bitmapClicadaPrimero);
                    }
                }
            }
        });

        binding.btnEditarMazo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<Integer> estadoActualMazo = obtenerEstadoCartas(ivMazo);
                List<Integer> estadoActualDisponible = obtenerEstadoCartas(ivDisponible);
                if (!estadoAnteriorMazo.equals(estadoActualMazo) || !estadoAnteriorDisponible.equals(estadoActualDisponible)) {
                    //Hubo Cambios
                } else {
                    //No hubo cambios
                }

            }
        });

        return root;
    }

    private void mostrarCartas() {
        int[] mazo = convertirMazo();
        int contadorMazo = 0;
        int contadorIVDisponible = 0;

        for (Carta carta : listaCartas) {

            // Comprobar si el idCarta está en el mazo
            boolean estaEnMazo = false;
            for (int id : mazo) {
                if (id == carta.getIdCarta()) {
                    estaEnMazo = true;
                    break;
                }
            }

            if (estaEnMazo) {
                // Hacer algo si la carta está en el mazo
                System.out.println("La carta con id " + carta.getIdCarta() + " está en el mazo.");

                for (int i = 0; i < mazo.length; i++) {
                    if (carta.getIdCarta() == mazo[i]) {
                        ivMazo.get(contadorMazo).setImageBitmap(ConvertidorImagen.convertirStringABitmap(carta.getImagen()));
                        contadorMazo++;
                    }
                }
            } else {
                // Hacer algo si la carta no está en el mazo
                System.out.println("La carta con id " + carta.getIdCarta() + " no está en el mazo.");

                ivDisponible.get(contadorIVDisponible).setImageBitmap(ConvertidorImagen.convertirStringABitmap(carta.getImagen()));
                contadorIVDisponible++;
            }
        }
    }


    // Función para convertir un String de números separados por comas a un array de enteros
    public int[] convertirMazo() {
        // Dividir el string en substrings usando la coma como separador
        String[] numerosArray = jugador.getMazo().split(",");

        // Crear un array de enteros para almacenar los números
        int[] mazo = new int[numerosArray.length];

        // Convertir cada substring a un entero y almacenarlo en el array mazo
        for (int i = 0; i < numerosArray.length; i++) {
            mazo[i] = Integer.parseInt(numerosArray[i]);
        }

        return mazo;
    }

    private List<Integer> obtenerEstadoCartas(List<ImageView> listaCartas) {
        List<Integer> estadoCartas = new ArrayList<>();

        for (ImageView carta : listaCartas) {
            // Obtener el contenido actual de cada ImageView (puedes adaptar esto según tu implementación)
            Drawable drawable = carta.getDrawable();
            if (drawable instanceof BitmapDrawable) {
                Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                estadoCartas.add(bitmap.hashCode()); // Usa hashCode para comparar imágenes
            }
        }

        return estadoCartas;
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