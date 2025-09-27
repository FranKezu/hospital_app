package hospital;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

public class Hospital {
    private String name;
    private String location;
    private Map<String, Department> departments;
    //private DatabaseManager dbManager;

    public Hospital(String name, String location){
        this.name = name;
        this.location = location;
        departments = new HashMap<>();
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getLocation(){
        return location;
    }
    public void setLocation(String location){
        this.location = location;
    }
    public int getDepartmentsSize() {
        return departments.size();
    }
    public Department getDepartment(String key) {
        return departments.get(key);
    }
    public ArrayList<Department> getDepartmentsList() {
        return new ArrayList<Department>(departments.values());
    }
    public void addDepartment(Department area){
        departments.put(area.getName(), area);
    }
    public void removeDepartments(String key){
        departments.remove(key);
        //dbManager.deleteDepartment(key); // Eliminar de BD
    }

    public Patient findPatient(int rut){
        for (Department department : departments.values()){
            ArrayList<Bed> beds = department.getBedsList();
            for (Bed bed : beds){
                if (bed.getOccupant() != null && bed.getOccupant().getRut() == rut){
                    return bed.getOccupant();
                }
            }
        }
        return null;
    }

    public Patient findPatient(String name){
        for (Department department : departments.values()){
            ArrayList<Bed> beds = department.getBedsList();
            for (Bed bed : beds){
                if (bed.getOccupant() != null && bed.getOccupant().getName().equals(name)){
                    return bed.getOccupant();
                }
            }
        }
        return null;
    }

}
