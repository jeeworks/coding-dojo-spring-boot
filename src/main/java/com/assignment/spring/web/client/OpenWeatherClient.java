package com.assignment.spring.web.client;

import com.assignment.spring.config.ClientConfiguration;
import com.assignment.spring.web.client.exception.ClientErrorException;
import com.assignment.spring.web.client.exception.ServerErrorException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class OpenWeatherClient {

    private final ClientConfiguration clientConfiguration;

    @Bean
    public WebClient openWeatherWebClient() {
        return WebClient.builder()
                .baseUrl(this.clientConfiguration.getOpenWeatherProperties().getUrl())
                .filter(errorHandlingFilter())
                .build();
    }

    private static ExchangeFilterFunction errorHandlingFilter() {
        return ExchangeFilterFunction.ofResponseProcessor( clientResponse -> {
            if (clientResponse.statusCode().isError() && clientResponse.statusCode().is4xxClientError()) {
                return clientResponse.bodyToMono(String.class)
                        .flatMap((body) -> {
                            throw new ClientErrorException(clientResponse.statusCode(), body);
                        });
            }

            if (clientResponse.statusCode().isError() && clientResponse.statusCode().is5xxServerError()) {
                return clientResponse.bodyToMono(String.class)
                        .flatMap((body) -> {
                            throw new ServerErrorException(clientResponse.statusCode(), body);
                        });
            }
            return Mono.just(clientResponse);
        });
    }
}
