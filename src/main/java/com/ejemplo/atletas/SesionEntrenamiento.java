package com.ejemplo.atletas;

public class SesionEntrenamiento {
    private final String fecha;
    private final double marca;

    public SesionEntrenamiento(String fecha, double marca) {
        this.fecha = fecha;
        this.marca = marca;
    }

    public String getFecha() {
        return fecha;
    }

    public double getMarca() {
        return marca;
    }

}