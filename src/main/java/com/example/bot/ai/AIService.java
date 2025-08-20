package com.example.bot.ai;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Service
public class AIService {

    private static final String GEMINI_URL =
            "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent";

    private final OkHttpClient client = new OkHttpClient();
    private final String apiKey;

    // Load API key in constructor
    public AIService() {
        this.apiKey = loadApiKey();
        if (apiKey == null || apiKey.isEmpty()) {
            System.err.println("[AIService] Warning: Gemini API key not found!");
        } else {
            System.out.println("[AIService] Gemini API key loaded successfully.");
        }
    }

    private String loadApiKey() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            if (input != null) {
                Properties props = new Properties();
                props.load(input);
                return props.getProperty("gemini.api.key");
            } else {
                System.err.println("[AIService] application.properties not found in resources!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String askAI(String userMessage) {
        if (apiKey == null || apiKey.isEmpty()) {
            return "AI service is not configured properly.";
        }

        // Build prompt JSON
        JSONObject prompt = new JSONObject()
                .put("contents", new JSONArray()
                        .put(new JSONObject()
                                .put("parts", new JSONArray()
                                        .put(new JSONObject()
                                                .put("text", "You are a helpful senior student. Be friendly and concise:\n" + userMessage)))));

        RequestBody body = RequestBody.create(
                prompt.toString(),
                MediaType.parse("application/json")
        );

        Request request = new Request.Builder()
                .url(GEMINI_URL + "?key=" + apiKey)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                return "Error: Gemini API request failed. HTTP code: " + response.code();
            }

            String responseBody = response.body().string();
            JSONObject json = new JSONObject(responseBody);

            JSONArray candidates = json.optJSONArray("candidates");
            if (candidates != null && candidates.length() > 0) {
                return candidates.getJSONObject(0)
                        .getJSONObject("content")
                        .getJSONArray("parts")
                        .getJSONObject(0)
                        .getString("text");
            }

            return "Sorry, I couldn’t get an answer from Gemini.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error contacting Gemini AI: " + e.getMessage();
        }
    }
}
