package com.example.mycaja.model;

public class ItemTablaFila {
    private String col1Line1;
    private String col1Line2; // Segunda línea opcional (ej: hora)
    private String col2;
    private String col3;
    private String col4;
    private String col5; // Quinta columna opcional
    private int numColumnas = 4; // Por defecto 4 columnas
    private boolean noBorder = false; // Sin borde por defecto

    // Constructor para 2 columnas simples con opción de sin borde
    public ItemTablaFila(String col1, String col2, boolean noBorder) {
        this.col1Line1 = col1;
        this.col1Line2 = null;
        this.col2 = col2;
        this.col3 = null;
        this.col4 = null;
        this.col5 = null;
        this.numColumnas = 2;
        this.noBorder = noBorder;
    }

    // Constructor para 3 columnas simples
    public ItemTablaFila(String col1, String col2, String col3) {
        this.col1Line1 = col1;
        this.col1Line2 = null;
        this.col2 = col2;
        this.col3 = col3;
        this.col4 = null;
        this.col5 = null;
        this.numColumnas = 3;
    }

    // Constructor para 3 columnas simples con opción de sin borde
    public ItemTablaFila(String col1, String col2, String col3, boolean noBorder) {
        this.col1Line1 = col1;
        this.col1Line2 = null;
        this.col2 = col2;
        this.col3 = col3;
        this.col4 = null;
        this.col5 = null;
        this.numColumnas = 3;
        this.noBorder = noBorder;
    }

    // Constructor para 4 columnas simples (sin fecha)
    public ItemTablaFila(String col1, String col2, String col3, String col4) {
        this.col1Line1 = col1;
        this.col1Line2 = null;
        this.col2 = col2;
        this.col3 = col3;
        this.col4 = col4;
        this.col5 = null;
        this.numColumnas = 44; // 44 = 4 columnas simples
    }

    // Constructor para 4 columnas simples con opción de sin borde
    public ItemTablaFila(String col1, String col2, String col3, String col4, boolean noBorder) {
        this.col1Line1 = col1;
        this.col1Line2 = null;
        this.col2 = col2;
        this.col3 = col3;
        this.col4 = col4;
        this.col5 = null;
        this.numColumnas = 44;
        this.noBorder = noBorder;
    }

    // Constructor con 2 líneas en la primera columna (4 columnas con fecha)
    public ItemTablaFila(String col1Line1, String col1Line2, String col2, String col3, String col4) {
        this.col1Line1 = col1Line1;
        this.col1Line2 = col1Line2;
        this.col2 = col2;
        this.col3 = col3;
        this.col4 = col4;
        this.col5 = null;
        this.numColumnas = 4; // 4 = 4 columnas con fecha
    }

    // Constructor para 5 columnas simples
    public ItemTablaFila(String col1, String col2, String col3, String col4, String col5, boolean is5Columns) {
        this.col1Line1 = col1;
        this.col1Line2 = null;
        this.col2 = col2;
        this.col3 = col3;
        this.col4 = col4;
        this.col5 = col5;
        this.numColumnas = 5;
    }

    // Constructor con 2 líneas en la primera columna (5 columnas)
    public ItemTablaFila(String col1Line1, String col1Line2, String col2, String col3, String col4, String col5) {
        this.col1Line1 = col1Line1;
        this.col1Line2 = col1Line2;
        this.col2 = col2;
        this.col3 = col3;
        this.col4 = col4;
        this.col5 = col5;
        this.numColumnas = 5;
    }

    public boolean isNoBorder() {
        return noBorder;
    }

    public void setNoBorder(boolean noBorder) {
        this.noBorder = noBorder;
    }
    public String getCol1Line1() {
        return col1Line1;
    }

    public void setCol1Line1(String col1Line1) {
        this.col1Line1 = col1Line1;
    }

    public String getCol1Line2() {
        return col1Line2;
    }

    public void setCol1Line2(String col1Line2) {
        this.col1Line2 = col1Line2;
    }

    public String getCol2() {
        return col2;
    }

    public void setCol2(String col2) {
        this.col2 = col2;
    }

    public String getCol3() {
        return col3;
    }

    public void setCol3(String col3) {
        this.col3 = col3;
    }

    public String getCol4() {
        return col4;
    }

    public void setCol4(String col4) {
        this.col4 = col4;
    }

    public String getCol5() {
        return col5;
    }

    public void setCol5(String col5) {
        this.col5 = col5;
    }

    public int getNumColumnas() {
        return numColumnas;
    }

    public boolean tieneSegundaLinea() {
        return col1Line2 != null && !col1Line2.isEmpty();
    }
}
