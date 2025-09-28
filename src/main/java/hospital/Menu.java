package hospital;

import javax.swing.*;
import java.util.ArrayList;

public class Menu {

    private JFrame frame;

    public Menu() {
        frame = new JFrame("Sistema Hospitalario PUCV");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
    }

    // Mensaje informativo
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(frame, message);
    }

    // Pedir número dentro de un rango
    public int validateNum(String text, int min, int max) {
        while (true) {
            String input = JOptionPane.showInputDialog(frame, text);
            if (input == null) { // usuario cerró ventana
                return min;
            }
            try {
                int num = Integer.parseInt(input);
                if (num >= min && num <= max) {
                    return num;
                }
                showMessage("Debe estar entre " + min + " y " + max + ".");
            } catch (NumberFormatException e) {
                showMessage("Entrada inválida. Ingrese solo números.");
            }
        }
    }

    // Mostrar departamentos disponibles
    public void displayAvailableDepartments(Hospital hospital) {
        StringBuilder sb = new StringBuilder("Departamentos disponibles:\n");
        ArrayList<Department> departments = hospital.getDepartmentsList();
        for (Department dept : departments) {
            sb.append("- ")
                    .append(dept.getName())
                    .append(" (Camas libres: ")
                    .append(dept.getAvailableBeds())
                    .append(")\n");
        }
        showMessage(sb.toString());
    }

    // Selección de departamento
    public Department validateDepartment(Hospital hospital) {
        displayAvailableDepartments(hospital);

        while (true) {
            String dName = JOptionPane.showInputDialog(frame, "Seleccione departamento (escribir exactamente):");
            if (dName == null) return null;

            Department selectedDepartment = hospital.getDepartment(dName);
            if (selectedDepartment != null) {
                return selectedDepartment;
            }
            showMessage("Departamento no encontrado. Intente de nuevo.");
        }
    }

    // Verificar que existan departamentos
    public boolean validateDepartmentExists(Hospital hospital) {
        if (hospital.getDepartmentsSize() == 0) {
            showMessage("No hay departamentos registrados. Por favor, registre uno primero.");
            return false;
        }
        return true;
    }

    // Menú principal
    public void display(Hospital hospital) {
        int option;
        do {
            String menu = "Bienvenido al sistema Hospitalario PUCV\n"
                    + "1. Administrar departamentos\n"
                    + "2. Administrar pacientes\n"
                    + "0. Salir\n";

            option = validateNum(menu + "\nIngrese una opción:", 0, 2);

            switch (option) {
                case 1:
                    MenuDepartment menu1 = new MenuDepartment();
                    menu1.display(hospital);
                    break;
                case 2:
                    if (!validateDepartmentExists(hospital)) break;
                    MenuPatients menu2 = new MenuPatients();
                    menu2.display(hospital);
                    break;
                case 0:
                    showMessage("Saliendo del sistema...");
                    break;
            }
        } while (option != 0);

        frame.dispose();
    }
}
