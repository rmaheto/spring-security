package com.example.springsecurity.weather;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class WeatherDetailsDto {
    @JsonProperty("temp")
    private double temperature;
    private double feels_like;
    private double temp_min;
    private double temp_max;
    private int pressure;
    private int humidity;


}
