package dev.journey.restcountries.service;

import dev.journey.restcountries.client.CountryClient;
import dev.journey.restcountries.dto.CountryApiResponse;
import dev.journey.restcountries.dto.CountryResponse;
import dev.journey.restcountries.exception.CountryNotFoundException;
import feign.FeignException;
import org.springframework.stereotype.Service;

@Service
public class CountryServiceImpl implements CountryServicePort{

    private final CountryClient countryClient;

    public CountryServiceImpl(CountryClient countryClient) {
        this.countryClient = countryClient;
    }

    @Override
    public CountryResponse getCountryInfo(String name) {
        try {
            CountryApiResponse[] response = countryClient.getCountryByName(name);

            CountryApiResponse country = response[0];

            return new CountryResponse(
                    country.name().common(),
                    (country.capital() != null && !country.capital().isEmpty()) ? country.capital().getFirst() : "N/A",
                    country.region(),
                    country.population(),
                    country.flags().png()
            );
        } catch (FeignException.NotFound e) {
            throw new CountryNotFoundException(name);
        } catch (FeignException e) {
            throw new RuntimeException("Error while calling external API: " + e.getMessage());
        }
    }
}
