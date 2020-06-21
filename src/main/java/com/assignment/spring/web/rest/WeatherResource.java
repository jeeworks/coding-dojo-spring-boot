package com.assignment.spring.web.rest;

import com.assignment.spring.service.WeatherService;
import com.assignment.spring.service.dto.WeatherDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class WeatherResource {

    private final WeatherService weatherService;

    @RequestMapping("/weather")
    public ResponseEntity<WeatherDTO> getWeather(@RequestParam(name = "city", required = false, defaultValue = "amsterdam") String city) {
//        String city = request.getParameter("city");
//        String url = Constants.WEATHER_API_URL.replace("{city}", city).replace("{appid}", Constants.APP_ID);
//        ResponseEntity<WeatherResponse> response = restTemplate.getForEntity(url, WeatherResponse.class);
//        return mapper(response.getBody());
        WeatherDTO weatherDTO = weatherService.getWeatherForCity(city);
        return ResponseEntity.ok().body(weatherDTO);
    }

}
