import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

class Translator {
    static StringBuilder response;
    static URL url;
    static String urlStr;
    public static String translate(String langFrom, String langTo, String text) throws IOException {
        // INSERT YOU URL HERE
        urlStr = "https://script.google.com/macros/s/AKfycbwrpYd0RlHsg5mgpeYXm4Qta8fiPfVqppC_jbRTmzDyRtdhUzWd0dP7ilsrfNpmYQ4gvQ/exec" +
                "?q=" + URLEncoder.encode(text, "UTF-8") +
                "&target=" + langTo +
                "&source=" + langFrom;
        url = new URL(urlStr);
        response = new StringBuilder();
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }
}