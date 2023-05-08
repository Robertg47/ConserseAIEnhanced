package com.example.converseaienhanced.View;

import com.example.converseaienhanced.Controller.Controller;
import com.example.converseaienhanced.main;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import javafx.scene.text.Text;

public class View {

    @FXML
    private ImageView screenshotImageView;
    @FXML 
    private Text ocrResponse;

    @FXML
    private Text chatGPTResponseLabel;

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
            askForScreenshotScene = new Scene(askForScreenshotFXML.load(), 180, 130); //
            screenshotDialogScene = new Scene(screenshotDialogFXML.load(), 470, 550);
            askForScreenshotScene.getStylesheets().add(main.class.getResource("style.css").toExternalForm());   // ADDING CSS to the first screen
            screenshotDialogScene.getStylesheets().add(main.class.getResource("style.css").toExternalForm());   // ADDING CSS to the second screen

            screenshotImageView = (ImageView) screenshotDialogFXML.getNamespace().get("screenshotImageView");
            ocrResponse = (Text) screenshotDialogFXML.getNamespace().get("ocrResponse");
            chatGPTResponseLabel = (Text) screenshotDialogFXML.getNamespace().get("chatGPTResponseLabel");
            } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setTitle("ConverseAI");
        stage.setScene(askForScreenshotScene);
        stage.show();
    }

    public String getOcrResponse(){
        return ocrResponse.getText();
    }

    public void setOcrResponseLabel(String ocrResponce){
        ocrResponse.setText(ocrResponce); //\n \n
    }

    public void setChatGPTResponseLabel(String chatGptResponse){
        chatGPTResponseLabel.setText(chatGptResponse); //\n \n
    }

    public void setSceneToShowScreenshot(File newestScreenshot) {
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
