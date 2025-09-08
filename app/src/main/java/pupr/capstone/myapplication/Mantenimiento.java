package pupr.capstone.myapplication;

import android.graphics.Bitmap;

public class Mantenimiento {
    private String tipo;
    private Bitmap imagenBitmap;

    public Mantenimiento(String tipo, Bitmap imagenBitmap) {
        this.tipo = tipo;
        this.imagenBitmap = imagenBitmap;
    }

    public String getTipo() {
        return tipo;
    }

    public Bitmap getImagenBitmap() {
        return imagenBitmap;
    }
}
