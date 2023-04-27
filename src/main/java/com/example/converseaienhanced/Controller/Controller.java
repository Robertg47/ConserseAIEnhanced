package com.example.converseaienhanced.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Controller {
    @FXML
    private Label welcomeText;

    @FXML
    protected void ButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}