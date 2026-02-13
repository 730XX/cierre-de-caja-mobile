package com.example.mycaja.model;

public class VentaPropina {
    private String tipoDoc;
    private String serieNumero;
    private String propina;
    private String comision;
    private String totalPropina;

    public VentaPropina(String numeroVenta, String montoPropina) {
        this.tipoDoc = numeroVenta;
        this.serieNumero = montoPropina;
    }

    public VentaPropina(String numeroVenta, String montoPropina, String propina, String comision, String totalPropina) {
        this.tipoDoc = numeroVenta;
        this.serieNumero = montoPropina;
        this.propina = propina;
        this.comision = comision;
        this.totalPropina= totalPropina;
    }

    public String getTipoDoc() {
        return tipoDoc;
    }

    public String getSerieNumero() {
        return serieNumero;
    }

    public void setSerieNumero(String serieNumero) {
        this.serieNumero = serieNumero;
    }

    public String getTotalPropina() {
        return totalPropina;
    }

    public void setTotalPropina(String totalPropina) {
        this.totalPropina = totalPropina;
    }

    public void setTipoDoc(String tipoDoc) {
        this.tipoDoc = tipoDoc;
    }

    public String getMontoPropina() {
        return serieNumero;
    }

    public void setMontoPropina(String montoPropina) {
        this.serieNumero = montoPropina;
    }

    public String getPropina() {
        return propina;
    }

    public void setPropina(String propina) {
        this.propina = propina;
    }

    public String getComision() {
        return comision;
    }

    public void setComision(String comision) {
        this.comision = comision;
    }
}
