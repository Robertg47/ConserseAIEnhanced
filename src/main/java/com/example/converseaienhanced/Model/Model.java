package com.example.converseaienhanced.Model;

import Password.Password;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.json.JSONArray;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Model {
    private final String apiEndpoint = "https://api.openai.com/v1/chat/completions";
    private final String apiKey;

    public Model() {
        this.apiKey = Password.getApiKey();// Replace with your actual API key or use a separate Password class
    }

    public String chatGptApiCall(String message) {
        System.out.println("User: " + message);
        System.out.println();

        String answer = "SUPER ERROR";
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
                String json = EntityUtils.toString(entity, "UTF-8");
                System.out.println("json:");
                System.out.println(json);

                // create an ObjectMapper instance
                ObjectMapper objectMapper = new ObjectMapper();

                // parse the JSON string into a JsonNode object
                JsonNode rootNode = objectMapper.readTree(json);

                String content = rootNode.path("choices").get(0).path("message").path("content").asText();
                answer = content;
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
        return answer;
    }
}