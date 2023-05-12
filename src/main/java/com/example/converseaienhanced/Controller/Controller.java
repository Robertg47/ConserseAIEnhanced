package com.example.converseaienhanced.Controller;

import com.example.converseaienhanced.Model.ImageToTextApiRequestTask;
import com.example.converseaienhanced.Model.Model;
import com.example.converseaienhanced.View.View;
import javafx.fxml.FXML;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.ExecutionException;

public class Controller {

    Model model;
    View view;

    public Controller(Model model, View view){
        this.model = model;
        this.view = view;
    }

    @FXML
    protected void takeScreenshot() {
        model.screenshot();
        try {
            Thread.sleep(100);
            view.setSceneToShowScreenshot(getLastScreenshot());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void process() {
        String osName = System.getProperty("os.name").toLowerCase();
        String ocrOutput = "";
        if (osName.contains("mac")) {
            ImageToTextApiRequestTask task = new ImageToTextApiRequestTask(getLastScreenshot());

            Thread thread = new Thread(task);
            thread.start();

            try {
                thread.join(); // Wait for the thread to finish
                System.out.println("ocrOutput");
                System.out.println(ocrOutput);
                ocrOutput = task.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
        } else {
            ocrOutput = Model.extractTextFromImage(getLastScreenshot());
        }
        System.out.println(ocrOutput);
        // clean the string from non-ascii characters
        ocrOutput = ocrOutput.replaceAll("[^\\x00-\\x7F]", "");

        String prompt = view.getUserPrompt() + "\n This text was generated with optical character recognition (OCR), restore if some words are misspelled.\n" + ocrOutput;
        view.setOcrResponseLabel(prompt);
    }

    @FXML
    protected void chatGptRequest() {
        String response = model.chatGptApiCall(view.getOcrResponse());

        view.setChatGPTResponseLabel(response);
    }


    public File getLastScreenshot(){
        File screenshotsDir = new File(".\\src\\main\\resources\\Screenshots");
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("mac")) {
            screenshotsDir = new File("src/main/resources/Screenshots");
        }
        File[] screenshots = screenshotsDir.listFiles();
        if (screenshots == null || screenshots.length-1 == -1) {
            System.out.println("no screenshots detected");
            return null;
        }
        Arrays.sort(screenshots, Comparator.comparing(File::getName));

        return screenshots[screenshots.length-1];
    }
}