package com.example.mycaja.model;

/**
 * Modelo para el resumen de efectivo y tarjetas
 */
public class ResumenPagos {
    private String fechaResumen;
    private String enEfectivo;
    private String enTarjetas;
    private String totalCaja;

    public ResumenPagos(String fechaResumen, String enEfectivo, String enTarjetas, String totalCaja) {
        this.fechaResumen = fechaResumen;
        this.enEfectivo = enEfectivo;
        this.enTarjetas = enTarjetas;
        this.totalCaja = totalCaja;
    }

    public String getFechaResumen() {
        return fechaResumen;
    }

    public void setFechaResumen(String fechaResumen) {
        this.fechaResumen = fechaResumen;
    }

    public String getEnEfectivo() {
        return enEfectivo;
    }

    public void setEnEfectivo(String enEfectivo) {
        this.enEfectivo = enEfectivo;
    }

    public String getEnTarjetas() {
        return enTarjetas;
    }

    public void setEnTarjetas(String enTarjetas) {
        this.enTarjetas = enTarjetas;
    }

    public String getTotalCaja() {
        return totalCaja;
    }

    public void setTotalCaja(String totalCaja) {
        this.totalCaja = totalCaja;
    }
}
