package Woche2;

public class TaschenrechnerController {

    public TaschenrechnerController(TaschenrechnerModel model, TaschenrechnerView view) {
        view.rechnen.setOnAction(e -> zeigeErgebnis(model, view));
        view.eingabe.setOnAction(e -> zeigeErgebnis(model, view));
        view.loeschen.setOnAction(e -> {
            view.eingabe.clear();
            view.ausgabe.setText("");
        });
        for (int i = 0; i < view.ziffern.length; i++) {
            int index = i;
            view.ziffern[i].setOnAction(e -> fuegeText(view, view.ziffern[index].getText()));
        }
        view.plus.setOnAction(e -> fuegeOperator(view, "+"));
        view.minus.setOnAction(e -> fuegeOperator(view, "-"));
        view.mal.setOnAction(e -> fuegeOperator(view, "*"));
        view.geteilt.setOnAction(e -> fuegeOperator(view, "/"));
        view.punkt.setOnAction(e -> fuegeText(view, "."));
    }

    private void zeigeErgebnis(TaschenrechnerModel model, TaschenrechnerView view) {
        view.ausgabe.setText(model.berechne(view.eingabe.getText()));
    }

    private void fuegeText(TaschenrechnerView view, String text) {
        view.eingabe.appendText(text);
        view.ausgabe.setText("");
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
        view.ausgabe.setText("");
    }
}
