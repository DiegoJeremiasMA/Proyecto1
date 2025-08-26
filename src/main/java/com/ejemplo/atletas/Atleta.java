package com.ejemplo.atletas;

import java.util.ArrayList;
import java.util.List;

public class Atleta {
    private String nombre;
    private int edad;
    private Disciplina disciplina; // MODIFICADO: Antes era String
    private String departamento;
    private List<SesionEntrenamiento> sesiones;

    // CONSTRUCTOR MODIFICADO
    public Atleta(String nombre, int edad, Disciplina disciplina, String departamento) {
        this.nombre = nombre;
        this.edad = edad;
        this.disciplina = disciplina; // MODIFICADO
        this.departamento = departamento;
        this.sesiones = new ArrayList<>();
    }

    // GETTER MODIFICADO
    public Disciplina getDisciplina() {
        return disciplina;
    }

    // ... los demás getters, setters y el método agregarSesion no cambian ...

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }
    public void setDisciplina(Disciplina disciplina) { this.disciplina = disciplina; }
    public String getDepartamento() { return departamento; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }
    public List<SesionEntrenamiento> getSesiones() { return sesiones; }
    public void agregarSesion(SesionEntrenamiento sesion) { this.sesiones.add(sesion); }
}