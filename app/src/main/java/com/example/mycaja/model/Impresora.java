package com.example.mycaja.model;

public class Impresora {
    public String nombre;
    public String modelo;
    public String tipoDoc; // Boleta, Factura, Comanda
    public String estado;  // Vinculado, Desconectado
    public boolean esBluetooth; // Para decidir qué icono mostrar

    public Impresora(String nombre, String modelo, String tipoDoc, String estado, boolean esBluetooth) {
        this.nombre = nombre;
        this.modelo = modelo;
        this.tipoDoc = tipoDoc;
        this.estado = estado;
        this.esBluetooth = esBluetooth;
    }
}