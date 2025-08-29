package com.ejemplo.atletas;

public enum Disciplina {
    ATLETISMO("Atletismo", "metros"),
    NATACION("Natación", "segundos"),
    LEVANTAMIENTO_PESAS("Levantamiento de Pesas", "kg"),
    CICLISMO("Ciclismo", "segundos"),
    BOXEO("Boxeo", "puntos");

    private final String nombreMostrado;
    private final String unidad;

    Disciplina(String nombreMostrado, String unidad) {
        this.nombreMostrado = nombreMostrado;
        this.unidad = unidad;
    }

    public String getNombreMostrado() {
        return nombreMostrado;
    }

    public String getUnidad() {
        return unidad;
    }

    public boolean menorEsMejor() {
        return this == NATACION || this == CICLISMO;
    }
}