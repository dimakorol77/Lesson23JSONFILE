import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONObject;

public class CurrencyExchangeRates {
    public static void main(String[] args) {
        String apiUrl = "https://www.cbr-xml-daily.ru/daily_json.js";
        String jsonData = "";
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                throw new RuntimeException("HTTP код ошибки : " + responseCode);
            }
            Scanner scanner = new Scanner(url.openStream());
            while (scanner.hasNext()) {
                jsonData += scanner.nextLine();
            }
            scanner.close();
            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = new JSONObject(jsonData);
        try (FileWriter file = new FileWriter("currency_exchange_rates.json")) {
            file.write(jsonObject.toString());
            System.out.println("JSON данные успешно записаны в файл");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
