package com.WeatherApp.main;

import com.WeatherApp.connection.WeatherAPI;

import javax.swing.*;
import java.awt.*;

public class AppPanel extends JPanel {
    private final int APP_HEIGHT = 490;
    private final int APP_WIDTH = 360;

    WeatherAPI api;

    public JLabel currentWeatherLabel;
    public JLabel currentTime;

    public JLabel weatherImg;
    public JLabel weatherParam;
    public ImageIcon png;

    public JPanel searchBar;
    public JTextField textField;

    public JLabel temperatureLabel;

    public JLabel weatherDescription;
    public JLabel cityName;

    public JLabel windLabel;
    public JLabel humidLabel;

    public JLabel windValue;
    public JLabel humidValue;

    public Listener actionListener;
    private SpringLayout layout = new SpringLayout();

    public AppPanel() {
        this.setPreferredSize(new Dimension(APP_WIDTH, APP_HEIGHT));
        this.setBackground(Color.decode(CommonConstants.BG_COLOR));
        this.setLayout(layout);
        this.setFocusable(true);

        api = new WeatherAPI();
        actionListener = new Listener(this);

        addComponents();
        setupAPI();
    }

    public void addComponents() {
        currentWeatherLabel = new JLabel("Current Weather");
        currentWeatherLabel.setForeground(Color.decode(CommonConstants.FONT_COLOR));
        currentWeatherLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 16));
        layout.putConstraint(SpringLayout.NORTH, currentWeatherLabel, 22, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, currentWeatherLabel, 18, SpringLayout.WEST, this);

        currentTime = new JLabel();
        currentTime.setForeground(Color.decode(CommonConstants.FONT_COLOR));
        currentTime.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
        layout.putConstraint(SpringLayout.NORTH, currentTime, 4, SpringLayout.SOUTH, currentWeatherLabel);
        layout.putConstraint(SpringLayout.WEST, currentTime, 32, SpringLayout.WEST, currentWeatherLabel);

        weatherImg = new JLabel();
        weatherImg.setPreferredSize(new Dimension(85, 65));
        weatherImg.setHorizontalAlignment(SwingConstants.CENTER);
        layout.putConstraint(SpringLayout.NORTH, weatherImg, 135, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, weatherImg, 60, SpringLayout.WEST, this);

        weatherParam = new JLabel();
        weatherParam.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
        weatherParam.setForeground(Color.decode(CommonConstants.FONT_COLOR));
        weatherParam.setPreferredSize(new Dimension(85, 45));
        weatherParam.setHorizontalAlignment(SwingConstants.CENTER);
        layout.putConstraint(SpringLayout.NORTH, weatherParam, 180, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, weatherParam, 60, SpringLayout.WEST, this);

        temperatureLabel = new JLabel();
        temperatureLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 78));
        temperatureLabel.setForeground(Color.decode(CommonConstants.FONT_COLOR));
        layout.putConstraint(SpringLayout.NORTH, temperatureLabel, 120, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.EAST, temperatureLabel, -18, SpringLayout.EAST, this);

        weatherDescription = new JLabel();
        weatherDescription.setFont(new Font(Font.MONOSPACED, Font.BOLD, 24));
        weatherDescription.setForeground(Color.decode(CommonConstants.FONT_COLOR));
        weatherDescription.setPreferredSize(new Dimension(APP_WIDTH, 44));
        weatherDescription.setHorizontalAlignment(SwingConstants.CENTER);
        layout.putConstraint(SpringLayout.NORTH, weatherDescription, 230, SpringLayout.NORTH, this);

        cityName = new JLabel();
        cityName.setFont(new Font(Font.MONOSPACED, Font.BOLD, 34));
        cityName.setForeground(Color.decode(CommonConstants.FONT_COLOR));
        cityName.setPreferredSize(new Dimension(APP_WIDTH, 48));
        cityName.setHorizontalAlignment(SwingConstants.CENTER);
        layout.putConstraint(SpringLayout.NORTH, cityName, 290, SpringLayout.NORTH, this);

        windLabel = new JLabel("Wind");
        windLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 34));
        windLabel.setForeground(Color.decode(CommonConstants.FONT_COLOR));
        windLabel.setPreferredSize(new Dimension(APP_WIDTH / 2, 30));
        windLabel.setHorizontalAlignment(SwingConstants.CENTER);
        layout.putConstraint(SpringLayout.SOUTH, windLabel, -80, SpringLayout.SOUTH, this);

        humidLabel = new JLabel("Humid");
        humidLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 34));
        humidLabel.setForeground(Color.decode(CommonConstants.FONT_COLOR));
        humidLabel.setPreferredSize(new Dimension(APP_WIDTH / 2, 30));
        humidLabel.setHorizontalAlignment(SwingConstants.CENTER);
        layout.putConstraint(SpringLayout.SOUTH, humidLabel, -80, SpringLayout.SOUTH, this);
        layout.putConstraint(SpringLayout.WEST, humidLabel, APP_WIDTH / 2, SpringLayout.WEST, this);

        windValue = new JLabel();
        windValue.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
        windValue.setForeground(Color.decode(CommonConstants.FONT_COLOR));
        windValue.setPreferredSize(new Dimension(APP_WIDTH / 2, 30));
        windValue.setHorizontalAlignment(SwingConstants.CENTER);
        layout.putConstraint(SpringLayout.NORTH, windValue, 15, SpringLayout.SOUTH, windLabel);

        humidValue = new JLabel();
        humidValue.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
        humidValue.setForeground(Color.decode(CommonConstants.FONT_COLOR));
        humidValue.setPreferredSize(new Dimension(APP_WIDTH / 2, 30));
        humidValue.setHorizontalAlignment(SwingConstants.CENTER);
        layout.putConstraint(SpringLayout.NORTH, humidValue, 15, SpringLayout.SOUTH, windLabel);
        layout.putConstraint(SpringLayout.WEST, humidValue, APP_WIDTH / 2, SpringLayout.WEST, this);


        this.add(currentWeatherLabel);
        this.add(currentTime);
        this.add(searchBar());
        this.add(weatherImg);
        this.add(weatherParam);
        this.add(temperatureLabel);
        this.add(weatherDescription);
        this.add(cityName);
        this.add(windLabel);
        this.add(humidLabel);
        this.add(windValue);
        this.add(humidValue);
    }

    public JPanel searchBar() {
        searchBar = new JPanel();
        searchBar.setPreferredSize(new Dimension(170,42));
        searchBar.setBackground(Color.decode(CommonConstants.PRIMARY_COLOR));

        layout.putConstraint(SpringLayout.NORTH, searchBar, 30, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.EAST, searchBar, -18, SpringLayout.EAST, this);

//        ImageIcon imgSearchIcon = new ImageIcon("src/com/WeatherApp/resources/img/search.png");
        JLabel searchIcon = new JLabel();

        ImageIcon img = new ImageIcon(getClass().getClassLoader().getResource("img/search.png"));
        searchIcon.setIcon(img);

        textField = new JTextField(12);
        textField.setFont(new Font(Font.MONOSPACED, Font.BOLD, 16));
        textField.setFocusable(true);
        textField.setRequestFocusEnabled(true);
        textField.addKeyListener(actionListener);

        searchBar.add(searchIcon);
        searchBar.add(textField);

        return searchBar;
    }

    public void setupAPI() {
        String defaultCity = "Maasin";
        System.out.println("Setting up API");
        try {
            if(api.checkConnection(defaultCity)) {
                api.fetch(defaultCity);
                api.gatherData(this);
            } else {
                api.loadJSONFromLocal();
                api.gatherData(this);

                textField.setEnabled(false);

                JOptionPane.showMessageDialog(null, "No Internet Connection: Failed to request API","Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setWeatherImg(String code) {
        png = new ImageIcon(getClass().getClassLoader().getResource("img/"+code+".png"));
        weatherImg.setIcon(png);
    }

}
