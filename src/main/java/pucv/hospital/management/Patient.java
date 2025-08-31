package pucv.hospital.management;

public class Patient{
    private String name;
    private int age;
    private String gender;
    private Severity level;
    private String entryDate;
    private String dischargeDate;
    private String address;
    private int rut;

    public Patient(String name, int age, String gender, Severity level, String entryDate, String dischargeDate, String address, int rut){
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.level = level;
        this.entryDate = entryDate;
        this.dischargeDate = dischargeDate;
        this.address = address;
        this.rut = rut;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public int getAge(){
        return age;
    }
    public void setAge(int age){
        this.age = age;
    }
    public String getGender(){
        return gender;
    }
    public void setGender(String gender){
        this.gender = gender;
    }
    public Severity getLevel(){
        return level;
    }
    public void setLevel(Severity level){
        this.level = level;
    }
    public String getEntryDate(){
        return entryDate;
    }
    public void setEntryDate(String entryDate){
        this.entryDate = entryDate;
    }
    public String getDischargeDate(){
        return dischargeDate;
    }
    public void setDischargeDate(String dischargeDate){
        this.dischargeDate = dischargeDate;
    }
    public String getAddress(){
        return address;
    }
    public void setAddress(String address){
        this.address = address;
    }
    public int getRut(){
        return rut;
    }
    public void setRut(int rut){
        this.rut = rut;
    }

    public void showPatient(){
        System.out.println("\n┌─────────────────────────────────────┐");
        System.out.println("│           Información del paciente           │");
        System.out.println("├─────────────────────────────────────┤");
        System.out.printf("│ Paciente: %-25s │%n", name);
        System.out.printf("│ RUT: %-30s │%n", rut);
        System.out.printf("│ Edad: %-29d │%n", age);
        System.out.printf("│ Género: %-27s │%n", gender);
        System.out.printf("│ Estado: %-27s │%n", level);
        System.out.printf("│ Fecha de ingreso: %-17s │%n", entryDate);
        System.out.printf("│ Fecha de alta: %-20s │%n", dischargeDate);
        System.out.printf("│ Dirección: %-24s │%n", address);
        System.out.println("└─────────────────────────────────────┘");
    }

}