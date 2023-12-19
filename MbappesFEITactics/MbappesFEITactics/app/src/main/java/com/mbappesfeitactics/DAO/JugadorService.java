package com.mbappesfeitactics.DAO;

import com.mbappesfeitactics.POJO.Jugador;


import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface JugadorService {
    @POST("jugador/iniciarsesion")
    Call<Jugador> loginUser(@Body Map<String, String> credentials);

    @POST("jugador/registrarjugador")
    Call<Jugador> createUser(@Body Map<String, Object> credentials);

}