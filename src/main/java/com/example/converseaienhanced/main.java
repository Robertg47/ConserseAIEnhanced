package com.example.converseaienhanced;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;

//import java.io.File;
import java.io.IOException;

import com.example.converseaienhanced.Helpers.ImageSaver;

public class main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);

        scene.setOnMouseClicked(event -> {
            // Calculate the selected region
            double x = event.getSceneX();
            double y = event.getSceneY();
            double w = 200; // Set the width of the selected region to 200 pixels
            double h = 200; // Set the height of the selected region to 200 pixels
            System.out.print("x");
            System.out.print(x);
            System.out.print("y");
            System.out.print(y);

            // Create a Robot to take a screenshot
            javafx.scene.robot.Robot robot = new javafx.scene.robot.Robot();
            WritableImage screenshot = robot.getScreenCapture(null, x, y, w, h);

            // Save the screenshot to a file
            // File file = new File("screenshot.png");
            try {
                ImageSaver.saveImage(screenshot, "src/main/resources/screenshots/screenshot.png");
                //ImageIO.write(SwingFXUtils.fromFXImage(screenshot, null), "png", file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}