package hospital;

import javax.swing.*;
import java.util.ArrayList;

public class MenuPatients extends Menu {

    @Override
    public void display(Hospital hospital) {
        int option;
        do {
            String menu = "-- MENÚ PACIENTES --\n"
                    + "1. Mostrar pacientes\n"
                    + "2. Buscar pacientes\n"
                    + "3. Ingresar paciente\n"
                    + "4. Dar de alta\n"
                    + "5. Mostrar pacientes por gravedad\n"
                    + "0. Volver atrás\n";

            option = validateNum(menu + "\nIngrese una opción:", 0, 5);

            switch (option) {
                case 1:
                    showPatients(hospital);
                    break;
                case 2:
                    try {
                        findPatient(hospital);
                    } catch (InvalidRutException e) {
                        showMessage("Error de RUT: " + e.getMessage());
                    }
                    break;
                case 3:
                    try {
                        addPatient(hospital);
                    } catch (InvalidRutException e) {
                        showMessage("Error de RUT: " + e.getMessage());
                    } catch (InvalidDateException e2) {
                        showMessage("Error de fecha: " + e2.getMessage());
                    }
                    break;
                case 4:
                    try {
                        dischargePatient(hospital);
                    } catch (InvalidDateException e) {
                        showMessage("Error de fecha: " + e.getMessage());
                    }
                    break;
                case 5:
                    showBySeverity(hospital);
                    break;
                case 0:
                    showMessage("Volviendo atrás...");
                    break;
            }
        } while (option != 0);
    }

    public void showPatients(Hospital hospital) {
        ArrayList<Department> departments = hospital.getDepartmentsList();
        StringBuilder sb = new StringBuilder();

        for (Department d : departments) {
            sb.append("\n==========================================\n")
                    .append(" DEPARTAMENTO: ").append(d.getName()).append("\n")
                    .append("==========================================\n");

            ArrayList<Bed> beds = d.getBedsList();
            if (d.getOccupiedBeds() == 0) {
                sb.append("No hay camas ocupadas en este departamento.\n");
            } else {
                for (Bed bed : beds) {
                    if (!bed.getAvailable()) {
                        Patient p = bed.getOccupant();
                        sb.append(" Nombre: ").append(p.getName()).append("\n")
                                .append(" RUT: ").append(p.getRut()).append("\n")
                                .append(" N° de Cama: ").append(bed.getId()).append("\n")
                                .append(" Gravedad: ").append(p.getSeverity()).append("\n")
                                .append("------------------------------------------\n");
                    }
                }
            }
        }

        // Mostrar en JTextArea con scroll
        JTextArea textArea = new JTextArea(sb.toString(), 25, 80); // filas y columnas ajustables
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        javax.swing.JOptionPane.showMessageDialog(null, scrollPane, "Pacientes", javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }


    public void findPatient(Hospital hospital) throws InvalidRutException {
        String inputStr = JOptionPane.showInputDialog("Ingrese RUT sin guión ni puntos, o nombre y apellido del paciente:");
        if (inputStr == null || inputStr.trim().isEmpty()) return;

        Patient p;
        if (inputStr.matches("\\d+")) {
            int rut = InvalidRutException.validateForSearch(inputStr);
            p = hospital.findPatient(rut);
        } else {
            p = hospital.findPatient(inputStr);
        }

        if (p == null) {
            showMessage("Paciente no encontrado!");
        } else {
            p.showPatientGUI(); // <-- Usamos la nueva función para mostrar toda la info en GUI
        }
    }

    public void addPatient(Hospital hospital) throws InvalidRutException, InvalidDateException {
        String name = JOptionPane.showInputDialog("Nombre y apellido:");
        if (name == null || name.trim().isEmpty()) return;

        int age = validateNum("Edad:", 0, 120);

        String gender = JOptionPane.showInputDialog("Género (M/F):");
        if (gender == null) return;
        gender = gender.toUpperCase();

        String severityMenu = "Nivel de severidad:\n1. Mínimo\n2. Medio\n3. Moderado\n4. Severo\n5. Crítico";
        int severityOption = validateNum(severityMenu + "\nIngrese opción (1-5):", 1, 5);

        Severity[] severities = {Severity.Minimo, Severity.Medio, Severity.Moderado, Severity.Severo, Severity.Critico};
        Severity severity = severities[severityOption - 1];

        String entryDate = JOptionPane.showInputDialog("Fecha de ingreso (YYYY-MM-DD):");
        InvalidDateException.validateDate(entryDate);

        String address = JOptionPane.showInputDialog("Dirección:");
        if (address == null) address = "";

        String rutInput = JOptionPane.showInputDialog("RUT (sin guión ni puntos):");
        if (rutInput == null) return;
        int rut = InvalidRutException.validateComplete(rutInput, hospital);

        Department d = validateDepartment(hospital);
        if (d == null) return;

        Bed aux = null;
        for (Bed bed : d.getBedsList()) {
            if (bed.getAvailable()) {
                aux = bed;
                break;
            }
        }

        if (aux != null) {
            Patient p = new Patient(
                    name, age, gender, severity, entryDate,
                    null, address, rut, aux.getId(), d.getName()
            );
            d.assignPatient(p);
            showMessage("Paciente ingresado correctamente.");
        } else {
            showMessage("No hay camas disponibles.");
        }
    }

    public void dischargePatient(Hospital hospital) throws InvalidDateException{
        String name = JOptionPane.showInputDialog("Ingrese RUT o nombre y apellido del paciente a dar de alta:");
        if (name == null || name.trim().isEmpty()) return;

        Patient p;
        if (name.matches("\\d+"))
            p = hospital.findPatient(Integer.parseInt(name));
        else
            p = hospital.findPatient(name);

        if (p == null) {
            showMessage("Paciente no encontrado!");
            return;
        }

        String dischargeDate = JOptionPane.showInputDialog("Ingrese fecha de alta (YYYY-MM-DD):");
        InvalidDateException.validateDischargeDate(dischargeDate, p.getEntryDate());

        p.setDischargeDate(dischargeDate);
        Department d = hospital.getDepartment(p.getDepartment());
        d.discharge(p.getBedID());

        showMessage("Paciente dado de alta correctamente.");
    }

    public void showBySeverity(Hospital hospital) {
        Department d = validateDepartment(hospital);
        if (d == null) return;

        String severityMenu = "Nivel de severidad:\n1. Mínimo\n2. Medio\n3. Moderado\n4. Severo\n5. Crítico";
        int severityOption = validateNum(severityMenu + "\nIngrese nivel de gravedad a filtrar (1-5):", 1, 5);

        Severity[] severities = {Severity.Minimo, Severity.Medio, Severity.Moderado, Severity.Severo, Severity.Critico};
        Severity severity = severities[severityOption - 1];

        StringBuilder sb = new StringBuilder();
        for (Bed bed : d.getBedsList()) {
            if (!bed.getAvailable() && bed.getOccupant().getSeverity() == severity) {
                Patient p = bed.getOccupant();
                sb.append(" Nombre: ").append(p.getName()).append("\n")
                        .append(" RUT: ").append(p.getRut()).append("\n")
                        .append(" N° de Cama: ").append(bed.getId()).append("\n")
                        .append("------------------------------------------\n");
            }
        }

        if (sb.length() == 0) sb.append("No hay pacientes con ese nivel de severidad.");

        // Mostrar en JTextArea con scroll
        JTextArea textArea = new JTextArea(sb.toString(), 20, 80); // filas y columnas ajustables
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        javax.swing.JOptionPane.showMessageDialog(null, scrollPane,
                "Pacientes por Gravedad - Departamento " + d.getName(),
                javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

}
