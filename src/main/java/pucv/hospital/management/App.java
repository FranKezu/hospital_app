package pucv.hospital.management;
import java.io.*;
public class App {
    public static void main(String[] args) throws IOException {
        Hospital hospital = new Hospital("Hospital PUCV", "Av. Brasil 2147, Valparaíso");
        DataLoader.cargarDatosPrueba(hospital);
        Menu menu = new Menu();
        menu.displayMenu(hospital); 
    }
}
