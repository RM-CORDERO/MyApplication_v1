package pupr.capstone.myapplication;

import android.graphics.Bitmap;

public class Auto {
    private String marca, modelo;
    private String tablilla;
    private Bitmap imagenBitmap;

    public Auto(String marca, String modelo, String tablilla, Bitmap imagenBitmap) {
        this.marca = marca;
        this.modelo = modelo;
        this.tablilla = tablilla;
        this.imagenBitmap = imagenBitmap;
    }

    public String getMarca() { return marca; }
    public String getModelo() { return modelo; }
    public String getTablilla() { return tablilla; }
    public Bitmap getImagenBitmap() { return imagenBitmap; }
}
