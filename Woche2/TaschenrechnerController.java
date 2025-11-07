package Woche2;

import java.util.Set;

public class TaschenrechnerController {

    private static final Set<String> FEHLER = Set.of(
        "keine Eingabe",
        "Teilung durch 0!",
        "Fehlerhafte Eingabe!"
    );

    public TaschenrechnerController(TaschenrechnerModel model, TaschenrechnerView view) {
        view.rechnen.setOnAction(e -> zeigeErgebnis(model, view));
        view.eingabe.setOnAction(e -> zeigeErgebnis(model, view));
        view.eingabe.setOnKeyTyped(e -> {
            if (!view.eingabe.getText().isEmpty()) {
                view.ausgabe.setText("0");
            }
        });
        view.loeschen.setOnAction(e -> {
            view.eingabe.clear();
            view.ausgabe.setText("0");
        });
        for (int i = 0; i < view.ziffern.length; i++) {
            int index = i;
            view.ziffern[i].setOnAction(e -> fuegeText(view, view.ziffern[index].getText()));
        }
        view.plus.setOnAction(e -> fuegeOperator(view, "+"));
        view.minus.setOnAction(e -> fuegeOperator(view, "-"));
        view.mal.setOnAction(e -> fuegeOperator(view, "*"));
        view.geteilt.setOnAction(e -> fuegeOperator(view, "/"));
    }

    private void zeigeErgebnis(TaschenrechnerModel model, TaschenrechnerView view) {
        String ergebnis = model.berechne(view.eingabe.getText());
        view.ausgabe.setText(ergebnis);
        if (!FEHLER.contains(ergebnis)) {
            view.eingabe.clear();
        }
    }

    private void fuegeText(TaschenrechnerView view, String text) {
        if (view.eingabe.getText().isEmpty() && !view.ausgabe.getText().equals("0")) {
            view.ausgabe.setText("0");
        }
        view.eingabe.appendText(text);
    }

    private void fuegeOperator(TaschenrechnerView view, String op) {
        String text = view.eingabe.getText();
        if (text.isEmpty() && !"-".equals(op)) {
            return;
        }
        if (text.endsWith(" ")) {
            text = text.trim();
        }
        view.eingabe.setText(text + " " + op + " ");
        view.eingabe.positionCaret(view.eingabe.getText().length());
    }
}
