package dev.journey.restcountries.dto;

import java.util.List;

public record CountryApiResponse(
        CountryName name,
        List<String> capital,
        String region,
        long population,
        CountryFlags flags
) {
    public record CountryName(
           String common
    ){}
    public record CountryFlags(
            String png
    ){}
}
