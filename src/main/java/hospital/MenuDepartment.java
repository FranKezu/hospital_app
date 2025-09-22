package hospital;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MenuDepartment{
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    public void display(Hospital hospital) throws IOException{

        int option;
        do{
            try{
                Menu.clear();
                System.out.println("-- MENÚ DEPARTAMENTOS --");
                System.out.println("1. Mostrar departamentos");
                System.out.println("2. Agregar departamentos");
                System.out.println("3. Eliminar departamentos");
                System.out.println("4. Agregar camas");
                // 5. Mostrar camas
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
                        addBeds(hospital);
                    case 5:
                        //showBeds(hospital)
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
        ArrayList<Department> departments = hospital.getDepartmentsList();
    
        for (Department d : departments) {
            System.out.println("\n==========================================");
            System.out.println(" DEPARTAMENTO: " + d.getName());
            System.out.println("==========================================");
            System.out.println(" Camas totales: " + d.getBedsSize());
            System.out.println(" Camas ocupadas: " + d.getOccupiedBeds());
            System.out.println(" Camas libres: " + d.getAvailableBeds());
            System.out.println("==========================================");
        }
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

    public void addBeds(Hospital hospital) throws IOException{
        System.out.println("Ingrese el departamento al que desea agregar camas: ");
        String name = input.readLine();
        System.out.println("Ingrese la cantidad de camas (por defecto 10): ");
        String aux = input.readLine();
        Department d = hospital.getDepartment(name);
        if(aux.equals("")) d.addBeds();
        else d.addBeds(Integer.parseInt(aux));
        System.out.println("Se han aumentado las camas del departamento '" + name + "' exitosamente.");
        Menu.pause();
    }
}
