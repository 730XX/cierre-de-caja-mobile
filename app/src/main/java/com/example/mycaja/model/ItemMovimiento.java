package com.example.mycaja.model;

public class ItemMovimiento {
    private String concepto;
    private String cantidad; // Columna del medio (opcional)
    private String monto;

    // Constructor para 2 columnas
    public ItemMovimiento(String concepto, String monto) {
        this.concepto = concepto;
        this.cantidad = null;
        this.monto = monto;
    }

    // Constructor para 3 columnas
    public ItemMovimiento(String concepto, String cantidad, String monto) {
        this.concepto = concepto;
        this.cantidad = cantidad;
        this.monto = monto;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public boolean tieneCantidad() {
        return cantidad != null && !cantidad.isEmpty();
    }
}
