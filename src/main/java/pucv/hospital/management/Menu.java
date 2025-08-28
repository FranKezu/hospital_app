package pucv.hospital.management;

import java.io.*;

public class Menu {
    
    public void clear(){ //PREGUNTAR SI SE PUEDE USAR
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void displayMenu(Hospital hospital) throws IOException {
        int option;
        do{
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            clear();
            System.out.println("Bienvenido al sistema Hospitalario PUCV");
            System.out.println("1. Administrar servicios");
            System.out.println("2. Administrar pacientes");
            System.out.println("0. Salir");
            System.out.println("\nIngrese una opción: ");
            option = Integer.parseInt(input.readLine());
            switch (option){
                case 1:
                    System.out.println("Administrar servicios");
                    break;
                case 2:
                    System.out.println("Administrar pacientes");
                    break;
                case 0:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
            }while(option != 0);

        }
}
