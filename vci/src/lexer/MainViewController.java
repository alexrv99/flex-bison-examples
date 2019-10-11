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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;

public class MainViewController {

    @FXML
    private TextArea txtInput;

    @FXML
    private FlowPane flowPane;


    public void initialize() {
        flowPane.setVgap(10);
        flowPane.setHgap(10);
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
                    flowPane.getChildren().clear();
                    for (int i = 0; i < vci.size(); i++) {
                        VciElement vciElement = vci.get(i);
                        VBox vBox = new VBox();
                        vBox.setSpacing(5);
                        vBox.setPadding(new Insets(15, 30, 15, 30));
                        vBox.setStyle("-fx-border-color: #c5c5c5; -fx-border-width: 1px; -fx-border-style: solid; -fx-border-radius: 10px");
                        vBox.setAlignment(Pos.CENTER);


                        Label tokenLabel = new Label(vciElement.getString());
                        tokenLabel.setStyle("-fx-font-size: 25px");

                        Label numberLabel = new Label(String.valueOf(i));
                        vBox.getChildren().add(tokenLabel);
                        vBox.getChildren().add(numberLabel);

                        flowPane.getChildren().add(vBox);
                    }
                    output.append("Completado");

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
                    // Estos tokens se agregan directamente a la pila
                    vci.add(vciElement);
                } else if (vciElement.getToken().equals(Token.PuntoYComa)) {
                    // Vaciar pila de operadores
                    while (!op.isEmpty()) {
                        VciElement operador = op.pop();
                        vci.add(operador);
                    }
                } else {
                    // Para los operadores

                    if (op.isEmpty()) {
                        op.push(vciElement);
                        continue;
                    }

                    // si es el primero
                    while (!op.isEmpty() && vciElement.getPriority() <= op.peek().getPriority()) {
                        VciElement operador = op.pop();
                        System.out.println("Popped: " + operador);
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
