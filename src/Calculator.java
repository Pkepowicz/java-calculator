import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Calculator {

    static JTextField textField;
    private static int temp, int1, int2;
    private static String lastSign;
    static boolean lastInputWasEqual = false;
    static boolean lastInputWasAction = true;
    static boolean dividingByZero = false;
    static boolean solved = true;
    static boolean hasBeenSolved = true;
    public static void createAndShowGUI() {

        ActionListener myActionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String buttonPressed = e.getActionCommand();
                switch (buttonPressed) {
                    case "0", "1", "2", "3", "4", "5", "6", "7", "8", "9":
                        pressNumber(buttonPressed);
                        break;
                    case "+", "-", "*", "/":
                        pressSign(buttonPressed);
                        break;
                    case "=":
                        pressEquals();
                        break;
                    case "C":
                        clean();
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
    public static void pressNumber(String pressed) {
        if (dividingByZero) {
            return;
        }
        if (lastInputWasAction || textField.getText().startsWith("0")) {
            textField.setText("");
        }
        if (lastInputWasEqual) {
            textField.setText("");
            lastSign = null;
        }
        textField.setText(textField.getText().concat(pressed));
        lastInputWasAction = false;
        lastInputWasEqual = false;
    }

    private static void pressSign(String pressed) {
        if (dividingByZero) {
            return;
        }
        if(lastInputWasAction) {
            lastSign = pressed;
            return;
        }
        if (textField.getText().isBlank()) {
            lastSign = pressed;
            int2 = 0;
            solved = false;
            lastInputWasAction = true;
            lastInputWasEqual = false;
            return;
        }
        if (!solved) {
            pressEquals();
            solved = true;
        }
        lastSign = pressed;
        int2 = Integer.parseInt(textField.getText());
        solved = false;
        lastInputWasAction = true;
        lastInputWasEqual = false;
    }

    private static void pressEquals() {
        if (dividingByZero) {
            return;
        }
        if (lastSign == null) {
            solved = true;
            lastInputWasEqual = false;
            lastInputWasAction = true;
            hasBeenSolved = true;
            if (!textField.getText().isEmpty()) {
                int2 = Integer.parseInt(textField.getText());
            }
            return;
        }
        if (lastInputWasEqual) {
            int2 = temp;
        } else {
            temp = int2;
            int1 = Integer.parseInt(textField.getText());
        }
        switch (lastSign) {
            case "+":
                temp = int2 + int1;
                break;
            case "-":
                temp = int2 - int1;
                break;
            case "*":
                temp = int2 * int1;
                break;
            case "/":
                if (int1 == 0) {
                    clean();
                    textField.setText("ERROR: DIVISION BY ZERO");
                    dividingByZero = true;
                    return;
                }
                temp = int2 / int1;
                break;
        }
        textField.setText(String.valueOf(temp));
        lastInputWasEqual = true;
        lastInputWasAction = false;
        solved = true;
    }

    public static void clean() {
        textField.setText("");
        int1 = 0;
        int2 = 0;
        lastSign = null;
        lastInputWasEqual = false;
        lastInputWasAction = true;
        hasBeenSolved = true;
        dividingByZero = false;
    }

    public static void main(String[] args) {
        System.out.println("Before");

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() { createAndShowGUI(); }
        });

        System.out.println("After");
    }
}
