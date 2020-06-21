package com.assignment.spring.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "config.open-weather-client")
public class OpenWeatherProperties {
    private String url;
    private String appid;


}
