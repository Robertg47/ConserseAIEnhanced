package com.example.converseaienhanced.Model;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Screenshot {

    public static void takeScreenshot(String outputFilePath) throws IOException, AWTException {
        String osName = System.getProperty("os.name").toLowerCase();

        if (osName.contains("win")) {
            System.out.println("windows os detected");
            // Windows OS
            ProcessBuilder pb = new ProcessBuilder("snippingtool", "/clip");
            pb.start();

            // Wait for the snipping tool to close and retrieve the screenshot from the clipboard
            try {
                Thread.sleep(500); // Wait for the snipping tool to load
                BufferedImage image = (BufferedImage) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.imageFlavor);

                // Save the screenshot to the specified output file path
                File output = new File(outputFilePath);
                ImageIO.write(image, "png", output);
            } catch (InterruptedException | UnsupportedFlavorException | IOException e) {
                e.printStackTrace();
            }
        } else if (osName.contains("mac")) {
            System.out.println("macOS detected");
            // macOS
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            Rectangle screenRectangle = new Rectangle(screenSize);
            Robot robot = new Robot();
            BufferedImage image = robot.createScreenCapture(screenRectangle);
            File output = new File(outputFilePath);
            ImageIO.write(image, "png", output);
        } else {
            // Unsupported OS
            throw new UnsupportedOperationException("Screenshot functionality not supported on this OS");
        }
    }
}