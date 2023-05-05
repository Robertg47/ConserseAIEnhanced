package com.example.converseaienhanced;

import com.example.converseaienhanced.Controller.Controller;
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
        Model model = new Model();
        View view = new View();
        Controller controller = new Controller(model, view);
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("hello-view.fxml"));
        fxmlLoader.setController(controller);
        //System.out.println("ChatGpt: " + model.chatGptApiCall("reply only with the digit. How much is (sinx)^2 + (cosx)^2?"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}