package com.example.weatherinfo.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class WeatherResponse {

    private List<Weather> weather;
    private Main main;
    private Wind wind;

    @Setter
    @Getter
    public static class Weather {
        private String description;
    }

    @Setter
    @Getter
    public static class Main {
        private double temp;
        private int humidity;
    }

    @Setter
    @Getter
    public static class Wind {
        private double speed;
    }
}