package org.uade.util;

public class Paciente {
    private String nombre;
    private int nivelUrgencia; // Podemos usar enteros: 1 (Alta), 2 (Media), 3 (Baja)
    private int id; // Identificador único del paciente
    private static int contadorId = 0;

    public Paciente(String nombre, String urgencia) {
        this.nombre = nombre;
        this.nivelUrgencia = convertirUrgencia(urgencia);
        this.id = ++contadorId;
    }

    private int convertirUrgencia(String urgencia) {
        return switch (urgencia.toUpperCase()) {
            case "ALTA" -> 1;
            case "MEDIA" -> 2;
            case "BAJA" -> 3;
            default -> throw new IllegalArgumentException("Nivel de urgencia inválido: " + urgencia);
        };
    }

    public String getNombre() {
        return nombre;
    }

    public int getNivelUrgencia() {
        return nivelUrgencia;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Paciente [id=" + id + ", nombre=" + nombre + ", urgencia=" + getNivelUrgenciaTexto() + "]";
    }

    public String getNivelUrgenciaTexto() {
        return switch (nivelUrgencia) {
            case 1 -> "Alta";
            case 2 -> "Media";
            case 3 -> "Baja";
            default -> "Desconocida";
        };
    }
}