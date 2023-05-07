package com.example.converseaienhanced.Model;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.util.Scanner;

import javafx.concurrent.Task;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ImageToTextApiRequestTask  extends Task<String>  {
    private final String apiUrl = "http://localhost:8080/ocr";

    private final File screenshotFile;

    public ImageToTextApiRequestTask(File screenshotFile) {
        this.screenshotFile = screenshotFile;
    }

    @Override
    protected String call() throws Exception {
        try {
            // Create API request
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW");

            // Set up request body
            String boundary = "----WebKitFormBoundary7MA4YWxkTrZu0gW";
            String crlf = "\r\n";
            String twoHyphens = "--";
            conn.getOutputStream().write((twoHyphens + boundary + crlf).getBytes());
            conn.getOutputStream().write(("Content-Disposition: form-data; name=\"image\"; filename=\"" + screenshotFile.getName() + "\"" + crlf).getBytes());
            conn.getOutputStream().write(("Content-Type: " + Files.probeContentType(screenshotFile.toPath()) + crlf + crlf).getBytes());
            Files.copy(screenshotFile.toPath(), conn.getOutputStream());
            conn.getOutputStream().write((crlf + twoHyphens + boundary + twoHyphens + crlf).getBytes());

            // Wait for response
            int responseCode = conn.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed to receive API response. HTTP error code: " + responseCode);
            }
            String jsonResponse = new Scanner(conn.getInputStream(), "UTF-8").useDelimiter("\\A").next();
            
            // Handle response
            String text = null;
            try {
                //text = getTextValue(jsonResponse);
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(jsonResponse);
                text = rootNode.get("text").asText();
                //System.out.println(text);
            } catch (Exception e) {
                // "text" key not found in JSON response
            }
            return text;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
