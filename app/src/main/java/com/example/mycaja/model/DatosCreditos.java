package com.example.mycaja.model;

/**
 * Modelo para los datos de créditos
 */
public class DatosCreditos {
    private String creditosGenerados;
    private String totalCreditos;
    private String valesPago;
    private String montoValesPago;

    public DatosCreditos(String creditosGenerados, String totalCreditos, String valesPago, String montoValesPago) {
        this.creditosGenerados = creditosGenerados;
        this.totalCreditos = totalCreditos;
        this.valesPago = valesPago;
        this.montoValesPago = montoValesPago;
    }

    public String getCreditosGenerados() {
        return creditosGenerados;
    }

    public void setCreditosGenerados(String creditosGenerados) {
        this.creditosGenerados = creditosGenerados;
    }

    public String getTotalCreditos() {
        return totalCreditos;
    }

    public void setTotalCreditos(String totalCreditos) {
        this.totalCreditos = totalCreditos;
    }

    public String getValesPago() {
        return valesPago;
    }

    public void setValesPago(String valesPago) {
        this.valesPago = valesPago;
    }

    public String getMontoValesPago() {
        return montoValesPago;
    }

    public void setMontoValesPago(String montoValesPago) {
        this.montoValesPago = montoValesPago;
    }
}
