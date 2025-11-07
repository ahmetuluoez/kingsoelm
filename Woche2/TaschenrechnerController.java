package Woche2;

public class TaschenrechnerController {

    private final TaschenrechnerModel model;
    private final TaschenrechnerView view;

    public TaschenrechnerController(TaschenrechnerModel model, TaschenrechnerView view) {
        this.model = model;
        this.view = view;
        initialisiere();
    }

    private void initialisiere() {
        view.getBerechneButton().setOnAction(event -> fuehreBerechnungAus());
        view.getEingabeFeld().setOnAction(event -> fuehreBerechnungAus());
    }

    private void fuehreBerechnungAus() {
        String eingabe = view.getEingabeFeld().getText();
        String resultat = model.berechne(eingabe);
        view.getAusgabeLabel().setText(resultat);
    }
}
