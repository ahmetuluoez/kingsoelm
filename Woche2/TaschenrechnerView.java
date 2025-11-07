package Woche2;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TaschenrechnerView extends VBox {

    final TextField eingabe = new TextField();
    final Label ausgabe = new Label();
    final Button rechnen = new Button("=");
    final Button loeschen = new Button("C");
    final Button[] ziffern = new Button[10];
    final Button plus = new Button("+");
    final Button minus = new Button("-");
    final Button mal = new Button("*");
    final Button geteilt = new Button("/");
    final Button punkt = new Button(".");

    public TaschenrechnerView() {
        setSpacing(10);
        setPadding(new Insets(16));
        setAlignment(Pos.CENTER);

        Label titel = new Label("Taschenrechner");
        eingabe.setPromptText("Ausdruck");

        GridPane feld = new GridPane();
        feld.setHgap(5);
        feld.setVgap(5);
        feld.setAlignment(Pos.CENTER);

        for (int i = 0; i < 10; i++) {
            ziffern[i] = taste(String.valueOf(i));
        }
        int n = 1;
        for (int zeile = 2; zeile >= 0; zeile--) {
            for (int spalte = 0; spalte < 3; spalte++) {
                feld.add(ziffern[n++], spalte, zeile);
            }
        }
        feld.add(ziffern[0], 0, 3, 2, 1);
        feld.add(punkt, 2, 3);
        feld.add(plus, 3, 0);
        feld.add(minus, 3, 1);
        feld.add(mal, 3, 2);
        feld.add(geteilt, 3, 3);

        rechnen.setPrefWidth(60);
        loeschen.setPrefWidth(60);
        HBox aktionen = new HBox(8, rechnen, loeschen);
        aktionen.setAlignment(Pos.CENTER);

        getChildren().addAll(titel, eingabe, feld, aktionen, ausgabe);
    }

    private Button taste(String text) {
        Button b = new Button(text);
        b.setPrefWidth(60);
        b.setFocusTraversable(false);
        return b;
    }
}
