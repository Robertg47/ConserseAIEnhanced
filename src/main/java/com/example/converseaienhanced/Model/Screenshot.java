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

    public static void takeScreenshot(String outputFilePath) throws IOException, AWTException, InterruptedException {
        String osName = System.getProperty("os.name").toLowerCase();

        ProcessBuilder pb;
        // create process with CMD command specific to OS user is using
        if (osName.contains("win")) {
            System.out.println("windows os detected");
            pb = new ProcessBuilder("snippingtool", "/clip");

        } else if (osName.contains("mac")) {
            System.out.println("macOS detected");
            pb = new ProcessBuilder("screencapture", "-i", outputFilePath);
        } else {
            // Unsupported OS
            throw new UnsupportedOperationException("Screenshot functionality not supported on this OS");
        }
        Process process = pb.start();
        process.waitFor();
        // Wait for the snipping tool/screencapture to close and retrieve the screenshot from the clipboard
        BufferedImage image = null;
        try {
            Thread.sleep(100);
            image = (BufferedImage) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.imageFlavor);
        } catch (InterruptedException | UnsupportedFlavorException | IOException e) {
            e.printStackTrace();
        }
        File output = new File(outputFilePath); // your output file path
        assert image != null;
        ImageIO.write(image, "png", output);
    }
}