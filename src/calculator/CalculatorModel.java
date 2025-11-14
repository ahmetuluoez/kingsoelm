package calculator;

public class CalculatorModel {
    private double storedValue = 0.0;    /* dieser double-wert merkt sich den wert der ersten zahl bei einer
    anfangs als ein null deklariert, weil wir noch nichts eingegeben haben am anfang. */
    private String pendingOperator = null;
    // und auch hier das gleiche mit dem operator.

    // Wird intern für Berechnungen benutzt
    private String displayValue = "0";

    // kompletter Ausdruck für die Anzeige (z. B. "32+32")
    private String expression = "";

    // Steuert, ob nach "=" ein neues Rechnen gestartet werden soll
    private boolean resetOnNextDigit = false;
    private boolean errorState = false;
    private boolean resultShown = false;

    // Ziffer anfuegen
    public void appendDigit(String digit) {
        if (errorState) return;

        // Wenn vorher "=" gedrueckt wurde: neue Rechnung beginnen
        if (resultShown) {
            displayValue = digit;
            expression = digit;
            storedValue = 0.0;
            pendingOperator = null;
            resetOnNextDigit = false;
            resultShown = false;
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

        // Ausdruck erweitern
        expression += digit;
    }

    // Dezimalpunkt anfuegen
    public void appendDecimalPoint() {
        if (errorState) return;

        if (resultShown) {
            displayValue = "0.";
            expression = "0.";
            storedValue = 0.0;
            pendingOperator = null;
            resetOnNextDigit = false;
            resultShown = false;
            return;
        }

        if (resetOnNextDigit) {
            displayValue = "0.";
            resetOnNextDigit = false;
            expression += "0.";
        } else if (!displayValue.contains(".")) {
            displayValue += ".";
            expression += ".";
        }
    }

    // Operator setzen (+, -, ×, ÷)
    public void setOperator(String operator) {
        if (errorState) return;

        if (resultShown) {
            // Weiterrechnen mit dem letzten Ergebnis
            storedValue = parseDisplayValue();
            expression = displayValue + operator;
            resultShown = false;
        } else {
            storedValue = parseDisplayValue();
            if (expression.isEmpty()) {
                expression = displayValue + operator;
            } else {
                char last = expression.charAt(expression.length() - 1);
                if (last == '+' || last == '-' || last == '×' || last == '÷') {
                    // Operator ersetzen, falls man ihn aendert
                    expression = expression.substring(0, expression.length() - 1) + operator;
                } else {
                    expression += operator;
                }
            }
        }

        pendingOperator = operator;
        resetOnNextDigit = true;
    }

    // "="
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
        resultShown = true;

        // Nach "=" soll nur noch das Ergebnis angezeigt werden
        expression = displayValue;
    }

    // "C"
    public void clear() {
        storedValue = 0.0;
        pendingOperator = null;
        displayValue = "0";
        resetOnNextDigit = false;
        errorState = false;
        expression = "";
        resultShown = false;
    }

    // Anzeige fuer die View
    public String getDisplayValue() {
        if (errorState) {
            return "Error";
        }

        // Wenn ein Operator gesetzt ist und wir mitten in einer Rechnung sind:
        // gesamten Ausdruck anzeigen (z. B. "32+32")
        if (pendingOperator != null && !resultShown && !expression.isEmpty()) {
            return expression;
        }

        // Sonst nur die aktuelle Zahl bzw. das Ergebnis
        return displayValue;
    }

    public boolean isError() {
        return errorState;
    }

    private void setErrorState() {
        errorState = true;
        displayValue = "Error";
        expression = "Error";
        pendingOperator = null;
        resultShown = false;
    }

    private double parseDisplayValue() {
        return Double.parseDouble(displayValue);
    }

    private String formatResult(double value) {
        if (Math.abs(value - Math.rint(value)) < 1e-10) {
            return String.valueOf((long) Math.rint(value));
        }
        return String.valueOf(value);
    }
}
