package com.mbappesfeitactics.DAO;

import com.mbappesfeitactics.POJO.Jugador;


import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface JugadorService {
    @POST("jugador/iniciarsesion")
    Call<Jugador> loginUser(@Body Map<String, String> credentials);

    @POST("jugador/registrarjugador")
    Call<Jugador> createUser(@Body Map<String, Object> credentials);

    @PUT("/jugador/modificarmazo")
    Call<Jugador> editarMazo(@Body Map<String, String> datosEditar);

    @PUT("/jugador/modificarimagenperfil")
    Call<Jugador> editarFotoPerfil(@Body Map<String, Object> datosEditar);

    @POST("/jugador/recuperaroponente")
    Call<Jugador> recuperarOponente(@Body Map<String, String> gamertag);
}