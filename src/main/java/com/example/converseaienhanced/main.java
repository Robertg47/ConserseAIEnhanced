package com.example.converseaienhanced;

import com.example.converseaienhanced.Model.Model;
import com.example.converseaienhanced.Controller.Controller;
import com.example.converseaienhanced.View.View;
import javafx.application.Application;
import javafx.stage.Stage;


public class main extends Application {
    @Override
    public void start(Stage stage) {
        Model model = new Model();
        View view = new View();
        Controller controller = new Controller(model, view);
        view.initialize(stage, controller);
    }


    public static void main(String[] args) {
        launch();
    }
}