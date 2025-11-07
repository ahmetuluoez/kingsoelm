package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CalculatorView extends JFrame {
    private final JTextField display = new JTextField("0");
    private final String[] buttons = {
        "7", "8", "9", "÷",
        "4", "5", "6", "×",
        "1", "2", "3", "-",
        "0", ".", "=", "+",
        "C"
    };
    private final List<JButton> buttonList = new ArrayList<>();

    public CalculatorView() {
        super("Einfacher Taschenrechner");
        buildUi();
    }

    private void buildUi() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(320, 420);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(30, 30, 30));

        display.setEditable(false);
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setFont(new Font("SansSerif", Font.BOLD, 32));
        display.setBackground(new Color(45, 45, 45));
        display.setForeground(Color.WHITE);
        display.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(display, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(0, 4, 10, 10));
        buttonPanel.setBackground(new Color(30, 30, 30));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (String text : buttons) {
            JButton button = new JButton(text);
            styleButton(button, text);
            buttonPanel.add(button);
            buttonList.add(button);
        }

        add(buttonPanel, BorderLayout.CENTER);
    }

    private void styleButton(JButton button, String text) {
        button.setFont(new Font("SansSerif", Font.BOLD, 24));
        button.setFocusPainted(false);
        button.setForeground(Color.WHITE);
        if ("C".equals(text)) {
            button.setBackground(new Color(255, 99, 71));
        } else if (text.matches("[÷×+=-]")) {
            button.setBackground(new Color(72, 61, 139));
        } else {
            button.setBackground(new Color(70, 130, 180));
        }
        button.setActionCommand(text);
    }

    public void addCalculatorListener(ActionListener listener) {
        for (JButton button : buttonList) {
            button.addActionListener(listener);
        }
    }

    public void updateDisplay(String value) {
        display.setText(value);
    }
}
