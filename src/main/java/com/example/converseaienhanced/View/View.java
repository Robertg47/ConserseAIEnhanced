package com.example.converseaienhanced.View;

import com.example.converseaienhanced.Model.Screenshot;

import java.awt.*;
import java.io.IOException;
import java.util.Date;

public class View {
    public void screenshot(){
        try {
            Screenshot.takeScreenshot("C:\\Users\\rober\\IdeaProjects\\ConserseAIEnhanced\\src\\main\\resources\\Screenshots\\");
        } catch (IOException | AWTException e){
            e.printStackTrace();
        }
    }
}
