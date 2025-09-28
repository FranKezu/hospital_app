package hospital;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private DatabaseConnection dbConnection;

    public DatabaseManager() {
        this.dbConnection = DatabaseConnection.getInstance();
    }

    // Insertar un nuevo departamento
    public boolean insertDepartment(Department department) {
        String sql = "INSERT INTO departamentos (name, description, available_beds, occupied_beds) VALUES (?, ?, ?, ?)";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, department.getName());
            pstmt.setString(2, department.getDescription());
            pstmt.setInt(3, department.getAvailableBeds());
            pstmt.setInt(4, department.getOccupiedBeds());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error al insertar departamento: " + e.getMessage());
            return false;
        }
    }

    // Actualizar un departamento existente
    public boolean updateDepartment(Department department) {
        String sql = "UPDATE departamentos SET description = ?, available_beds = ?, occupied_beds = ? WHERE name = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, department.getDescription());
            pstmt.setInt(2, department.getAvailableBeds());
            pstmt.setInt(3, department.getOccupiedBeds());
            pstmt.setString(4, department.getName());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar departamento: " + e.getMessage());
            return false;
        }
    }

    // Eliminar un departamento
    public boolean deleteDepartment(String departmentName) {
        String sql = "DELETE FROM departamentos WHERE name = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, departmentName);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar departamento: " + e.getMessage());
            return false;
        }
    }

    // Obtener todos los departamentos de la base de datos
    public List<Department> getAllDepartments() {
        List<Department> departments = new ArrayList<>();
        String sql = "SELECT name, description, available_beds, occupied_beds FROM departamentos";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String name = rs.getString("name");
                String description = rs.getString("description");
                int availableBeds = rs.getInt("available_beds");
                int occupiedBeds = rs.getInt("occupied_beds");

                // Crear departamento con la cantidad total de camas
                int totalBeds = availableBeds + occupiedBeds;
                Department department = new Department(name, description, totalBeds > 0 ? totalBeds : 20);
                department.setAvailableBeds(availableBeds);
                department.setOccupiedBeds(occupiedBeds);

                departments.add(department);
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener departamentos: " + e.getMessage());
        }

        return departments;
    }

    // Verificar si un departamento existe
    public boolean departmentExists(String departmentName) {
        String sql = "SELECT COUNT(*) FROM departamentos WHERE name = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, departmentName);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al verificar existencia del departamento: " + e.getMessage());
        }

        return false;
    }

    // Actualizar solo el número de camas disponibles y ocupadas
    public boolean updateBedCounts(String departmentName, int availableBeds, int occupiedBeds) {
        String sql = "UPDATE departamentos SET available_beds = ?, occupied_beds = ? WHERE name = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, availableBeds);
            pstmt.setInt(2, occupiedBeds);
            pstmt.setString(3, departmentName);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar contadores de camas: " + e.getMessage());
            return false;
        }
    }

    // Método para sincronizar todas las camas de un departamento con la BD
    public void syncDepartmentBeds(String departmentName, ArrayList<Bed> beds) {
        // Primero eliminar todas las camas existentes de este departamento
        String deleteSql = "DELETE FROM camas WHERE department_name = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {

            deleteStmt.setString(1, departmentName);
            deleteStmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error al limpiar camas del departamento: " + e.getMessage());
            return;
        }

        // Insertar todas las camas actuales
        String insertSql = "INSERT INTO camas (id, department_name, is_available, patient_rut) VALUES (?, ?, ?, ?)";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {

            for (Bed bed : beds) {
                insertStmt.setInt(1, bed.getId());
                insertStmt.setString(2, departmentName);
                insertStmt.setBoolean(3, bed.getAvailable());

                if (!bed.getAvailable() && bed.getOccupant() != null) {
                    insertStmt.setInt(4, bed.getOccupant().getRut());
                } else {
                    insertStmt.setNull(4, java.sql.Types.INTEGER);
                }

                insertStmt.executeUpdate();
            }

        } catch (SQLException e) {
            System.err.println("Error al sincronizar camas: " + e.getMessage());
        }
    }

    // Insertar o actualizar un paciente
    public boolean savePatient(Patient patient) {
        // Primero intentar actualizar
        String updateSql = "UPDATE pacientes SET name = ?, age = ?, gender = ?, severity = ?, " +
                          "entry_date = ?, discharge_date = ?, address = ?, department_name = ?, " +
                          "bed_id = ?, is_active = ? WHERE rut = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {

            updateStmt.setString(1, patient.getName());
            updateStmt.setInt(2, patient.getAge());
            updateStmt.setString(3, patient.getGender());
            updateStmt.setString(4, patient.getSeverity().toString());
            updateStmt.setString(5, patient.getEntryDate());
            updateStmt.setString(6, patient.getDischargeDate());
            updateStmt.setString(7, patient.getAddress());
            updateStmt.setString(8, patient.getDepartment());
            updateStmt.setInt(9, patient.getBedID());
            updateStmt.setBoolean(10, patient.getDischargeDate() == null);
            updateStmt.setInt(11, patient.getRut());

            int rowsAffected = updateStmt.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            }

        } catch (SQLException e) {
            System.err.println("Error al actualizar paciente: " + e.getMessage());
        }

        // Si no se actualizó, insertar nuevo
        String insertSql = "INSERT INTO pacientes (rut, name, age, gender, severity, entry_date, " +
                          "discharge_date, address, department_name, bed_id, is_active) " +
                          "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {

            insertStmt.setInt(1, patient.getRut());
            insertStmt.setString(2, patient.getName());
            insertStmt.setInt(3, patient.getAge());
            insertStmt.setString(4, patient.getGender());
            insertStmt.setString(5, patient.getSeverity().toString());
            insertStmt.setString(6, patient.getEntryDate());
            insertStmt.setString(7, patient.getDischargeDate());
            insertStmt.setString(8, patient.getAddress());
            insertStmt.setString(9, patient.getDepartment());
            insertStmt.setInt(10, patient.getBedID());
            insertStmt.setBoolean(11, patient.getDischargeDate() == null);

            int rowsAffected = insertStmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error al insertar paciente: " + e.getMessage());
            return false;
        }
    }

    // Cargar camas desde la base de datos para un departamento
    public void loadDepartmentBeds(Department department) {
        String sql = "SELECT c.id, c.is_available, c.patient_rut, " +
                    "p.name, p.age, p.gender, p.severity, p.entry_date, p.discharge_date, p.address " +
                    "FROM camas c LEFT JOIN pacientes p ON c.patient_rut = p.rut AND p.is_active = 1 " +
                    "WHERE c.department_name = ? ORDER BY c.id";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, department.getName());

            try (ResultSet rs = pstmt.executeQuery()) {
                // Limpiar camas existentes usando el nuevo método
                department.clearBeds();
                int availableBeds = 0;
                int occupiedBeds = 0;

                while (rs.next()) {
                    int bedId = rs.getInt("id");
                    boolean isAvailable = rs.getBoolean("is_available");

                    Bed bed = new Bed(bedId);

                    if (!isAvailable && rs.getInt("patient_rut") != 0) {
                        // Crear paciente desde la BD
                        Patient patient = new Patient(
                            rs.getString("name"),
                            rs.getInt("age"),
                            rs.getString("gender"),
                            Severity.valueOf(rs.getString("severity")),
                            rs.getString("entry_date"),
                            rs.getString("discharge_date"),
                            rs.getString("address"),
                            rs.getInt("patient_rut"),
                            bedId,
                            department.getName()
                        );

                        bed.setOccupant(patient);
                        occupiedBeds++;
                    } else {
                        availableBeds++;
                    }

                    // Agregar la cama al departamento usando el nuevo método
                    department.addBedDirect(bed);
                }

                // Actualizar contadores
                department.setAvailableBeds(availableBeds);
                department.setOccupiedBeds(occupiedBeds);

            }

        } catch (SQLException e) {
            System.err.println("Error al cargar camas del departamento: " + e.getMessage());
        }
    }

    // Marcar paciente como dado de alta
    public boolean dischargePatient(int patientRut, String dischargeDate) {
        String sql = "UPDATE pacientes SET discharge_date = ?, is_active = 0 WHERE rut = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, dischargeDate);
            pstmt.setInt(2, patientRut);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error al dar de alta paciente: " + e.getMessage());
            return false;
        }
    }

    // Método para cerrar la conexión a la base de datos
    public void closeConnection() {
        if (dbConnection != null) {
            dbConnection.closeConnection();
        }
    }
}
