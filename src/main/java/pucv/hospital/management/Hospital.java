package pucv.hospital.management;

import java.util.*;

public class Hospital {
    private String name;
    private String address;
    private Map<String, Department> departments;

    public Hospital(String name, String address){
        this.name = name;
        this.address = address;
        this.departments = new HashMap<>();
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getAddress(){
        return address;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public void addDepartment(Department area){
        departments.put(area.getName(), area);
    }
}
