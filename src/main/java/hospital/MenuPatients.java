package hospital;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

public class MenuPatients{
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    public void display(Hospital hospital) throws IOException{

        int option;
        do{
            try{
                Menu.clear();
                System.out.println("-- MENÚ PACIENTES --");
                System.out.println("1. Mostrar pacientes");
                System.out.println("2. Buscar pacientes");
                System.out.println("3. Ingresar paciente");
                // 4. Dar de alta
                System.out.println("0. Volver atrás");
                System.out.println("\nIngrese una opción: ");
                option = Integer.parseInt(input.readLine());
                switch (option){
                    case 1:
                        showPatients(hospital);
                        break;
                    case 2:
                        findPatient(hospital);
                        break;
                    case 3:
                        addPatient(hospital);
                        break;
                    case 4:
                        //dischargePatient(hospital);
                    case 0:
                        System.out.println("Volviendo atrás...");
                        break;
                    default:
                        System.out.println("Opción no válida. Intente de nuevo.");
                        Menu.pause();
                }
            } catch (NumberFormatException e){
                System.out.println("Error: Debe ingresar un número válido.");
                option = -1;
                Menu.pause();
            }
        } while(option != 0);
    }

    public void showPatients(Hospital hospital) throws IOException{
        Map<String, Department> departments = hospital.getDepartments();
        departments.forEach((key1, d) -> {
            System.out.println("\n==========================================");
            System.out.println(" DEPARTAMENTO: " + d.getName());
            System.out.println("==========================================");
            d.getBeds().forEach((key2, b) ->{
                if(!b.getAvailable()){
                    Patient p = b.getOccupant();
                    System.out.println(" Nombre: " + p.getName());
                    System.out.println(" RUT: " + p.getRut());
                    System.out.println(" N° de Cama: " + key2);
                    System.out.println("------------------------------------------");
                }
            });
            System.out.println("==========================================");
        });
        Menu.pause();
    }

    public void findPatient(Hospital hospital) throws IOException{
        System.out.println("Ingrese RUT sin guión, o nombre y apellido del paciente: ");
        String name = input.readLine();
        Patient p;
        if(name.matches("\\d+"))
            p = hospital.findPatient(Integer.parseInt(name));
        else
            p = hospital.findPatient(name);
        
        if(p == null){
            System.out.println("Paciente no encontrado!");
        }
        else p.showPatient();
        Menu.pause();
    }

    public void addPatient(Hospital hospital) throws IOException{
        System.out.println("==========================================");
        System.out.println("        INGRESAR NUEVO PACIENTE");
        System.out.println("==========================================");
        
        System.out.print("Nombre y apellido: ");
        String name = input.readLine();
        
        System.out.print("Edad: ");
        int age = Integer.parseInt(input.readLine());
        
        System.out.print("Género (M/F): ");
        String gender = input.readLine().toUpperCase();
        
        System.out.println("Nivel de severidad:");
        System.out.println("1. Mínimo");
        System.out.println("2. Medio");
        System.out.println("3. Moderado");
        System.out.println("4. Severo");
        System.out.println("5. Crítico");
        System.out.print("Seleccione opción (1-5): ");
        int severityOption = Integer.parseInt(input.readLine());
        
        Severity severity;
        switch(severityOption){
            case 1: 
                severity = Severity.Mínimo;
                break;
            case 2: 
                severity = Severity.Medio;
                break;
            case 3: 
                severity = Severity.Moderado;
                break;
            case 4: 
                severity = Severity.Severo;
                break;
            case 5: 
                severity = Severity.Crítico;
                break;
            default:
                severity = Severity.Medio;
                break;
        }
        
        System.out.print("Fecha de ingreso (YYYY-MM-DD): ");
        String entryDate = input.readLine();
        
        System.out.print("Dirección: ");
        String address = input.readLine();
        
        System.out.print("RUT (sin guión ni puntos): ");
        int rut = Integer.parseInt(input.readLine());
        
        System.out.println("\nDepartamentos disponibles:");
        Map<String, Department> departments = hospital.getDepartments();
        departments.forEach((key, dept) -> {
            System.out.println("- " + key + " (Camas libres: " + dept.getAvailableBeds() + ")");
        });
        
        // FALTA AGREGAR VALIDACIÓN
        System.out.print("Seleccione departamento (escribir exactamente): ");
        String dName = input.readLine();
        
        Department department = departments.get(dName);
        Bed aux = null;
        for(Bed bed : department.getBeds().values()){
            if(bed.getAvailable()){
                aux = bed;
                break;
            }
        }
        
        if(aux != null){
            Patient p = new Patient(name, age, gender, severity, entryDate, null, address, rut, aux.getId(), dName);
            aux.assignPatient(p);
            System.out.println("Paciente " + name + " ingresado exitosamente!");
        } else {
            System.out.println("No hay camas disponibles.");
        }
        
        Menu.pause();
    }
}
