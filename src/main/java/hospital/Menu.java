package hospital;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class Menu {
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    // Para limpiar la pantalla y que el texto no se acumule.
    public static void clear(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    // Pausa para poder leer el contenido que aparece en pantalla.
    public static void pause() throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("\nPresione cualquier letra para continuar...");
        input.readLine();
    }

    public void display(Hospital hospital) throws IOException{
        int option;
        do{
            try {
                clear();
                System.out.println("Bienvenido al sistema Hospitalario PUCV");
                System.out.println("1. Administrar departamntos");
                System.out.println("2. Administrar pacientes");
                System.out.println("0. Salir");
                System.out.println("\nIngrese una opción: ");
                option = Integer.parseInt(input.readLine());
                
                switch (option){
                    case 1:
                        System.out.println("Administrar departamntos");
                        MenuDepartment menu1 = new MenuDepartment();
                        menu1.display(hospital);
                        break;
                    case 2:
                        System.out.println("Administrar pacientes");
                        MenuPatients menu2 = new MenuPatients();
                        menu2.display(hospital);
                        break;
                    case 0:
                        System.out.println("Saliendo del sistema...");
                        break;
                    default:
                        System.out.println("Opción no válida. Intente de nuevo.");
                        pause();
                }
            } catch (NumberFormatException e) {
                // Si no se ingresa un número.
                System.out.println("Error: Debe ingresar un número válido.");
                option = -1; // Para que no salga del búcle
                pause();
            }
        } while(option != 0);
    }
}
