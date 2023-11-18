package com.mbappesfeitactics.DAO;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConexionAPI {
    private static final String BASE_URL = "https://mk2m8b3x-3000.usw3.devtunnels.ms";
    private static final String TAG = "ConexionApi";

    private static ConexionAPI instance;
    private HttpURLConnection urlConnection;

    private ConexionAPI() {
        try {
            URL url = new URL(BASE_URL);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(5000); // Timeout de conexión en milisegundos
            urlConnection.setReadTimeout(5000);    // Timeout de lectura en milisegundos
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized ConexionAPI getInstance() {
        if (instance == null) {
            instance = new ConexionAPI();
        }
        return instance;
    }

    public HttpURLConnection getConnection() {
        return urlConnection;
    }

    public void closeConnection() {
        if (urlConnection != null) {
            urlConnection.disconnect();
        }
    }

    public String buildApiUrl(String apiEndpoint) {

        String baseURL = BASE_URL.endsWith("/") ? BASE_URL.substring(0, BASE_URL.length() - 1) : BASE_URL;
        String apiEndpointNoSlash = apiEndpoint.startsWith("/") ? apiEndpoint.substring(1) : apiEndpoint;
        // Combina la URL base y la parte específica del API
        return baseURL + "/" + apiEndpointNoSlash;
    }
}