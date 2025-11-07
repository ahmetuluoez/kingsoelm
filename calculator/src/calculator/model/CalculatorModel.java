package calculator.model;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Locale;

/** Rechner-Model. */
public class CalculatorModel {
    private final List<String> tokens = new ArrayList<>();
    private StringBuilder current = new StringBuilder();
    private boolean error;
    private String display = "0";

    public void clear() {
        tokens.clear();
        current = new StringBuilder();
        error = false;
        display = "0";
    }

    public void backspace() {
        if (error) {
            clear();
            return;
        }
        if (current.length() > 0) {
            current.deleteCharAt(current.length() - 1);
        } else if (!tokens.isEmpty()) {
            String last = tokens.get(tokens.size() - 1);
            if (!isOperator(last)) {
                current = new StringBuilder(last);
                tokens.remove(tokens.size() - 1);
                if (current.length() > 0) {
                    current.deleteCharAt(current.length() - 1);
                }
            }
        }
        updateDisplay();
    }

    public void appendDigit(char d) {
        if (error) {
            clear();
        }
        if (current.toString().equals("0")) {
            current.setLength(0);
        }
        current.append(d);
        updateDisplay();
    }

    public void appendComma() {
        if (error) {
            clear();
        }
        if (current.indexOf(".") >= 0) {
            return;
        }
        if (current.length() == 0) {
            current.append("0");
        }
        current.append('.');
        updateDisplay();
    }

    public void applyOperator(char op) {
        if (error) {
            clear();
        }
        flushCurrent();
        if (tokens.isEmpty()) {
            return;
        }
        String last = tokens.get(tokens.size() - 1);
        if (isOperator(last)) {
            tokens.set(tokens.size() - 1, String.valueOf(op));
        } else {
            tokens.add(String.valueOf(op));
        }
        updateDisplay();
    }

    public void toggleSign() {
        if (error) {
            clear();
        }
        if (current.length() > 0) {
            if (current.charAt(0) == '-') {
                current.deleteCharAt(0);
            } else {
                current.insert(0, '-');
            }
        } else if (!tokens.isEmpty()) {
            int idx = tokens.size() - 1;
            String last = tokens.get(idx);
            if (!isOperator(last)) {
                tokens.set(idx, negate(last));
            }
        }
        updateDisplay();
    }

    public void percent() {
        if (error) {
            clear();
        }
        if (current.length() > 0) {
            current = new StringBuilder(divideByHundred(current.toString()));
        } else if (!tokens.isEmpty()) {
            int idx = tokens.size() - 1;
            String last = tokens.get(idx);
            if (!isOperator(last)) {
                tokens.set(idx, divideByHundred(last));
            }
        }
        updateDisplay();
    }

    public String evaluate() {
        if (error) {
            return "Fehler";
        }
        flushCurrent();
        if (tokens.isEmpty()) {
            return display;
        }
        try {
            BigDecimal result = compute(tokens);
            if (error) {
                return "Fehler";
            }
            tokens.clear();
            current = new StringBuilder(result.stripTrailingZeros().toPlainString());
            display = formatResult(result);
            return display;
        } catch (IllegalArgumentException ex) {
            setError();
            return "Fehler";
        }
    }

    public String getDisplay() {
        return display;
    }

    private void flushCurrent() {
        if (current.length() == 0) {
            return;
        }
        if (current.charAt(current.length() - 1) == '.') {
            current.append('0');
        }
        tokens.add(current.toString());
        current = new StringBuilder();
    }

    private void updateDisplay() {
        if (error) {
            display = "Fehler";
            return;
        }
        List<String> parts = new ArrayList<>(tokens);
        if (current.length() > 0) {
            parts.add(current.toString());
        }
        if (parts.isEmpty()) {
            display = "0";
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (String part : parts) {
            if (isOperator(part)) {
                sb.append(mapOperatorForDisplay(part.charAt(0)));
            } else {
                sb.append(toDisplay(part));
            }
        }
        display = sb.toString();
    }

    private BigDecimal compute(List<String> expr) {
        Deque<BigDecimal> values = new ArrayDeque<>();
        Deque<Character> ops = new ArrayDeque<>();
        for (String token : expr) {
            if (isOperator(token)) {
                char op = token.charAt(0);
                while (!ops.isEmpty() && precedence(ops.peek()) >= precedence(op)) {
                    applyOp(values, ops.pop());
                    if (error) {
                        return BigDecimal.ZERO;
                    }
                }
                ops.push(op);
            } else {
                values.push(new BigDecimal(token));
            }
        }
        while (!ops.isEmpty()) {
            applyOp(values, ops.pop());
            if (error) {
                return BigDecimal.ZERO;
            }
        }
        if (values.size() != 1) {
            throw new IllegalArgumentException();
        }
        return values.pop();
    }

    private void applyOp(Deque<BigDecimal> values, char op) {
        if (values.size() < 2) {
            throw new IllegalArgumentException();
        }
        BigDecimal b = values.pop();
        BigDecimal a = values.pop();
        switch (op) {
            case '+':
                values.push(a.add(b));
                break;
            case '-':
                values.push(a.subtract(b));
                break;
            case '*':
                values.push(a.multiply(b));
                break;
            case '/':
                if (b.compareTo(BigDecimal.ZERO) == 0) {
                    setError();
                    return;
                }
                values.push(a.divide(b, MathContext.DECIMAL64));
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    private void setError() {
        error = true;
        tokens.clear();
        current = new StringBuilder();
        display = "Fehler";
    }

    private boolean isOperator(String token) {
        return token.length() == 1 && ("+-*/".indexOf(token.charAt(0)) >= 0);
    }

    private int precedence(char op) {
        return (op == '+' || op == '-') ? 1 : 2;
    }

    private String negate(String value) {
        return value.startsWith("-") ? value.substring(1) : "-" + value;
    }

    private String divideByHundred(String value) {
        BigDecimal bd = new BigDecimal(value).divide(new BigDecimal("100"), MathContext.DECIMAL64);
        return bd.stripTrailingZeros().toPlainString();
    }

    private String formatResult(BigDecimal value) {
        String plain = value.stripTrailingZeros().toPlainString();
        String shown = plain.replace('.', ',');
        if (shown.length() <= 12) {
            return shown;
        }
        String sci = String.format(Locale.US, "%1.6g", value);
        return sci.replace('.', ',');
    }

    private String toDisplay(String value) {
        if (value.isEmpty()) {
            return "0";
        }
        if (value.equals("-")) {
            return "-";
        }
        if (value.charAt(value.length() - 1) == '.') {
            return value.substring(0, value.length() - 1).replace('.', ',') + ",";
        }
        BigDecimal bd = new BigDecimal(value);
        return bd.stripTrailingZeros().toPlainString().replace('.', ',');
    }

    private char mapOperatorForDisplay(char op) {
        switch (op) {
            case '*':
                return '×';
            case '/':
                return '÷';
            case '-':
                return '−';
            default:
                return op;
        }
    }
}
