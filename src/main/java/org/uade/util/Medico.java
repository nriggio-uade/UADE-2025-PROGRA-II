package org.uade.util;

public class Medico {
    private int id;
    private String nombre;
    private boolean disponible;
    private static int contadorId = 0;

    public Medico(String nombre) {
        this.id = ++contadorId;
        this.nombre = nombre;
        this.disponible = true;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    @Override
    public String toString() {
        return "Médico [id=" + id + ", nombre=" + nombre + ", disponible=" + disponible + "]";
    }
}