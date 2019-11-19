package lexer;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class MainViewController {

    @FXML
    private TextArea txtInput;

    @FXML
    private TextArea txtOutput;

    @FXML
    private FlowPane flowPane;

    @FXML
    private HBox hBoxStack;

    @FXML
    private Label lblError;

    private int column = 0;

    private boolean poppedLast = false;


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
        Stack<VciElement> est = new Stack<>();
        Stack<Integer> dir = new Stack<>();

        List<LexerElement> elements;
        try {
            elements = new ArrayList<>();
            Reader lector = new BufferedReader(new FileReader("archivo.txt"));
            Lexer lexer = new Lexer(lector);
            while (true) {
                Token token = lexer.yylex();
                String string = lexer.lexeme;
                if (token == null) {
                    break;
                }
                elements.add(new LexerElement(string, token));
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        elements.forEach(System.out::println);

        StringBuilder output;
        try {
            output = new StringBuilder();
            for (int i = 0; i < elements.size(); i++) {
                LexerElement element = elements.get(i);
                Token token = element.getToken();
                String string = element.getString();


                VciElement vciElement = new VciElement(token, string, token.getPriority());


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
                } else if (token.equals(Token.If)) {
                    est.push(vciElement);
                } else if (token.equals(Token.Then)) {
                    op.clear();
                    dir.push(vci.size());
                    vci.add(null);
                    vci.add(vciElement);
                } else if (token.equals(Token.Else)) {
                    est.push(vciElement);
                    int position = dir.pop();
                    vci.set(position, new VciElement(null, String.valueOf(vci.size() + 2), null));
                    dir.push(vci.size());
                    vci.add(null); // token falso
                    vci.add(vciElement); // genera token else
                } else if (token.equals(Token.End)) {
                    if ((i + 1) == elements.size()) {
                        int position = dir.pop();
                        vci.set(position, new VciElement(null, String.valueOf(vci.size()), null));
                    } else if (!elements.get(i + 1).getToken().equals(Token.Else)) {
                        int position = dir.pop();
                        vci.set(position, new VciElement(null, String.valueOf(vci.size()), null));
                    }

                } else if (token.equals(Token.Read) || token.equals(Token.Write)) {
                    vci.add(vciElement);
                } else if (token.equals(Token.Cadena)) {
                    System.out.println("Added cadena: " + string);
                    vci.add(vciElement);
                } else {
                    // Para los operadores

                    if (op.isEmpty()) {
                        op.push(vciElement);
                        continue;
                    }

                    // si es el primero
                    while (!op.isEmpty() && vciElement.getPriority() <= op.peek().getPriority()) {
                        VciElement operador = op.pop();
                        vci.add(operador);
                    }
                    op.push(vciElement);
                }
            }
        } catch (Exception e) {
            lblError.setVisible(true);
            lblError.setText("Ha ocurrido un error lexico");
            e.printStackTrace();
            return;
        }

        vci.forEach(vciElement -> {
            System.out.println("[" + vciElement.getString() + "]");
            output.append(" [").append(vciElement.getString()).append("]\t").append(vciElement.getToken()).append(" \n");
        });
        flowPane.getChildren().clear();


        for (int i = 0; i < vci.size(); i++) {
            VciElement vciElement = vci.get(i);
            VBox vBox = new VBox();
            vBox.setSpacing(5);
            vBox.setPadding(new Insets(8, 12, 8, 12));
            vBox.setStyle("-fx-border-color: #c5c5c5; -fx-border-width: 1px; -fx-border-style: solid; -fx-border-radius: 10px");
            vBox.setAlignment(Pos.CENTER);


            Label tokenLabel = new Label(vciElement.getString());
            tokenLabel.setStyle("-fx-font-size: 15px");

            Label numberLabel = new Label(String.valueOf(i));
            vBox.getChildren().add(tokenLabel);
            vBox.getChildren().add(numberLabel);

            flowPane.getChildren().add(vBox);
        }
        output.append("Completado");


        // ############### INICIO DE EJECUCION ###############

        HashMap<String, Double> symbolsTable = null;
        try {
            hBoxStack.getChildren().clear();
            txtOutput.clear();
            column = 0;
            lblError.setVisible(false);


            symbolsTable = new HashMap<>();

            Stack<String> ej = new Stack<>();

            for (int i = 0; i < vci.size(); i++) {
                VciElement vciElement = vci.get(i);


                if (vciElement.getToken() == null) {
                    addToExecutionStack(vciElement.getString(), ej);
                } // operadores binarios
                else if (vciElement.getToken().getPriority() != null && vciElement.getToken().getPriority() != 0 && vciElement.getToken().getPriority() != 30) {

                    double operand2 = 0;
                    String item = removeFromExecutionStack(ej);
                    try {
                        operand2 = Double.parseDouble(item);
                    } catch (NumberFormatException e) {
                        operand2 = symbolsTable.get(item);
                    }

                    double operand1 = 0;
                    item = removeFromExecutionStack(ej);
                    try {
                        operand1 = Double.parseDouble(item);
                    } catch (NumberFormatException e) {
                        operand1 = symbolsTable.get(item);
                    }

                    double res = 0;
                    switch (vciElement.getToken()) {
                        case Suma:
                            res = operand1 + operand2;
                            break;
                        case Resta:
                            res = operand1 - operand2;
                            break;
                        case Multiplicacion:
                            res = operand1 * operand2;
                            break;
                        case Division:
                            res = operand1 / operand2;
                            break;
                        case LogicoAND:
                            res = (operand1 == 1) && (operand2 == 1) ? 1 : 0;
                            break;
                        case LogicoOR:
                            res = (operand1 == 1) || (operand2 == 1) ? 1 : 0;
                            break;
                        case IgualIgual:
                            res = operand1 == operand2 ? 1 : 0;
                            break;
                        case Menor:
                            res = operand1 < operand2 ? 1 : 0;
                            break;
                        case MenorIgual:
                            res = operand1 <= operand2 ? 1 : 0;
                            break;
                        case Mayor:
                            res = operand1 > operand2 ? 1 : 0;
                            break;
                        case MayorIgual:
                            res = operand1 >= operand2 ? 1 : 0;
                            break;
                        case Diferente:
                            res = operand1 != operand2 ? 1 : 0;
                            break;
                    }
                    System.out.println("Result of " + operand1 + vciElement.getString() + operand2 + ": " + res);
                    addToExecutionStack(String.valueOf(res), ej);
                } else if (vciElement.getToken().equals(Token.Negacion)) {
                    int num = Integer.parseInt(removeFromExecutionStack(ej));
                    num = num == 1 ? 0 : 1;
                    addToExecutionStack(String.valueOf(num), ej);
                } else if (vciElement.getToken().equals(Token.Igual)) {
                    double value = Double.parseDouble(removeFromExecutionStack(ej));
                    String identifier = removeFromExecutionStack(ej);
                    symbolsTable.put(identifier, value);
                } else if (vciElement.getToken().equals(Token.Enteros) || vciElement.getToken().equals(Token.Identificador) || vciElement.getToken().equals(Token.Cadena)) {
                    addToExecutionStack(vciElement.getString(), ej);
                } else if (vciElement.getToken().equals(Token.Write)) {
                    VciElement nextVciElement = vci.get(i + 1);
                    i++; // saltar siguiente valor

                    if (nextVciElement.getToken().equals(Token.Cadena)) {
                        txtOutput.setText(txtOutput.getText() + nextVciElement.getString() + "\n");
                    } else {
                        String item = nextVciElement.getString();
                        double value;
                        try {
                            value = Double.parseDouble(item);
                        } catch (NumberFormatException e) {
                            value = symbolsTable.get(item);
                        }
                        txtOutput.setText(txtOutput.getText() + value + "\n");
                    }
                } else if (vciElement.getToken().equals(Token.Read)) {
                    i++; // saltar siguiente valor
                } else if (vciElement.getToken().equals(Token.Then)) {
                    double pcAux = Double.parseDouble(removeFromExecutionStack(ej));
                    System.out.println("pc_aux: " + pcAux);
                    double vv = Double.parseDouble(removeFromExecutionStack(ej));
                    System.out.println("vv: " + vv);
                    if (vv == 0) {
                        i = (int) pcAux - 1; // minus 1 because the for increments already
                    }
                } else if (vciElement.getToken().equals(Token.Else)) {
                    i = Integer.parseInt(removeFromExecutionStack(ej)) - 1; // salto incondicional
                }
            }
        } catch (Exception e) {
            lblError.setVisible(true);
            lblError.setText("Ha ocurrido un error en la ejecucion");
            e.printStackTrace();
        }
        System.out.println(symbolsTable);
    }

    private void addToExecutionStack(String string, Stack<String> stack) {
        stack.push(string);
        System.out.println("size: " + stack.size());

        if (hBoxStack.getChildren().isEmpty()) {
            VBox vbox = new VBox();
            vbox.setAlignment(Pos.BOTTOM_CENTER);
            vbox.setSpacing(10);
            hBoxStack.getChildren().add(vbox);
        }

        if (poppedLast) {
            column++;
            poppedLast = false;

            VBox vbox = new VBox();
            vbox.setAlignment(Pos.BOTTOM_CENTER);
            vbox.setSpacing(10);
            hBoxStack.getChildren().add(vbox);
            VBox vBox = (VBox) hBoxStack.getChildren().get(column);
            for (int i = 0; i < stack.size() - 1; i++) {
                Label stringLabel = new Label();
                stringLabel.setStyle("-fx-strikethrough: true; -fx-font-size: 16px; -fx-padding: 5px 10px");
                vBox.getChildren().add(0, stringLabel);
            }
        }
        VBox vBox = (VBox) hBoxStack.getChildren().get(column);
        Label stringLabel = new Label(string);
        stringLabel.setStyle("-fx-strikethrough: true; -fx-font-size: 16px; -fx-padding: 5px 10px");
        vBox.getChildren().add(0, stringLabel);
    }

    private String removeFromExecutionStack(Stack<String> stack) {
        poppedLast = true;
        return stack.pop();
    }
}
