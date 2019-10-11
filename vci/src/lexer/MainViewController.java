package lexer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class MainViewController {

    @FXML
    private TextArea txtInput;

    @FXML
    private TextArea txtOutput;

    @FXML
    private Button btnAnalyze;

    @FXML
    private AnchorPane visualVci;


    public void initialize() {
        txtInput.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER && event.isControlDown()) {
                analyze();
            }
        });
    }

    public void analyze() {
        GridPane grid = new GridPane();
        visualVci.getChildren().add(grid);

        File archivo = new File("archivo.txt");
        PrintWriter printWriter;
        try {
            printWriter = new PrintWriter(archivo);
            printWriter.print(txtInput.getText());
            printWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        List<VciElement> vci = new ArrayList<>();
        Stack<VciElement> op = new Stack<>();

        try {
            Reader lector = new BufferedReader(new FileReader("archivo.txt"));
            Lexer lexer = new Lexer(lector);
            StringBuilder output = new StringBuilder();
            while (true) {
                Token token = lexer.yylex();
                String string = lexer.lexeme;


                if (token == null) {
                    vci.forEach(vciElement -> {
                        System.out.println("[" + vciElement.getString() + "]");
                        output.append(" [").append(vciElement.getString()).append("]\t").append(vciElement.getToken()).append(" \n");
                    });
                    output.append("Completado");
                    txtOutput.setText(output.toString());
                    break;
                }


                VciElement vciElement = new VciElement(token, string, token.getPriority());
                System.out.println(vciElement);


                // Logica del VCI
                if (token.equals(Token.AbreParentesis)) {
                    op.push(vciElement); // sin preguntar
                } else if (token.equals(Token.CierraParentesis)) {
                    // vacia la pila hasta el primer parentesis
                    while (!op.peek().getToken().equals(Token.AbreParentesis)) {
                        VciElement operador = op.pop();
                        vci.add(operador);
                    }
                    op.pop(); // quitar parentesis que cierra
                } else if (vciElement.getToken().equals(Token.Identificador) || vciElement.getToken().equals(Token.Enteros)) {
                    vci.add(vciElement);
                }
                else if (vciElement.getToken().equals(Token.PuntoYComa)) {
                    while (!op.isEmpty()) {
                        VciElement operador = op.pop();
                        vci.add(operador);
                    }
                }
                else {
                    // Para operadores

                    if (op.isEmpty()) {
                        op.push(vciElement);
                        continue;
                    }

                    VciElement ultimo = op.peek();
                    System.out.println("This is the last: " + ultimo);
                    // si es el primero
                    if (vciElement.getPriority() <= ultimo.getPriority()) {
                        VciElement operador = op.pop();
                        vci.add(operador);
                    }
                    op.push(vciElement);
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
