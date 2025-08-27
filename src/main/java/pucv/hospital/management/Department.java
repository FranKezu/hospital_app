package pucv.hospital.management;

public class Department{
    private String name;
    private String description;
    // private Arraylist beds;

    public Department(String name, String description){
        this.name = name;
        this.description = description;
        // Inicializar ArrayList
    }

    public String getName() {
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
}