import java.util.HashMap;
import java.util.Map;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class CalculatorView {
    private final TextField display;
    private final Map<String, Button> buttons = new HashMap<>();
    private final BorderPane root;

    // Erstellt die Benutzeroberfläche des Rechners.
    public CalculatorView() {
        display = new TextField("0");
        display.setEditable(false);
        display.setAlignment(Pos.CENTER_RIGHT);
        display.setFocusTraversable(false);
        display.setStyle("-fx-font-size: 32;");

        GridPane buttonGrid = createButtonGrid();

        VBox content = new VBox(20, display, buttonGrid);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(30));

        root = new BorderPane(content);
        root.setPrefSize(320, 480);
    }

    // Liefert die Wurzel des Layouts für die Scene.
    public Parent getRoot() {
        return root;
    }

    // Aktualisiert den Text der Anzeige.
    public void setDisplayText(String text) {
        display.setText(text);
    }

    // Liefert den Button zu einem Label.
    public Button getButton(String label) {
        return buttons.get(label);
    }

    // Erstellt das Tastenfeld und legt alle Buttons an.
    private GridPane createButtonGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(12);
        grid.setVgap(12);
        grid.setAlignment(Pos.CENTER);

        String[][] layout = {
            {"C", "", "", "÷"},
            {"7", "8", "9", "×"},
            {"4", "5", "6", "-"},
            {"1", "2", "3", "+"},
            {"0", "", ".", "="}
        };

        for (int row = 0; row < layout.length; row++) {
            for (int col = 0; col < layout[row].length; col++) {
                String label = layout[row][col];
                if (label.isEmpty()) {
                    continue;
                }
                Button button = createButton(label);
                if (label.equals("C")) {
                    button.setPrefWidth(140);
                    GridPane.setColumnSpan(button, 3);
                } else if (label.equals("0")) {
                    button.setPrefWidth(140);
                    GridPane.setColumnSpan(button, 2);
                }
                grid.add(button, col, row);
            }
        }
        return grid;
    }

    // Legt einen einzelnen Button mit Standardstil an.
    private Button createButton(String label) {
        Button button = new Button(label);
        button.setPrefSize(64, 64);
        button.setFocusTraversable(false);
        button.setStyle("-fx-font-size: 20; -fx-background-radius: 32; -fx-border-radius: 32; -fx-min-width: 64; -fx-min-height: 64;");
        buttons.put(label, button);
        return button;
    }
}
