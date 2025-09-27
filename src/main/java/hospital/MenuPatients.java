package hospital;

import java.io.IOException;
import java.util.ArrayList;

public class MenuPatients extends Menu{

    @Override
    public void display(Hospital hospital) throws IOException{
        int option;
        do{
            super.clear();
            System.out.println("-- MENÚ PACIENTES --");
            System.out.println("1. Mostrar pacientes");
            System.out.println("2. Buscar pacientes");
            System.out.println("3. Ingresar paciente");
            System.out.println("4. Dar de alta");
            System.out.println("5. Mostrar pacientes por gravedad");
            System.out.println("0. Volver atrás");

            option = validateNum("\nIngrese una opción: ", 0, 5);

            switch (option){
                case 1:
                    showPatients(hospital);
                    break;
                case 2:
                    findPatient(hospital);
                    break;
                case 3:
                    addPatient(hospital);
                    break;
                case 4:
                    dischargePatient(hospital);
                case 5:
                    showbySeverity(hospital);
                    break;
                case 0:
                    System.out.println("Volviendo atrás...");
                    break;
            }
        } while(option != 0);
    }

    public void showPatients(Hospital hospital) throws IOException{
        ArrayList<Department> departments = hospital.getDepartmentsList();

        for (Department d : departments){
            System.out.println("\n==========================================");
            System.out.println(" DEPARTAMENTO: " + d.getName());
            System.out.println("==========================================");
            ArrayList<Bed> beds = d.getBedsList();
            if(d.getOccupiedBeds() == 0){
                System.out.println("No hay camas ocupadas en este departamento.");
            }
            else{
                for(Bed bed : beds){
                    if(!bed.getAvailable()){
                        Patient p = bed.getOccupant();
                        System.out.println(" Nombre: " + p.getName());
                        System.out.println(" RUT: " + p.getRut());
                        System.out.println(" N° de Cama: " + bed.getId());
                        System.out.println(" Gravedad: " + bed.getOccupant().getSeverity());
                        System.out.println("------------------------------------------");
                    }
                }
                System.out.println("==========================================");
            }
        }
        super.pause();
    }

    public void findPatient(Hospital hospital) throws IOException{
        System.out.println("Ingrese RUT sin guión, o nombre y apellido del paciente: ");
        String aux = input.readLine();
        Patient p;
        if(aux.matches("\\d+")) {
            int rut = Integer.parseInt(aux);
            //SI es que se ingresa con longitud menor a 7 u mayor a 8
            p = hospital.findPatient(rut);
        }
        //else if(){
            // AQUI HAY QUE VALIDAR SI ES QUE EL RUT ESTA CON PUNTOS O GUIÓN
        //}
        else {
            // name = aux;
            p = hospital.findPatient(aux);
        }

            if(p == null){
            System.out.println("Paciente no encontrado!");
        }
        else p.showPatient();
        super.pause();
    }

    public void addPatient(Hospital hospital) throws IOException{
        System.out.println("==========================================");
        System.out.println("        INGRESAR NUEVO PACIENTE");
        System.out.println("==========================================");

        System.out.print("Nombre y apellido: ");
        String name = input.readLine();

        int age = super.validateNum("Edad: ", 0, 120);

        System.out.print("Género (M/F): ");
        String gender = input.readLine().toUpperCase();

        System.out.println("Nivel de severidad:");
        System.out.println("1. Mínimo");
        System.out.println("2. Medio");
        System.out.println("3. Moderado");
        System.out.println("4. Severo");
        System.out.println("5. Crítico");
        int severityOption = super.validateNum("Ingrese opción (1-5): ", 1, 5);

        Severity[] severities = {Severity.Minimo, Severity.Medio, Severity.Moderado, Severity.Severo, Severity.Critico};
        Severity severity = severities[severityOption - 1];

        System.out.print("Fecha de ingreso (YYYY-MM-DD): ");
        String entryDate = input.readLine();

        System.out.print("Dirección: ");
        String address = input.readLine();

        System.out.print("RUT (sin guión ni puntos): ");
        int rut = Integer.parseInt(input.readLine());

        Department d = super.validateDepartment(hospital);

        ArrayList<Bed> beds = d.getBedsList();
        Bed aux = null;
        for(Bed bed : beds){
            if(bed.getAvailable()){
                aux = bed;
                break;
            }
        }

        if(aux != null){
            Patient p = new Patient(name, age, gender, severity, entryDate, null, address, rut, aux.getId(), d.getName());
            d.assignPatient(p);
        } else {
            System.out.println("No hay camas disponibles.");
        }
        super.pause();
    }

    public void dischargePatient(Hospital hospital) throws IOException{
        System.out.println("Ingrese RUT sin guión, o nombre y apellido del paciente a dar de alta: ");
        String name = input.readLine();
        Patient p;
        if(name.matches("\\d+"))
            p = hospital.findPatient(Integer.parseInt(name));
        else
            p = hospital.findPatient(name);

        if(p == null){
            System.out.println("Paciente no encontrado!");
        }
        else {
            System.out.print("Ingrese fecha de alta (YYYY-MM-DD): ");
            String dischargeDate = input.readLine();
            p.setDischargeDate(dischargeDate);
            Department d = hospital.getDepartment(p.getDepartment());
            d.discharge(p.getBedID());
        }
        super.pause();
    }

    public void showbySeverity(Hospital hospital) throws IOException{
        Department d = super.validateDepartment(hospital);
        System.out.println("Nivel de severidad:");
        System.out.println("1. Mínimo");
        System.out.println("2. Medio");
        System.out.println("3. Moderado");
        System.out.println("4. Severo");
        System.out.println("5. Crítico");
        int severityOption = super.validateNum("Ingrese nivel de gravedad a filtrar (1-5): ", 1, 5);

        Severity[] severities = {Severity.Minimo, Severity.Medio, Severity.Moderado, Severity.Severo, Severity.Critico};
        Severity severity = severities[severityOption - 1];

        ArrayList<Bed> beds = d.getBedsList();
        for(Bed bed : beds){
            if(!bed.getAvailable() && bed.getOccupant().getSeverity() == severity){
                Patient p = bed.getOccupant();
                System.out.println(" Nombre: " + p.getName());
                System.out.println(" RUT: " + p.getRut());
                System.out.println(" N° de Cama: " + bed.getId());
                System.out.println("------------------------------------------");
            }
        }
        super.pause();
    }
}
