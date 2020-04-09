package pl.lodz.p.it.ssbd2020.ssbd05.web.auth;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.net.ssl.HttpsURLConnection;

public class VerifyRecaptcha {
    public static final String URL = "https://www.google.com/recaptcha/api/siteverify";
    public static final String SECRET = "6Lde2ucUAAAAABo2d2eehKupJ62B0c7avPKl0HoC";
    private final static String USER_AGENT = "Mozilla/5.0";
    public static boolean verify(String gRecaptchaResponse) throws IOException {
        if (gRecaptchaResponse == null || "".equals(gRecaptchaResponse)) {
            return false;
        }
        URL obj = new URL(URL);
        HttpsURLConnection conn = (HttpsURLConnection) obj.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("User-Agent", USER_AGENT);
        conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        String postParams = "secret=" + SECRET + "&response="
                + gRecaptchaResponse;
        conn.setDoOutput(true);

        try (OutputStream outStream = conn.getOutputStream()) {
            outStream.write(postParams.getBytes());
            outStream.flush();
        }
        catch(IOException ioe){
            return false;
        }

        InputStream is = conn.getInputStream();
        JsonObject jsonObject;
        try (JsonReader jsonReader = Json.createReader(is)) {
            jsonObject = jsonReader.readObject();
        }
        catch(RuntimeException re){
            return false;
        }
        return jsonObject.getBoolean("success");
    }
}