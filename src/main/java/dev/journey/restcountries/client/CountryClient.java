package dev.journey.restcountries.client;

import dev.journey.restcountries.config.CountryFeignConfig;
import dev.journey.restcountries.dto.CountryApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "countryClient",
        configuration = CountryFeignConfig.class
)
public interface CountryClient {

    @GetMapping("/name/{name}")
    CountryApiResponse[] getCountryByName(@PathVariable("name") String name);
}
