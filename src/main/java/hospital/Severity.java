package hospital;

public enum Severity {
    Minimo(1),
    Medio(2),
    Moderado(3),
    Severo(4),
    Critico(5);

    private final int level;


    Severity(int level) {
        this.level = level;
    }

    public int getSeverity() {
        return level;
    }
}

