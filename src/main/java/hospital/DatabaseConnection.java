package hospital;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    private static final String DB_URL = "jdbc:sqlite:hospital.db";
    private static DatabaseConnection instance;
    private Connection connection;

    private DatabaseConnection() {
        try {
            // Cargar explícitamente el driver de SQLite
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DB_URL);
            initializeDatabase();
        } catch (ClassNotFoundException e) {
            System.err.println("Driver SQLite no encontrado: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Error al conectar con la base de datos: " + e.getMessage());
        }
    }

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(DB_URL);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener conexión: " + e.getMessage());
        }
        return connection;
    }

    private void initializeDatabase() {
        try (Statement stmt = connection.createStatement()) {
            // Crear tabla departamentos si no existe
            String createDepartmentsTable = "CREATE TABLE IF NOT EXISTS departamentos (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL UNIQUE, " +
                "description TEXT NOT NULL, " +
                "available_beds INTEGER DEFAULT 0, " +
                "occupied_beds INTEGER DEFAULT 0, " +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ")";

            // Crear tabla pacientes
            String createPatientsTable = "CREATE TABLE IF NOT EXISTS pacientes (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "rut INTEGER NOT NULL UNIQUE, " +
                "name TEXT NOT NULL, " +
                "age INTEGER NOT NULL, " +
                "gender TEXT NOT NULL, " +
                "severity TEXT NOT NULL, " +
                "entry_date TEXT NOT NULL, " +
                "discharge_date TEXT, " +
                "address TEXT NOT NULL, " +
                "department_name TEXT NOT NULL, " +
                "bed_id INTEGER NOT NULL, " +
                "is_active BOOLEAN DEFAULT 1, " +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ")";

            // Crear tabla camas
            String createBedsTable = "CREATE TABLE IF NOT EXISTS camas (" +
                "id INTEGER PRIMARY KEY, " +
                "department_name TEXT NOT NULL, " +
                "is_available BOOLEAN DEFAULT 1, " +
                "patient_rut INTEGER, " +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                "UNIQUE(id, department_name)" +
                ")";

            stmt.execute(createDepartmentsTable);
            stmt.execute(createPatientsTable);
            stmt.execute(createBedsTable);
            System.out.println("Base de datos inicializada correctamente.");

        } catch (SQLException e) {
            System.err.println("Error al inicializar la base de datos: " + e.getMessage());
        }
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
}
