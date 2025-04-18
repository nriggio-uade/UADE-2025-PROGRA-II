package org.uade.util;

import org.uade.util.Atencion;
import org.uade.util.Medico;
import org.uade.util.Paciente;
import org.uade.structure.implementation.dynamic.DynamicLinkedList;
import org.uade.structure.implementation.dynamic.DynamicPriorityQueue;

public class CentroEmergenciasService {
    private DynamicPriorityQueue pacientesEnEspera; // Priorizamos por nivel de urgencia (menor número = mayor prioridad)
    private DynamicLinkedList medicosDisponibles;
    private DynamicLinkedList pacientesAtendidos;

    public CentroEmergenciasService(int numMedicosIniciales) {
        this.pacientesEnEspera = new DynamicPriorityQueue();
        this.medicosDisponibles = new DynamicLinkedList();
        this.pacientesAtendidos = new DynamicLinkedList();
        inicializarMedicos(numMedicosIniciales);
    }

    private void inicializarMedicos(int numMedicos) {
        for (int i = 1; i <= numMedicos; i++) {
            medicosDisponibles.add(new Medico("Médico " + i).getId());
        }
    }

    public void registrarPaciente(String nombre, String urgencia) {
        Paciente paciente = new Paciente(nombre, urgencia);
        pacientesEnEspera.add(paciente.getId(), paciente.getNivelUrgencia());
        System.out.println("Paciente registrado: " + paciente);
    }

    public void darDeAltaMedico(String nombre) {
        Medico nuevoMedico = new Medico(nombre);
        medicosDisponibles.add(nuevoMedico.getId());
        System.out.println("Médico dado de alta: " + nuevoMedico);
    }

    public void asignarMedico() {
        if (!pacientesEnEspera.isEmpty() && medicosDisponibles.size() > 0) {
            int pacienteId = pacientesEnEspera.getElement();
            pacientesEnEspera.remove();

            int medicoId = medicosDisponibles.get(0);
            medicosDisponibles.remove(0);

            Paciente paciente = buscarPaciente(pacienteId);
            Medico medico = buscarMedico(medicoId);

            if (paciente != null && medico != null) {
                Atencion atencion = new Atencion(paciente, medico);
                System.out.println("Asignando a " + medico.getNombre() + " al paciente " + paciente.getNombre());
                // Simulamos la atención (podría tener su propia lógica)
                atenderPaciente(atencion);
            }
        } else if (pacientesEnEspera.isEmpty()) {
            System.out.println("No hay pacientes en espera.");
        } else {
            System.out.println("No hay médicos disponibles.");
        }
    }

    private Paciente buscarPaciente(int id) {
        // En una implementación real, podríamos necesitar una estructura más eficiente para buscar por ID
        // Por ahora, podemos iterar sobre la cola (aunque no es lo ideal para buscar)
        DynamicPriorityQueue tempQueue = new DynamicPriorityQueue();
        Paciente encontrado = null;
        while (!pacientesEnEspera.isEmpty()) {
            int pacienteId = pacientesEnEspera.getElement();
            int prioridad = pacientesEnEspera.getPriority();
            Paciente paciente = obtenerPacienteDesdeId(pacienteId); // Necesitamos una forma de obtener el objeto Paciente desde su ID
            tempQueue.add(pacienteId, prioridad);
            if (paciente != null && paciente.getId() == id) {
                encontrado = paciente;
            }
            pacientesEnEspera.remove();
        }
        // Restaurar la cola original
        while (!tempQueue.isEmpty()) {
            pacientesEnEspera.add(tempQueue.getElement(), tempQueue.getPriority());
            tempQueue.remove();
        }
        return encontrado;
    }

