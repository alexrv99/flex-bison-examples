package lexer;

import java.io.File;

public class Generador {

  public static void main(String[] args) {
    String ruta = "src/lexer/Lexer.flex";
    generarLexer(ruta);
  }

  public static void generarLexer(String ruta) {
    File archivo = new File(ruta);
    JFlex.Main.generate(archivo);
  }
}
