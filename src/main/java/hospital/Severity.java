package hospital;

public enum Severity {
    Critico(5),
    Severo(4),
    Moderado(3),
    Medio(2),
    Minimo(1);
    
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
