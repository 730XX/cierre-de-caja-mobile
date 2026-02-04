package com.example.mycaja.model;

/**
 * Modelo para los datos de atenciones
 */
public class DatosAtenciones {
    private String mesasAtendidas;
    private String personasAtendidas;

    public DatosAtenciones(String mesasAtendidas, String personasAtendidas) {
        this.mesasAtendidas = mesasAtendidas;
        this.personasAtendidas = personasAtendidas;
    }

    public String getMesasAtendidas() {
        return mesasAtendidas;
    }

    public void setMesasAtendidas(String mesasAtendidas) {
        this.mesasAtendidas = mesasAtendidas;
    }

    public String getPersonasAtendidas() {
        return personasAtendidas;
    }

    public void setPersonasAtendidas(String personasAtendidas) {
        this.personasAtendidas = personasAtendidas;
    }
}
