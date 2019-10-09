package lexer;

import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {


  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    primaryStage.setTitle("Vector de codigo intermedio");
    primaryStage.getIcons().add(new Image("file:src/lexer/analyze.png"));
    FXMLLoader fxmlLoader = new FXMLLoader(new URL(Main.class.getResource("MainView.fxml").toExternalForm()));
    Parent parent = fxmlLoader.load();
    primaryStage.setScene(new Scene(parent));
    primaryStage.show();
  }
}
