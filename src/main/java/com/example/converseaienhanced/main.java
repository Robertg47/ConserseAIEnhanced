package com.example.converseaienhanced;

import com.example.converseaienhanced.Model.Model;
import com.example.converseaienhanced.View.View;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("hello-view.fxml"));
        Model model = new Model();
        //System.out.println("ChatGpt: " + model.chatGptApiCall("reply only with the digit. How much is (sinx)^2 + (cosx)^2?"));
        View view = new View();
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
        view.screenshot();
        System.out.println("screenshot taken");
    }

    public static void main(String[] args) {
        launch();
    }
}