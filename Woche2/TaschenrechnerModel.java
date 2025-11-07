package Woche2;

import java.util.Stack;

public class TaschenrechnerModel {

    public String berechne(String eingabe) {
        if (eingabe == null || eingabe.trim().isEmpty()) {
            return "keine Eingabe";
        }

        String ausdruck = eingabe.replace(',', '.').replaceAll("\\s+", "");
        if (!ausdruck.matches("[-+*/0-9.]+")) {
            return "Fehlerhafte Eingabe!";
        }

        try {
            double wert = rechne(ausdruck);
            if (Double.isInfinite(wert) || Double.isNaN(wert)) {
                throw new ArithmeticException();
            }
            long ganz = (long) wert;
            return wert == ganz ? Long.toString(ganz) : Double.toString(wert);
        } catch (ArithmeticException e) {
            return "Teilung durch 0!";
        } catch (Exception e) {
            return "Fehlerhafte Eingabe!";
        }
    }

    private double rechne(String text) {
        Stack<Double> werte = new Stack<>();
        Stack<Character> ops = new Stack<>();
        int i = 0;
        while (i < text.length()) {
            char c = text.charAt(i);
            if (Character.isDigit(c) || c == '.') {
                int j = i + 1;
                while (j < text.length() && (Character.isDigit(text.charAt(j)) || text.charAt(j) == '.')) {
                    j++;
                }
                werte.push(Double.parseDouble(text.substring(i, j)));
                i = j;
                continue;
            }
            if ((c == '+' || c == '-') && (i == 0 || istOperator(text.charAt(i - 1)))) {
                int j = i + 1;
                while (j < text.length() && (Character.isDigit(text.charAt(j)) || text.charAt(j) == '.')) {
                    j++;
                }
                werte.push(Double.parseDouble(text.substring(i, j)));
                i = j;
                continue;
            }
            while (!ops.isEmpty() && prioritaet(ops.peek()) >= prioritaet(c)) {
                werte.push(apply(ops.pop(), werte.pop(), werte.pop()));
            }
            ops.push(c);
            i++;
        }
        while (!ops.isEmpty()) {
            werte.push(apply(ops.pop(), werte.pop(), werte.pop()));
        }
        if (werte.size() != 1) {
            throw new IllegalArgumentException();
        }
        return werte.pop();
    }

    private boolean istOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    private int prioritaet(char c) {
        return (c == '+' || c == '-') ? 1 : 2;
    }

    private double apply(char op, double rechts, double links) {
        switch (op) {
            case '+':
                return links + rechts;
            case '-':
                return links - rechts;
            case '*':
                return links * rechts;
            case '/':
                if (rechts == 0.0) {
                    throw new ArithmeticException();
                }
                return links / rechts;
            default:
                throw new IllegalArgumentException();
        }
    }
}
