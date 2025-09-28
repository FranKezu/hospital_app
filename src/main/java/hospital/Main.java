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
        System.out.println("Agregando datos de prueba...");

        // Departamentos de prueba
        Department cardio = new Department("Cardiologia", "Departamento para enfermedades del corazón.", 20);

        Department[] departments = {cardio};

        // Insertar departamentos y sus camas
        for (Department dept : departments) {
            System.out.println("Insertando departamento: " + dept.getName());
            dbManager.insertDepartment(dept);
            dbManager.syncDepartmentBeds(dept.getName(), dept.getBedsList());
        }

        // Pacientes de prueba - los crearemos y asignaremos correctamente
        createAndAssignPatients(dbManager, cardio);

        System.out.println("Datos de prueba agregados correctamente.");
    }

    private static void createAndAssignPatients(DatabaseManager dbManager, Department cardio) {
        // Cardiologia
        assignPatientToDepartment(dbManager, cardio, new Patient("Juan Pérez", 35, "Masculino", Severity.Critico, "2023-01-10", null, "Calle Falsa 123", 12345678, 0, "Cardiologia"));
        assignPatientToDepartment(dbManager, cardio, new Patient("Ana Gómez", 45, "Femenino", Severity.Severo, "2023-02-15", null, "Avenida Siempreviva 742", 87654321, 0, "Cardiologia"));
        assignPatientToDepartment(dbManager, cardio, new Patient("Diego Fernández", 70, "Masculino", Severity.Critico, "2023-08-15", null, "Avenida Central 890", 66778899, 0, "Cardiologia"));
        assignPatientToDepartment(dbManager, cardio, new Patient("Fernando López", 65, "Masculino", Severity.Critico, "2023-02-25", null, "Avenida Mar 99", 22335566, 0, "Cardiologia"));
        assignPatientToDepartment(dbManager, cardio, new Patient("Alfonso Reyes", 44, "Masculino", Severity.Critico, "2023-02-14", null, "Pasaje Roble 88", 44556677, 0, "Cardiologia"));
        assignPatientToDepartment(dbManager, cardio, new Patient("Rodrigo Paredes", 62, "Masculino", Severity.Critico, "2023-06-27", null, "Pasaje Lirio 99", 88990011, 0, "Cardiologia"));
        assignPatientToDepartment(dbManager, cardio, new Patient("Raúl Espinoza", 46, "Masculino", Severity.Critico, "2023-02-18", null, "Avenida Cedro 12", 66778899, 0, "Cardiologia"));
        assignPatientToDepartment(dbManager, cardio, new Patient("Mauricio León", 51, "Masculino", Severity.Critico, "2023-06-14", null, "Pasaje Roble 77", 10112233, 0, "Cardiologia"));
        // Sincronizar todas las camas después de asignar pacientes
        dbManager.syncDepartmentBeds("Cardiologia", cardio.getBedsList());
    }

    private static void assignPatientToDepartment(DatabaseManager dbManager, Department department, Patient patient) {
        // Buscar una cama disponible
        for (Bed bed : department.getBedsList()) {
            if (bed.getAvailable()) {
                patient.setBedID(bed.getId());
                bed.setOccupant(patient);
                department.setAvailableBeds(department.getAvailableBeds() - 1);
                department.setOccupiedBeds(department.getOccupiedBeds() + 1);

                // Guardar paciente en BD
                dbManager.savePatient(patient);
                break;
            }
        }
    }

}