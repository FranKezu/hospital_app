package pucv.hospital.management;

public class Bed {
    private Boolean available;
    private Patient occupant;
    
    public Bed (Boolean available, Patient occupant){
        this.available = available;
        this.occupant = occupant;
    }

    public Bed (){
        this.available = false;
        this.occupant = null;
    }

    public Boolean getAvailable(){
        return available;
    }
    public void setAvailable(Boolean available){
        this.available = available; 
    }
    public Patient getOccupant(){
        return occupant;
    }
    public void setOccupant(Patient occupant){
        this.occupant = occupant;
    }

    public void assignPatient(Patient patient){
        if (available){
            this.occupant = patient;
            this.available = false;
        } else {
            System.out.println("Cama no disponible.");
        }
    }

    public void discharge(Patient patient){
        if (available){
            available = false;
            occupant = null;
        }
    }
}
