package org.uade.util;

import java.time.LocalDateTime;

public class Atencion {
    private Paciente paciente;
    private Medico medico;
    private LocalDateTime inicioAtencion;
    private LocalDateTime finAtencion;

    public Atencion(Paciente paciente, Medico medico) {
        this.paciente = paciente;
        this.medico = medico;
        this.inicioAtencion = LocalDateTime.now();
        this.finAtencion = null;
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

    @Override
    public String toString() {
        return "Atención [paciente=" + paciente.getNombre() + ", médico=" + medico.getNombre() + ", inicio=" + inicioAtencion + ", fin=" + finAtencion + "]";
    }
}