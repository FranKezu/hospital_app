package hospital;

import javax.swing.*;
import java.util.ArrayList;

public class MenuDepartment extends Menu {

    @Override
    public void display(Hospital hospital) {
        int option;
        do {
            String menu = "-- MENÚ DEPARTAMENTOS --\n"
                    + "1. Mostrar departamentos\n"
                    + "2. Agregar departamentos\n"
                    + "3. Eliminar departamentos\n"
                    + "4. Agregar camas\n"
                    + "5. Mostrar camas\n"
                    + "0. Volver atrás\n";

            option = super.validateNum(menu + "\nIngrese una opción:", 0, 5);

            switch (option) {
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
                    showMessage("Volviendo atrás...");
                    break;
            }
        } while (option != 0);
    }

    public void showDepartaments(Hospital hospital) {
        if (!super.validateDepartmentExists(hospital)) return;

        ArrayList<Department> departments = hospital.getDepartmentsList();
        StringBuilder sb = new StringBuilder();

        for (Department d : departments) {
            sb.append("\n==========================================\n")
                    .append(" DEPARTAMENTO: ").append(d.getName()).append("\n")
                    .append(" Descripción: ").append(d.getDescription()).append("\n")
                    .append("==========================================\n")
                    .append(" Camas totales: ").append(d.getBedsSize()).append("\n")
                    .append(" Camas ocupadas: ").append(d.getOccupiedBeds()).append("\n")
                    .append(" Camas libres: ").append(d.getAvailableBeds()).append("\n")
                    .append("==========================================\n");
        }

        // Mostrar en JTextArea con scroll
        JTextArea textArea = new JTextArea(sb.toString(), 25, 80); // filas y columnas ajustables
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        javax.swing.JOptionPane.showMessageDialog(null, scrollPane, "Departamentos", javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    public void addDepartment(Hospital hospital) {
        String name = JOptionPane.showInputDialog(null, "Ingrese el nombre del departamento que desea agregar:");
        if (name == null || name.trim().isEmpty()) return;

        String description = JOptionPane.showInputDialog(null, "Ingrese la descripción del departamento:");
        if (description == null) description = "";

        Department newD = new Department(name, description);
        hospital.addDepartment(newD);

        showMessage("Departamento '" + name + "' añadido correctamente.\nDescripción: " + description);
    }

    public void removeDepartment(Hospital hospital) {
        if (!super.validateDepartmentExists(hospital)) return;

        Department d = super.validateDepartment(hospital);
        if (d == null) return;

        hospital.removeDepartments(d.getName());
        showMessage("Departamento '" + d.getName() + "' eliminado correctamente.");
    }

    public void addBeds(Hospital hospital) {
        if (!super.validateDepartmentExists(hospital)) return;

        Department d = super.validateDepartment(hospital);
        if (d == null) return;

        String aux = JOptionPane.showInputDialog(null, "Ingrese la cantidad de camas (por defecto 10):");
        if (aux == null || aux.trim().isEmpty()) {
            d.addBeds();
        } else {
            try {
                d.addBeds(Integer.parseInt(aux));
            } catch (NumberFormatException e) {
                showMessage("Entrada inválida. No se agregaron camas.");
                return;
            }
        }
        showMessage("Se han aumentado las camas del departamento '" + d.getName() + "' exitosamente.");
    }

    public void showBeds(Hospital hospital) {
        if (!super.validateDepartmentExists(hospital)) return;

        ArrayList<Department> departments = hospital.getDepartmentsList();
        StringBuilder sb = new StringBuilder();

        sb.append("==========================================\n")
                .append("         CAMAS DE TODOS LOS DEPARTAMENTOS\n")
                .append("==========================================\n");

        for (Department department : departments) {
            sb.append(department.showBedsGUI()).append("\n");
        }

        // Mostrar en un JTextArea grande con scroll
        JTextArea textArea = new JTextArea(sb.toString(), 25, 80); // ajusta filas y columnas
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        JOptionPane.showMessageDialog(null, scrollPane, "Camas de Departamentos", JOptionPane.INFORMATION_MESSAGE);
    }

}
