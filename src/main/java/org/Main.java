package org;

import org.uade.util.CentroEmergenciasService;
import java.util.Scanner;

public class Main { // Renombramos la clase a MainCentroEmergencias

    private static CentroEmergenciasService servicio;
    private static Scanner scanner;

    public static void main(String[] args) {
        servicio = new CentroEmergenciasService(10);
        scanner = new Scanner(System.in);
        cargarDatosIniciales();
        ejecutarMenu();
        System.out.println("Saliendo del sistema.");
        scanner.close();
    }

    private static void cargarDatosIniciales() {
        System.out.println("Cargando datos iniciales...");
        servicio.registrarPaciente("Paciente 1", "Alta");
        servicio.registrarPaciente("Paciente 2", "Media");
        servicio.registrarPaciente("Paciente 3", "Baja");
        System.out.println("Datos iniciales cargados.");
    }

    private static void ejecutarMenu() {
        String opcion;
        do {
            mostrarMenu();
            System.out.print("Ingrese una opción: ");
            opcion = scanner.nextLine();
            procesarOpcion(opcion);
        } while (!opcion.equalsIgnoreCase("salir"));
    }

    private static void mostrarMenu() {
        System.out.println("\n--- Centro de Emergencias ---");
        System.out.println("1. Registrar paciente");
        System.out.println("2. Dar de alta médico");
        System.out.println("3. Asignar médico al paciente");
        System.out.println("4. Mostrar pacientes pendientes");
        System.out.println("5. Generar reportes");
        System.out.println("salir");
    }

    private static void procesarOpcion(String opcion) {
        switch (opcion.toLowerCase()) {
            case "1":
                System.out.print("Ingrese el nombre del paciente: ");
                String nombrePaciente = scanner.nextLine();
                System.out.print("Ingrese el nivel de urgencia (Alta, Media, Baja): ");
                String urgencia = scanner.nextLine();
                servicio.registrarPaciente(nombrePaciente, urgencia);
                break;
            case "2":
                System.out.print("Ingrese el nombre del nuevo médico: ");
                String nombreMedico = scanner.nextLine();
                servicio.darDeAltaMedico(nombreMedico);
                break;
            case "3":
                servicio.asignarMedico();
                break;
            case "4":
                servicio.mostrarPacientesPendientes();
                break;
            case "5":
                servicio.generarReportes();
                break;
            case "salir":
                break;
            default:
                System.out.println("Opción inválida.");
        }
    }
}