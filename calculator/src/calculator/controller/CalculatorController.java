package calculator.controller;

import calculator.model.CalculatorModel;
import calculator.view.CalculatorView;
import java.util.LinkedHashMap;
import java.util.Map;

/** Rechner-Controller. */
public class CalculatorController {
    private final CalculatorModel model;
    private final CalculatorView view;

    public CalculatorController(CalculatorModel model, CalculatorView view) {
        this.model = model;
        this.view = view;
        bindButtons();
        bindKeys();
        view.setDisplay(model.getDisplay());
        view.setVisible(true);
    }

    private void bindButtons() {
        String digits = "0123456789";
        for (char d : digits.toCharArray()) {
            view.addButtonListener(String.valueOf(d), e -> {
                model.appendDigit(d);
                refresh();
            });
        }
        view.addButtonListener(",", e -> {
            model.appendComma();
            refresh();
        });
        view.addButtonListener("AC", e -> {
            model.clear();
            refresh();
        });
        view.addButtonListener("⌫", e -> {
            model.backspace();
            refresh();
        });
        view.addButtonListener("%", e -> {
            model.percent();
            refresh();
        });
        view.addButtonListener("±", e -> {
            model.toggleSign();
            refresh();
        });
        view.addButtonListener("+", e -> {
            model.applyOperator('+');
            refresh();
        });
        view.addButtonListener("−", e -> {
            model.applyOperator('-');
            refresh();
        });
        view.addButtonListener("×", e -> {
            model.applyOperator('*');
            refresh();
        });
        view.addButtonListener("÷", e -> {
            model.applyOperator('/');
            refresh();
        });
        view.addButtonListener("=", e -> view.setDisplay(model.evaluate()));
    }

    private void bindKeys() {
        Map<String, Runnable> map = new LinkedHashMap<>();
        for (char d = '0'; d <= '9'; d++) {
            char digit = d;
            map.put("typed " + d, () -> {
                model.appendDigit(digit);
                refresh();
            });
        }
        map.put("typed ,", () -> {
            model.appendComma();
            refresh();
        });
        map.put("ENTER", () -> view.setDisplay(model.evaluate()));
        map.put("BACK_SPACE", () -> {
            model.backspace();
            refresh();
        });
        map.put("typed +", () -> {
            model.applyOperator('+');
            refresh();
        });
        map.put("typed -", () -> {
            model.applyOperator('-');
            refresh();
        });
        map.put("typed *", () -> {
            model.applyOperator('*');
            refresh();
        });
        map.put("typed /", () -> {
            model.applyOperator('/');
            refresh();
        });
        view.addKeyBindings(map);
    }

    private void refresh() {
        view.setDisplay(model.getDisplay());
    }
}
