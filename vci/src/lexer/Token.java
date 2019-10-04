package lexer;

public enum Token {
  // palabras reservadas
  Prog(-1, -1),
  Var(-2, -1),
  Proc(-3, -1),
  Inicio(-4, -1),
  Final(-5, -1),
  Entero(-6, -1),
  Real(-7, -1),
  String(-8, -1),
  Limpiar(-9, -1),
  Vexy(-10, -1),
  Leer(-11, -1),
  Escribir(-12, -1),
  Repite(-13, -1),
  Hasta(-14, -1),
  Mientras(-15, -1),
  Si(-16, -1),
  SiNo(-17, -1),
  Ejecuta(-18, -1),
  And(-19, -1),
  Or(-20, -1),
  Hola(-21, -1),

  Suma(-31, -1),
  Resta(-32, -1),
  Multiplicacion(-33, -1),
  Division(-34, -1),
  Igual(-35, -1),

  MayorIgual(-41, -1),
  Mayor(-42, -1),
  MenorIgual(-43, -1),
  Menor(-44, -1),
  Diferente(-45, -1),

  PuntoYComa(-51, -1),
  CierraCorchete(-52, -1),
  AbreCorchete(-53, -1),
  Coma(-54, -1),
  DosPuntos(-55, -1),
  CierraParentesis(-56, -1),
  AbreParentesis(-57, -1),
  DosPuntosIgual(-58, -1),

  Identificador(-61, -2),
  Enteros(-62, -1),
  Reales(-63, -1),
  Cadena(-64, -1),
  
  LogicoAND(-71, -1),
  LogicoOR(-72, -1),
  Negacion(-73, -1),

  Comentario(-80, -1),
  
  ERROR(-100, -1);


  private int number;
  private int tablePosition;

  Token(int number, int tablePosition) {
    this.number = number;
    this.tablePosition = tablePosition;
  }

  public int getNumber() {
    return number;
  }

  public int getTablePosition() {
    return tablePosition;
  }
}
