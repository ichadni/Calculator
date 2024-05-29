import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Calculator implements ActionListener {
    JFrame frame;
    JTextField textfield;
    JButton[] numberButtons = new JButton[10];
    JButton[] functionButtons = new JButton[9];
    JButton addButton, subButton, mulButton, divButton;
    JButton decButton, equButton, delButton, clrButton, negButton;
    JPanel panel;

    Font myFont = new Font("Arial", Font.BOLD, 30); // Change font here
    Color buttonColor = Color.BLACK; // Change button color here
    Color textColor = Color.RED; // Change text color here

    double num1 = 0, num2 = 0, result = 0;
    char operator;
    boolean isFloat = false; // Flag to indicate if input or result is a floating-point number

    Calculator() {

        frame = new JFrame("Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(420, 550);
        frame.setLayout(null);

        textfield = new JTextField();
        textfield.setBounds(50, 25, 300, 50);
        textfield.setFont(myFont);
        textfield.setEditable(false);

        addButton = new JButton("+");
        subButton = new JButton("-");
        mulButton = new JButton("*");
        divButton = new JButton("/");
        decButton = new JButton(".");
        equButton = new JButton("=");
        delButton = new JButton("Del");
        clrButton = new JButton("Clr");
        negButton = new JButton("(-)");

        functionButtons[0] = addButton;
        functionButtons[1] = subButton;
        functionButtons[2] = mulButton;
        functionButtons[3] = divButton;
        functionButtons[4] = decButton;
        functionButtons[5] = equButton;
        functionButtons[6] = delButton;
        functionButtons[7] = clrButton;
        functionButtons[8] = negButton;

        for (int i = 0; i < 9; i++) {
            functionButtons[i].addActionListener(this);
            functionButtons[i].setFont(myFont);
            functionButtons[i].setForeground(textColor); // Set text color
            functionButtons[i].setBackground(buttonColor); // Set button color
            functionButtons[i].setFocusable(false);
        }

        for (int i = 0; i < 10; i++) {
            numberButtons[i] = new JButton(String.valueOf(i));
            numberButtons[i].addActionListener(this);
            numberButtons[i].setFont(myFont);
            numberButtons[i].setForeground(textColor); // Set text color
            numberButtons[i].setBackground(buttonColor); // Set button color
            numberButtons[i].setFocusable(false);
        }

        negButton.setBounds(50, 430, 100, 50);
        delButton.setBounds(150, 430, 100, 50);
        clrButton.setBounds(250, 430, 100, 50);

        panel = new JPanel();
        panel.setBounds(50, 100, 300, 300);
        panel.setLayout(new GridLayout(4, 4, 10, 10));

        panel.add(numberButtons[1]);
        panel.add(numberButtons[2]);
        panel.add(numberButtons[3]);
        panel.add(addButton);
        panel.add(numberButtons[4]);
        panel.add(numberButtons[5]);
        panel.add(numberButtons[6]);
        panel.add(subButton);
        panel.add(numberButtons[7]);
        panel.add(numberButtons[8]);
        panel.add(numberButtons[9]);
        panel.add(mulButton);
        panel.add(decButton);
        panel.add(numberButtons[0]);
        panel.add(equButton);
        panel.add(divButton);

        frame.add(panel);
        frame.add(negButton);
        frame.add(delButton);
        frame.add(clrButton);
        frame.add(textfield);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        Calculator calc = new Calculator();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String currentText = textfield.getText();
        String buttonText = "";

        for (int i = 0; i < 10; i++) {
            if (e.getSource() == numberButtons[i]) {
                buttonText = String.valueOf(i);
                break;
            }
        }

        if (e.getSource() == decButton) {
            buttonText = ".";
            isFloat = true; // Set flag indicating input is a floating-point number
        } else if (e.getSource() == addButton) {
            buttonText = " + ";
            isFloat = false; // Reset flag for non-floating-point operations
        } else if (e.getSource() == subButton) {
            buttonText = " - ";
            isFloat = false;
        } else if (e.getSource() == mulButton) {
            buttonText = " * ";
            isFloat = false;
        } else if (e.getSource() == divButton) {
            buttonText = " / ";
            isFloat = false;
        }

        if (!buttonText.equals("")) {
            textfield.setText(currentText + buttonText);
            return;
        }

        if (e.getSource() == equButton) {
            String[] parts = currentText.split(" ");

            if (parts.length == 3) {
                double firstOperand = Double.parseDouble(parts[0]);
                double secondOperand = Double.parseDouble(parts[2]);
                double result = 0;

                switch (parts[1]) {
                    case "+":
                        result = firstOperand + secondOperand;
                        break;
                    case "-":
                        result = firstOperand - secondOperand;
                        break;
                    case "*":
                        result = firstOperand * secondOperand;
                        break;
                    case "/":
                        if (secondOperand != 0) {
                            result = firstOperand / secondOperand;
                        } else {
                            textfield.setText("Error: Division by zero");
                            return;
                        }
                        break;
                    default:
                        textfield.setText("Error: Invalid operation");
                        return;
                }

                if (isFloat) {
                    textfield.setText(String.valueOf(result));
                } else {
                    // Check if the result is an integer
                    if (result % 1 == 0) {
                        textfield.setText(String.valueOf((int) result));
                    } else {
                        String formatR=String.format("%.16f",result);
                        textfield.setText(String.valueOf(formatR));
                    }
                }
                isFloat = false; // Reset flag after calculation
            } else {
                textfield.setText("Error: Invalid expression");
            }
        }

        if (e.getSource() == clrButton) {
            textfield.setText("");
            isFloat = false; // Reset flag for non-floating-point input
        }

        if (e.getSource() == delButton) {
            if (!currentText.isEmpty()) {
                textfield.setText(currentText.substring(0, currentText.length() - 1));
            }
        }

        if (e.getSource() == negButton) {
            if (!currentText.isEmpty()) {
                double temp = Double.parseDouble(currentText);
                temp *= -1;
                textfield.setText(String.valueOf(temp));
            }
        }
    }
}
