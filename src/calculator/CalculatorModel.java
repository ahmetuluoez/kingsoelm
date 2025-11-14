package calculator;

public class CalculatorModel {
    private double storedValue = 0.0;
    private String pendingOperator = null;
    private String displayValue = "0";
    private boolean resetOnNextDigit = false;
    private boolean errorState = false;

    // Fügt eine Ziffer zur aktuellen Eingabe hinzu.
    public void appendDigit(String digit) {
        if (errorState) {
            return;
        }
        if (resetOnNextDigit) {
            displayValue = digit;
            resetOnNextDigit = false;
        } else if (displayValue.equals("0")) {
            displayValue = digit;
        } else {
            displayValue += digit;
        }
    }

    // Fügt einen Dezimalpunkt zur aktuellen Eingabe hinzu.
    public void appendDecimalPoint() {
        if (errorState) {
            return;
        }
        if (resetOnNextDigit) {
            displayValue = "0.";
            resetOnNextDigit = false;
        } else if (!displayValue.contains(".")) {
            displayValue += ".";
        }
    }

    // Setzt den Operator für die nächste Berechnung.
    public void setOperator(String operator) {
        if (errorState) {
            return;
        }
        storedValue = parseDisplayValue();
        pendingOperator = operator;
        resetOnNextDigit = true;
    }

    // Führt die Berechnung basierend auf dem gespeicherten Operator aus.
    public void calculate() {
        if (errorState || pendingOperator == null || resetOnNextDigit) {
            return;
        }
        double currentValue = parseDisplayValue();
        double result;
        switch (pendingOperator) {
            case "+":
                result = storedValue + currentValue;
                break;
            case "-":
                result = storedValue - currentValue;
                break;
            case "×":
                result = storedValue * currentValue;
                break;
            case "÷":
                if (currentValue == 0.0) {
                    setErrorState();
                    return;
                }
                result = storedValue / currentValue;
                break;
            default:
                return;
        }
        displayValue = formatResult(result);
        storedValue = result;
        pendingOperator = null;
        resetOnNextDigit = true;
    }

    // Löscht alle gespeicherten Werte und setzt den Rechner zurück.
    public void clear() {
        storedValue = 0.0;
        pendingOperator = null;
        displayValue = "0";
        resetOnNextDigit = false;
        errorState = false;
    }

    // Gibt die aktuelle Anzeige zurück.
    public String getDisplayValue() {
        return errorState ? "Error" : displayValue;
    }

    // Prüft, ob sich das Modell im Fehlerzustand befindet.
    public boolean isError() {
        return errorState;
    }

    // Setzt das Modell in den Fehlerzustand.
    private void setErrorState() {
        errorState = true;
        displayValue = "Error";
        pendingOperator = null;
    }

    // Wandelt den Anzeigestring in eine Zahl um.
    private double parseDisplayValue() {
        return Double.parseDouble(displayValue);
    }

    // Formatiert das Ergebnis für die Anzeige.
    private String formatResult(double value) {
        if (Math.abs(value - Math.rint(value)) < 1e-10) {
            return String.valueOf((long) Math.rint(value));
        }
        return String.valueOf(value);
    }
}
