package hospital;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException{
        // Inicializar la base de datos primero
        //DatabaseManager dbManager = new DatabaseManager();

        Hospital hospital = new Hospital("Hospital PUCV", "Av. Brasil 2147, Valparaíso");

        Menu menu = new Menu();
        menu.display(hospital);
    }
}
