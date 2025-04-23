package org.uade.util;

import java.time.LocalDateTime;

public class Atencion {
    private Paciente paciente;
    private Medico medico;
    private LocalDateTime inicioAtencion;
    private LocalDateTime finAtencion;
    private int atencionID;
    private static int contadorId = 0;

    public Atencion(Paciente paciente, Medico medico) {
        this.paciente = paciente;
        this.medico = medico;
        this.inicioAtencion = LocalDateTime.now();
        this.finAtencion = null;
        this.atencionID = ++contadorId;
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
        return atencionID;
    }

    @Override
    public String toString() {
        return "Atención [id=" + atencionID + ", paciente=" + paciente.getNombre() + ", médico=" + medico.getNombre() + ", inicio=" + inicioAtencion + ", fin=" + finAtencion + "]";
    }
}