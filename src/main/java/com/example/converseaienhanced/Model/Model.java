package com.example.converseaienhanced.Model;

import Password.Password;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;
import org.json.JSONArray;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import Password.Password; // Uncomment if you have a separate Password class

public class Model {
    private final String apiEndpoint = "https://api.openai.com/v1/chat/completions";
    private final String apiKey;

    public Model() {
        this.apiKey = Password.getApiKey();// Replace with your actual API key or use a separate Password class
    }

    public void chatGptOutput(String message) {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        try {
            HttpPost httpPost = new HttpPost(apiEndpoint);
            httpPost.addHeader("Content-Type", "application/json");
            httpPost.addHeader("Authorization", "Bearer " + apiKey);

            JSONObject requestBody = new JSONObject();
            requestBody.put("model", "gpt-3.5-turbo");
            JSONArray messages = new JSONArray();
            JSONObject messageObj = new JSONObject();
            messageObj.put("role", "user");
            messageObj.put("content", message);
            messages.put(messageObj);
            requestBody.put("messages", messages);
            requestBody.put("temperature", 0.7);

            StringEntity params = new StringEntity(requestBody.toString());
            httpPost.setEntity(params);

            CloseableHttpResponse response = httpClient.execute(httpPost);

            try (response) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    InputStream inputStream = entity.getContent();
                    String responseBody = new Scanner(inputStream, StandardCharsets.UTF_8)
                            .useDelimiter("\r")
                            .next();
                    JSONObject jsonResponse = new JSONObject(responseBody);
                    String output = jsonResponse.getJSONArray("choices")
                            .getJSONObject(0)
                            .getJSONObject("message")
                            .getString("content");
                    System.out.println("Output: " + output.trim());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}