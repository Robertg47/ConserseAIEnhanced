package com.example.converseaienhanced.Model;

import java.io.File;
import Password.Password;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
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

import java.awt.*;
import java.io.IOException;

public class Model {
    private final String apiEndpoint = "https://api.openai.com/v1/chat/completions";
    private final String apiKey;

    public Model() {
        this.apiKey = Password.getApiKey();// Replace with your actual API key or use a separate Password class
    }

    public static String extractTextFromImage(File imageFile) {
        Tesseract tesseract = new Tesseract();
        tesseract.setLanguage("eng");
        tesseract.setDatapath("src\\main\\java\\com\\example\\converseaienhanced\\Tools\\tessdata");
        try {
            String result = tesseract.doOCR(imageFile);
            return result;
        } catch (TesseractException e) {
            System.err.println(e.getMessage());
            return "tesseract error";
        }
    }

    public void screenshot(){
        try {
            long milliseconds = System.currentTimeMillis();
            Screenshot.takeScreenshot(".\\src\\main\\resources\\screenshots\\" + milliseconds + ".png");
        } catch (IOException | AWTException | InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("screenshot taken");
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

    public void img2txt(String screenshotFilePath){
        File screenshot = new File(screenshotFilePath);
        ImageToTextApiRequestTask ImageToTextApiRequestTask = new ImageToTextApiRequestTask(screenshot);

        // Execute task on a separate thread
        Thread thread = new Thread(ImageToTextApiRequestTask);
        thread.setDaemon(true);
        thread.start();

        // Handle task completion
        ImageToTextApiRequestTask.setOnSucceeded(event -> {
            String text = ImageToTextApiRequestTask.getValue();
            if (text != null) {
                // Handle successful API response
                System.out.println("API response text: " + text);
            } else {
                // Handle unsuccessful API response
                System.out.println("API response was null or did not contain \"text\" key");
            }
        });

        // Handle task failure
        ImageToTextApiRequestTask.setOnFailed(event -> {
            System.out.println("API request task failed");
            event.getSource().getException().printStackTrace();
        });
    } 
}

