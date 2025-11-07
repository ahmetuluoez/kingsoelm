import java.util.Stack;

public class CalculatorModel {

    public String calculate(String input) {
        if (input == null || input.trim().isEmpty()) {
            return "no input";
        }

        String expression = input.replace(',', '.').replaceAll("\\s+", "");
        if (!expression.matches("[-+*/0-9.]+")) {
            return "Invalid input!";
        }

        try {
            double value = evaluate(expression);
            if (Double.isInfinite(value) || Double.isNaN(value)) {
                throw new ArithmeticException();
            }
            long whole = (long) value;
            return value == whole ? Long.toString(whole) : Double.toString(value);
        } catch (ArithmeticException e) {
            return "Division by 0!";
        } catch (Exception e) {
            return "Invalid input!";
        }
    }

    private double evaluate(String text) {
        Stack<Double> values = new Stack<>();
        Stack<Character> ops = new Stack<>();
        int i = 0;
        while (i < text.length()) {
            char c = text.charAt(i);
            if (Character.isDigit(c) || c == '.') {
                int j = i + 1;
                while (j < text.length() && (Character.isDigit(text.charAt(j)) || text.charAt(j) == '.')) {
                    j++;
                }
                values.push(Double.parseDouble(text.substring(i, j)));
                i = j;
                continue;
            }
            if ((c == '+' || c == '-') && (i == 0 || isOperator(text.charAt(i - 1)))) {
                int j = i + 1;
                while (j < text.length() && (Character.isDigit(text.charAt(j)) || text.charAt(j) == '.')) {
                    j++;
                }
                values.push(Double.parseDouble(text.substring(i, j)));
                i = j;
                continue;
            }
            while (!ops.isEmpty() && precedence(ops.peek()) >= precedence(c)) {
                values.push(apply(ops.pop(), values.pop(), values.pop()));
            }
            ops.push(c);
            i++;
        }
        while (!ops.isEmpty()) {
            values.push(apply(ops.pop(), values.pop(), values.pop()));
        }
        if (values.size() != 1) {
            throw new IllegalArgumentException();
        }
        return values.pop();
    }

    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    private int precedence(char c) {
        return (c == '+' || c == '-') ? 1 : 2;
    }

    private double apply(char op, double right, double left) {
        switch (op) {
            case '+':
                return left + right;
            case '-':
                return left - right;
            case '*':
                return left * right;
            case '/':
                if (right == 0.0) {
                    throw new ArithmeticException();
                }
                return left / right;
            default:
                throw new IllegalArgumentException();
        }
    }
}
