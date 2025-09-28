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
        Department cardio = new Department("Cardiologia", "Departamento para enfermedades del corazón.", 20);
        Department neuro = new Department("Neurologia", "Departamento para trastornos del sistema nervioso.", 15);
        Department onco = new Department("Oncologia", "Departamento para el tratamiento del cáncer.", 25);
        Department pedia = new Department("Pediatria", "Departamento para la atención de niños.", 30);
        Department ortho = new Department("Ortopedia", "Departamento para lesiones y enfermedades del sistema óseo.", 18);
        Department derm = new Department("Dermatologia", "Departamento para enfermedades de la piel.", 10);
        Department gastro = new Department("Gastroenterologia", "Departamento para enfermedades digestivas.", 12);
        Department nephro = new Department("Nefrologia", "Departamento para enfermedades renales.", 14);
        Department pneumo = new Department("Neumologia", "Departamento para enfermedades respiratorias.", 16);
        Department psiqui = new Department("Psiquiatria", "Departamento para trastornos mentales.", 8);

        Department[] departments = {cardio, neuro, onco, pedia, ortho, derm, gastro, nephro, pneumo, psiqui};
        for (Department dept : departments) {
            dbManager.insertDepartment(dept);
        }

        // Pacientes de prueba
        Patient[] patients = {
                new Patient("Juan Pérez", 35, "Masculino", Severity.Critico, "2023-01-10", null, "Calle Falsa 123", 12345678, 1, "Cardiología"),
                new Patient("Ana Gómez", 45, "Femenino", Severity.Severo, "2023-02-15", null, "Avenida Siempreviva 742", 87654321, 2, "Cardiología"),
                new Patient("Carlos Soto", 50, "Masculino", Severity.Moderado, "2023-03-20", null, "Pasaje Los Lirios 45", 11223344, 1, "Neurología"),
                new Patient("Luisa Muñoz", 28, "Femenino", Severity.Medio, "2023-04-05", null, "El Roble 123", 44332211, 2, "Neurología"),
                new Patient("Elena Ríos", 60, "Femenino", Severity.Critico, "2023-05-12", null, "Los Aromos 567", 55667788, 1, "Oncología"),
                new Patient("Miguel Ángel", 8, "Masculino", Severity.Severo, "2023-06-01", null, "Los Cerezos 890", 99887766, 1, "Pediatría"),
                new Patient("Sofía Herrera", 12, "Femenino", Severity.Moderado, "2023-07-08", null, "Calle Larga 234", 33445566, 2, "Pediatría"),
                new Patient("Diego Fernández", 70, "Masculino", Severity.Critico, "2023-08-15", null, "Avenida Central 890", 66778899, 1, "Cardiología"),
                new Patient("Valentina Cruz", 25, "Femenino", Severity.Medio, "2023-09-10", null, "Pasaje Verde 56", 22334455, 2, "Dermatología"),
                new Patient("Andrés Molina", 40, "Masculino", Severity.Severo, "2023-10-05", null, "Calle Roble 123", 77889900, 1, "Ortopedia"),
                new Patient("Isabel Soto", 55, "Femenino", Severity.Moderado, "2023-11-12", null, "Avenida Los Pinos 34", 44556677, 2, "Oncología"),
                new Patient("Tomás Rivas", 33, "Masculino", Severity.Medio, "2023-12-01", null, "Calle Luna 78", 88990011, 1, "Ortopedia"),
                // Aquí agregamos más pacientes de forma sistemática
                new Patient("Paula Jiménez", 18, "Femenino", Severity.Medio, "2023-01-20", null, "Calle Flores 12", 11224455, 2, "Pediatría"),
                new Patient("Fernando López", 65, "Masculino", Severity.Critico, "2023-02-25", null, "Avenida Mar 99", 22335566, 1, "Cardiología"),
                new Patient("Lucía Méndez", 42, "Femenino", Severity.Severo, "2023-03-18", null, "Pasaje Sol 77", 33446677, 2, "Neurología"),
                new Patient("Javier Torres", 30, "Masculino", Severity.Moderado, "2023-04-22", null, "Calle Niebla 45", 44557788, 1, "Gastroenterología"),
                new Patient("Camila Vargas", 9, "Femenino", Severity.Medio, "2023-05-14", null, "Avenida Estrella 33", 55668899, 2, "Pediatría"),
                new Patient("Ricardo Peña", 58, "Masculino", Severity.Severo, "2023-06-07", null, "Calle Roble 66", 66779900, 1, "Oncología"),
                new Patient("Mariana Díaz", 23, "Femenino", Severity.Medio, "2023-07-19", null, "Pasaje Lirio 21", 77881122, 2, "Dermatología"),
                new Patient("Sebastián Ruiz", 36, "Masculino", Severity.Critico, "2023-08-13", null, "Calle Luna 88", 88992233, 1, "Nefrología"),
                new Patient("Natalia Castro", 47, "Femenino", Severity.Severo, "2023-09-05", null, "Avenida Roble 55", 99003344, 2, "Psiquiatría"),
                new Patient("Martín Salazar", 52, "Masculino", Severity.Moderado, "2023-10-17", null, "Calle Sol 99", 10112233, 1, "Ortopedia"),
                new Patient("Andrea Molina", 34, "Femenino", Severity.Medio, "2023-11-23", null, "Pasaje Estrella 11", 11223344, 2, "Gastroenterología"),
                new Patient("Héctor Blanco", 29, "Masculino", Severity.Severo, "2023-12-30", null, "Avenida Cerezo 12", 22334455, 1, "Neumología"),
                new Patient("Gabriela Torres", 39, "Femenino", Severity.Moderado, "2023-01-11", null, "Calle Loto 77", 33445566, 2, "Dermatología"),
                new Patient("Alfonso Reyes", 44, "Masculino", Severity.Critico, "2023-02-14", null, "Pasaje Roble 88", 44556677, 1, "Cardiología"),
                new Patient("Renata Campos", 31, "Femenino", Severity.Medio, "2023-03-21", null, "Calle Azucena 23", 55667788, 2, "Oncología"),
                new Patient("Esteban Guerrero", 27, "Masculino", Severity.Severo, "2023-04-29", null, "Avenida Sol 44", 66778899, 1, "Neurología"),
                new Patient("Patricia Rojas", 53, "Femenino", Severity.Moderado, "2023-05-16", null, "Calle Roble 33", 77889900, 2, "Gastroenterología"),
                new Patient("Rodrigo Paredes", 62, "Masculino", Severity.Critico, "2023-06-27", null, "Pasaje Lirio 99", 88990011, 1, "Cardiología"),
                new Patient("Carla Fuentes", 22, "Femenino", Severity.Medio, "2023-07-30", null, "Calle Lila 56", 99001122, 2, "Dermatología"),
                new Patient("Gonzalo Vidal", 41, "Masculino", Severity.Severo, "2023-08-21", null, "Avenida Cedro 21", 10112233, 1, "Ortopedia"),
                new Patient("Lorena Pino", 37, "Femenino", Severity.Medio, "2023-09-12", null, "Pasaje Jazmín 78", 11223344, 2, "Psiquiatría"),
                new Patient("Fabián Cortés", 48, "Masculino", Severity.Critico, "2023-10-03", null, "Calle Roble 11", 22334455, 1, "Oncología"),
                new Patient("Elisa Morales", 26, "Femenino", Severity.Medio, "2023-11-19", null, "Avenida Lirio 65", 33445566, 2, "Gastroenterología"),
                new Patient("Diego Serrano", 55, "Masculino", Severity.Severo, "2023-12-22", null, "Pasaje Roble 34", 44556677, 1, "Neurología"),
                new Patient("Mónica Aguirre", 32, "Femenino", Severity.Medio, "2023-01-09", null, "Calle Rosa 89", 55667788, 2, "Dermatología"),
                new Patient("Raúl Espinoza", 46, "Masculino", Severity.Critico, "2023-02-18", null, "Avenida Cedro 12", 66778899, 1, "Cardiología"),
                new Patient("Verónica Salas", 29, "Femenino", Severity.Severo, "2023-03-15", null, "Pasaje Loto 45", 77889900, 2, "Pediatría"),
                new Patient("Ignacio Herrera", 38, "Masculino", Severity.Moderado, "2023-04-06", null, "Calle Sol 66", 88990011, 1, "Ortopedia"),
                new Patient("Camila Torres", 19, "Femenino", Severity.Medio, "2023-05-28", null, "Avenida Azucena 23", 99001122, 2, "Pediatría"),
                new Patient("Mauricio León", 51, "Masculino", Severity.Critico, "2023-06-14", null, "Pasaje Roble 77", 10112233, 1, "Cardiología"),
                new Patient("Paola Vargas", 24, "Femenino", Severity.Medio, "2023-07-04", null, "Calle Lirio 12", 11223344, 2, "Dermatología"),
                new Patient("Santiago Ruiz", 39, "Masculino", Severity.Severo, "2023-08-30", null, "Avenida Cedro 33", 22334455, 1, "Neurología"),
                new Patient("Daniela Ortiz", 36, "Femenino", Severity.Medio, "2023-09-22", null, "Pasaje Jazmín 44", 33445566, 2, "Psiquiatría"),
                new Patient("Joaquín Méndez", 43, "Masculino", Severity.Critico, "2023-10-11", null, "Calle Roble 55", 44556677, 1, "Oncología"),
                new Patient("Natalia Pérez", 30, "Femenino", Severity.Medio, "2023-11-05", null, "Avenida Lila 66", 55667788, 2, "Gastroenterología"),
                new Patient("Victor Herrera", 47, "Masculino", Severity.Severo, "2023-12-19", null, "Pasaje Cedro 99", 66778899, 1, "Ortopedia")
        };

        for (Patient patient : patients) {
            dbManager.savePatient(patient);
        }
    }

}