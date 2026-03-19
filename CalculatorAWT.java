import java.awt.*;
import java.awt.event.*;
public class CalculatorAWT extends Frame implements ActionListener {
    Label l1, l2, l3;
    TextField t1, t2, t3;
    Button add, sub, mul, div;
    CalculatorAWT() {
        // Labels
        l1 = new Label("Number 1:");
        l2 = new Label("Number 2:");
        l3 = new Label("Result:");
        // TextFields
        t1 = new TextField();
        t2 = new TextField();
        t3 = new TextField();
        t3.setEditable(false);
        // Buttons
        add = new Button("Add");
        sub = new Button("Subtract");
        mul = new Button("Multiply");
        div = new Button("Divide");
        // Set layout
        setLayout(new GridLayout(5, 2, 10, 10));
        // Add components
        add(l1); add(t1);
        add(l2); add(t2);
        add(l3); add(t3);
        add(add); add(sub);
        add(mul); add(div);
        // Register events
        add.addActionListener(this);
        sub.addActionListener(this);
        mul.addActionListener(this);
        div.addActionListener(this);
        // Frame settings
        setTitle("Simple AWT Calculator");
        setSize(300, 250);
        setVisible(true);
        // Close window
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }
    // Event Handling
    public void actionPerformed(ActionEvent e) {
        int a = Integer.parseInt(t1.getText());
        int b = Integer.parseInt(t2.getText());
        int result = 0;
        if (e.getSource() == add)
            result = a + b;
        else if (e.getSource() == sub)
            result = a - b;
        else if (e.getSource() == mul)
            result = a * b;
        else if (e.getSource() == div)
            result = a / b;
        t3.setText(String.valueOf(result));
    }
    public static void main(String[] args) {
        new CalculatorAWT();
    }
}