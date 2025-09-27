package hospital;

public class Bed {
    private int id;
    private boolean available;
    private Patient occupant;
    
    public Bed(boolean available, Patient occupant, int id){
        this.available = available;
        this.occupant = occupant;
        this.id = id;
    }

    public Bed(int id){
        this.available = true;
        this.occupant = null;
        this.id = id;
    }

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    public boolean getAvailable(){
        return available;
    }
    public void setAvailable(boolean available){
        this.available = available; 
    }
    public Patient getOccupant(){
        return occupant;
    }

    public void setOccupant(Patient patient){
        this.occupant = patient;
        this.available = (patient == null);
    }

    public void dischargePatient(){
        this.occupant = null;
        this.available = true;
    }
}
