package com.example.converseaienhanced.View;

import com.example.converseaienhanced.Controller.Controller;
import com.example.converseaienhanced.Model.Screenshot;
import com.example.converseaienhanced.main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

public class View {

    @FXML
    private ImageView screenshotImageView;
    FXMLLoader askForScreenshotFXML;
    FXMLLoader screenshotDialogFXML;

    Stage stage;

    Scene askForScreenshotScene;

    Scene screenshotDialogScene;

    public void initialize(Stage stage, Controller controller) {
        this.stage = stage;
        askForScreenshotFXML = new FXMLLoader(main.class.getResource("hello-view.fxml"));
        screenshotDialogFXML = new FXMLLoader(main.class.getResource("screenshot-dialog.fxml"));
        askForScreenshotFXML.setController(controller);
        screenshotDialogFXML.setController(controller);
        //System.out.println("ChatGpt: " + model.chatGptApiCall("reply only with the digit. How much is (sinx)^2 + (cosx)^2?"));
        try {
            askForScreenshotScene = new Scene(askForScreenshotFXML.load(), 200, 200);
            screenshotDialogScene = new Scene(screenshotDialogFXML.load(), 470, 460);
            screenshotImageView = (ImageView) screenshotDialogFXML.getNamespace().get("screenshotImageView");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setTitle("Hello!");
        stage.setScene(askForScreenshotScene);
        stage.show();
    }

    public void setSceneToAskForScreenshot(){
        stage.setScene(askForScreenshotScene);
    }
    public void setSceneToShowScreenshot() {
        File screenshotsDir = new File(".\\src\\main\\resources\\Screenshots");
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("mac")) {
            screenshotsDir = new File("src/main/resources/Screenshots");
        }
        File[] screenshots = screenshotsDir.listFiles();
        if (screenshots == null || screenshots.length-1 == -1) {
            System.out.println("no screenshots detected");
            return;
        }
        Arrays.sort(screenshots, Comparator.comparing(File::getName));

        File newestScreenshot = screenshots[screenshots.length-1];
        String path = newestScreenshot.getPath();
        String url = "empty";
        try {
            url = newestScreenshot.toURI().toURL().toExternalForm();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("newest screenshot's path: " + path);
        System.out.println("newest screenshot's URL: " + url);
        screenshotImageView.setImage(new ImageView(url).getImage());

        stage.setScene(screenshotDialogScene);
    }
}
