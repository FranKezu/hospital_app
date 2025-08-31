package hospital;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException{
        Hospital hospital = new Hospital("Hospital PUCV", "Av. Brasil 2147, Valparaíso");
        
        // Crear departamentos de prueba
        Department uci = new Department("UCI", "Unidad de Cuidados Intensivos", 10);
        Department pediatria = new Department("Pediatria", "Departamento de Pediatria", 15);
        Department cardiologia = new Department("Cardiologia", "Departamento de Cardiologia", 12);
        Department neurologia = new Department("Neurologia", "Departamento de Neurologia", 8);
        Department oncologia = new Department("Oncologia", "Departamento de Oncologia", 10);
        
        // Agregar departamentos al hospital
        hospital.addDepartment(uci);
        hospital.addDepartment(pediatria);
        hospital.addDepartment(cardiologia);
        hospital.addDepartment(neurologia);
        hospital.addDepartment(oncologia);
        
        // Crear pacientes de prueba
        Patient paciente1 = new Patient("Juan Perez", 45, "M", Severity.Moderado, "2024-01-15", "", "Valparaiso 123", 12345678, 0, "UCI");
        Patient paciente2 = new Patient("Maria Rodriguez", 67, "F", Severity.Crítico, "2024-01-16", "", "Vina del Mar 456", 23456789, 0, "UCI");
        Patient paciente3 = new Patient("Carlos Lopez", 32, "M", Severity.Medio, "2024-01-17", "", "Santiago 789", 34567890, 0, "Pediatria");
        Patient paciente4 = new Patient("Ana Martinez", 28, "F", Severity.Moderado, "2024-01-18", "", "Quilpue 321", 45678901, 0, "Cardiologia");
        Patient paciente5 = new Patient("Luis Garcia", 55, "M", Severity.Crítico, "2024-01-19", "", "Concepcion 654", 56789012, 0, "Neurologia");
        Patient paciente6 = new Patient("Claudio Cubillos", 30, "M", Severity.Mínimo, "2025-08-31", "", "PUCV", 67890123, 0, "Oncologia");
        
        // Asignar pacientes a camas
        uci.getBeds().get(1).assignPatient(paciente1);
        uci.getBeds().get(2).assignPatient(paciente2);
        pediatria.getBeds().get(1).assignPatient(paciente3);
        cardiologia.getBeds().get(1).assignPatient(paciente4);
        neurologia.getBeds().get(1).assignPatient(paciente5);
        oncologia.getBeds().get(1).assignPatient(paciente6);
        
        Menu menu = new Menu();
        menu.display(hospital); 
    }
}
