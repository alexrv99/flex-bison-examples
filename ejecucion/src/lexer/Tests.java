package lexer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class Tests {

  @Test
  public void palabrasClave() {
    Reader reader = new StringReader(
        "PROG VAR PROC INICIO FIN ENTERO REAL STRING LIMPIAR VEXY LEER ESCRIBIR REPITE HASTA MIENTRAS SI SINO EJECUTA AND OR");
    List<Token> actualTokens = getTokens(reader);
    Assert.assertTrue(actualTokens.stream().allMatch(token ->
        token.getNumber() <= -1 && token.getNumber() >= -20)
    );
  }

  @Test
  public void constantes() {
    List<Token> expectedTokens = new ArrayList<>();
    expectedTokens.add(Token.Cadena);
    expectedTokens.add(Token.Enteros);
    expectedTokens.add(Token.Reales);

    Reader reader = new StringReader("\"cadena\" 5313 1512.65");
    List<Token> actualTokens = getTokens(reader);
    Assert.assertEquals(expectedTokens, actualTokens);
  }

  @Test
  public void codigoCompleto() throws FileNotFoundException {
    Reader reader = new BufferedReader(new FileReader("pruebas/codigo-completo.txt"));
    List<Token> actualTokens = getTokens(reader);
    System.out.println(actualTokens);
    Assert.assertFalse(actualTokens.contains(Token.ERROR));
  }

  @Test
  public void errorConMultiplesPuntosDecimales() {
    Reader reader = new StringReader("2.6.5");
    List<Token> actualTokens = getTokens(reader);
    System.out.println(actualTokens);
    Assert.assertTrue(actualTokens.contains(Token.ERROR));
  }

  @Test
  public void errorConUnaSolaComilla() {
    Reader reader = new StringReader("\"");
    List<Token> actualTokens = getTokens(reader);
    Assert.assertTrue(actualTokens.contains(Token.ERROR));
  }

  @Test
  public void errorIdentificadorConNumerosDePrefijo() {
    Reader reader = new StringReader("15numero");
    List<Token> actualTokens = getTokens(reader);
    Assert.assertTrue(actualTokens.contains(Token.ERROR));
  }

  private List<Token> getTokens(Reader reader) {
    Lexer lexer = new Lexer(reader);

    List<Token> tokens = new ArrayList<>();
    try {
      while (true) {
        Token token = lexer.yylex();
        if (token == null) {
          reader.close();
          break;
        }
        tokens.add(token);
        System.out.println(token);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return tokens;
  }
}
