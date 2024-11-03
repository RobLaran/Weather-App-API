package com.WeatherApp.main;

import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AppFrame extends JFrame {
    AppPanel panel;

    public AppFrame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Weather Application with API");

        ImageIcon appIcon = new ImageIcon(getClass().getClassLoader().getResource("img/icon.png"));
        Image icon = appIcon.getImage();
        this.setIconImage(icon);

        try {
            Path dirPath = Paths.get("data");
            Files.createDirectories(dirPath);
            Path filePath = Paths.get("data/weather-info.json");

            if(!Files.exists(filePath)) {
                Files.createFile(filePath);
                System.out.println("Created the weather-info file");
            } else {
                System.out.println("File exist");
            }
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }

        panel = new AppPanel();

        this.add(panel);
        this.pack();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

}
