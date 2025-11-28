package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherSystemApiHelper {
    public static String fetchWeatherCondition() {
        String urlStr = "https://api.open-meteo.com/v1/forecast?latitude=55.9533&longitude=-3.1883&current_weather=true";
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            int status = conn.getResponseCode();
            if (status != 200) return "Clear";
            StringBuilder content = new StringBuilder();
            try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                String line;
                while ((line = in.readLine()) != null) {
                    content.append(line);
                }
            }
            conn.disconnect();
            String body = content.toString();
            int idx = body.indexOf("\"weathercode\":");
            if (idx != -1) {
                int start = idx + 14;
                int end = start;
                while (end < body.length() && java.lang.Character.isDigit(body.charAt(end))) end++;
                int code = Integer.parseInt(body.substring(start, end));
                if (code == 0) return "Clear";
                if (code == 1 || code == 2 || code == 3) return "Cloudy";
                if ((code >= 51 && code <= 67) || (code >= 80 && code <= 99)) return "Rain";
                return "Clear";
            }
            return "Clear";
        } catch (IOException | NumberFormatException e) {
            return "Clear";
        }
    }
}
