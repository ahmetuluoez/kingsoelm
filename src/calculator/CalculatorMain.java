package calculator;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CalculatorMain extends Application {
    // Startet die JavaFX-Anwendung und baut die Szene auf.
    @Override
    public void start(Stage primaryStage) {
        CalculatorModel model = new CalculatorModel();
        CalculatorView view = new CalculatorView();
        new CalculatorController(model, view);

        Scene scene = new Scene(view.getRoot());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Calculator");
        primaryStage.show();
    }

    // Startet die Anwendung von der Konsole aus.
    public static void main(String[] args) {
        launch(args);
    }
}
