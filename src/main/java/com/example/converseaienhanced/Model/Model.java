package com.example.converseaienhanced.Model;

import java.io.File;


public class Model {
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
