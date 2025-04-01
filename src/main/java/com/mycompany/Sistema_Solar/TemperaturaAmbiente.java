package com.mycompany.Sistema_Solar;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

/**
 * Classe responsável por obter a temperatura ambiente atual de uma cidade usando a API do OpenWeatherMap.
 */
public class TemperaturaAmbiente {

    /**
     * Chama a API do OpenWeatherMap para obter a temperatura atual de uma cidade especificada.
     * 
     * @return A temperatura atual em graus Celsius. Retorna 0 em caso de erro.
     */
    public static double chamarTemperaturaAmbiente() {
        String apiKey = "9e4b6edd20941fa382ac6d9c1fc40205"; // Chave da API do OpenWeatherMap
        String cidade = "Florianopolis,br"; // Cidade para a qual obter a temperatura (personalizável)

        try {
            // Monta a URL para consulta da API
            String urlString = "https://api.openweathermap.org/data/2.5/weather?q=" + cidade + "&units=metric&APPID=" + apiKey;
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // Lê a resposta da API
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            // Parse do JSON para extrair a temperatura
            JSONObject json = new JSONObject(content.toString());
            double temperatura = json.getJSONObject("main").getDouble("temp");

            return temperatura;
        } catch (Exception e) {
            e.printStackTrace(); // Caso ocorra algum erro, imprime a pilha de exceções
        }
        return 0; // Retorna 0 em caso de falha ao obter a temperatura
    }
}

