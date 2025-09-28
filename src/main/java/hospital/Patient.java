package hospital;

import javax.swing.*;

public class Patient{
    // Información personal
    private String name;
    private int rut;
    private int age;
    private String gender;
    private String address;

    // Información médica
    private Severity severity;
    private String entryDate;
    private String dischargeDate;

    // Información hospitalaria
    private String department;
    private int bedID;

    public Patient(String name, int age, String gender, Severity severity, String entryDate, String dischargeDate, String address, int rut, int bedID, String department){
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.severity = severity;
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
    public Severity getSeverity() { return severity; }
    public void setSeverity(Severity severity){
        this.severity = severity;
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

    public void showPatientGUI() {
        StringBuilder sb = new StringBuilder();

        sb.append("\n==========================================\n")
                .append("           FICHA DEL PACIENTE           \n")
                .append("==========================================\n")
                .append(" Paciente: ").append(name).append("\n")
                .append(" RUT: ").append(rut).append("\n")
                .append(" Edad: ").append(age).append(" años\n")
                .append(" Género: ").append(gender).append("\n")
                .append(" Estado: ").append(severity).append("\n")
                .append(" Fecha de ingreso: ").append(entryDate).append("\n")
                .append(" Fecha de alta: ").append((dischargeDate == null) ? "Sin fecha de alta" : dischargeDate).append("\n")
                .append(" Dirección: ").append(address).append("\n")
                .append(" N° de Cama: ").append(bedID).append("\n")
                .append(" Departamento: ").append(department).append("\n")
                .append("==========================================\n");

        // Mostrar en JTextArea con scroll
        JTextArea textArea = new JTextArea(sb.toString(), 20, 50); // filas y columnas ajustables
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        javax.swing.JOptionPane.showMessageDialog(null, scrollPane, "Ficha del Paciente", javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }


}