package hospital;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ReportGenerator {

    public void generatePatientReport(Hospital hospital) {
        try (FileWriter writer = new FileWriter("reporte.txt")) {
            writer.write("Reporte de Pacientes\n");
            writer.write("=====================\n\n");

            ArrayList<Department> departments = hospital.getDepartmentsList();
            if (departments == null) {
                writer.write("No hay departamentos registrados.\n");
                return;
            }

            for (Department department : departments) {
                writer.write("Departamento: " + department.getName() + "\n");
                writer.write("---------------------\n");
                ArrayList<Bed> beds = department.getBedsList();
                boolean hasPatients = false;

                if (beds != null) {
                    for (Bed b : beds) {
                        if (!b.getAvailable()) {
                            Patient p = b.getOccupant();
                            if (p != null) {
                                hasPatients = true;
                                writer.write("==========================================" + "\n");
                                writer.write(" Paciente: " + p.getName() + "\n");
                                writer.write(" RUT: " + p.getRut() + "\n");
                                writer.write(" Edad: " + p.getAge() + " años" + "\n");
                                writer.write(" Género: " + p.getGender() + "\n");
                                writer.write(" Estado: " + p.getSeverity() + "\n");
                                writer.write(" Fecha de ingreso: " + p.getEntryDate() + "\n");
                                writer.write(" Fecha de alta: " +
                                        ((p.getDischargeDate() == null) ? "Sin fecha de alta" : p.getDischargeDate()) + "\n");
                                writer.write(" Dirección: " + p.getAddress() + "\n");
                                writer.write(" N° de Cama: " + p.getBedID() + "\n");
                                writer.write(" Departamento: " + p.getDepartment() + "\n");
                                writer.write("==========================================" + "\n");
                            }
                        }
                    }
                }

                if (!hasPatients) {
                    writer.write("No hay pacientes en este departamento.\n");
                }
                writer.write("\n");
            }
            System.out.println("Reporte generado exitosamente en reporte.txt");
        } catch (IOException e) {
            System.out.println("Error al generar el reporte: " + e.getMessage());
        }
    }
}