    private Paciente obtenerPacienteDesdeId(int id) {
        // Esta es una implementación temporal y poco eficiente.
        // En un sistema real, se usaría un mapa (HashTable) para acceder rápidamente por ID.
        // Como no podemos usar estructuras adicionales, lo simulamos.
        // ¡Importante!: Esto requerirá iterar sobre la cola, lo cual no es óptimo.
        DynamicPriorityQueue tempQueue = new DynamicPriorityQueue();
        Paciente encontrado = null;
        while (!pacientesEnEspera.isEmpty()) {
            int pacienteId = pacientesEnEspera.getElement();
            int prioridad = pacientesEnEspera.getPriority();
            // Aquí asumimos que el 'value' en la PriorityQueue es el ID del paciente.
            // Necesitaríamos almacenar el objeto Paciente directamente o tener un mecanismo de lookup eficiente.
            // Para simplificar y usar las estructuras dadas, podríamos reconsiderar almacenar directamente objetos Paciente en la cola.
            // Por ahora, simulamos la búsqueda.
            // ¡Advertencia!: Esta parte es conceptual y requeriría una adaptación real.
            // Si la 'value' fuera el objeto Paciente, la búsqueda sería directa.
            // Dado que es un 'int', necesitamos una forma de mapear el ID al objeto.
            // Para este ejemplo, lo dejaremos como un placeholder.
            tempQueue.add(pacienteId, prioridad);
            pacientesEnEspera.remove();
        }
        while (!tempQueue.isEmpty()) {
            pacientesEnEspera.add(tempQueue.getElement(), tempQueue.getPriority());
            tempQueue.remove();
        }
        return null; // Placeholder
    }

    private Medico buscarMedico(int id) {
        for (int i = 0; i < medicosDisponibles.size(); i++) {
            if (medicosDisponibles.get(i) == id) {
                return obtenerMedicoDesdeId(id); // Necesitamos una forma de obtener el objeto Medico desde su ID
            }
        }
        return null;
    }

    private Medico obtenerMedicoDesdeId(int id) {
        // Similar al paciente, esto es un placeholder.
        // Idealmente, tendríamos una forma más eficiente de acceder al objeto Medico por ID.
        for (int i = 0; i < medicosDisponibles.size(); i++) {
            // Aquí asumimos que la LinkedList contiene directamente los IDs de los médicos.
            // Si contuviera objetos Medico, la búsqueda sería más directa.
            if (medicosDisponibles.get(i) == id) {
                // Tendríamos que tener una lista maestra de médicos para obtener el objeto.
                // Para simplificar, lo dejaremos como placeholder.
                return new Medico("Médico " + id); // Simulación
            }
        }
        return null; // Placeholder
    }


    public void atenderPaciente(Atencion atencion) {
        System.out.println("Iniciando atención a " + atencion.getPaciente().getNombre() + " por " + atencion.getMedico().getNombre());
        // Simulación de tiempo de atención
        try {
            Thread.sleep(2000); // Simula 2 segundos de atención
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        atencion.finalizarAtencion();
        pacientesAtendidos.add(atencion);
        Medico medico = atencion.getMedico();
        medico.setDisponible(true);
        medicosDisponibles.add(medico.getId()); // Volvemos a agregar el ID del médico a la lista de disponibles
        System.out.println("Atención finalizada para " + atencion.getPaciente().getNombre() + ". " + atencion.getMedico().getNombre() + " está disponible.");
    }

    public void mostrarPacientesPendientes() {
        System.out.println("Pacientes pendientes de atención (" + pacientesEnEspera.size() + "):");
        DynamicPriorityQueue tempQueue = new DynamicPriorityQueue();
        while (!pacientesEnEspera.isEmpty()) {
            int pacienteId = pacientesEnEspera.getElement();
            int prioridad = pacientesEnEspera.getPriority();
            Paciente paciente = obtenerPacienteDesdeId(pacienteId); // Necesitamos obtener el objeto Paciente
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
        System.out.println("\nPacientes atendidos (" + pacientesAtendidos.size() + "):");
        for (int i = 0; i < pacientesAtendidos.size(); i++) {
            System.out.println("- " + pacientesAtendidos.get(i));
        }
    }
}