package com.mbappesfeitactics.Vista;

import com.mbappesfeitactics.POJO.Carta;
import com.mbappesfeitactics.POJO.FotosPerfil;
import com.mbappesfeitactics.POJO.Jugador;

import java.util.List;

public class RecursosCompartidosViewModel {

    private static RecursosCompartidosViewModel instanciaSingleton;

    private List<Carta> cartas;
    private List<FotosPerfil> fotosPerfil;
    private Jugador jugador;

    // Constructor privado para evitar instanciación directa
    private RecursosCompartidosViewModel() {
        // Inicializa tus listas u otras configuraciones necesarias aquí
        cartas = null; // Reemplaza null con la inicialización adecuada
        fotosPerfil = null; // Reemplaza null con la inicialización adecuada
        jugador = new Jugador(); // Inicializa el jugador, puedes personalizar esto según tus necesidades
    }

    // Método para obtener la instancia única del singleton
    public static RecursosCompartidosViewModel obtenerInstancia() {
        if (instanciaSingleton == null) {
            instanciaSingleton = new RecursosCompartidosViewModel();
        }
        return instanciaSingleton;
    }

    // Getters y setters para cartas
    public List<Carta> getCartas() {
        return cartas;
    }

    public void setCartas(List<Carta> cartas) {
        this.cartas = cartas;
    }

    // Getters y setters para fotosPerfil
    public List<FotosPerfil> getFotosPerfil() {
        return fotosPerfil;
    }

    public void setFotosPerfil(List<FotosPerfil> fotosPerfil) {
        this.fotosPerfil = fotosPerfil;
    }

    // Getters y setters para jugador
    public Jugador getJugador() {
        return jugador;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }
}
