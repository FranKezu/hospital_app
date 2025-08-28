package pucv.hospital.management;

import java.util.*;

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

    public void addDepartment(Department area){
        departments.put(area.getName(), area);
    }
}
