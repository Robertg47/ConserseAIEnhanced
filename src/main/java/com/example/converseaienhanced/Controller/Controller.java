package com.example.converseaienhanced.Controller;

import com.example.converseaienhanced.Model.Model;
import com.example.converseaienhanced.View.View;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Controller {

    Model model;
    View view;

    public Controller(Model model, View view){
        this.model = model;
        this.view = view;
    }

    @FXML
    private Label welcomeText;

    @FXML
    protected void takeScreenshot() {
        model.screenshot();
    }
}