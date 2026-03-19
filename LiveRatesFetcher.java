import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.LinkedHashMap;
import javax.swing.JTextArea;
import org.json.JSONObject;

public class LiveRatesFetcher {

    public static void updateRates(String baseCurrency,
                                   LinkedHashMap<String, String> currencyNames,
                                   JTextArea outputArea) {

        try {

            String api = "https://api.exchangerate-api.com/v4/latest/" + baseCurrency;

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(new URL(api).openStream())
            );

            JSONObject json = new JSONObject(br.readLine());
            JSONObject rates = json.getJSONObject("rates");

            StringBuilder sb = new StringBuilder();
            sb.append("1 ").append(baseCurrency).append(" =\n\n");

            for (String code : currencyNames.keySet()) {
                sb.append(code)
                  .append(" : ")
                  .append(rates.getDouble(code))
                  .append("\n");
            }

            outputArea.setText(sb.toString());

        } catch (Exception e) {
            outputArea.setText("Unable to load live rates.");
        }
    }
}
