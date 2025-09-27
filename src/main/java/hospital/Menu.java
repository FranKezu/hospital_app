package hospital;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;

public class Menu {
    protected BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    // Para limpiar la pantalla y que el texto no se acumule.
    public void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    // Pausa para poder leer el contenido que aparece en pantalla.
    public void pause() throws IOException {
        System.out.print("\nPresione cualquier letra para continuar...");
        input.readLine();
    }

    private int validateNum(String text) throws IOException {
        while (true) {
            System.out.print(text);
            try {
                return Integer.parseInt(input.readLine());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Ingrese solo números.\n");
            }
        }
    }

    public int validateNum(String text, int min, int max) throws IOException {
        while (true) {
            int num = validateNum(text);
            if (num >= min && num <= max) {
                return num;
            }
            System.out.println("Debe estar entre " + min + " y " + max + ".");
        }
    }

    public void displayAvailableDepartments(Hospital hospital) {
        System.out.println("\nDepartamentos disponibles:");
        ArrayList<Department> departments = hospital.getDepartmentsList();
        for(Department dept : departments){
            System.out.println("- " + dept.getName() + " (Camas libres: " + dept.getAvailableBeds() + ")");
        }
    }

    public Department validateDepartment(Hospital hospital) throws IOException {
        displayAvailableDepartments(hospital);

        System.out.print("Seleccione departamento (escribir exactamente): ");
        String dName = input.readLine();
        Department selectedDepartment = hospital.getDepartment(dName);

        while(selectedDepartment == null) {
            System.out.println("\nDepartamento no encontrado. Intente de nuevo.\n");
            System.out.print("Seleccione departamento (escribir exactamente): ");
            dName = input.readLine();

            selectedDepartment = hospital.getDepartment(dName);
        }

        return selectedDepartment;
    }

    public boolean validateDepartmentExists(Hospital hospital) throws IOException {
        if(hospital.getDepartmentsSize() == 0) {
            System.out.println("No hay departamentos registrados. Por favor, registre uno primero.");
            pause();
            return false;
        }
        return true;
    }

    public void display(Hospital hospital) throws IOException {
        int option;
        do {
            clear();
            System.out.println("Bienvenido al sistema Hospitalario PUCV");
            System.out.println("1. Administrar departamentos");
            System.out.println("2. Administrar pacientes");
            System.out.println("0. Salir");

            option = validateNum("Ingrese una opción: ", 0, 2);

            switch (option) {
                case 1:
                    MenuDepartment menu1 = new MenuDepartment();
                    menu1.display(hospital);
                    break;
                case 2:
                    if(!validateDepartmentExists(hospital)) break;
                    MenuPatients menu2 = new MenuPatients();
                    menu2.display(hospital);
                    break;
                case 0:
                    System.out.println("Saliendo del sistema...");
                    break;
            }
        } while (option != 0);
    }
}
