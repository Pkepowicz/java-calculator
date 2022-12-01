import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Calculator {

    static JTextField textField;
    static String currentScreenValue, savedNumber, lastPressed, currentOperator, previousOperator;

    public static void createAndShowGUI() {

        ActionListener myActionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String buttonPressed = e.getActionCommand();
                switch (buttonPressed) {
                    case "0", "1", "2", "3", "4", "5", "6", "7", "8", "9":
                        addValue(buttonPressed);
                        updateScreen();
                        lastPressed = buttonPressed;
                        break;
                    case "+", "-", "*", "/":
                        operatorPressed(buttonPressed);
                        updateScreen();
                        lastPressed = buttonPressed;
                        break;
                    case "=":
                        equalsPressed();
                        updateScreen();
                        lastPressed = buttonPressed;
                        break;
                    case "C":
                        clean();
                        updateScreen();
                        break;
                    default:
                        System.out.println("Nieznany przycisk");
                }
            }
        };

        JFrame jf = new JFrame("Calculator");
        JPanel jp = new JPanel();

        // Generowanie przyciskow funkcyjnych
        JButton [] FuncButton = new JButton[6];
        JButton addButton,difButton,mulButton,divButton,eqButton,clcButton;

        addButton = new JButton("+");
        difButton = new JButton("-");
        mulButton = new JButton("*");
        divButton = new JButton("/");
        eqButton = new JButton("=");
        clcButton = new JButton("C");

        FuncButton[0] = addButton;
        FuncButton[1] = difButton;
        FuncButton[2] = mulButton;
        FuncButton[3] = divButton;
        FuncButton[4] = eqButton;
        FuncButton[5] = clcButton;

        for(int i =0; i< FuncButton.length; i++) {
            FuncButton[i].addActionListener(myActionListener);
            FuncButton[i].setFocusable(false);
        }

        // Generowanie przycisków z cyframi
        JButton [] NumButtons = new JButton[10];
        for(int i =0; i< NumButtons.length; i++) {
            NumButtons[i] = new JButton(String.valueOf(i));
            NumButtons[i].addActionListener(myActionListener);
            NumButtons[i].setFocusable(false);
        }

        // Wrzucenie wszystkiego w pane
        jp.setBounds(5,50,300,200);
        jp.setLayout(new GridLayout(5,4,10,5));
        jp.add(NumButtons[1]);jp.add(NumButtons[2]);jp.add(NumButtons[3]);jp.add(FuncButton[0]);
        jp.add(NumButtons[4]);jp.add(NumButtons[5]);jp.add(NumButtons[6]);jp.add(FuncButton[1]);
        jp.add(NumButtons[7]);jp.add(NumButtons[8]);jp.add(NumButtons[9]);jp.add(FuncButton[2]);
        jp.add(NumButtons[0]);jp.add(FuncButton[5]);jp.add(FuncButton[4]);jp.add(FuncButton[3]);

        // Ustawienia text field
        textField = new JTextField();
        textField.setBounds(5, 5, 300, 30);
        textField.setFont(new Font("Arial", Font.BOLD,16));
        textField.setHorizontalAlignment(JTextField.RIGHT);
        textField.setText("0");
        textField.setEditable(false);

        // Ustawienia okna
        jf.setResizable(false);
        jf.setLayout(null);
        jf.setSize(326, 255); // + 16 bo windows dodaje swoje wlasne ramki do wielkosci calego okna
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Dodanie pól do okna
        jf.add(textField);
        jf.add(jp);
        jf.setVisible(true);
    }

    private static void updateScreen() {
        textField.setText(currentScreenValue);
    }

    private static void addValue(String a) {
        if(currentScreenValue == null || currentScreenValue.equals("0") || lastPressed.equals("=")) {
            //clean();
            currentScreenValue = a;
            return;
        }
        currentScreenValue += a;
    }

    private static void operatorPressed(String pressed) {
        if (currentOperator.isEmpty()) {
            currentOperator = pressed;
            return;
        }
        if (!previousOperator.isEmpty()) {
            equalsPressed();
        }
        currentOperator = pressed;
        savedNumber = currentScreenValue;
        currentScreenValue = "0";
    }

    private static void equalsPressed() {
        if (currentOperator.length() < 1 || savedNumber.length() < 1) {
            return;
        }
        if (lastPressed.equals("=")) {
            switch (currentOperator) {
                case "+":
                    currentScreenValue = Integer.toString(Integer.parseInt(currentScreenValue) + Integer.parseInt(savedNumber));
                    return;
                case "-":
                    currentScreenValue = Integer.toString(Integer.parseInt(currentScreenValue) - Integer.parseInt(savedNumber));
                    return;
                case "*":
                    currentScreenValue = Integer.toString(Integer.parseInt(currentScreenValue) * Integer.parseInt(savedNumber));
                    return;
                case "/":
                    currentScreenValue = Integer.toString(Integer.parseInt(currentScreenValue) / Integer.parseInt(savedNumber));
                    return;
            }
        }
        switch (currentOperator) {
            case "+":
                currentScreenValue = Integer.toString(Integer.parseInt(savedNumber) + Integer.parseInt(currentScreenValue));
                return;
            case "-":
                currentScreenValue = Integer.toString(Integer.parseInt(savedNumber) - Integer.parseInt(currentScreenValue));
                return;
            case "*":
                currentScreenValue = Integer.toString(Integer.parseInt(savedNumber) * Integer.parseInt(currentScreenValue));
                return;
            case "/":
                currentScreenValue = Integer.toString(Integer.parseInt(savedNumber) / Integer.parseInt(currentScreenValue));
                return;
        }
    }

    private static void clean() {
        currentScreenValue = "0";
        savedNumber = null;
        lastPressed = null;
        currentOperator = null;
    }

    public static void main(String[] args) {
        System.out.println("Before");

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() { createAndShowGUI(); }
        });

        System.out.println("After");
    }
}
