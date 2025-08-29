package com.ejemplo.atletas;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Estadistica {

    public static double calcularPromedio(List<SesionEntrenamiento> sesiones) {
        if (sesiones == null || sesiones.isEmpty()) {
            return 0.0;
        }
        double sumaMarcas = 0;
        for (SesionEntrenamiento sesion : sesiones) {
            sumaMarcas += sesion.getMarca();
        }
        return sumaMarcas / sesiones.size();
    }

    public static double encontrarMejorMarca(List<SesionEntrenamiento> sesiones, Disciplina disciplina) {
        if (sesiones == null || sesiones.isEmpty()) {
            return 0.0;
        }

        if (disciplina.menorEsMejor()) {
            return sesiones.stream()
                    .min(Comparator.comparing(SesionEntrenamiento::getMarca))
                    .get()
                    .getMarca();
        } else {
            return sesiones.stream()
                    .max(Comparator.comparing(SesionEntrenamiento::getMarca))
                    .get()
                    .getMarca();
        }
    }

    public static List<SesionEntrenamiento> obtenerEvolucion(List<SesionEntrenamiento> sesiones) {
        if (sesiones == null) {
            return new ArrayList<>();
        }
        return sesiones.stream()
                .sorted(Comparator.comparing(SesionEntrenamiento::getFecha))
                .collect(Collectors.toList());
    }
}