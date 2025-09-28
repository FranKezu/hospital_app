package hospital;

public class InvalidRutException extends Exception {
    public InvalidRutException(String mensaje) {
        super(mensaje);
    }

    // Constructor sin parámetros
    public InvalidRutException() {
        super("RUT inválido.");
    }

    public static void validateWithoutHyphen(String rut) throws InvalidRutException {
        if (rut == null || rut.trim().isEmpty()) {
            throw new InvalidRutException("El RUT no puede estar vacío.");
        }

        rut = rut.trim();

        // Validar que solo contenga dígitos
        if (!rut.matches("\\d+")) {
            throw new InvalidRutException("El RUT debe contener solo números.");
        }

        // Validar longitud (7-8 dígitos)
        if (rut.length() < 7 || rut.length() > 8) {
            throw new InvalidRutException("El RUT debe tener entre 7 y 8 dígitos.");
        }
    }

    // Método más completo que incluye validación de duplicados
    public static int validateComplete(String rut, Hospital hospital) throws InvalidRutException {
        // Primero validar el formato
        validateWithoutHyphen(rut);

        // Convertir a int
        int rutInt = Integer.parseInt(rut.trim());

        // Verificar si el paciente ya existe
        if (hospital.findPatient(rutInt) != null) {
            throw new InvalidRutException("Ya existe un paciente registrado con este RUT.");
        }

        return rutInt;
    }

    // Método para validar RUT en búsquedas (sin verificar duplicados)
    public static int validateForSearch(String rut) throws InvalidRutException {
        validateWithoutHyphen(rut);
        return Integer.parseInt(rut.trim());
    }
}
