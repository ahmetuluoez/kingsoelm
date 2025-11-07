package Woche2;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class TaschenrechnerView extends VBox {

    final Label ausgabe = new Label("0");
    final TextField eingabe = new TextField();
    final Button rechnen = new Button("=");
    final Button loeschen = new Button("C");
    final Button[] ziffern = new Button[10];
    final Button plus = new Button("+");
    final Button minus = new Button("-");
    final Button mal = new Button("*");
    final Button geteilt = new Button("/");

    public TaschenrechnerView() {
        setSpacing(12);
        setPadding(new Insets(16));
        setAlignment(Pos.CENTER);

        Label titel = new Label("Taschenrechner");
        ausgabe.setStyle("-fx-font-size: 20px;");
        ausgabe.setMaxWidth(Double.MAX_VALUE);
        ausgabe.setAlignment(Pos.CENTER_RIGHT);

        eingabe.setPromptText("Ausdruck");

        GridPane feld = new GridPane();
        feld.setHgap(6);
        feld.setVgap(6);
        feld.setAlignment(Pos.CENTER);

        for (int i = 0; i < 10; i++) {
            ziffern[i] = taste(String.valueOf(i));
        }

        feld.add(ziffern[1], 0, 0);
        feld.add(ziffern[2], 1, 0);
        feld.add(ziffern[3], 2, 0);

        feld.add(ziffern[4], 0, 1);
        feld.add(ziffern[5], 1, 1);
        feld.add(ziffern[6], 2, 1);

        feld.add(ziffern[7], 0, 2);
        feld.add(ziffern[8], 1, 2);
        feld.add(ziffern[9], 2, 2);

        feld.add(loeschen, 0, 3);
        feld.add(ziffern[0], 1, 3);
        feld.add(rechnen, 2, 3);

        feld.add(plus, 3, 0);
        feld.add(minus, 3, 1);
        feld.add(mal, 3, 2);
        feld.add(geteilt, 3, 3);

        rechnen.setPrefWidth(60);
        loeschen.setPrefWidth(60);

        getChildren().addAll(titel, ausgabe, eingabe, feld);
    }

    private Button taste(String text) {
        Button b = new Button(text);
        b.setPrefWidth(60);
        b.setFocusTraversable(false);
        return b;
    }
}
