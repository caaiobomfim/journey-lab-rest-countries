package dev.journey.restcountries.service;

import dev.journey.restcountries.dto.CountryResponse;

public interface CountryServicePort {
    CountryResponse getCountryInfo(String name);
}
