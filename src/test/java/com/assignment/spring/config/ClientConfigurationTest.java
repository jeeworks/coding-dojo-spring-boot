package com.assignment.spring.config;

import com.assignment.spring.config.properties.OpenWeatherProperties;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ClientConfigurationTest {

    @Test
    public void testClientConfiguration() {
        OpenWeatherProperties props = new OpenWeatherProperties();
        ClientConfiguration config = new ClientConfiguration(props);
        assertThat(config.getOpenWeatherProperties()).isNotNull();
    }
}
