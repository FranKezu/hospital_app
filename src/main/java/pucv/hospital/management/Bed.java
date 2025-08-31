package pucv.hospital.management;

public class Bed {
    private Boolean available;
    private Patient occupant;
    private Department department;
    
    public Bed(Boolean available, Patient occupant, Department department){
        this.available = available;
        this.occupant = occupant;
        this.department = department;
    }

    public Bed(Department department){
        available = true;
        occupant = null;
        this.department = department;
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
            department.setAvailableBeds(department.getAvailableBeds() - 1);
            department.setOccupiedBeds(department.getOccupiedBeds() + 1);
        } else {
            System.out.println("Cama no disponible.");
        }
    }

    public void discharge(Patient patient){
        if (!available){
            available = true;
            occupant = null;
            department.setAvailableBeds(department.getAvailableBeds() + 1);
            department.setOccupiedBeds(department.getOccupiedBeds() - 1);
        }
    }
}
