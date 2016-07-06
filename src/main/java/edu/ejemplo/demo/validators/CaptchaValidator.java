package edu.ejemplo.demo.validators;

import edu.ejemplo.demo.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.net.ssl.HttpsURLConnection;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

@Component
public class CaptchaValidator {

    private final String SITE_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";
    private Logger log = LoggerFactory.getLogger(CaptchaValidator.class);


    public Boolean verify (String gRecaptchaResponse){
        if(gRecaptchaResponse == null || gRecaptchaResponse.length() == 0){
            return false;
        }

        try {
            URL verifyUrl = new URL(SITE_VERIFY_URL);

            //Open connection to url
            HttpsURLConnection connection = (HttpsURLConnection) verifyUrl.openConnection();

            //Add request header
            connection.setRequestMethod("POST");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

            //Sent data to server
            String postParam = "secret=" + User.SECRET_KEY + "&response=" + gRecaptchaResponse;
            log.info("sent parameter " + postParam + " to URL");

            //Sent request
            connection.setDoOutput(true);

            //Get the output stream of connection
            //Write data into the stream, which mean to sent data to server
            OutputStream out = connection.getOutputStream();
            out.write(postParam.getBytes());

            out.flush();
            out.close();

            //Response code return from server
            int responseCode = connection.getResponseCode();
            log.info("get response code : " + responseCode);

            // Get the InputStream from Connection to read data sent from the server.
            InputStream is = connection.getInputStream();

            JsonReader jsonReader = Json.createReader(is);
            JsonObject jsonObject = jsonReader.readObject();
            jsonReader.close();

            // ==> {"success": true}
            System.out.println("Response: " + jsonObject);

            return jsonObject.getBoolean("success");

        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }


    }
}
