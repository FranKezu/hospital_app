package hospital;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:hospital.db";
    private Connection connection;
    
    public DatabaseManager() {
        initializeDatabase();
    }
    
    private void initializeDatabase() {
        try {
            connection = DriverManager.getConnection(DB_URL);
            createTables();
        } catch (SQLException e) {
            System.err.println("Error al inicializar la base de datos: " + e.getMessage());
        }
    }
    
    private void createTables() throws SQLException {
        String createDepartmentsTable = "CREATE TABLE IF NOT EXISTS departments (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL UNIQUE," +
                "description TEXT NOT NULL," +
                "total_beds INTEGER DEFAULT 0," +
                "available_beds INTEGER DEFAULT 0," +
                "occupied_beds INTEGER DEFAULT 0" +
                ")";
        
        String createBedsTable = "CREATE TABLE IF NOT EXISTS beds (" +
                "id INTEGER PRIMARY KEY," +
                "department_name TEXT NOT NULL," +
                "available BOOLEAN DEFAULT TRUE," +
                "FOREIGN KEY (department_name) REFERENCES departments(name)" +
                ")";
        
        String createPatientsTable = "CREATE TABLE IF NOT EXISTS patients (" +
                "rut INTEGER PRIMARY KEY," +
                "name TEXT NOT NULL," +
                "age INTEGER NOT NULL," +
                "gender TEXT NOT NULL," +
                "severity_level INTEGER NOT NULL," +
                "entry_date TEXT NOT NULL," +
                "discharge_date TEXT," +
                "address TEXT NOT NULL," +
                "bed_id INTEGER," +
                "department_name TEXT," +
                "FOREIGN KEY (bed_id) REFERENCES beds(id)," +
                "FOREIGN KEY (department_name) REFERENCES departments(name)" +
                ")";
        
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(createDepartmentsTable);
            stmt.executeUpdate(createBedsTable);
            stmt.executeUpdate(createPatientsTable);
        }
    }
    
    // Métodos para Departamentos
    public void saveDepartment(Department department) {
        String sql = "INSERT OR REPLACE INTO departments (name, description, total_beds, available_beds, occupied_beds) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, department.getName());
            pstmt.setString(2, department.getDescription());
            pstmt.setInt(3, department.getBedsSize());
            pstmt.setInt(4, department.getAvailableBeds());
            pstmt.setInt(5, department.getOccupiedBeds());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al guardar departamento: " + e.getMessage());
        }
    }
    
    public ArrayList<Department> loadDepartments() {
        ArrayList<Department> departments = new ArrayList<>();
        String sql = "SELECT * FROM departments";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                String name = rs.getString("name");
                String description = rs.getString("description");
                int totalBeds = rs.getInt("total_beds");
                
                Department dept = new Department(name, description, totalBeds);
                departments.add(dept);
            }
        } catch (SQLException e) {
            System.err.println("Error al cargar departamentos: " + e.getMessage());
        }
        
        return departments;
    }
    
    public void deleteDepartment(String name) {
        String sql = "DELETE FROM departments WHERE name = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al eliminar departamento: " + e.getMessage());
        }
    }
    
    // Métodos para Camas
    public void saveBed(Bed bed, String departmentName) {
        String sql = "INSERT OR REPLACE INTO beds (id, department_name, available) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, bed.getId());
            pstmt.setString(2, departmentName);
            pstmt.setBoolean(3, bed.getAvailable());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al guardar cama: " + e.getMessage());
        }
    }
    
    // Métodos para Pacientes
    public void savePatient(Patient patient) {
        String sql = "INSERT OR REPLACE INTO patients (rut, name, age, gender, severity_level, entry_date, discharge_date, address, bed_id, department_name) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, patient.getRut());
            pstmt.setString(2, patient.getName());
            pstmt.setInt(3, patient.getAge());
            pstmt.setString(4, patient.getGender());
            pstmt.setInt(5, patient.getLevel().getLevel());
            pstmt.setString(6, patient.getEntryDate());
            pstmt.setString(7, patient.getDischargeDate());
            pstmt.setString(8, patient.getAddress());
            pstmt.setInt(9, patient.getBedID());
            pstmt.setString(10, patient.getDepartment());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al guardar paciente: " + e.getMessage());
        }
    }
    
    public Patient loadPatient(int rut) {
        String sql = "SELECT * FROM patients WHERE rut = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, rut);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return createPatientFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error al cargar paciente: " + e.getMessage());
        }
        return null;
    }
    
    public Patient loadPatient(String name) {
        String sql = "SELECT * FROM patients WHERE name = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return createPatientFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error al cargar paciente: " + e.getMessage());
        }
        return null;
    }
    
    private Patient createPatientFromResultSet(ResultSet rs) throws SQLException {
        String name = rs.getString("name");
        int age = rs.getInt("age");
        String gender = rs.getString("gender");
        int severityLevel = rs.getInt("severity_level");
        String entryDate = rs.getString("entry_date");
        String dischargeDate = rs.getString("discharge_date");
        String address = rs.getString("address");
        int rut = rs.getInt("rut");
        int bedId = rs.getInt("bed_id");
        String department = rs.getString("department_name");
        
        Severity severity = getSeverityFromLevel(severityLevel);
        
        return new Patient(name, age, gender, severity, entryDate, dischargeDate, address, rut, bedId, department);
    }
    
    private Severity getSeverityFromLevel(int level) {
        if (level == 1) return Severity.Minimo;
        if (level == 2) return Severity.Medio;
        if (level == 3) return Severity.Moderado;
        if (level == 4) return Severity.Severo;
        if (level == 5) return Severity.Critico;
        return Severity.Medio;
    }
    
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar conexión: " + e.getMessage());
        }
    }
}