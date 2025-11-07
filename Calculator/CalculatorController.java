import java.util.Set;

public class CalculatorController {

    private static final Set<String> ERRORS = Set.of(
        "no input",
        "Division by 0!",
        "Invalid input!"
    );

    public CalculatorController(CalculatorModel model, CalculatorView view) {
        view.equals.setOnAction(e -> showResult(model, view));
        view.input.setOnAction(e -> showResult(model, view));
        view.input.setOnKeyTyped(e -> {
            if (!view.input.getText().isEmpty()) {
                view.output.setText("0");
            }
        });
        view.clear.setOnAction(e -> {
            view.input.clear();
            view.output.setText("0");
        });
        for (int i = 0; i < view.digits.length; i++) {
            int index = i;
            view.digits[i].setOnAction(e -> append(view, view.digits[index].getText()));
        }
        view.plus.setOnAction(e -> addOperator(view, "+"));
        view.minus.setOnAction(e -> addOperator(view, "-"));
        view.times.setOnAction(e -> addOperator(view, "*"));
        view.divide.setOnAction(e -> addOperator(view, "/"));
    }

    private void showResult(CalculatorModel model, CalculatorView view) {
        String result = model.calculate(view.input.getText());
        view.output.setText(result);
        if (!ERRORS.contains(result)) {
            view.input.clear();
        }
    }

    private void append(CalculatorView view, String text) {
        if (view.input.getText().isEmpty() && !view.output.getText().equals("0")) {
            view.output.setText("0");
        }
        view.input.appendText(text);
    }

    private void addOperator(CalculatorView view, String op) {
        String text = view.input.getText();
        if (text.isEmpty() && !"-".equals(op)) {
            return;
        }
        if (text.endsWith(" ")) {
            text = text.trim();
        }
        view.input.setText(text + " " + op + " ");
        view.input.positionCaret(view.input.getText().length());
    }
}
