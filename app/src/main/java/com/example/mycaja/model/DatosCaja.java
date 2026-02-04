package com.example.mycaja.model;

/**
 * Modelo para los datos generales de la caja (header principal)
 */
public class DatosCaja {
    private String usuario;
    private String apertura;
    private String turno;
    private String fechaApertura;
    private String totalCaja;

    public DatosCaja(String usuario, String apertura, String turno, String fechaApertura, String totalCaja) {
        this.usuario = usuario;
        this.apertura = apertura;
        this.turno = turno;
        this.fechaApertura = fechaApertura;
        this.totalCaja = totalCaja;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getApertura() {
        return apertura;
    }

    public void setApertura(String apertura) {
        this.apertura = apertura;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(String fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    public String getTotalCaja() {
        return totalCaja;
    }

    public void setTotalCaja(String totalCaja) {
        this.totalCaja = totalCaja;
    }
}
