package hospital;

public class InvalidDateException extends RuntimeException {
    public InvalidDateException(String mensaje) {
        super(mensaje);
    }

    // Constructor sin parámetros
    public InvalidDateException() {
        super("Fecha inválida, debe ser en formato YYYY-MM-DD. Ejemplo: 2023-12-31");
    }

    public static void validateDate(String fecha) throws InvalidDateException {
        if (fecha == null || fecha.trim().isEmpty()) {
            throw new InvalidDateException("La fecha no puede estar vacía.");
        }

        fecha = fecha.trim();

        // Validar longitud exacta
        if (fecha.length() != 10) {
            throw new InvalidDateException("La fecha debe tener exactamente 10 caracteres (YYYY-MM-DD).");
        }

        // Validar formato YYYY-MM-DD
        if (!fecha.matches("\\d{4}-\\d{2}-\\d{2}")) {
            throw new InvalidDateException("Formato de fecha inválido. Use YYYY-MM-DD. Ejemplo: 2023-12-31");
        }

        // Validar por rangos de la fecha
        String[] partes = fecha.split("-");
        int año = Integer.parseInt(partes[0]);
        int mes = Integer.parseInt(partes[1]);
        int dia = Integer.parseInt(partes[2]);

        // Validar mes
        if (mes < 1 || mes > 12) {
            throw new InvalidDateException("El mes debe estar entre 01 y 12.");
        }

        // Validar día
        if (dia < 1 || dia > 31) {
            throw new InvalidDateException("El día debe estar entre 01 y 31.");
        }
    }

    // Método para validar fecha de alta
    public static void validateDischargeDate(String dischargeDate, String entryDate) throws InvalidDateException {
        validateDate(dischargeDate); // Primero validar el formato

        if (entryDate != null && !entryDate.isEmpty()) {
            if (dischargeDate.compareTo(entryDate) < 0) {
                throw new InvalidDateException("La fecha de alta no puede ser anterior a la fecha de ingreso (" + entryDate + ").");
            }
        }
    }
}
