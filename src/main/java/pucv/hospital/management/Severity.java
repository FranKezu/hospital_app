package pucv.hospital.management;

public enum Severity {
    Critical(5),
    Severe(4),
    Moderate(3),
    Mild(2),
    Minimal(1);
    
    private final int level;

    // Colocamos el constructor como private porque las enumeraciones de java por defecto se tratan como privados,
    // esta clase solo instancia los niveles de severidad, por lo tanto no necesita la creación de instancias externas.

    private Severity(int level){
        this.level = level;
    }

    public int getLevel(){
        return level;
    }
}
