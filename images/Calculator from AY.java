import java.awt.*;
import javax.swing.*;
import javax.swing.event.CaretListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator implements ActionListener {
    public JFrame frame;
    public JPanel panel1, panel2, panel3, panel4, panel5, panel6, panel7, panel8, panel9;
    public JTextField screen1, screen2;
    public JButton minus, add, multiply, divid, equal, plus, one, two, three, four, five, six, seven, eight, nine, zero,
            delete;
    public JLabel label, result;
    public String operator;
    public Double value1, value2;

    public Calculator() {
        frame = new JFrame("Calculator");
        frame.setBackground(Color.MAGENTA);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new GridLayout(3, 1));
        // frame.setResizable(false);

        panel1 = new JPanel(new FlowLayout());
        panel2 = new JPanel(new FlowLayout());
        panel3 = new JPanel();

        screen1 = new JTextField(10);
        screen1.setEditable(false);

        panel1.add(screen1);
        label = new JLabel("");
        panel1.add(label);

        screen2 = new JTextField(10);
        screen2.setEditable(false);

        panel1.add(screen2);
        result = new JLabel();
        panel1.add(result);

        panel3.setLayout(new GridLayout(3, 3, 5, 5));

        one = new JButton("1");
        one.addActionListener(this);
        panel3.add(one);

        two = new JButton("2");
        two.addActionListener(this);
        panel3.add(two);

        three = new JButton("3");
        three.addActionListener(this);
        panel3.add(three);

        four = new JButton("4");
        four.addActionListener(this);
        panel3.add(four);

        five = new JButton("5");
        five.addActionListener(this);
        panel3.add(five);

        six = new JButton("6");
        six.addActionListener(this);
        panel3.add(six);

        seven = new JButton("7");
        seven.addActionListener(this);
        panel3.add(seven);

        eight = new JButton("8");
        eight.addActionListener(this);
        panel3.add(eight);

        zero = new JButton("0");
        zero.addActionListener(this);
        panel3.add(zero);

        nine = new JButton("9");
        nine.addActionListener(this);
        panel3.add(nine);

        delete = new JButton("del");
        delete.addActionListener(this);
        panel3.add(delete);

        plus = new JButton("+");
        plus.addActionListener(this);
        panel2.add(plus);
        minus = new JButton("-");
        minus.addActionListener(this);
        panel2.add(minus);
        multiply = new JButton("X");
        multiply.addActionListener(this);
        panel2.add(multiply);
        divid = new JButton("/");
        divid.addActionListener(this);
        panel2.add(divid);
        equal = new JButton("=");
        equal.addActionListener(this);
        panel2.add(equal);
        
        frame.setResizable(false);
        frame.add(panel1);
        frame.add(panel3);
        frame.add(panel2);

        frame.setVisible(true);
        frame.pack();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("=")) {
            try {
                value1 = Double.parseDouble(screen1.getText());
                value2 = Double.parseDouble(screen2.getText());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "invalid message");
            }
            if (operator.equals("+")) {
                result.setText(((int) (value1 + value2)) + "");
            } else if (operator.equals("-")) {
                result.setText((value1 - value2) + "");
            } else if (operator.equals("X")) {
                result.setText((value1 * value2) + "");
            } else if (operator.equals("/")) {
                result.setText((value1 / value2) + "");
            }

        } else {
            if (e.getActionCommand() == "1") {
                if (label.getText().equals("")) {

                    screen1.setText(screen1.getText() + 1);
                    return;
                }
                screen2.setText(screen2.getText() + 1);
                return;
            } else if (e.getActionCommand() == "2") {
                if (label.getText().equals("")) {
                    screen1.setText(screen1.getText() + 2);
                    return;
                }
                screen2.setText(screen2.getText() + 2);
            } else if (e.getActionCommand() == "3") {
                if (label.getText().equals("")) {
                    screen1.setText(screen1.getText() + 3);
                    return;
                }
                screen2.setText(screen2.getText() + 3);
            } else if (e.getActionCommand() == "4") {
                if (label.getText().equals("")) {
                    screen1.setText(screen1.getText() + 4);
                    return;
                }
                screen2.setText(screen2.getText() + 4);
            } else if (e.getActionCommand() == "5") {
                if (label.getText().equals("")) {
                    screen1.setText(screen1.getText() + 5);
                    return;
                }
                screen2.setText(screen2.getText() + 5);
            } else if (e.getActionCommand() == "6") {
                if (label.getText().equals("")) {
                    screen1.setText(screen1.getText() + 6);
                    return;
                }
                screen2.setText(screen2.getText() + 6);
            } else if (e.getActionCommand() == "7") {
                if (label.getText().equals("")) {
                    screen1.setText(screen1.getText() + 7);
                    return;
                }
                screen2.setText(screen2.getText() + 7);
            } else if (e.getActionCommand() == "8") {
                if (label.getText().equals("")) {
                    screen1.setText(screen1.getText() + 8);
                    return;
                }
                screen2.setText(screen2.getText() + 8);
            } else if (e.getActionCommand() == "9") {
                if (label.getText().equals("")) {
                    screen1.setText(screen1.getText() + 9);
                    return;
                }
                screen2.setText(screen2.getText() + 9);
            } else if (e.getActionCommand() == "0") {
                if (label.getText().equals("")) {
                    screen1.setText(screen1.getText() + 0);
                    return;
                }
                screen2.setText(screen2.getText() + 0);
            } else if (e.getActionCommand() == "del") {
                if (result.getText().equals("")) {
                    if (label.getText().equals("") && (screen2.getText().equals(""))) {
                        screen1.setText(screen1.getText().replaceAll(".$", ""));
                        return;
                    } else {
                        if (screen2.getText().equals("")) {
                            label.setText("");
                        } else {
                            screen2.setText(screen2.getText().replaceAll(".$", ""));
                        }
                    }
                }else{
                    result.setText("");
                }

            } else {

                operator = e.getActionCommand();
                label.setText(operator);
            }
        }
    }

    public static void main(String[] args) {
        Calculator cal = new Calculator();
    }

}
