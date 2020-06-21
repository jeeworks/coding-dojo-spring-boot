package com.assignment.spring.service;

import com.assignment.spring.domain.Weather;
import com.assignment.spring.repository.WeatherRepository;
import com.assignment.spring.service.dto.WeatherDTO;
import com.assignment.spring.service.impl.WeatherServiceImpl;
import com.assignment.spring.service.mapper.WeatherMapper;
import com.assignment.spring.service.mapper.WeatherMapperImpl;
import com.assignment.spring.web.api.Main;
import com.assignment.spring.web.api.Sys;
import com.assignment.spring.web.api.WeatherResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class WeatherServiceImplTest {

    private static final String CITY = "Breda";
    private static final String COUNTRY = "NL";
    private static final double TEMP = 291.52;

    private WeatherServiceImpl weatherService;

    @Mock
    private WeatherRepository weatherRepositoryMock;

    @Mock
    private WebClient openWeatherWebClientMock;

    @Mock
    private WeatherMapper weatherMapper;

    private WeatherResponse weatherResponse;

    @Captor
    ArgumentCaptor<Weather> weatherArgumentCaptor;

    @Before
    public void setup() {
        weatherMapper = new WeatherMapperImpl();
        weatherService = new WeatherServiceImpl(weatherRepositoryMock, weatherMapper, openWeatherWebClientMock);

        final var uriSpecMock = mock(WebClient.RequestHeadersUriSpec.class);
        final var headersSpecMock = mock(WebClient.RequestHeadersSpec.class);
        final var responseSpecMock = mock(WebClient.ResponseSpec.class);
        Mono<WeatherResponse> weatherResponseMono = mock(Mono.class);

        weatherResponse = new WeatherResponse();
        weatherResponse.setName(CITY);
        weatherResponse.setSys(new Sys());
        weatherResponse.getSys().setCountry(COUNTRY);
        weatherResponse.setMain(new Main());
        weatherResponse.getMain().setTemp(TEMP);

        when(openWeatherWebClientMock.get()).thenReturn(uriSpecMock);
        when(uriSpecMock.uri(any(Function.class))).thenReturn(headersSpecMock);
        when(headersSpecMock.retrieve()).thenReturn(responseSpecMock);
        when(responseSpecMock.bodyToMono(eq(WeatherResponse.class))).thenReturn(weatherResponseMono);
        when(weatherResponseMono.block()).thenReturn(weatherResponse);

        when(weatherRepositoryMock.save(any(Weather.class))).thenAnswer(new Answer<Weather>() {
            @Override
            public Weather answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                return (Weather) args[0];
            }
        });
    }

    @Test
    public void testGetWeatherForCity() {
        WeatherDTO weatherDTO = weatherService.getWeatherForCity(CITY);
        assertThat(weatherDTO).isNotNull();
        assertThat(weatherDTO.getCity()).isEqualTo(CITY);
        assertThat(weatherDTO.getCountry()).isEqualTo(COUNTRY);
        assertThat(weatherDTO.getTemperature()).isEqualTo(TEMP);

        verify(weatherRepositoryMock, times(1)).save(weatherArgumentCaptor.capture());
        assertThat(weatherArgumentCaptor).isNotNull();
        assertThat(weatherArgumentCaptor.getValue().getCity()).isEqualTo(CITY);
        assertThat(weatherArgumentCaptor.getValue().getCountry()).isEqualTo(COUNTRY);
        assertThat(weatherArgumentCaptor.getValue().getTemperature()).isEqualTo(TEMP);
    }
}
