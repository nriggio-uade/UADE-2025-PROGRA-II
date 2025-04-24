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
    private DynamicLinkedList listaPacientesIds;
    private DynamicLinkedList listaMedicosIds;
    private Paciente[] listaPacientesObjetos;
    private int contadorPacientes = 0;
    private Medico[] listaMedicosObjetos;
    private int contadorMedicos = 0;
    private static final int MAX_PACIENTES = 100;
    private static final int MAX_MEDICOS = 20;
    private DynamicLinkedList atencionesEnCursoIds = new DynamicLinkedList();
    private Atencion[] atencionesEnCursoObjetos = new Atencion[MAX_HISTORIAL];
    private int contadorAtencionesEnCurso = 0;

    public CentroEmergenciasService(int numMedicosIniciales) {
        this.pacientesEnEspera = new DynamicPriorityQueue();
        this.medicosDisponiblesIds = new DynamicLinkedList();
        this.pacientesAtendidosIds = new DynamicLinkedList();
        this.historialAtenciones = new Atencion[MAX_HISTORIAL];
        this.listaPacientesIds = new DynamicLinkedList();
        this.listaMedicosIds = new DynamicLinkedList();
        this.listaPacientesObjetos = new Paciente[MAX_PACIENTES];
        this.listaMedicosObjetos = new Medico[MAX_MEDICOS];
        this.atencionesEnCursoIds = new DynamicLinkedList();
        this.atencionesEnCursoObjetos = new Atencion[MAX_HISTORIAL];
        this.contadorAtencionesEnCurso = 0;
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
        // Siempre obtener el paciente con la mayor prioridad de la cola.
        Paciente pacientePrioritario = obtenerPacientePrioritario();
        Medico medicoDisponible = obtenerMedicoDisponible();

        if (pacientePrioritario != null && medicoDisponible != null) {
            crearAtencion(pacientePrioritario, medicoDisponible);
        } else if (pacientePrioritario != null) {
            // Si no hay médico disponible, re-encolar al paciente prioritario.
            pacientesEnEspera.add(pacientePrioritario.getId(), pacientePrioritario.getNivelUrgencia());
            System.out.println("No hay médicos disponibles en este momento. El paciente " + pacientePrioritario.getNombre() + " espera.");
        } else if (medicoDisponible != null) {
            System.out.println("No hay pacientes en espera para asignar al médico " + medicoDisponible.getNombre() + ".");
            // Si hay un médico disponible pero no pacientes, lo dejamos en la lista de disponibles.
            medicosDisponiblesIds.add(medicoDisponible.getId()); // Re-agregamos el ID del médico a la lista de disponibles.
        } else {
            System.out.println("No hay pacientes en espera ni médicos disponibles.");
        }
    }

    private Paciente buscarPacientePorId(int pacienteId) {
        for (int i = 0; i < contadorPacientes; i++) {
            if (listaPacientesObjetos[i] != null && listaPacientesObjetos[i].getId() == pacienteId) {
                return listaPacientesObjetos[i];
            }
        }
        return null;
    }

    private Medico buscarMedicoPorId(int medicoId) {
        for (int i = 0; i < contadorMedicos; i++) {
            if (listaMedicosObjetos[i] != null && listaMedicosObjetos[i].getId() == medicoId) {
                return listaMedicosObjetos[i];
            }
        }
        return null;
    }

    public void finalizarAtencion(int id) {
        Atencion atencion = buscarAtencionEnCursoPorId(id);
        if (atencion == null) {
            System.out.println("Atención no encontrada o ya finalizada.");
            return;
        }
        atencion.finalizarAtencion();
        if (contadorHistorial < MAX_HISTORIAL) {
            historialAtenciones[contadorHistorial++] = atencion;
            pacientesAtendidosIds.add(atencion.getId());
        } else {
            System.out.println("Historial lleno. No se puede registrar esta atención.");
        }
        medicosDisponiblesIds.add(atencion.getMedico().getId());
        removerAtencionDeEnCurso(id);
        System.out.println("Atención finalizada: " + atencion);
    }

    private Atencion buscarAtencionEnCursoPorId(int id) {
        for (int i = 0; i < contadorAtencionesEnCurso; i++) {
            if (atencionesEnCursoObjetos[i] != null && atencionesEnCursoObjetos[i].getId() == id) {
                return atencionesEnCursoObjetos[i];
            }
        }
        return null;
    }

    public void generarReportes() {
        mostrarPacientesPendientes();
        mostrarAtencionesEnCurso();
        mostrarPacientesAtendidos();
    }

    private Atencion buscarAtencionPorId(int id) {
        for (int i = 0; i < contadorHistorial; i++) {
            if (historialAtenciones[i] != null && historialAtenciones[i].getId() == id) {
                return historialAtenciones[i];
            }
        }
        return null;
    }

    private Paciente obtenerPacientePrioritario() {
        if (pacientesEnEspera.isEmpty()) return null;
        int pacienteId = pacientesEnEspera.getElement();
        pacientesEnEspera.remove();
        return buscarPacientePorId(pacienteId);
    }

    private Medico obtenerMedicoDisponible() {
        if (medicosDisponiblesIds.isEmpty()) return null;
        int medicoId = medicosDisponiblesIds.get(0);
        medicosDisponiblesIds.remove(0);
        return buscarMedicoPorId(medicoId);
    }

    private void crearAtencion(Paciente paciente, Medico medico) {
        Atencion atencion = new Atencion(paciente, medico);
        atencionesEnCursoIds.add(atencion.getId());
        atencionesEnCursoObjetos[contadorAtencionesEnCurso++] = atencion;
        System.out.println("Atención iniciada (ID: " + atencion.getId() + ") para el paciente "
                + paciente.getNombre() + " con el médico " + medico.getNombre());
    }

    private void removerAtencionDeEnCurso(int id) {
        for (int i = 0; i < atencionesEnCursoIds.size(); i++) {
            if (atencionesEnCursoIds.get(i) == id) {
                atencionesEnCursoIds.remove(i);
                break;
            }
        }
        for (int i = 0; i < contadorAtencionesEnCurso; i++) {
            if (atencionesEnCursoObjetos[i] != null && atencionesEnCursoObjetos[i].getId() == id) {
                atencionesEnCursoObjetos[i] = null;
                break;
            }
        }
    }

    public void mostrarPacientesPendientes() {
        int cantidadPendientes = 0;
        DynamicPriorityQueue tempQueue = new DynamicPriorityQueue();
        System.out.println("\nPacientes pendientes de atención:");
        while (!pacientesEnEspera.isEmpty()) {
            int pacienteId = pacientesEnEspera.getElement();
            int prioridad = pacientesEnEspera.getPriority();
            Paciente paciente = buscarPacientePorId(pacienteId);
            if (paciente != null) {
                System.out.println("- " + paciente);
            }
            tempQueue.add(pacienteId, prioridad);
            pacientesEnEspera.remove();
            cantidadPendientes++;
        }
        while (!tempQueue.isEmpty()) {
            pacientesEnEspera.add(tempQueue.getElement(), tempQueue.getPriority());
            tempQueue.remove();
        }
        System.out.println("Total pendientes: " + cantidadPendientes);
    }

    public void mostrarAtencionesEnCurso() {
        System.out.println("\nAtenciones en curso (" + atencionesEnCursoIds.size() + "):");
        for (int i = 0; i < contadorAtencionesEnCurso; i++) {
            Atencion atencion = atencionesEnCursoObjetos[i];
            if (atencion != null) {
                System.out.println("- " + atencion);
            }
        }
    }

    public void mostrarPacientesAtendidos() {
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
}
