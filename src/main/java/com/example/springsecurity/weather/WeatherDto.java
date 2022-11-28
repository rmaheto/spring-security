package com.example.springsecurity.weather;

import lombok.Data;

@Data
public class WeatherDto {

    private int temperatureC;
    private int temperatureF;
    private String summary;
}
