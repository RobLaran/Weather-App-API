package com.WeatherApp.main;

import com.WeatherApp.connection.WeatherAPI;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Listener extends KeyAdapter {
    AppPanel panel;
    WeatherAPI api;

    public Listener(AppPanel panel) {
        this.panel = panel;
        this.api = panel.api;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(KeyEvent.VK_ENTER == e.getKeyChar()) {
            System.out.println("Fetching");
            String city = panel.textField.getText();

            if(api.checkConnection(city)) {
                try {
                    api.fetch(city);
                    api.gatherData(panel);

                    panel.textField.setText("");
                } catch (Exception exc) {
                    exc.printStackTrace();
                }
            } else {
                api.loadJSONFromLocal();
                api.gatherData(panel);

                panel.textField.setEnabled(false);
                panel.textField.setText("");

                JOptionPane.showMessageDialog(null, "No Internet Connection: Failed to request API","Error", JOptionPane.ERROR_MESSAGE);
            }

        }
    }

}
