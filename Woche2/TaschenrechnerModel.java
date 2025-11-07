package Woche2;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayDeque;
import java.util.Deque;

public class TaschenrechnerModel {

    public String berechne(String eingabe) {
        if (eingabe == null || eingabe.trim().isEmpty()) {
            return "keine Eingabe";
        }

        String normalisiert = eingabe.replace(',', '.');
        if (!normalisiert.matches("[0-9\\+\\-\\*/.\\s]+")) {
            return "Fehlerhafte Eingabe!";
        }

        try {
            double ergebnis = evaluiere(normalisiert);
            return formatiere(ergebnis);
        } catch (ArithmeticException e) {
            return e.getMessage();
        } catch (IllegalArgumentException e) {
            return "Fehlerhafte Eingabe!";
        }
    }

    private double evaluiere(String ausdruck) {
        Deque<Double> werte = new ArrayDeque<>();
        Deque<Character> operatoren = new ArrayDeque<>();
        boolean erwarteZahl = true;
        int i = 0;

        while (i < ausdruck.length()) {
            char c = ausdruck.charAt(i);
            if (Character.isWhitespace(c)) {
                i++;
                continue;
            }

            if (erwarteZahl) {
                int sign = 1;
                if (c == '+' || c == '-') {
                    sign = (c == '-') ? -1 : 1;
                    i++;
                    while (i < ausdruck.length() && Character.isWhitespace(ausdruck.charAt(i))) {
                        i++;
                    }
                    if (i >= ausdruck.length()) {
                        throw new IllegalArgumentException("Unerwartetes Ende nach Vorzeichen");
                    }
                    c = ausdruck.charAt(i);
                }

                if (!Character.isDigit(c) && c != '.') {
                    throw new IllegalArgumentException("Es wurde eine Zahl erwartet");
                }

                StringBuilder zahlBuilder = new StringBuilder();
                boolean hatPunkt = false;
                boolean hatZiffer = false;
                while (i < ausdruck.length()) {
                    c = ausdruck.charAt(i);
                    if (Character.isDigit(c)) {
                        zahlBuilder.append(c);
                        hatZiffer = true;
                        i++;
                    } else if (c == '.') {
                        if (hatPunkt) {
                            throw new IllegalArgumentException("Mehrere Dezimalpunkte");
                        }
                        hatPunkt = true;
                        zahlBuilder.append(c);
                        i++;
                    } else {
                        break;
                    }
                }

                if (!hatZiffer) {
                    throw new IllegalArgumentException("Keine gültige Zahl");
                }

                double wert = sign * Double.parseDouble(zahlBuilder.toString());
                werte.push(wert);
                erwarteZahl = false;
            } else {
                if (istOperator(c)) {
                    while (!operatoren.isEmpty() &&
                           prioritaet(operatoren.peek()) >= prioritaet(c)) {
                        berechneObersteOperation(werte, operatoren);
                    }
                    operatoren.push(c);
                    erwarteZahl = true;
                    i++;
                } else {
                    throw new IllegalArgumentException("Operator erwartet");
                }
            }
        }

        if (erwarteZahl) {
            throw new IllegalArgumentException("Unvollständiger Ausdruck");
        }

        while (!operatoren.isEmpty()) {
            berechneObersteOperation(werte, operatoren);
        }

        if (werte.size() != 1) {
            throw new IllegalArgumentException("Fehlerhafte Auswertung");
        }

        return werte.pop();
    }

    private boolean istOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    private int prioritaet(char op) {
        if (op == '+' || op == '-') {
            return 1;
        }
        if (op == '*' || op == '/') {
            return 2;
        }
        return 0;
    }

    private void berechneObersteOperation(Deque<Double> werte, Deque<Character> operatoren) {
        if (werte.size() < 2) {
            throw new IllegalArgumentException("Zu wenige Operanden");
        }
        double b = werte.pop();
        double a = werte.pop();
        char op = operatoren.pop();

        switch (op) {
            case '+':
                werte.push(a + b);
                break;
            case '-':
                werte.push(a - b);
                break;
            case '*':
                werte.push(a * b);
                break;
            case '/':
                if (Math.abs(b) < 1e-12) {
                    throw new ArithmeticException("Teilung durch 0!");
                }
                werte.push(a / b);
                break;
            default:
                throw new IllegalArgumentException("Unbekannter Operator");
        }
    }

    private String formatiere(double wert) {
        BigDecimal dezimal = new BigDecimal(Double.toString(wert));
        dezimal = dezimal.stripTrailingZeros();
        if (dezimal.scale() < 0) {
            dezimal = dezimal.setScale(0, RoundingMode.UNNECESSARY);
        }
        return dezimal.toPlainString();
    }
}
