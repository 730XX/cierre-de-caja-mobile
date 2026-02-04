package com.example.mycaja.model;

/**
 * Modelo para los datos de impuestos
 */
public class DatosImpuestos {
    private String igv;
    private String icbper;

    public DatosImpuestos(String igv, String icbper) {
        this.igv = igv;
        this.icbper = icbper;
    }

    public String getIgv() {
        return igv;
    }

    public void setIgv(String igv) {
        this.igv = igv;
    }

    public String getIcbper() {
        return icbper;
    }

    public void setIcbper(String icbper) {
        this.icbper = icbper;
    }
}
