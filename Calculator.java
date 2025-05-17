import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Calculator extends JFrame implements ActionListener {
    JTextField display;
    JPanel buttonPanel;

    String operator = "";
    double num1 = 0, num2 = 0, result = 0;
    boolean isOperatorPressed = false;

    String[] buttonLabels = {
        "AC", "%", "<-", "/",
        "7", "8", "9", "*",
        "4", "5", "6", "-",
        "1", "2", "3", "+",
        "00", "0", ".", "="
    };

    JButton[] buttons = new JButton[20];

    public Calculator() {
        setTitle("Mini Calculator");
        setSize(280, 380); // Compact size
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        display = new JTextField();
        display.setFont(new Font("Arial", Font.BOLD, 20));
        display.setEditable(false);
        display.setHorizontalAlignment(SwingConstants.RIGHT);
        add(display, BorderLayout.NORTH);

        buttonPanel = new JPanel(new GridLayout(5, 4, 5, 5));
        for (int i = 0; i < buttonLabels.length; i++) {
            buttons[i] = new JButton(buttonLabels[i]);
            buttons[i].setFont(new Font("Arial", Font.BOLD, 16));
            buttons[i].addActionListener(this);

            // Color theming
            if (buttonLabels[i].matches("[0-9.]+") || buttonLabels[i].equals("00")) {
                buttons[i].setBackground(new Color(230, 230, 250)); // Light lavender for numbers
            } else {
                buttons[i].setBackground(new Color(173, 216, 230)); // Light blue for operators
            }

            buttonPanel.add(buttons[i]);
        }

        add(buttonPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String input = e.getActionCommand();

        switch (input) {
            case "AC":
                display.setText("");
                num1 = num2 = result = 0;
                operator = "";
                break;

            case "<-":
                String current = display.getText();
                if (!current.isEmpty())
                    display.setText(current.substring(0, current.length() - 1));
                break;

            case "%":
                try {
                    double val = Double.parseDouble(display.getText());
                    display.setText(String.valueOf(val / 100));
                } catch (Exception ex) {
                    display.setText("Error");
                }
                break;

            case "+": case "-": case "*": case "/":
                try {
                    num1 = Double.parseDouble(display.getText());
                    operator = input;
                    display.setText("");
                    isOperatorPressed = true;
                } catch (Exception ex) {
                    display.setText("Error");
                }
                break;

            case "=":
                try {
                    num2 = Double.parseDouble(display.getText());
                    switch (operator) {
                        case "+": result = num1 + num2; break;
                        case "-": result = num1 - num2; break;
                        case "*": result = num1 * num2; break;
                        case "/":
                            if (num2 == 0) {
                                display.setText("Divide by 0");
                                return;
                            }
                            result = num1 / num2;
                            break;
                    }
                    display.setText(String.valueOf(result));
                } catch (Exception ex) {
                    display.setText("Error");
                }
                break;

            default:
                if (isOperatorPressed && display.getText().isEmpty()) {
                    isOperatorPressed = false;
                }
                display.setText(display.getText() + input);
                break;
        }
    }

    public static void main(String[] args) {
        new Calculator();
    }
}