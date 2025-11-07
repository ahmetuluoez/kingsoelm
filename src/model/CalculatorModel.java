package model;

public class CalculatorModel {
    private Double storedValue;
    private String pendingOperator;

    public void setPendingOperation(double currentValue, String operator) {
        if (storedValue != null && pendingOperator != null) {
            storedValue = compute(storedValue, currentValue, pendingOperator);
        } else {
            storedValue = currentValue;
        }
        pendingOperator = operator;
    }

    public double evaluate(double currentValue) {
        if (storedValue == null || pendingOperator == null) {
            return currentValue;
        }
        double result = compute(storedValue, currentValue, pendingOperator);
        storedValue = null;
        pendingOperator = null;
        return result;
    }

    public void clear() {
        storedValue = null;
        pendingOperator = null;
    }

    private double compute(double left, double right, String operator) {
        switch (operator) {
            case "+":
                return left + right;
            case "-":
                return left - right;
            case "×":
            case "*":
                return left * right;
            case "÷":
            case "/":
                return right == 0 ? Double.NaN : left / right;
            default:
                return right;
        }
    }
}
