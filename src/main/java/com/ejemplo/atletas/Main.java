package com.ejemplo.atletas;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static List<Atleta> atletas = new ArrayList<>();
    private static final String NOMBRE_ARCHIVO = "atletas.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void main(String[] args) {
        cargarDatos();
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n--- Menú Principal ---");
            System.out.println("1. Registrar nuevo atleta");
            System.out.println("2. Registrar sesión de entrenamiento");
            System.out.println("3. Mostrar historial de un atleta");
            System.out.println("4. Calcular y mostrar estadísticas");
            System.out.println("5. Salir (y guardar)");

            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    registrarAtleta(scanner);
                    break;
                case 2:
                    registrarSesion(scanner);
                    break;
                case 3:
                    mostrarHistorial(scanner);
                    break;
                case 4:
                    mostrarEstadisticas(scanner);
                    break;
                case 5:
                    guardarDatos();
                    System.out.println("Datos guardados. ¡Adiós!");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 5);
    }

    private static void registrarAtleta(Scanner scanner) {
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Edad: ");
        int edad = scanner.nextInt();
        scanner.nextLine();

        Disciplina disciplinaSeleccionada = seleccionarDisciplina(scanner);

        System.out.print("Departamento: ");
        String departamento = scanner.nextLine();

        atletas.add(new Atleta(nombre, edad, disciplinaSeleccionada, departamento));
        System.out.println("Atleta registrado con éxito.");
    }

    private static Disciplina seleccionarDisciplina(Scanner scanner) {
        System.out.println("Seleccione la disciplina:");
        Disciplina[] disciplinas = Disciplina.values();
        for (int i = 0; i < disciplinas.length; i++) {
            System.out.println((i + 1) + ". " + disciplinas[i].getNombreMostrado());
        }

        int opcion = 0;
        while (opcion < 1 || opcion > disciplinas.length) {
            System.out.print("Opción: ");
            opcion = scanner.nextInt();
            if (opcion < 1 || opcion > disciplinas.length) {
                System.out.println("Opción no válida. Inténtelo de nuevo.");
            }
        }
        scanner.nextLine();
        return disciplinas[opcion - 1];
    }

    private static void registrarSesion(Scanner scanner) {
        Atleta atleta = seleccionarAtleta(scanner);
        if (atleta == null) return;

        System.out.println("Ingrese la fecha de la sesión:");
        System.out.print(" -> Día (DD): ");
        int dia = scanner.nextInt();
        System.out.print(" -> Mes (MM): ");
        int mes = scanner.nextInt();
        System.out.print(" -> Año (YYYY): ");
        int anio = scanner.nextInt();
        scanner.nextLine();

        String fecha = String.format("%04d-%02d-%02d", anio, mes, dia);

        String unidad = atleta.getDisciplina().getUnidad();
        System.out.print("Marca (" + unidad + "): ");
        double marca = scanner.nextDouble();
        scanner.nextLine();

        atleta.agregarSesion(new SesionEntrenamiento(fecha, marca));
        System.out.println("Sesión registrada con éxito.");
    }

    private static void mostrarHistorial(Scanner scanner) {
        Atleta atleta = seleccionarAtleta(scanner);
        if (atleta == null) return;

        String unidad = atleta.getDisciplina().getUnidad();
        System.out.println("\n--- Historial de " + atleta.getNombre() + " (" + atleta.getDisciplina().getNombreMostrado() + ") ---");
        if (atleta.getSesiones().isEmpty()) {
            System.out.println("No hay sesiones registradas.");
        } else {
            for (SesionEntrenamiento sesion : atleta.getSesiones()) {
                String fechaMostrada = formatearFechaParaMostrar(sesion.getFecha());
                System.out.println("Fecha: " + fechaMostrada + ", Marca: " + sesion.getMarca() + " " + unidad);
            }
        }
    }

    private static void mostrarEstadisticas(Scanner scanner) {
        Atleta atleta = seleccionarAtleta(scanner);
        if (atleta == null) return;

        List<SesionEntrenamiento> sesiones = atleta.getSesiones();
        if (sesiones.isEmpty()) {
            System.out.println("No hay datos suficientes para calcular estadísticas.");
            return;
        }

        String unidad = atleta.getDisciplina().getUnidad();
        System.out.println("\n--- Estadísticas para " + atleta.getNombre() + " (" + atleta.getDisciplina().getNombreMostrado() + ") ---");

        double promedio = Estadistica.calcularPromedio(sesiones);
        System.out.printf("Promedio de rendimiento: %.2f %s\n", promedio, unidad);

        double mejorMarca = Estadistica.encontrarMejorMarca(sesiones, atleta.getDisciplina());
        System.out.println("Mejor marca: " + mejorMarca + " " + unidad);

        System.out.println("Evolución (ordenado por fecha):");
        List<SesionEntrenamiento> evolucion = Estadistica.obtenerEvolucion(sesiones);
        for (SesionEntrenamiento sesion : evolucion) {
            String fechaMostrada = formatearFechaParaMostrar(sesion.getFecha());
            System.out.println(" -> Fecha: " + fechaMostrada + ", Marca: " + sesion.getMarca() + " " + unidad);
        }
    }

    private static Atleta seleccionarAtleta(Scanner scanner) {
        if (atletas.isEmpty()) {
            System.out.println("No hay atletas registrados.");
            return null;
        }

        System.out.println("Seleccione un atleta:");
        for (int i = 0; i < atletas.size(); i++) {
            System.out.println((i + 1) + ". " + atletas.get(i).getNombre());
        }

        System.out.print("Número del atleta: ");
        int indice = scanner.nextInt() - 1;
        scanner.nextLine();

        if (indice >= 0 && indice < atletas.size()) {
            return atletas.get(indice);
        } else {
            System.out.println("Selección no válida.");
            return null;
        }
    }

    private static void guardarDatos() {
        try (FileWriter writer = new FileWriter(NOMBRE_ARCHIVO)) {
            gson.toJson(atletas, writer);
        } catch (IOException e) {
            System.out.println("Error al guardar los datos: " + e.getMessage());
        }
    }

    private static void cargarDatos() {
        try (FileReader reader = new FileReader(NOMBRE_ARCHIVO)) {
            Type tipoListaAtletas = new TypeToken<ArrayList<Atleta>>() {}.getType();
            atletas = gson.fromJson(reader, tipoListaAtletas);
            if (atletas == null) {
                atletas = new ArrayList<>();
            }
            System.out.println("Datos cargados correctamente.");
        } catch (IOException e) {
            System.out.println("No se encontró un archivo de datos, se iniciará con una lista vacía.");
        }
    }

    private static String formatearFechaParaMostrar(String fechaISO) {
        if (fechaISO == null || fechaISO.length() != 10) {
            return fechaISO;
        }
        String[] partes = fechaISO.split("-");
        if (partes.length != 3) {
            return fechaISO;
        }
        return partes[2] + "-" + partes[1] + "-" + partes[0];
    }
}