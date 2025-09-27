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
    }

    public void addBeds(){
        int actualID = beds.size();
        for(int k = actualID ; k < (10 + actualID); k++){
            Bed bed = new Bed(k + 1);
            beds.put(k + 1, bed);
        }
        availableBeds += 10;
    }

    public void showBeds(Hospital hospital) {
        System.out.println("\n--- CAMAS DEL DEPARTAMENTO: " + this.name + " ---");
        System.out.println("Total de camas: " + beds.size());
        System.out.println("Camas disponibles: " + availableBeds);
        System.out.println("Camas ocupadas: " + occupiedBeds);

        beds.forEach((key, value) -> {
            Bed b = value;
            if (!b.getAvailable()) {
                Patient p = b.getOccupant();
                System.out.println("\n==========================================");
                System.out.println(" CAMA #" + key + " - OCUPADA");
                System.out.println("==========================================");
                if (p != null) {
                    System.out.println(" Paciente: " + p.getName());
                    System.out.println(" RUT: " + p.getRut());
                    System.out.println(" Edad: " + p.getAge() + " años");
                    System.out.println(" Nivel: " + p.getSeverity());
                } else {
                    System.out.println(" ERROR: Cama marcada como ocupada sin paciente");
                }
                System.out.println("==========================================");
            } else {
                System.out.println(" CAMA #" + key + " - DISPONIBLE");
            }
        });
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
        
        System.out.println("Paciente " + patient.getName() + " dado de alta exitosamente\n");
        patient.showPatient();
        return patient;
    }
}