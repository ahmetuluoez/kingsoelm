package Woche2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TaschenrechnerApp extends Application {

    @Override
    public void start(Stage stage) {
        TaschenrechnerModel model = new TaschenrechnerModel();
        TaschenrechnerView view = new TaschenrechnerView();
        new TaschenrechnerController(model, view);

        Scene scene = new Scene(view, 400, 250);
        stage.setTitle("Taschenrechner");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
