package hospital;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException{
        // Inicializar la base de datos primero
        DatabaseManager dbManager = new DatabaseManager();

        // Bloque para agregar datos de prueba solo si la base de datos está vacía
        if (dbManager.getAllDepartments().isEmpty()) {
            addTestData(dbManager);
        }

        Hospital hospital = new Hospital("Hospital PUCV", "Av. Brasil 2147, Valparaíso");

        Menu menu = new Menu();
        menu.display(hospital);
    }

    private static void addTestData(DatabaseManager dbManager) {
        // Departamentos de prueba
        Department cardio = new Department("Cardiología", "Departamento para enfermedades del corazón.", 20);
        Department neuro = new Department("Neurología", "Departamento para trastornos del sistema nervioso.", 15);
        Department onco = new Department("Oncología", "Departamento para el tratamiento del cáncer.", 25);
        Department pedia = new Department("Pediatría", "Departamento para la atención de niños.", 30);

        dbManager.insertDepartment(cardio);
        dbManager.insertDepartment(neuro);
        dbManager.insertDepartment(onco);
        dbManager.insertDepartment(pedia);

        // Pacientes de prueba
        try {
            Patient p1 = new Patient("Juan Pérez", 35, "Masculino", Severity.CRITICAL, "2023-01-10", null, "Calle Falsa 123", 12345678, 1, "Cardiología");
            Patient p2 = new Patient("Ana Gómez", 45, "Femenino", Severity.SERIOUS, "2023-02-15", null, "Avenida Siempreviva 742", 87654321, 2, "Cardiología");
            Patient p3 = new Patient("Carlos Soto", 50, "Masculino", Severity.MODERATE, "2023-03-20", null, "Pasaje Los Lirios 45", 11223344, 1, "Neurología");
            Patient p4 = new Patient("Luisa Muñoz", 28, "Femenino", Severity.MILD, "2023-04-05", null, "El Roble 123", 44332211, 2, "Neurología");
            Patient p5 = new Patient("Elena Ríos", 60, "Femenino", Severity.CRITICAL, "2023-05-12", null, "Los Aromos 567", 55667788, 1, "Oncología");
            Patient p6 = new Patient("Miguel Ángel", 8, "Masculino", Severity.SERIOUS, "2023-06-01", null, "Los Cerezos 890", 99887766, 1, "Pediatría");

            dbManager.savePatient(p1);
            dbManager.savePatient(p2);
            dbManager.savePatient(p3);
            dbManager.savePatient(p4);
            dbManager.savePatient(p5);
            dbManager.savePatient(p6);

        } catch (InvalidRutException | InvalidDateException e) {
            System.err.println("Error al crear pacientes de prueba: " + e.getMessage());
        }
    }
}