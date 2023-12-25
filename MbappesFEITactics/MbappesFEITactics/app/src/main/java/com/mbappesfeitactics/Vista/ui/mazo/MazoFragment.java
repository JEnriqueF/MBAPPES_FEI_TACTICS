package com.mbappesfeitactics.Vista.ui.mazo;

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
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.mbappesfeitactics.ConvertidorImagen;
import com.mbappesfeitactics.DAO.JugadorDAO;
import com.mbappesfeitactics.POJO.Carta;
import com.mbappesfeitactics.POJO.Jugador;
import com.mbappesfeitactics.Vista.RecursosCompartidosViewModel;
import com.mbappesfeitactics.databinding.FragmentMazoBinding;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MazoFragment extends Fragment {

    private MazoViewModel mazoViewModel;
    private FragmentMazoBinding binding;

    private ArrayList<ImageView> ivMazo = new ArrayList<>();
    private int[] mazoEditado = new int[4];
    private ArrayList<ImageView> ivDisponible = new ArrayList<>();
    private int[] idCartasDisponible = new int[2];
    private List<Carta> listaCartas;
    private Jugador jugador;

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

        for (int idCarta: mazoEditado) {
            Log.d("Carta Mazo", String.valueOf(idCarta));
        }

        for (int idCarta: idCartasDisponible) {
            Log.d("Carta Disponible", String.valueOf(idCarta));
        }

        ivMazo.get(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moverCartas(ivMazo.get(0));
            }
        });
        ivMazo.get(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moverCartas(ivMazo.get(1));
            }
        });
        ivMazo.get(2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moverCartas(ivMazo.get(2));
            }
        });
        ivMazo.get(3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moverCartas(ivMazo.get(3));
            }
        });
        ivDisponible.get(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moverCartas(ivDisponible.get(0));
            }
        });
        ivDisponible.get(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moverCartas(ivDisponible.get(1));
            }
        });

        binding.btnEditarMazo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("Mazo Editado", mazoEditado[0]+" "+mazoEditado[1]+" "+mazoEditado[2]+" "+mazoEditado[3]);
                int[] mazoOriginal = convertirMazo();
                Log.d("Mazo original", mazoOriginal[0]+" "+mazoOriginal[1]+" "+mazoOriginal[2]+" "+mazoOriginal[3]);

                if (Arrays.equals(mazoEditado, convertirMazo())) {
                    //No hubo cambios
                    Log.d("No hubo Cambios", "");

                } else {
                    //Hubo Cambios
                    StringBuilder mazoBuilder = new StringBuilder();

                    for (int i = 0; i < mazoEditado.length; i++) {
                        // Agregar cada elemento al StringBuilder
                        mazoBuilder.append(mazoEditado[i]);

                        // Si no es el último elemento, agregar una coma
                        if (i < mazoEditado.length - 1) {
                            mazoBuilder.append(",");
                        }
                    }

                    JugadorDAO.editarMazo(jugador.getGamertag(), mazoBuilder.toString(), new Callback<Jugador>() {
                        @Override
                        public void onResponse(Call<Jugador> call, Response<Jugador> response) {
                            if (response.isSuccessful()) {
                                Log.d("Todo Bien", "");
                                jugador.setMazo(mazoBuilder.toString());
                                RecursosCompartidosViewModel.obtenerInstancia().setJugador(jugador);
                            } else {
                                Log.d("Todo Mal", "");
                            }
                        }

                        @Override
                        public void onFailure(Call<Jugador> call, Throwable t) {
                            Log.d("Todo Mal", "");
                        }
                    });
                }

            }
        });

        return root;
    }

    private void moverCartas(ImageView ivInvocadoraActual) {
        if (imagenClicada == ivInvocadoraActual) {
            Log.d("Misma Carta CLicada", "");
            imagenClicada = null;
        } else {
            if (imagenClicada == null) {
                Log.d("Primera Carta Clicada", "");
                imagenClicada = ivInvocadoraActual;
            } else {
                Log.d("Segunda Carta Clicada", "");
                //Clicada Primero
                Bitmap bitmapClicadaPrimero = convertirDrawableABitmap(imagenClicada.getDrawable());
                String imagenClicadaPrimero = ConvertidorImagen.convertirBitmapAString(bitmapClicadaPrimero);
                int idCartaClicadaPrimero = 0;
                boolean clicadaPrimeroEsMazo = false;

                for (ImageView ivClicadaPrimero: ivMazo) {
                    if (imagenClicada == ivClicadaPrimero) {
                        int index = ivMazo.indexOf(ivClicadaPrimero);
                        idCartaClicadaPrimero = mazoEditado[index];
                        clicadaPrimeroEsMazo = true;
                    }
                }

                for (ImageView ivClicadaPrimero: ivDisponible) {
                    if (imagenClicada == ivClicadaPrimero) {
                        int index = ivDisponible.indexOf(ivClicadaPrimero);
                        idCartaClicadaPrimero = idCartasDisponible[index];
                    }
                }

                //Clicada Actual
                Bitmap bitmapClicadaActual = convertirDrawableABitmap(ivInvocadoraActual.getDrawable());
                String imagenClicadaActual = ConvertidorImagen.convertirBitmapAString(bitmapClicadaActual);
                int idCartaClicadaActual = 0;
                boolean clicadaActualEsMazo = false;

                for (ImageView ivClicadaActual: ivMazo) {
                    if (ivInvocadoraActual == ivClicadaActual) {
                        int index = ivMazo.indexOf(ivClicadaActual);
                        idCartaClicadaActual = mazoEditado[index];
                        clicadaActualEsMazo = true;
                    }
                }

                for (ImageView ivClicadaActual: ivDisponible) {
                    if (ivInvocadoraActual == ivClicadaActual) {
                        int index = ivDisponible.indexOf(ivClicadaActual);
                        idCartaClicadaActual = idCartasDisponible[index];
                    }
                }

                //Evaluar
                if ((clicadaPrimeroEsMazo && clicadaActualEsMazo) || (!clicadaPrimeroEsMazo && !clicadaActualEsMazo)) {
                    Log.d("Entro en el IF", "");
                    imagenClicada = null;
                } else {
                    if (clicadaPrimeroEsMazo) {
                        for (int i = 0; i < mazoEditado.length; i++) {
                            if (idCartaClicadaPrimero == mazoEditado[i]) {
                                mazoEditado[i] = idCartaClicadaActual;
                            }
                        }

                    } else if (clicadaActualEsMazo) {
                        for (int i = 0; i < mazoEditado.length; i++) {
                            if (idCartaClicadaActual == mazoEditado[i]) {
                                mazoEditado[i] = idCartaClicadaPrimero;
                            }
                        }
                    }
                    ivInvocadoraActual.setImageBitmap(bitmapClicadaPrimero);
                    imagenClicada.setImageBitmap(bitmapClicadaActual);
                    imagenClicada = null;
                    Log.d("Mazo Editado", mazoEditado[0]+" "+mazoEditado[1]+" "+mazoEditado[2]+" "+mazoEditado[3]);
                }
            }
        }
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
                idCartasDisponible[contadorIVDisponible] = carta.getIdCarta();
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