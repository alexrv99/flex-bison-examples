package lexer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;

public class MainViewController {

  @FXML
  private TextArea txtInput;

  @FXML
  private TextArea txtOutput;

  @FXML
  private Button btnAnalyze;


  public void initialize() {
    txtInput.setOnKeyPressed(event -> {
      if (event.getCode() == KeyCode.ENTER && event.isControlDown()) {
        analyze();
      }
    });
  }

  public void analyze() {
    File archivo = new File("archivo.txt");
    PrintWriter printWriter;
    try {
      printWriter = new PrintWriter(archivo);
      printWriter.print(txtInput.getText());
      printWriter.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    try {
      Reader lector = new BufferedReader(new FileReader("archivo.txt"));
      Lexer lexer = new Lexer(lector);
      String output = "";
      while (true) {
        Token token = lexer.yylex();
        String string = lexer.lexeme;

        if (token == null) {
          output += "Completado";
          txtOutput.setText(output);
          return;
        }

        output += token +
            " (" + string + "," + token.getNumber() + "," + token.getTablePosition() + ","
            + (lexer.getLine() + 1) + ")\n";
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
