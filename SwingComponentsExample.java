import javax.swing.*;
import java.awt.*;

public class SwingComponentsExample {

    public static void main(String[] args) {

        // JFrame (top-level container)
        JFrame frame = new JFrame("Swing Components Example");
        frame.setSize(450, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set layout
        frame.setLayout(new FlowLayout());

        // JLabel
        JLabel label = new JLabel("Enter Your Name:");

        // JTextField
        JTextField textField = new JTextField(15);

        // JTextArea
        JTextArea textArea = new JTextArea(4, 20);
        textArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // JButton
        JButton button = new JButton("Submit");

        // JComboBox
        String[] departments = {"CSE", "ECE", "EEE", "MECH"};
        JComboBox<String> comboBox = new JComboBox<>(departments);

        // JList
        String[] subjects = {"Java", "Python", "DSA", "OS"};
        JList<String> list = new JList<>(subjects);
        list.setVisibleRowCount(3);
        JScrollPane listScroll = new JScrollPane(list);

        // Add components to frame
        frame.add(label);
        frame.add(textField);
        frame.add(new JLabel("Department:"));
        frame.add(comboBox);
        frame.add(new JLabel("Subjects:"));
        frame.add(listScroll);
        frame.add(new JLabel("Remarks:"));
        frame.add(textArea);
        frame.add(button);

        // Show frame
        frame.setVisible(true);
    }
}
