package com.mbappesfeitactics;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

public class ConvertidorImagen {

    public static Bitmap convertirStringABitmap(String imagenBase64) {
        try {
            // Decodificar la cadena Base64 a bytes
            byte[] bytesImagen = android.util.Base64.decode(imagenBase64, android.util.Base64.DEFAULT);

            // Crear un InputStream desde los bytes de la imagen
            InputStream inputStream = new ByteArrayInputStream(bytesImagen);

            // Utilizar BitmapFactory para crear un Bitmap desde el InputStream
            return BitmapFactory.decodeStream(inputStream);
        } catch (Exception e) {
            Log.e("ConvertidorImagen", "Error al convertir la imagen a Bitmap: " + e.getMessage());
            return null;
        }
    }
}
