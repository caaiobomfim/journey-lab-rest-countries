package dev.journey.restcountries.config;

import feign.Logger;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

@Configuration
public class CountryFeignConfig {

    @Bean
    public RequestInterceptor feignInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("Accept", MediaType.APPLICATION_JSON_VALUE);
        };
    }

    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}