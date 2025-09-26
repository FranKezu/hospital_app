package hospital;

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
        addBeds(100);
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
            Bed bed = new Bed(this, k + 1);
            beds.put(k + 1, bed);
        }
        availableBeds += quantity;
    }

    public void addBeds(){
        int actualID = beds.size();
        for(int k = actualID ; k < (10 + actualID); k++){
            Bed bed = new Bed(this, k + 1);
            beds.put(k + 1, bed);
        }
        availableBeds += 10;
    }

    public void showBeds(){
        beds.forEach((key,value) ->{
            Bed b = (Bed) value;
            if(!b.getAvailable()){
                Patient p = b.getOccupant();
                System.out.println("\n==========================================");
                System.out.println(" CAMA #" + key + " - OCUPADA");
                System.out.println("==========================================");
                System.out.println(" Paciente: " + p.getName());
                System.out.println(" RUT: " + p.getRut());
                System.out.println(" Edad: " + p.getAge() + " años");
                System.out.println(" Nivel: " + p.getLevel());
                System.out.println("==========================================");
            }
            else{
                System.out.println("\n==========================================");
                System.out.println(" CAMA #" + key + " - DISPONIBLE");
                System.out.println("==========================================");
            }
        });
    }

    public void assignPatient(Patient patient){
        beds.forEach((key,value) ->{
            Bed b = value;
            if (b.getAvailable()){
                b.getOccupant().setBedID(b.getId());
                b.setOccupant(patient);
                b.setAvailable(false);
                availableBeds--;
                occupiedBeds++;
            } else {
                System.out.println("Cama no disponible.");
            }
        });
    }

    public Patient discharge(int id){
        Bed b = beds.get(id);
        if (b != null && b.getAvailable()){
            Patient p = b.getOccupant();
            b.setOccupant(null);
            b.setAvailable(true);
            availableBeds++;
            occupiedBeds--;
            return p;
        } else {
            System.out.println("No se encontró el paciente.");
            return null;
        }
    }
}