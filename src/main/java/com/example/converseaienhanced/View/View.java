package com.example.converseaienhanced.View;

import com.example.converseaienhanced.Controller.Controller;
import com.example.converseaienhanced.Model.Screenshot;
import com.example.converseaienhanced.main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.util.Date;

public class View {
    public void initialize(Stage stage, Controller controller) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("hello-view.fxml"));
        fxmlLoader.setController(controller);
        //System.out.println("ChatGpt: " + model.chatGptApiCall("reply only with the digit. How much is (sinx)^2 + (cosx)^2?"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }
}
