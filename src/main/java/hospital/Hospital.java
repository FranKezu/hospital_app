package hospital;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

public class Hospital {
    private String name;
    private String location;
    private Map<String, Department> departments;
    private DatabaseManager dbManager;

    public Hospital(String name, String location){
        this.name = name;
        this.location = location;
        departments = new HashMap<>();
        dbManager = new DatabaseManager();
        loadDepartmentsFromDatabase();
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

    // Getter para el DatabaseManager
    public DatabaseManager getDatabaseManager() {
        return dbManager;
    }

    public void addDepartment(Department area){
        area.setHospital(this); // Establecer referencia del hospital
        departments.put(area.getName(), area);
        // Persistir en base de datos
        if (dbManager.departmentExists(area.getName())) {
            dbManager.updateDepartment(area);
        } else {
            dbManager.insertDepartment(area);
        }
    }

    public void removeDepartments(String key){
        departments.remove(key);
        dbManager.deleteDepartment(key); // Eliminar de BD
    }

    // Cargar departamentos desde la base de datos al inicializar
    private void loadDepartmentsFromDatabase() {
        try {
            for (Department dept : dbManager.getAllDepartments()) {
                dept.setHospital(this); // Establecer referencia del hospital

                // Cargar las camas completas desde la BD (incluyendo pacientes)
                dbManager.loadDepartmentBeds(dept);

                departments.put(dept.getName(), dept);
            }
        } catch (Exception e) {
            System.err.println("Error al cargar departamentos desde la base de datos: " + e.getMessage());
        }
    }

    // Método para sincronizar cambios en las camas con la base de datos
    public void updateDepartmentBedCounts(String departmentName) {
        Department dept = departments.get(departmentName);
        if (dept != null) {
            // Actualizar contadores
            dbManager.updateBedCounts(departmentName, dept.getAvailableBeds(), dept.getOccupiedBeds());

            // Sincronizar todas las camas (estado y pacientes)
            dbManager.syncDepartmentBeds(departmentName, dept.getBedsList());
        }
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

    // Método para dar de alta un paciente (delegado al DatabaseManager)
    public boolean dischargePatient(Patient patient, String dischargeDate) {
        return dbManager.dischargePatient(patient.getRut(), dischargeDate);
    }

    // Método para cerrar correctamente la aplicación y persistir todos los datos
    public void shutdown() {
        System.out.println("Cerrando sistema hospitalario y guardando datos...");

        // Sincronizar todos los departamentos con la base de datos
        for (Department dept : departments.values()) {
            try {
                // Actualizar contadores de camas
                dbManager.updateBedCounts(dept.getName(), dept.getAvailableBeds(), dept.getOccupiedBeds());

                // Sincronizar todas las camas y sus pacientes
                dbManager.syncDepartmentBeds(dept.getName(), dept.getBedsList());

                System.out.println("Departamento '" + dept.getName() + "' sincronizado correctamente.");
            } catch (Exception e) {
                System.err.println("Error al sincronizar departamento " + dept.getName() + ": " + e.getMessage());
            }
        }

        // Cerrar conexión a la base de datos
        dbManager.closeConnection();
        System.out.println("Datos guardados y conexión cerrada correctamente.");
    }
}
