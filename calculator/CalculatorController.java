import java.util.Arrays;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class CalculatorController {
    private final CalculatorModel model;
    private final CalculatorView view;

    // Verknüpft das Modell mit der View und registriert alle Listener.
    public CalculatorController(CalculatorModel model, CalculatorView view) {
        this.model = model;
        this.view = view;
        initializeHandlers();
    }

    // Richtet die Ereignisbehandlung für alle Buttons ein.
    private void initializeHandlers() {
        List<String> digits = Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");
        for (String digit : digits) {
            view.getButton(digit).setOnAction(handleDigit(digit));
        }

        view.getButton(".").setOnAction(event -> {
            model.appendDecimalPoint();
            updateDisplay();
        });

        List<String> operators = Arrays.asList("+", "-", "×", "÷");
        for (String operator : operators) {
            view.getButton(operator).setOnAction(event -> {
                model.setOperator(operator);
                updateDisplay();
            });
        }

        view.getButton("=").setOnAction(event -> {
            model.calculate();
            updateDisplay();
        });

        view.getButton("C").setOnAction(event -> {
            model.clear();
            updateDisplay();
        });
    }

    // Erzeugt einen Handler für die Zahlentasten.
    private EventHandler<ActionEvent> handleDigit(String digit) {
        return event -> {
            model.appendDigit(digit);
            updateDisplay();
        };
    }

    // Aktualisiert die Anzeige auf Grundlage des Modells.
    private void updateDisplay() {
        view.setDisplayText(model.getDisplayValue());
    }
}
