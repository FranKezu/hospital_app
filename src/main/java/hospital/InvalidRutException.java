package hospital;

public class InvalidRutException extends Exception {
    private int rutIngresado;
    private String tipoError;

    // Constructor principal para errores de RUT (solo acepta int)
    public InvalidRutException(int rut, String tipoError) {
        super("RUT inválido: " + rut + ". " + tipoError);
        this.rutIngresado = rut;
        this.tipoError = tipoError;
    }

    // Constructor para mensajes genéricos
    public InvalidRutException(String mensaje) {
        super(mensaje);
        this.tipoError = "Error general";
    }

    // Getters para información adicional
    public int getRutIngresado() {
        return rutIngresado;
    }

    public String getTipoError() {
        return tipoError;
    }

    // Método para obtener sugerencia de corrección
    public String getSugerencia() {
        if (tipoError != null && tipoError.contains("longitud")) {
            return "El RUT debe tener entre 7 y 8 dígitos";
        } else if (tipoError != null && tipoError.contains("rango")) {
            return "El RUT debe ser un número positivo válido";
        } else if (tipoError != null && tipoError.contains("negativo")) {
            return "El RUT no puede ser un número negativo";
        } else if (tipoError != null && tipoError.contains("formato")) {
            return "El RUT no puede contener puntos (.) ni guiones (-). Ingrese solo números. Ejemplo: 12345678";
        } else {
            return "Verifique que el RUT sea válido para el sistema hospitalario";
        }
    }
}