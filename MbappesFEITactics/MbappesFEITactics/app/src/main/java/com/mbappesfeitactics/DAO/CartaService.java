package com.mbappesfeitactics.DAO;

import com.mbappesfeitactics.POJO.Carta;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CartaService {
    @GET("/carta/recuperarcartas")
    Call<ArrayList<Carta>> recuperarCartas();
}
