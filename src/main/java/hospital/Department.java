package hospital;

import java.io.IOException;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

public class Department{
    private String name;
    private String description;
    private Map<Integer,Bed> beds;
    private int availableBeds;
    private int occupiedBeds;
    private Hospital hospital; // Referencia al hospital para sincronización con BD

    public Department(String name, String description){
        this.name = name;
        this.description = description;
        beds = new HashMap<>();
        addBeds(20);
        occupiedBeds = 0;
    }

    public Department(String name, String description, int availableBeds){
        this.name = name;
        this.description = description;
        beds = new HashMap<>();
        addBeds(availableBeds);
        occupiedBeds = 0;
    }

    // Método para establecer la referencia al hospital
    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }
    public int getBedsSize(){
        return beds.size();
    }
    public Bed getBed(int key){
        return beds.get(key);
    }
    public ArrayList<Bed> getBedsList(){
        return new ArrayList<Bed>(beds.values());
    }

    // Método para que DatabaseManager pueda acceder a la colección directa
    public Map<Integer, Bed> getBedsMap() {
        return beds;
    }

    // Método para limpiar todas las camas (usado por DatabaseManager)
    public void clearBeds() {
        beds.clear();
        availableBeds = 0;
        occupiedBeds = 0;
    }

    // Método para agregar cama directamente (usado por DatabaseManager)
    public void addBedDirect(Bed bed) {
        beds.put(bed.getId(), bed);
    }

    public int getAvailableBeds(){
        return availableBeds;
    }
    public void setAvailableBeds(int availableBeds){
        this.availableBeds = availableBeds;
    }

    public int getOccupiedBeds(){
        return occupiedBeds;
    }

    public void setOccupiedBeds(int occupatedBeds){
        this.occupiedBeds = occupatedBeds;
    }

    public void addBeds(int quantity){
        int actualID = beds.size();
        for(int k = actualID ; k < (quantity + actualID); k++){
            Bed bed = new Bed(k + 1);
            beds.put(k + 1, bed);
        }
        availableBeds += quantity;
        // Sincronizar con base de datos si el hospital está configurado
        if (hospital != null) {
            hospital.updateDepartmentBedCounts(this.name);
        }
    }

    public void addBeds(){
        int actualID = beds.size();
        for(int k = actualID ; k < (10 + actualID); k++){
            Bed bed = new Bed(k + 1);
            beds.put(k + 1, bed);
        }
        availableBeds += 10;
        // Sincronizar con base de datos si el hospital está configurado
        if (hospital != null) {
            hospital.updateDepartmentBedCounts(this.name);
        }
    }

    public void showBedsGUI() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n--- CAMAS DEL DEPARTAMENTO: ").append(this.name).append(" ---\n")
                .append("Total de camas: ").append(beds.size()).append("\n")
                .append("Camas disponibles: ").append(availableBeds).append("\n")
                .append("Camas ocupadas: ").append(occupiedBeds).append("\n");

        beds.forEach((key, value) -> {
            Bed b = value;
            if (!b.getAvailable()) {
                Patient p = b.getOccupant();
                sb.append("\n==========================================\n")
                        .append(" CAMA #").append(key).append(" - OCUPADA\n")
                        .append("==========================================\n");
                if (p != null) {
                    sb.append(" Paciente: ").append(p.getName()).append("\n")
                            .append(" RUT: ").append(p.getRut()).append("\n")
                            .append(" Edad: ").append(p.getAge()).append(" años\n")
                            .append(" Nivel: ").append(p.getSeverity()).append("\n");
                } else {
                    sb.append(" ERROR: Cama marcada como ocupada sin paciente\n");
                }
                sb.append("==========================================\n");
            } else {
                sb.append(" CAMA #").append(key).append(" - DISPONIBLE\n");
            }
        });

        javax.swing.JOptionPane.showMessageDialog(null, sb.toString(),
                "Camas del Departamento", javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }


    public void assignPatient(Patient patient){
        for (Map.Entry<Integer, Bed> entry : beds.entrySet()) {
            Bed b = entry.getValue();
            if (b.getAvailable()) {
                patient.setBedID(b.getId());
                b.setOccupant(patient);
                availableBeds--;
                occupiedBeds++;
                System.out.println("Paciente " + patient.getName() + " asignado a la cama " + b.getId() + " exitosamente.");

                // Sincronizar con base de datos
                if (hospital != null) {
                    // Guardar el paciente en la base de datos
                    hospital.getDatabaseManager().savePatient(patient);

                    // Sincronizar camas y contadores
                    hospital.updateDepartmentBedCounts(this.name);
                }
                return;
            }
        }
        System.out.println("Cama no disponible.");
    }

    public Patient discharge(int id){
        
        // Validar que el ID sea positivo
        if (id <= 0) {
            System.out.println("ID de cama inválido: " + id);
            return null;
        }
        Bed bed = beds.get(id);
        // Verificar si la cama existe
        if (bed == null) {
            System.out.println("No se encontró la cama con ID: " + id);
            return null;
        }
        // Verificar si la cama está ocupada
        if (bed.getAvailable()) {
            System.out.println("La cama " + id + " ya está disponible (no tiene paciente).");
            return null;
        }
        // Realizar el alta del paciente
        Patient patient = bed.getOccupant();
        bed.dischargePatient();
        patient.setBedID(0); // Reiniciar el ID de cama del paciente
        patient.setDischargeDate(java.time.LocalDate.now().toString());
        availableBeds++;
        occupiedBeds--;
        
        // Sincronizar con base de datos
        if (hospital != null) {
            hospital.dischargePatient(patient, patient.getDischargeDate()); // Marcar como dado de alta en BD
            hospital.updateDepartmentBedCounts(this.name); // Sincronizar camas y contadores
        }

        System.out.println("Paciente " + patient.getName() + " dado de alta exitosamente\n");
        patient.showPatient();
        return patient;
    }
}