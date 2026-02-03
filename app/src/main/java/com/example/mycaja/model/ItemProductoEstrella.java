package com.example.mycaja.model;

public class ItemProductoEstrella {
    private int ranking;
    private String letraInicial;
    private String nombreProducto;
    private String categoria;
    private String ventas;
    private String porcentaje;

    public ItemProductoEstrella(int ranking, String letraInicial, String nombreProducto, String categoria, String ventas, String porcentaje) {
        this.ranking = ranking;
        this.letraInicial = letraInicial;
        this.nombreProducto = nombreProducto;
        this.categoria = categoria;
        this.ventas = ventas;
        this.porcentaje = porcentaje;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public String getLetraInicial() {
        return letraInicial;
    }

    public void setLetraInicial(String letraInicial) {
        this.letraInicial = letraInicial;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getVentas() {
        return ventas;
    }

    public void setVentas(String ventas) {
        this.ventas = ventas;
    }

    public String getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(String porcentaje) {
        this.porcentaje = porcentaje;
    }
}
