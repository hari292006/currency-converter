import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.LinkedHashMap;
import org.json.JSONObject;
import java.awt.event.*;

public class CurrencyConverter extends JFrame {

    private JComboBox<CurrencyItem> fromBox, toBox;
    private JTextField amountField;
    private JLabel resultLabel;
    private JTextArea liveRatesArea;

    // Load currencies from separate file
    private LinkedHashMap<String, String> currencyNames = CurrencyList.getCurrencies();

    public CurrencyConverter() {

        setTitle("Currency Converter Application");
        setSize(900, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(new Color(240, 245, 255));
        add(main);

        // ---------------- HEADER ----------------
        JPanel header = new JPanel();
        header.setBackground(new Color(0, 70, 140));
        header.setPreferredSize(new Dimension(400, 90));

        JLabel title = new JLabel("Currency Converter Application");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        header.add(title);

        main.add(header, BorderLayout.NORTH);

        // ---------------- LEFT SIDE ----------------
        JPanel center = new JPanel(new GridLayout(6, 1, 12, 12));
        center.setBorder(new EmptyBorder(20, 25, 20, 25));
        center.setBackground(new Color(240, 245, 255));

        fromBox = new JComboBox<>(loadItems());
        toBox = new JComboBox<>(loadItems());
        fromBox.setRenderer(new CurrencyRenderer());
        toBox.setRenderer(new CurrencyRenderer());

        amountField = new JTextField();
        amountField.setFont(new Font("Segoe UI", Font.PLAIN, 19));

        JButton convertBtn = new JButton("Convert");
        convertBtn.setFont(new Font("Segoe UI", Font.BOLD, 20));
        convertBtn.setBackground(new Color(0, 200, 0));
        convertBtn.setForeground(Color.WHITE);

        // UPDATE BUTTON TEXT + LIVE RATES SIDE PANEL
        ItemListener updateBtn = e -> {
            CurrencyItem f = (CurrencyItem) fromBox.getSelectedItem();
            CurrencyItem t = (CurrencyItem) toBox.getSelectedItem();
            convertBtn.setText("Convert " + f.code + " to " + t.code);

            // fetch live rates
            LiveRatesFetcher.updateRates(f.code, currencyNames, liveRatesArea);
        };

        fromBox.addItemListener(updateBtn);
        toBox.addItemListener(updateBtn);

        convertBtn.addActionListener(e -> convertCurrency());

        resultLabel = new JLabel("", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        resultLabel.setBorder(new LineBorder(Color.GRAY, 2, true));

        center.add(makeRow("From Currency:", fromBox));
        center.add(makeRow("To Currency:", toBox));
        center.add(makeRow("Amount:", amountField));
        center.add(convertBtn);
        center.add(resultLabel);

        main.add(center, BorderLayout.WEST);

        // ---------------- RIGHT PANEL (LIVE RATES) ----------------
        liveRatesArea = new JTextArea();
        liveRatesArea.setEditable(false);
        liveRatesArea.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        JScrollPane scroll = new JScrollPane(liveRatesArea);
        scroll.setPreferredSize(new Dimension(350, 400));

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(new JLabel("Live Exchange Rates", SwingConstants.CENTER),
                       BorderLayout.NORTH);
        rightPanel.add(scroll, BorderLayout.CENTER);

        main.add(rightPanel, BorderLayout.EAST);

        // Initial load
        LiveRatesFetcher.updateRates("USD", currencyNames, liveRatesArea);
    }

    private JPanel makeRow(String text, JComponent input) {
        JPanel row = new JPanel(new BorderLayout());
        row.setBackground(new Color(240, 245, 255));
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 18));
        row.add(label, BorderLayout.WEST);
        row.add(input, BorderLayout.CENTER);
        return row;
    }

    // ---------------- Currency Item ----------------
    class CurrencyItem {
        String code, name;
        ImageIcon icon;

        CurrencyItem(String code, String name) {
            this.code = code;
            this.name = name;
            this.icon = new ImageIcon("flags/" + code.toLowerCase() + ".png");
        }

        public String toString() {
            return code + " – " + name;
        }
    }

    class CurrencyRenderer extends JLabel implements ListCellRenderer<CurrencyItem> {
        public Component getListCellRendererComponent(JList<? extends CurrencyItem> list,
                CurrencyItem item, int index, boolean isSelected, boolean cellHasFocus) {

            setText(item.code + " – " + item.name);
            setIcon(item.icon);
            setOpaque(true);
            setBackground(isSelected ? new Color(220, 240, 255) : Color.WHITE);
            return this;
        }
    }

    private CurrencyItem[] loadItems() {
        CurrencyItem[] arr = new CurrencyItem[currencyNames.size()];
        int i = 0;
        for (String code : currencyNames.keySet())
            arr[i++] = new CurrencyItem(code, currencyNames.get(code));
        return arr;
    }

    // ---------------- Convert ----------------
    private void convertCurrency() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            CurrencyItem from = (CurrencyItem) fromBox.getSelectedItem();
            CurrencyItem to = (CurrencyItem) toBox.getSelectedItem();

            String api = "https://api.exchangerate-api.com/v4/latest/" + from.code;

            BufferedReader br = new BufferedReader(new InputStreamReader(new URL(api).openStream()));
            JSONObject json = new JSONObject(br.readLine());

            double rate = json.getJSONObject("rates").getDouble(to.code);

            resultLabel.setText("" + (amount * rate) + " " + to.code);

        } catch (Exception e) {
            resultLabel.setText("Please enter a valid number..!!!");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CurrencyConverter().setVisible(true));
    }
}
