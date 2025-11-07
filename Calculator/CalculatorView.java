import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class CalculatorView extends VBox {

    final Label output = new Label("0");
    final TextField input = new TextField();
    final Button equals = new Button("=");
    final Button clear = new Button("C");
    final Button[] digits = new Button[10];
    final Button plus = new Button("+");
    final Button minus = new Button("-");
    final Button times = new Button("*");
    final Button divide = new Button("/");

    public CalculatorView() {
        setSpacing(12);
        setPadding(new Insets(16));
        setAlignment(Pos.CENTER);

        Label title = new Label("Calculator");
        output.setStyle("-fx-font-size: 20px;");
        output.setMaxWidth(Double.MAX_VALUE);
        output.setAlignment(Pos.CENTER_RIGHT);

        input.setPromptText("Expression");

        GridPane pad = new GridPane();
        pad.setHgap(6);
        pad.setVgap(6);
        pad.setAlignment(Pos.CENTER);

        for (int i = 0; i < 10; i++) {
            digits[i] = button(String.valueOf(i));
        }

        pad.add(digits[1], 0, 0);
        pad.add(digits[2], 1, 0);
        pad.add(digits[3], 2, 0);

        pad.add(digits[4], 0, 1);
        pad.add(digits[5], 1, 1);
        pad.add(digits[6], 2, 1);

        pad.add(digits[7], 0, 2);
        pad.add(digits[8], 1, 2);
        pad.add(digits[9], 2, 2);

        pad.add(clear, 0, 3);
        pad.add(digits[0], 1, 3);
        pad.add(equals, 2, 3);

        pad.add(plus, 3, 0);
        pad.add(minus, 3, 1);
        pad.add(times, 3, 2);
        pad.add(divide, 3, 3);

        equals.setPrefWidth(60);
        clear.setPrefWidth(60);

        getChildren().addAll(title, output, input, pad);
    }

    private Button button(String text) {
        Button b = new Button(text);
        b.setPrefWidth(60);
        b.setFocusTraversable(false);
        return b;
    }
}
