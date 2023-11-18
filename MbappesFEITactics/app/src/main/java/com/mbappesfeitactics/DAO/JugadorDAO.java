package com.mbappesfeitactics.DAO;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mbappesfeitactics.POJO.Jugador;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class JugadorDAO {
    public static Jugador inciarSesion(String gamertag, String password) {

        //Creación del objeto json para mandar

        Log.d("Paso1", "Creación Json");
        JsonObject jsonBody = new JsonObject();
        jsonBody.addProperty("gamertag", gamertag);
        jsonBody.addProperty("password", password);

        Log.d("Paso2", "Json Creado Correcto");

        //Conexión con el API
        Log.d("Paso3", "Comienzo conexion api");
        ConexionAPI conexionApi = ConexionAPI.getInstance();
        conexionApi.buildApiUrl("jugador/iniciarsesion");
        HttpURLConnection conexionAceptada = conexionApi.getConnection();

        Log.d("Paso4", "Conexión completada");

        try {
            // Configurar la conexión para una solicitud POST
            Log.d("Paso5", "Inicio POST");
            conexionAceptada.setRequestMethod("POST");
            conexionAceptada.setRequestProperty("Content-Type", "application/json");
            conexionAceptada.setDoOutput(true);

            Log.d("Paso5", "POST completado");

            // Enviar el JSON en el cuerpo de la solicitud

            Log.d("Paso6", "envío POST");
            try (OutputStream os = conexionAceptada.getOutputStream()) {
                byte[] input = jsonBody.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            Log.d("Paso6", "POST enviado");

            // Obtener la respuesta del servidor

            Log.d("Paso7", "Esperando Respuesta");

            int responseCode = conexionAceptada.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {


                // La solicitud fue exitosa, procesar la respuesta
                try (BufferedReader br = new BufferedReader(new InputStreamReader(conexionAceptada.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    // Convertir la respuesta JSON a un objeto Jugador usando Gson
                    Gson gson = new Gson();
                    Jugador jugador = gson.fromJson(response.toString(), Jugador.class);
                    return  jugador;
                }
            } else {
                // La solicitud no fue exitosa, manejar el código de respuesta según sea necesario
                return null;
            }
        } catch (IOException e) {
            // Manejar excepciones de conexión
            e.printStackTrace();
        } finally {
            // Cerrar la conexión
            conexionApi.closeConnection();
        }
        return null;
    }
}