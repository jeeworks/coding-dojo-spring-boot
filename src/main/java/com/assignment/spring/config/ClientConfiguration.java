package com.assignment.spring.config;

import com.assignment.spring.config.properties.OpenWeatherProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Getter
public class ClientConfiguration {
    private final OpenWeatherProperties openWeatherProperties;
}
