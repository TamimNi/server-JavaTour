package at.fhtw.swen2.tutorial.controller;
import okhttp3.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
@Component
public class ApiCall {
    private static final String API_URL = "https://www.mapquestapi.com/directions/v2/routematrix?key=%s";
    private static final String apiKey = "ySmPoP4ZPSU290woGOMvF9PQcVCD8NT6";

    private static final Logger logger = LogManager.getLogger(ApiCall.class);
    public static String getRoute(String origin, String destination) throws IOException {
        OkHttpClient client = new OkHttpClient();
        logger.debug("api call to mapquest");
        JSONArray locations = new JSONArray();
        locations.put(origin);
        locations.put(destination);

        JSONObject requestBody = new JSONObject();
        requestBody.put("locations", locations);

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, requestBody.toString());

        Request request = new Request.Builder()
                .url(String.format(API_URL, apiKey))
                .post(body)
                .addHeader("Content-Type", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

}
