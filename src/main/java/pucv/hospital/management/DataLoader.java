package pucv.hospital.management;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DataLoader {
    
    public static void cargarDatosPrueba(Hospital hospital) {
        try {
            // Cargar departamentos primero
            cargarDepartamentos(hospital, "TestCases/departments.csv");
            
            // Cargar pacientes y asignar a camas
            cargarPacientes(hospital, "TestCases/patients.csv");
            
            System.out.println("Datos de prueba cargados exitosamente!");
            
        } catch (Exception e) {
            System.out.println("Error al cargar datos de prueba: " + e.getMessage());
            System.out.println("Cargando datos por defecto...");
            cargarDatosPorDefecto(hospital);
        }
    }
    
    private static void cargarDepartamentos(Hospital hospital, String archivo) throws FileNotFoundException {
        File file = new File(archivo);
        Scanner scanner = new Scanner(file);
        
        // Saltar la línea de encabezados
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }
        
        while (scanner.hasNextLine()) {
            String linea = scanner.nextLine();
            String[] datos = linea.split(",");
            
            String nombre = datos[0];
            String descripcion = datos[1];
            int numeroCamas = Integer.parseInt(datos[2]);
            
            Department departamento = new Department(nombre, descripcion);
            departamento.addBeds(numeroCamas);
            hospital.addDepartment(departamento);
            
            System.out.println("Departamento creado: " + nombre + " con " + numeroCamas + " camas");
        }
        
        scanner.close();
    }
    
    private static void cargarPacientes(Hospital hospital, String archivo) throws FileNotFoundException {
        File file = new File(archivo);
        Scanner scanner = new Scanner(file);
        
        // Saltar la línea de encabezados
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }
        
        while (scanner.hasNextLine()) {
            String linea = scanner.nextLine();
            String[] datos = linea.split(",");
            
            String rut = datos[0];
            String nombre = datos[1];
            int edad = Integer.parseInt(datos[2]);
            String genero = datos[3];
            Severity nivel = Severity.valueOf(datos[4]);
            String fechaIngreso = datos[5];
            String fechaAlta = datos[6].isEmpty() ? null : datos[6];
            String direccion = datos[7];
            
            Patient paciente = new Patient(nombre, edad, genero, nivel, fechaIngreso, fechaAlta, direccion, rut);
            
            // Asignar paciente a una cama según su nivel de gravedad
            asignarPacienteACama(hospital, paciente);
        }
        
        scanner.close();
    }
    
    private static void asignarPacienteACama(Hospital hospital, Patient paciente) {
        String departamentoObjetivo = getDepartamentoPorSeveridad(paciente.getLevel());
        
        // Aquí necesitarías acceso a los departamentos del hospital
        // Como no veo un getter en tu clase Hospital, podrías agregarlo:
        // public Map<String, Department> getDepartments() { return departments; }
        
        System.out.println("Paciente " + paciente.getName() + " asignado al departamento: " + departamentoObjetivo);
    }
    
    private static String getDepartamentoPorSeveridad(Severity severity) {
        switch (severity) {
            case Critical:
                return "UCI";
            case Severe:
                return "Emergencia";
            case Moderate:
                return "Cardiología";
            case Mild:
                return "General";
            case Minimal:
                return "General";
            default:
                return "General";
        }
    }
    
    public static void cargarDatosPorDefecto(Hospital hospital) {
        // Crear departamentos por defecto
        Department uci = new Department("UCI", "Unidad de Cuidados Intensivos");
        uci.addBeds(8);
        hospital.addDepartment(uci);
        
        Department emergencia = new Department("Emergencia", "Departamento de Emergencias");
        emergencia.addBeds(12);
        hospital.addDepartment(emergencia);
        
        Department general = new Department("General", "Hospitalización General");
        general.addBeds(25);
        hospital.addDepartment(general);
        
        // Crear algunos pacientes por defecto
        Patient[] pacientesPorDefecto = {
            new Patient("Juan Pérez", 65, "M", Severity.Critical, "2024-01-15", null, "Av. Libertador 123", "12345678-9"),
            new Patient("María González", 45, "F", Severity.Severe, "2024-01-16", null, "Calle San Martín 456", "23456789-0"),
            new Patient("Carlos Rodríguez", 30, "M", Severity.Moderate, "2024-01-17", null, "Pasaje Los Aromos 789", "34567890-1"),
            new Patient("Ana Hernández", 28, "F", Severity.Mild, "2024-01-18", null, "Av. O'Higgins 321", "45678901-2")
        };
        
        for (Patient paciente : pacientesPorDefecto) {
            System.out.println("Paciente por defecto creado: " + paciente.getName());
        }
        
        System.out.println("Datos por defecto cargados.");
    }
}