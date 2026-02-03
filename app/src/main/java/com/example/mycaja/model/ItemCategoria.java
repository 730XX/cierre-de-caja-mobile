package com.example.mycaja.model;

public class ItemCategoria {
    private String nombre;
    private String monto;
    private float porcentaje;
    private int color;

    public ItemCategoria(String nombre, String monto, float porcentaje, int color) {
        this.nombre = nombre;
        this.monto = monto;
        this.porcentaje = porcentaje;
        this.color = color;
    }

    public String getNombre() {
        return nombre;
    }

    public String getMonto() {
        return monto;
    }

    public float getPorcentaje() {
        return porcentaje;
    }

    public int getColor() {
        return color;
    }
}
