package org.uade.util;

public class Medico {
    private int medicoID;
    private String nombre;
    private boolean disponible;
    private static int contadorId = 0;

    public Medico(String nombre) {
        this.medicoID = ++contadorId;
        this.nombre = nombre;
        this.disponible = true;
    }

    public int getId() {
        return medicoID;
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
        return "Médico [id=" + medicoID + ", nombre=" + nombre + ", disponible=" + disponible + "]";
    }
}