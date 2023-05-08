package com.example.converseaienhanced.Controller;

import com.example.converseaienhanced.Model.ImageToTextApiRequestTask;
import com.example.converseaienhanced.Model.Model;
import com.example.converseaienhanced.View.View;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

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
        //ImageToTextApiRequestTask task = new ImageToTextApiRequestTask(getLastScreenshot());

        /*
        Thread thread = new Thread(task);
        thread.start();

        try {
            thread.join(); // Wait for the thread to finish
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println(ImageToTextApiRequestTask.result);
        */
        //view.setOcrResponseLabel(ImageToTextApiRequestTask.result);

        String tesseractResponse = "2x2+2? \n a) 4 \n b) 6 \n c) 8\n";
        String prompt = "This is a test question, please reply with an answer, and explain the answer \n " + tesseractResponse;
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