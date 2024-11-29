package com.mycompany.Sistema_Solar;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class TemperaturaAmbiente {

    public static double chamarTemperaturaAmbiente() {
        String apiKey = "013dd2d0c1a23017ba41ad54b7585424";
        String cidade = "Florianopolis,br"; // Pode personalizar a cidade

        try {
            String urlString = "https://api.openweathermap.org/data/2.5/weather?q=" + cidade + "&units=metric&APPID=" + apiKey;
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            // Parse da resposta JSON para obter a temperatura
            JSONObject json = new JSONObject(content.toString());
            double temperatura = json.getJSONObject("main").getDouble("temp");

            return temperatura;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}
