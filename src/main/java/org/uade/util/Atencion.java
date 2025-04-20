package org.uade.util;

import java.time.LocalDateTime;

public class Atencion {
    private Paciente paciente;
    private Medico medico;
    private LocalDateTime inicioAtencion;
    private LocalDateTime finAtencion;
    private int id; // Nuevo atributo para el ID único
    private static int contadorId = 0; // Contador estático para generar IDs únicos

    public Atencion(Paciente paciente, Medico medico) {
        this.paciente = paciente;
        this.medico = medico;
        this.inicioAtencion = LocalDateTime.now();
        this.finAtencion = null;
        this.id = ++contadorId; // Asignar un ID único al crear la atención
    }

    public void finalizarAtencion() {
        this.finAtencion = LocalDateTime.now();
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public LocalDateTime getInicioAtencion() {
        return inicioAtencion;
    }

    public LocalDateTime getFinAtencion() {
        return finAtencion;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Atención [id=" + id + ", paciente=" + paciente.getNombre() + ", médico=" + medico.getNombre() + ", inicio=" + inicioAtencion + ", fin=" + finAtencion + "]";
    }
}