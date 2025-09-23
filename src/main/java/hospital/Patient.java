package hospital;

public class Patient{
    private String name;
    private int age;
    private String gender;
    private Severity level;
    private String entryDate;
    private String dischargeDate;
    private String address;
    private String department;
    private int rut;
    private int bedID;

    public Patient(String name, int age, String gender, Severity level, String entryDate, String dischargeDate, String address, int rut, int bedID, String department){
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.level = level;
        this.entryDate = entryDate;
        this.dischargeDate = dischargeDate;
        this.address = address;
        this.department = department;
        this.rut = rut;
        this.bedID = bedID;
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
    public void setBedID(int bedID){
        this.bedID = bedID;
    }
    public int getBedID(){
        return bedID;
    }
    public String getDepartment(){
        return department;
    }
    public void setDepartment(String department){
        this.department = department;
    }


    public void showPatient(){
        System.out.println("\n==========================================");
        System.out.println("           FICHA DEL PACIENTE           ");
        System.out.println("==========================================");
        System.out.println(" Paciente: " + name);
        System.out.println(" RUT: " + rut);
        System.out.println(" Edad: " + age + " años");
        System.out.println(" Género: " + gender);
        System.out.println(" Estado: " + level);
        System.out.println(" Fecha de ingreso: " + entryDate);
        System.out.println(" Fecha de alta: " + 
            ((dischargeDate == null) ? "Sin fecha de alta" : dischargeDate));
        System.out.println(" Dirección: " + address);
        System.out.println(" N° de Cama: " + bedID);
        System.out.println(" Departamento: " + department);
        System.out.println("==========================================");
    }

}