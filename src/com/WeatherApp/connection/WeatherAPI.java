package com.WeatherApp.connection;

import com.WeatherApp.main.AppPanel;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class WeatherAPI {

    String APIKey = "ce067dccbeaf2d6f3048b7dfa7fe14a1";
    public String address;
    JsonNode node;

    public void fetch(String city) throws Exception {
        address = "https://api.openweathermap.org/data/2.5/weather?q="+
                city+"&appid="+APIKey+"&units=metric";

        try {
           HttpClient client = HttpClient.newHttpClient();

           HttpRequest request = HttpRequest.newBuilder().uri(URI.create(address)).header("Accept", "application/json").build();
           HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

           ObjectMapper mapper = new ObjectMapper();
           saveLatestJSONFromAPI(response);
           node = mapper.readTree(response.body());
        } catch(Exception e) {
            System.err.println("Error creating HTTP com.WeatherApp.connection");
            e.printStackTrace();
            throw e;
        }
    }

    public void saveLatestJSONFromAPI(HttpResponse<String> response) {
        File file = new File("data/weather-info.json");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(response.body());
            System.out.println("Stored latest JSON data to local");
        } catch(IOException  ioExc) {
            ioExc.printStackTrace();
        }
    }

    public void loadJSONFromLocal() {
        File file = new File("data/weather-info.json");

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            ObjectMapper mapper = new ObjectMapper();
            node = mapper.readTree(reader);
        } catch(IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public boolean checkConnection(String city) {
        try {
            address = "https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid="+APIKey+"&units=metric";

            URL url = new URL(address);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");

            if(connection.getResponseCode() == 200) {
                System.out.println("API Requested");
            }
            connection.disconnect();
        } catch(IOException ioException) {
            System.err.println("Failed to connect: " + ioException.getMessage());
            return false;
        }

        return true;
    }

    public void gatherData(AppPanel panel) {
        panel.currentTime.setText(getCurrentTime());
        panel.temperatureLabel.setText(getTemperature()+"\u00b0");
        panel.setWeatherImg(getIconCode());
        panel.weatherParam.setText(mainWeather());
        panel.weatherDescription.setText("Country: " + weatherInfo());
        panel.cityName.setText(getName());
        panel.windValue.setText(getWind()+" km/h");
        panel.humidValue.setText(getHumid()+"%");
    }

    public String getCurrentTime() {
        // Get timezone offset and UTC time from response
        int timezoneOffset = node.get("timezone").asInt();
        long timestamp = node.get("dt").asLong();

        // Calculate the correct local time
        Instant utcTime = Instant.ofEpochSecond(timestamp);
        ZoneOffset zoneOffset = ZoneOffset.ofTotalSeconds(timezoneOffset);
        LocalDateTime localTime = LocalDateTime.ofInstant(utcTime, zoneOffset);

        // Format the local time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        String formattedTime = localTime.format(formatter);

        return formattedTime;
    }

    public int getTemperature() {
        return node.get("main").get("temp").asInt();
    }

    public String getIconCode() {
        return node.get("weather").findValues("icon").get(0).asText();
    }

    public String mainWeather() {
        return node.get("weather").findValues("main").get(0).asText();
    }

    public String weatherInfo() {
        return node.get("sys").findValues("country").get(0).asText();
    }

    public String getName() {
        System.out.println();

        return  node.get("name").asText();
    }

    public String getWind() {
        System.out.println();

        return String.valueOf(node.get("wind").findValues("speed").get(0).asInt());
    }

    public String getHumid() {
        return node.get("main").findValues("humidity").get(0).asText();
    }

}
