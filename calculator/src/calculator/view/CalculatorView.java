package calculator.view;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import javax.swing.AbstractAction;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.KeyStroke;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

/** Rechner-View. */
public class CalculatorView extends JFrame {
    private static boolean fxStarted;
    private final JFXPanel fxPanel = new JFXPanel();
    private final Map<String, Button> buttons = new HashMap<>();
    private TextField display;

    public CalculatorView() {
        super("calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(420, 600);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(0x1C1C1C));
        add(fxPanel);
        initFx();
    }

    private void initFx() {
        runAndWait(() -> {
            BorderPane root = new BorderPane();
            root.setStyle("-fx-background-color: #1C1C1C;");

            display = new TextField("0");
            display.setEditable(false);
            display.setAlignment(Pos.CENTER_RIGHT);
            display.setPrefHeight(90);
            display.setStyle(
                "-fx-font-size: 36px; -fx-background-color: white; -fx-text-fill: black;"
            );

            VBox top = new VBox(display);
            top.setPadding(new Insets(20, 20, 10, 20));
            root.setTop(top);

            GridPane grid = new GridPane();
            grid.setPadding(new Insets(10));
            grid.setHgap(6);
            grid.setVgap(6);
            grid.setAlignment(Pos.CENTER);

            for (int i = 0; i < 4; i++) {
                ColumnConstraints cc = new ColumnConstraints();
                cc.setHgrow(Priority.ALWAYS);
                cc.setPercentWidth(25);
                grid.getColumnConstraints().add(cc);
            }
            for (int i = 0; i < 5; i++) {
                RowConstraints rc = new RowConstraints();
                rc.setVgrow(Priority.ALWAYS);
                rc.setPercentHeight(20);
                grid.getRowConstraints().add(rc);
            }

            String[] labels = {
                "⌫", "AC", "%", "÷",
                "1", "2", "3", "×",
                "4", "5", "6", "−",
                "7", "8", "9", "+",
                "±", "0", ",", "="
            };

            for (int i = 0; i < labels.length; i++) {
                int row = i / 4;
                int col = i % 4;
                Button btn = new Button(labels[i]);
                btn.setMinSize(80, 80);
                btn.setPrefSize(80, 80);
                btn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                btn.setStyle(baseStyleFor(row, col));
                buttons.put(labels[i], btn);
                grid.add(btn, col, row);
            }

            root.setCenter(grid);
            fxPanel.setScene(new Scene(root, 420, 600));
        });
    }

    private String baseStyleFor(int row, int col) {
        String color;
        String text = "-fx-text-fill: white;";
        if (col == 3) {
            color = "#FF9500";
        } else if (row == 0 && col < 3) {
            color = "#A5A5A5";
            text = "-fx-text-fill: black;";
        } else {
            color = "#505050";
        }
        return String.join(
            " ",
            "-fx-background-color: " + color + ";",
            text,
            "-fx-font-size: 22px;",
            "-fx-font-weight: bold;",
            "-fx-background-radius: 35px;",
            "-fx-cursor: hand;"
        );
    }

    public void setDisplay(String text) {
        runLater(() -> display.setText(text));
    }

    public void addButtonListener(String label, ActionListener l) {
        Button btn = buttons.get(label);
        if (btn != null) {
            btn.addEventHandler(
                javafx.event.ActionEvent.ACTION,
                e -> java.awt.EventQueue.invokeLater(
                    () -> l.actionPerformed(
                        new java.awt.event.ActionEvent(btn, java.awt.event.ActionEvent.ACTION_PERFORMED, label)
                    )
                )
            );
        }
    }

    public void addKeyBindings(Map<String, Runnable> bindings) {
        InputMap map = getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        javax.swing.ActionMap actions = getRootPane().getActionMap();
        for (Map.Entry<String, Runnable> entry : bindings.entrySet()) {
            KeyStroke ks = KeyStroke.getKeyStroke(entry.getKey());
            if (ks == null) {
                continue;
            }
            String key = entry.getKey();
            map.put(ks, key);
            actions.put(key, new AbstractAction() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    entry.getValue().run();
                }
            });
        }
        fxPanel.getScene().addEventFilter(KeyEvent.ANY, e -> {});
    }

    private static void runLater(Runnable r) {
        if (Platform.isFxApplicationThread()) {
            r.run();
        } else {
            Platform.runLater(r);
        }
    }

    private static void runAndWait(Runnable r) {
        try {
            if (!fxStarted) {
                CountDownLatch latch = new CountDownLatch(1);
                Platform.startup(() -> {
                    r.run();
                    latch.countDown();
                });
                fxStarted = true;
                latch.await();
            } else if (Platform.isFxApplicationThread()) {
                r.run();
            } else {
                CountDownLatch latch = new CountDownLatch(1);
                Platform.runLater(() -> {
                    r.run();
                    latch.countDown();
                });
                latch.await();
            }
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}
