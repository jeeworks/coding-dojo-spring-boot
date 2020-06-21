package com.assignment.spring.service.impl;

import com.assignment.spring.domain.Weather;
import com.assignment.spring.repository.WeatherRepository;
import com.assignment.spring.service.WeatherService;
import com.assignment.spring.service.dto.WeatherDTO;
import com.assignment.spring.service.mapper.WeatherMapper;
import com.assignment.spring.web.api.WeatherResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {

    private final WeatherRepository weatherRepository;

    private final WeatherMapper weatherMapper;

    @Qualifier("openWeatherWebClient")
    private final WebClient openWeatherWebClient;

    @Override
    public WeatherDTO getWeatherForCity(String city) {
        WeatherResponse weatherResponse = openWeatherWebClient
                .get()
                .uri( uriBuilder -> uriBuilder.build(city) )
                .retrieve()
                .bodyToMono(WeatherResponse.class)
                .block();

        return this.weatherMapper.toDto(this.mapAndSave(weatherResponse));
    }

    private Weather mapAndSave(WeatherResponse response) {
        Weather weather = new Weather();
        weather.setCity(response.getName());
        weather.setCountry(response.getSys().getCountry());
        weather.setTemperature(response.getMain().getTemp());

        return weatherRepository.save(weather);
    }

}
