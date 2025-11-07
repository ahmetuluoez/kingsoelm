package logic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.CalculatorModel;
import view.CalculatorView;

public class CalculatorController {
    private final CalculatorModel model;
    private final CalculatorView view;
    private String currentInput = "0";

    public CalculatorController(CalculatorModel model, CalculatorView view) {
        this.model = model;
        this.view = view;
        registerListeners();
        view.updateDisplay(currentInput);
    }

    private void registerListeners() {
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleCommand(e.getActionCommand());
            }
        };
        view.addCalculatorListener(listener);
    }

    private void handleCommand(String command) {
        if (command.matches("\\d")) {
            appendDigit(command);
        } else if ("C".equals(command)) {
            clear();
        } else if ("=".equals(command)) {
            evaluate();
        } else if ("+".equals(command) || "-".equals(command) || "×".equals(command) || "÷".equals(command)) {
            setOperator(command);
        } else if (".".equals(command)) {
            appendDecimal();
        }
    }

    private void appendDigit(String digit) {
        if ("0".equals(currentInput)) {
            currentInput = digit;
        } else {
            currentInput += digit;
        }
        view.updateDisplay(currentInput);
    }

    private void appendDecimal() {
        if (!currentInput.contains(".")) {
            currentInput += ".";
            view.updateDisplay(currentInput);
        }
    }

    private void clear() {
        currentInput = "0";
        model.clear();
        view.updateDisplay(currentInput);
    }

    private void setOperator(String operator) {
        double currentValue = parseCurrentInput();
        model.setPendingOperation(currentValue, operator);
        currentInput = "0";
        view.updateDisplay(operator);
    }

    private void evaluate() {
        double currentValue = parseCurrentInput();
        double result = model.evaluate(currentValue);
        currentInput = formatResult(result);
        view.updateDisplay(currentInput);
    }

    private double parseCurrentInput() {
        return Double.parseDouble(currentInput);
    }

    private String formatResult(double value) {
        if (Double.isNaN(value) || Double.isInfinite(value)) {
            return "Error";
        }
        if (Math.abs(value - Math.rint(value)) < 1e-10) {
            return String.valueOf((long) Math.rint(value));
        }
        return String.valueOf(value);
    }
}
