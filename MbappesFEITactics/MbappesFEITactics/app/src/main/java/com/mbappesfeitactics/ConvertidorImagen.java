package com.mbappesfeitactics;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import android.util.Base64;

public class ConvertidorImagen {

    public static Bitmap convertirStringABitmap(String imagenBase64) {
        try {
            // Decodificar la cadena Base64 a bytes
            byte[] bytesImagen = Base64.decode(imagenBase64, Base64.DEFAULT);

            // Crear un InputStream desde los bytes de la imagen
            InputStream inputStream = new ByteArrayInputStream(bytesImagen);

            // Utilizar BitmapFactory para crear un Bitmap desde el InputStream
            return BitmapFactory.decodeStream(inputStream);
        } catch (Exception e) {
            Log.e("ConvertidorImagen", "Error al convertir la imagen a Bitmap: " + e.getMessage());
            return null;
        }
    }

    public static String convertirBitmapAString(Bitmap bitmap) {
        try {
            // Comprimir el bitmap a un formato de byte array de JPEG
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

            // Codificar el byte array a Base64
            byte[] bytesImagen = byteArrayOutputStream.toByteArray();
            String imagenBase64 = Base64.encodeToString(bytesImagen, Base64.DEFAULT);

            return imagenBase64;
        } catch (Exception e) {
            Log.e("ConvertidorImagen", "Error al convertir el Bitmap a String: " + e.getMessage());
            return null;
        }
    }
}
