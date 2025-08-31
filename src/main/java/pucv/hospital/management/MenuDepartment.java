package pucv.hospital.management;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

public class MenuDepartment{
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    public void display(Hospital hospital) throws IOException{

        int option;
        do{
            try{
                Menu.clear();
                System.out.println("Bienvenido al sistema Hospitalario PUCV");
                System.out.println("1. Mostrar departamentos");
                System.out.println("2. Agregar departamentos");
                System.out.println("3. Eliminar departamentos");
                System.out.println("4. Buscar paciente");
                System.out.println("5. Agregar camas");
                System.out.println("0. Volver atrás");
                System.out.println("\nIngrese una opción: ");
                option = Integer.parseInt(input.readLine());
                switch (option){
                    case 1:
                        showDepartaments(hospital);
                        break;
                    case 2:
                        addDepartment(hospital);
                        break;
                    case 3:
                        removeDepartment(hospital);
                        break;
                    case 4:
                        findPatient(hospital);
                    case 5:
                        addBeds(hospital);
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

    public void showDepartaments(Hospital hospital) throws IOException{
        Map<String, Department> departments = hospital.getDepartments();
        departments.forEach((key, value) -> {
            Department d = (Department) value;
            System.out.println("\n┌─────────────────────────────────────┐");
            System.out.printf("│ DEPARTAMENTO: %-23s│\n", d.getName());
            System.out.println("├─────────────────────────────────────┤");
            System.out.printf("│ Camas totales: %-22d│\n", d.getBeds().size());
            System.out.printf("│ Camas ocupadas: %-21d│\n", d.getOccupiedBeds());
            System.out.printf("│ Camas libres: %-24d│\n", d.getAvailableBeds());
            System.out.println("└─────────────────────────────────────┘");
        });
        Menu.pause();
    }

    public void addDepartment(Hospital hospital) throws IOException{
        System.out.println("Ingrese el nombre del departamento que desea agregar: ");
        String name = input.readLine();

        System.out.println("Ingrese la descripción del departamento: ");
        String description = input.readLine();

        Department newD = new Department(name, description);
        hospital.addDepartment(newD);

        System.out.println("Departamento " + name + " añadido correctamente.");
        System.out.println("Descripción " + description);
        Menu.pause();
    }

    public void removeDepartment(Hospital hospital) throws IOException{
        System.out.println("Ingrese el nombre del departamento que desea eliminar: ");
        String name = input.readLine();

        hospital.removeDepartments(name);
        System.out.println("Departamento " + name + " eliminado correctamente.");
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

    public void addBeds(Hospital hospital) throws IOException{
        System.out.println("Ingrese el departamento al que desea agregar camas: ");
        String name = input.readLine();
        System.out.println("Ingrese la cantidad de camas (por defecto 10): ");
        String aux = input.readLine();
        Department d = hospital.getDepartments().get(name);
        if(aux.equals("")) d.addBeds();
        else d.addBeds(Integer.parseInt(aux)); 
    }
}
