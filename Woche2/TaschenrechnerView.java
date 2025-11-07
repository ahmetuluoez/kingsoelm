package Woche2;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class TaschenrechnerView extends VBox {

    private final TextField eingabeFeld;
    private final Button berechneButton;
    private final Label ausgabeLabel;

    public TaschenrechnerView() {
        setSpacing(12);
        setPadding(new Insets(20));
        setAlignment(Pos.CENTER);

        Label titel = new Label("Taschenrechner");
        titel.getStyleClass().add("titel-label");

        eingabeFeld = new TextField();
        eingabeFeld.setPromptText("Gib einen Ausdruck ein, z. B. 3 + 4 * 2");

        berechneButton = new Button("Berechnen");
        berechneButton.setDefaultButton(true);

        ausgabeLabel = new Label();
        ausgabeLabel.getStyleClass().add("ergebnis-label");

        getChildren().addAll(titel, eingabeFeld, berechneButton, ausgabeLabel);
    }

    public TextField getEingabeFeld() {
        return eingabeFeld;
    }

    public Button getBerechneButton() {
        return berechneButton;
    }

    public Label getAusgabeLabel() {
        return ausgabeLabel;
    }
}
