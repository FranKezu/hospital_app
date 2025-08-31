package pucv.hospital.management;

import java.util.Map;
import java.util.HashMap;

public class Hospital {
    private String name;
    private String location;
    private Map<String, Department> departments;

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
    public Map<String, Department> getDepartments(){
        return departments;
    }
    public void addDepartment(Department area){
        departments.put(area.getName(), area);
    }
    public void removeDepartments(String key){
        departments.remove(key);
    }

    public Patient findPatient(int rut){
        for (Department department : departments.values()){
            for (Bed bed : department.getBeds().values()){
                if (bed.getOccupant() != null && bed.getOccupant().getRut() == rut){
                    return bed.getOccupant();
                }
            }
        }
        return null;
    }

    public Patient findPatient(String name){
        for (Department department : departments.values()){
            for (Bed bed : department.getBeds().values()){
                if (bed.getOccupant() != null && bed.getOccupant().getName().equals(name)){
                    return bed.getOccupant();
                }
            }
        }
        return null;
    }

}
