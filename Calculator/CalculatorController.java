public class CalculatorController {

    public CalculatorController(CalculatorModel model, CalculatorView view) {
        view.equals.setOnAction(e -> showResult(model, view));
        view.input.setOnAction(e -> showResult(model, view));
        view.clear.setOnAction(e -> {
            view.input.clear();
            view.output.setText("");
        });
        for (int i = 0; i < view.digits.length; i++) {
            int index = i;
            view.digits[i].setOnAction(e -> append(view, view.digits[index].getText()));
        }
        view.plus.setOnAction(e -> addOperator(view, "+"));
        view.minus.setOnAction(e -> addOperator(view, "-"));
        view.times.setOnAction(e -> addOperator(view, "*"));
        view.divide.setOnAction(e -> addOperator(view, "/"));
        view.dot.setOnAction(e -> append(view, "."));
    }

    private void showResult(CalculatorModel model, CalculatorView view) {
        view.output.setText(model.calculate(view.input.getText()));
    }

    private void append(CalculatorView view, String text) {
        view.input.appendText(text);
        view.output.setText("");
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
        view.output.setText("");
    }
}
