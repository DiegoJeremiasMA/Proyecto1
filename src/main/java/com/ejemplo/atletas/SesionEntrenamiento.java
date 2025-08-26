package com.ejemplo.atletas;

public class SesionEntrenamiento {
    private String fecha;
    private double marca; // Puede ser tiempo en segundos, peso levantado, etc.

    // CONSTRUCTOR MODIFICADO (sin el parámetro 'tipo')
    public SesionEntrenamiento(String fecha, double marca) {
        this.fecha = fecha;
        this.marca = marca;
    }

    // Getters y Setters (ya no están los de 'tipo')
    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double getMarca() {
        return marca;
    }

    public void setMarca(double marca) {
        this.marca = marca;
    }
}