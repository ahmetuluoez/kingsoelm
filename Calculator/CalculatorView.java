import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CalculatorView extends VBox {

    final TextField input = new TextField();
    final Label output = new Label();
    final Button equals = new Button("=");
    final Button clear = new Button("C");
    final Button[] digits = new Button[10];
    final Button plus = new Button("+");
    final Button minus = new Button("-");
    final Button times = new Button("*");
    final Button divide = new Button("/");
    final Button dot = new Button(".");

    public CalculatorView() {
        setSpacing(10);
        setPadding(new Insets(16));
        setAlignment(Pos.CENTER);

        Label title = new Label("Calculator");
        input.setPromptText("Expression");

        GridPane pad = new GridPane();
        pad.setHgap(5);
        pad.setVgap(5);
        pad.setAlignment(Pos.CENTER);

        for (int i = 0; i < 10; i++) {
            digits[i] = button(String.valueOf(i));
        }
        int n = 1;
        for (int row = 2; row >= 0; row--) {
            for (int col = 0; col < 3; col++) {
                pad.add(digits[n++], col, row);
            }
        }
        pad.add(digits[0], 0, 3, 2, 1);
        pad.add(dot, 2, 3);
        pad.add(plus, 3, 0);
        pad.add(minus, 3, 1);
        pad.add(times, 3, 2);
        pad.add(divide, 3, 3);

        equals.setPrefWidth(60);
        clear.setPrefWidth(60);
        HBox actions = new HBox(8, equals, clear);
        actions.setAlignment(Pos.CENTER);

        getChildren().addAll(title, input, pad, actions, output);
    }

    private Button button(String text) {
        Button b = new Button(text);
        b.setPrefWidth(60);
        b.setFocusTraversable(false);
        return b;
    }
}
