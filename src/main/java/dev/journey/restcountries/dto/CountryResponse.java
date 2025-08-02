package dev.journey.restcountries.dto;

public record CountryResponse(
        String name,
        String capital,
        String region,
        long population,
        String flagUrl
) {}
