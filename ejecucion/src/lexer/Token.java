package lexer;

public enum Token {
    // palabras reservada
    Entero(-6, -1),
    Real(-7, -1),
    And(-19, -1),
    Or(-20, -1),
    Suma(-31, -1, 50),
    Resta(-32, -1, 50),
    Multiplicacion(-33, -1, 60),
    Division(-34, -1, 60),
    Igual(-35, -1, 0),
    MayorIgual(-41, -1, 40),
    Mayor(-42, -1, 40),
    MenorIgual(-43, -1, 40),
    Menor(-44, -1, 40),
    Diferente(-45, -1),
    IgualIgual(-46, -1, 40),
    PuntoYComa(-51, -1),
    Coma(-54, -1),
    DosPuntos(-55, -1),
    CierraParentesis(-56, -1),
    AbreParentesis(-57, -1, 0),
    Identificador(-61, -2),
    Enteros(-62, -1),
    Reales(-63, -1),
    Cadena(-64, -1),
    LogicoAND(-71, -1, 20),
    LogicoOR(-72, -1, 10),
    Negacion(-73, -1, 30),
    Comentario(-80, -1),
    If(-100, -1),
    Then(-101, -1),
    Else(-102, -1),
    End(-103, -1),
    Begin(-104, -1),
    Read(-200, -1),
    Write(-201, -1),
    ERROR(-1000, -1);


    private int number;
    private int tablePosition;
    private Integer priority;


    Token(int number, int tablePosition) {
        this.number = number;
        this.tablePosition = tablePosition;
    }

    Token(int number, int tablePosition, Integer priority) {
        this.number = number;
        this.tablePosition = tablePosition;
        this.priority = priority;
    }

    public int getNumber() {
        return number;
    }

    public int getTablePosition() {
        return tablePosition;
    }

    public Integer getPriority() {
        return priority;
    }
}
