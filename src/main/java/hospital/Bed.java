package hospital;

public class Bed {
    private int id;
    private Boolean available;
    private Patient occupant;
    
    public Bed(Boolean available, Patient occupant, Department department, int id){
        this.available = available;
        this.occupant = occupant;
        this.id = id;
    }

    public Bed(Department department, int id){
        available = true;
        occupant = null;
        this.id = id;
    }

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
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
}
