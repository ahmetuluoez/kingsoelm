package calculator.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;
import javax.swing.AbstractAction;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

/** Rechner-View. */
public class CalculatorView extends JFrame {
    private final JTextField display = new JTextField("0");
    private final Map<String, JButton> buttons = new HashMap<>();

    public CalculatorView() {
        super("calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(8, 8));
        display.setEditable(false);
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setFont(display.getFont().deriveFont(Font.BOLD, 28f));
        add(display, BorderLayout.NORTH);
        JPanel grid = new JPanel(new GridLayout(4, 5, 6, 6));
        String[] labels = {
            "⌫", "AC", "%", "÷",
            "7", "8", "9", "×",
            "4", "5", "6", "−",
            "1", "2", "3", "+",
            "±", "0", ",", "="
        };
        Color main = new Color(0xDDDDDD);
        Color accent = new Color(0xFF8C42);
        for (int i = 0; i < labels.length; i++) {
            JButton btn = new JButton(labels[i]);
            btn.setFocusable(false);
            btn.setBackground((i % 4 == 3) ? accent : main);
            if (i % 4 == 3) {
                btn.setForeground(Color.WHITE);
            }
            buttons.put(labels[i], btn);
            grid.add(btn);
        }
        add(grid, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
    }

    public void setDisplay(String text) {
        display.setText(text);
    }

    public void addButtonListener(String label, java.awt.event.ActionListener l) {
        JButton btn = buttons.get(label);
        if (btn != null) {
            btn.addActionListener(l);
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
    }
}
