package com.assignment.spring.service;

import com.assignment.spring.service.dto.WeatherDTO;

public interface WeatherService {

    WeatherDTO getWeatherForCity(String city);
}
