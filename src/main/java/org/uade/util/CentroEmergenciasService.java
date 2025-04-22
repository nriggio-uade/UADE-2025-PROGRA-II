package org.uade.util;

import org.uade.structure.implementation.dynamic.DynamicLinkedList;
import org.uade.structure.implementation.dynamic.DynamicPriorityQueue;


public class CentroEmergenciasService {
    private DynamicPriorityQueue pacientesEnEspera;
    private DynamicLinkedList medicosDisponiblesIds;
    private DynamicLinkedList pacientesAtendidosIds;
    private Atencion[] historialAtenciones;
    private int contadorHistorial = 0;
    private static final int MAX_HISTORIAL = 100;
    private DynamicLinkedList listaPacientesIds; // Lista de IDs de pacientes
    private DynamicLinkedList listaMedicosIds;   // Lista de IDs de médicos
    private Paciente[] listaPacientesObjetos; // Array para los objetos Paciente
    private int contadorPacientes = 0;
    private Medico[] listaMedicosObjetos;   // Array para los objetos Medico
    private int contadorMedicos = 0;
    private static final int MAX_PACIENTES = 100; // Tamaños máximos (ajustables)
    private static final int MAX_MEDICOS = 20;

    public CentroEmergenciasService(int numMedicosIniciales) {
        this.pacientesEnEspera = new DynamicPriorityQueue();
        this.medicosDisponiblesIds = new DynamicLinkedList();
        this.pacientesAtendidosIds = new DynamicLinkedList();
        this.historialAtenciones = new Atencion[MAX_HISTORIAL];
        this.listaPacientesIds = new DynamicLinkedList();
        this.listaMedicosIds = new DynamicLinkedList();
        this.listaPacientesObjetos = new Paciente[MAX_PACIENTES];
        this.listaMedicosObjetos = new Medico[MAX_MEDICOS];
        inicializarMedicos(numMedicosIniciales);
    }

    private void inicializarMedicos(int numMedicos) {
        for (int i = 1; i <= numMedicos; i++) {
            Medico medico = new Medico("Médico " + i);
            if (contadorMedicos < MAX_MEDICOS) {
                listaMedicosObjetos[contadorMedicos] = medico;
                listaMedicosIds.add(medico.getId());
                contadorMedicos++;
            } else {
                System.out.println("Se ha alcanzado el número máximo de médicos.");
                break;
            }
            medicosDisponiblesIds.add(medico.getId());
        }
    }

    public void registrarPaciente(String nombre, String urgencia) {
        Paciente paciente = new Paciente(nombre, urgencia);
        if (contadorPacientes < MAX_PACIENTES) {
            listaPacientesObjetos[contadorPacientes] = paciente;
            listaPacientesIds.add(paciente.getId());
            contadorPacientes++;
            pacientesEnEspera.add(paciente.getId(), paciente.getNivelUrgencia());
            System.out.println("Paciente registrado: " + paciente);
        } else {
            System.out.println("Se ha alcanzado el número máximo de pacientes.");
        }
    }

    public void darDeAltaMedico(String nombre) {
        Medico nuevoMedico = new Medico(nombre);
        if (contadorMedicos < MAX_MEDICOS) {
            listaMedicosObjetos[contadorMedicos] = nuevoMedico;
            listaMedicosIds.add(nuevoMedico.getId());
            contadorMedicos++;
            medicosDisponiblesIds.add(nuevoMedico.getId());
            System.out.println("Médico dado de alta: " + nuevoMedico);
        } else {
            System.out.println("Se ha alcanzado el número máximo de médicos.");
        }
    }

    public void asignarMedico() {
        if (!pacientesEnEspera.isEmpty() && medicosDisponiblesIds.size() > 0) {
            int pacienteId = pacientesEnEspera.getElement();
            pacientesEnEspera.remove();

            int medicoId = medicosDisponiblesIds.get(0);
            medicosDisponiblesIds.remove(0);

            Paciente paciente = buscarPacientePorId(pacienteId);
            Medico medico = buscarMedicoPorId(medicoId);

            if (paciente != null && medico != null) {
                Atencion atencion = new Atencion(paciente, medico);
                System.out.println("Asignando a " + medico.getNombre() + " al paciente " + paciente.getNombre());
                atenderPaciente(atencion);
            }
        } else if (pacientesEnEspera.isEmpty()) {
            System.out.println("No hay pacientes en espera.");
        } else {
            System.out.println("No hay médicos disponibles.");
        }
    }

    private Paciente buscarPacientePorId(int id) {
        for (int i = 0; i < contadorPacientes; i++) {
            if (listaPacientesObjetos[i] != null && listaPacientesObjetos[i].getId() == id) {
                return listaPacientesObjetos[i];
            }
        }
        return null;
    }

    private Medico buscarMedicoPorId(int id) {
        for (int i = 0; i < contadorMedicos; i++) {
            if (listaMedicosObjetos[i] != null && listaMedicosObjetos[i].getId() == id) {
                return listaMedicosObjetos[i];
            }
        }
        return null;
    }

    public void atenderPaciente(Atencion atencion) {
        System.out.println("Iniciando atención a " + atencion.getPaciente().getNombre() + " por " + atencion.getMedico().getNombre());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        atencion.finalizarAtencion();
        if (contadorHistorial < MAX_HISTORIAL) {
            historialAtenciones[contadorHistorial++] = atencion;
            pacientesAtendidosIds.add(atencion.getId());
        } else {
            System.out.println("Historial de atenciones lleno. No se puede registrar la atención.");
        }
        Medico medico = atencion.getMedico();
        medico.setDisponible(true);
        medicosDisponiblesIds.add(medico.getId());
        System.out.println("Atención finalizada para " + atencion.getPaciente().getNombre() + ". " + atencion.getMedico().getNombre() + " está disponible.");
    }

    public void mostrarPacientesPendientes() {
        System.out.println("Pacientes pendientes de atención (" + pacientesEnEspera.getElement() + "):");
        DynamicPriorityQueue tempQueue = new DynamicPriorityQueue();
        while (!pacientesEnEspera.isEmpty()) {
            int pacienteId = pacientesEnEspera.getElement();
            int prioridad = pacientesEnEspera.getPriority();
            Paciente paciente = buscarPacientePorId(pacienteId);
            if (paciente != null) {
                System.out.println("- " + paciente);
            }
            tempQueue.add(pacienteId, prioridad);
            pacientesEnEspera.remove();
        }
        while (!tempQueue.isEmpty()) {
            pacientesEnEspera.add(tempQueue.getElement(), tempQueue.getPriority());
            tempQueue.remove();
        }
    }

    public void generarReportes() {
        mostrarPacientesPendientes();
        System.out.println("\nPacientes atendidos (" + pacientesAtendidosIds.size() + "):");
        for (int i = 0; i < pacientesAtendidosIds.size(); i++) {
            int atencionId = pacientesAtendidosIds.get(i);
            Atencion atencion = buscarAtencionPorId(atencionId);
            if (atencion != null) {
                System.out.println("- " + atencion);
            } else {
                System.out.println("- Atención no encontrada (ID: " + atencionId + ")");
            }
        }
    }

    private Atencion buscarAtencionPorId(int id) {
        for (int i = 0; i < contadorHistorial; i++) {
            if (historialAtenciones[i] != null && historialAtenciones[i].getId() == id) {
                return historialAtenciones[i];
            }
        }
        return null;
    }
}
