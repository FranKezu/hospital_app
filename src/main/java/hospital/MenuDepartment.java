package hospital;

import java.io.IOException;
import java.util.ArrayList;

public class MenuDepartment extends Menu{

    @Override
    public void display(Hospital hospital) throws IOException{
        int option;
        do{
            super.clear();
            System.out.println("-- MENÚ DEPARTAMENTOS --");
            System.out.println("1. Mostrar departamentos");
            System.out.println("2. Agregar departamentos");
            System.out.println("3. Eliminar departamentos");
            System.out.println("4. Agregar camas");
            System.out.println("5. Mostrar camas");
            System.out.println("0. Volver atrás");

            option = super.validateNum("\nIngrese una opción: ", 0, 5);

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
                    break;
                case 5:
                    showBeds(hospital);
                    break;
                case 0:
                    System.out.println("Volviendo atrás...");
                    break;
            }
        } while(option != 0);
    }

    public void showDepartaments(Hospital hospital) throws IOException{
        if(!super.validateDepartmentExists(hospital)) return;

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
        super.pause();
    }

    public void addDepartment(Hospital hospital) throws IOException{
        System.out.println("Ingrese el nombre del departamento que desea agregar: ");
        String name = super.input.readLine();

        System.out.println("Ingrese la descripción del departamento: ");
        String description = super.input.readLine();

        Department newD = new Department(name, description);
        hospital.addDepartment(newD);

        System.out.println("Departamento " + name + " añadido correctamente.");
        System.out.println("Descripción " + description);
        super.pause();
    }

    public void removeDepartment(Hospital hospital) throws IOException{
        if(!super.validateDepartmentExists(hospital)) return;

        Department d = super.validateDepartment(hospital);

        hospital.removeDepartments(d.getName());
        System.out.println("Departamento " + d.getName() + " eliminado correctamente.");
        super.pause();
    }

    public void addBeds(Hospital hospital) throws IOException{
        if(!super.validateDepartmentExists(hospital)) return;

        Department d = super.validateDepartment(hospital);
        String name = super.input.readLine();
        System.out.println("Ingrese la cantidad de camas (por defecto 10): ");
        String aux = super.input.readLine();
        if(aux.isEmpty()) d.addBeds();
        else d.addBeds(Integer.parseInt(aux));
        System.out.println("Se han aumentado las camas del departamento '" + d.getName() + "' exitosamente.");
        super.pause();
    }

    public void showBeds(Hospital hospital) throws IOException {
        if(!super.validateDepartmentExists(hospital)) return;

        ArrayList<Department> departments = hospital.getDepartmentsList();

        System.out.println("\n==========================================");
        System.out.println("         CAMAS DE TODOS LOS DEPARTAMENTOS");
        System.out.println("==========================================");

        for (Department department : departments) {
            System.out.println("\n--- DEPARTAMENTO: " + department.getName() + " ---");
            department.showBeds(hospital);
        }

        super.pause();
    }
}
