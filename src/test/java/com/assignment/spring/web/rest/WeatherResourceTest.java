package com.assignment.spring.web.rest;

import com.assignment.spring.service.WeatherService;
import com.assignment.spring.service.dto.WeatherDTO;
import com.assignment.spring.web.client.exception.ClientErrorException;
import com.assignment.spring.web.client.exception.ServerErrorException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WeatherResource.class)
@RunWith(SpringRunner.class)
public class WeatherResourceTest {

    private static final int ID = 1234;
    private static final String CITY = "Breda";
    private static final String COUNTRY = "NL";
    private static final double TEMP = 291.52;

    private WeatherDTO weatherDTO;

    @Before
    public void setup() {
        weatherDTO = WeatherDTO.builder()
                .id(ID)
                .city(CITY)
                .country(COUNTRY)
                .temperature(TEMP)
                .build();
    }

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WeatherService weatherService;

    @Test
    public void testGetWeather() throws Exception {
        when(weatherService.getWeatherForCity(CITY)).thenReturn(weatherDTO);
        this.mockMvc.perform(MockMvcRequestBuilders
            .get("/api/weather").param("city", CITY))
            .andExpect(status().is2xxSuccessful())
            .andExpect(content().json("{\"id\":1234,\"city\":\"Breda\",\"country\":\"NL\",\"temperature\":291.52}"));
    }

    @Test
    public void testGetWeatherThrowsServerErrorException() throws Exception {
        ServerErrorException serverErrorException = new ServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "We have an internal server error");
        when(weatherService.getWeatherForCity(CITY)).thenThrow(serverErrorException);
        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/api/weather").param("city", CITY))
                .andExpect(status().is5xxServerError())
                .andExpect(content().string("We have an internal server error"));
    }

    @Test
    public void testGetWeatherThrowsClientErrorException() throws Exception {
        ClientErrorException clientErrorException = new ClientErrorException(HttpStatus.NOT_FOUND, "We could not found the city error");
        when(weatherService.getWeatherForCity(CITY)).thenThrow(clientErrorException);
        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/api/weather").param("city", CITY))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string("We could not found the city error"));
    }

}
