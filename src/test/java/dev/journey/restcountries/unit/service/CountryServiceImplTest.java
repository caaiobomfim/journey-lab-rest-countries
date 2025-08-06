package dev.journey.restcountries.unit.service;

import dev.journey.restcountries.client.CountryClient;
import dev.journey.restcountries.dto.CountryApiResponse;
import dev.journey.restcountries.dto.CountryResponse;
import dev.journey.restcountries.exception.CountryNotFoundException;
import dev.journey.restcountries.service.CountryServiceImpl;
import dev.journey.restcountries.unit.factory.CountryInstancioFactory;
import feign.FeignException;
import feign.Request;
import feign.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("unit")
class CountryServiceImplTest {

    private CountryClient countryClient;
    private CountryServiceImpl countryService;

    @BeforeEach
    void setUp() {
        countryClient = mock(CountryClient.class);
        countryService = new CountryServiceImpl(countryClient);
    }

    @Test
    void shouldReturnCountryInfoSuccessfully() {
        CountryApiResponse mockApiResponse = CountryInstancioFactory.createCountryApiResponse();
        when(countryClient.getCountryByName(CountryInstancioFactory.createCountryApiResponse().name().common()))
                .thenReturn(new CountryApiResponse[]{mockApiResponse});

        CountryResponse response = countryService.getCountryInfo(
                CountryInstancioFactory.createCountryApiResponse().name().common());

        assertEquals(CountryInstancioFactory.createCountryApiResponse().name().common(), response.name());
        assertEquals(CountryInstancioFactory.createCountryApiResponse().capital().getFirst(), response.capital());
        assertEquals(CountryInstancioFactory.createCountryApiResponse().region(), response.region());
        assertEquals(CountryInstancioFactory.createCountryApiResponse().population(), response.population());
        assertEquals(CountryInstancioFactory.createCountryApiResponse().flags().png(), response.flagUrl());
    }

    @Test
    void shouldReturnDefaultCapitalWhenListIsEmpty() {
        CountryApiResponse mockApiResponse = CountryInstancioFactory.createCountryApiResponseWithCapitalEmpty();
        when(countryClient.getCountryByName(CountryInstancioFactory.createCountryApiResponseWithCapitalEmpty().name().common()))
                .thenReturn(new CountryApiResponse[]{mockApiResponse});

        CountryResponse response = countryService.getCountryInfo(
                CountryInstancioFactory.createCountryApiResponse().name().common());

        assertEquals(CountryInstancioFactory.createCountryApiResponseWithCapitalEmpty().name().common(), response.name());
        assertEquals("N/A", response.capital());
        assertEquals(CountryInstancioFactory.createCountryApiResponseWithCapitalEmpty().region(), response.region());
        assertEquals(CountryInstancioFactory.createCountryApiResponseWithCapitalEmpty().population(), response.population());
        assertEquals(CountryInstancioFactory.createCountryApiResponseWithCapitalEmpty().flags().png(), response.flagUrl());
    }

    @Test
    void shouldThrowCountryNotFoundExceptionWhenFeignNotFoundIsThrown() {
        String countryName = "unknown";

        Request request = Request.create(
                Request.HttpMethod.GET,
                "/v3.1/name/" + countryName,
                Collections.emptyMap(),
                null,
                StandardCharsets.UTF_8,
                null
        );

        Response response = Response.builder()
                .status(404)
                .reason("Not Found")
                .request(request)
                .build();

        FeignException notFound = FeignException.errorStatus("GET /v3.1/name/" + countryName, response);

        when(countryClient.getCountryByName(countryName)).thenThrow(notFound);

        CountryNotFoundException thrown = assertThrows(CountryNotFoundException.class, () ->
                countryService.getCountryInfo(countryName)
        );

        assertEquals("Country 'unknown' not found.", thrown.getMessage());
    }

    @Test
    void shouldThrowRuntimeExceptionWhenOtherFeignExceptionIsThrown() {
        String countryName = "brazil";

        Request request = Request.create(
                Request.HttpMethod.GET,
                "/v3.1/name/" + countryName,
                Collections.emptyMap(),
                null,
                StandardCharsets.UTF_8,
                null
        );

        Response response = Response.builder()
                .status(500)
                .reason("Internal Server Error")
                .request(request)
                .build();

        FeignException internalError = FeignException.errorStatus("GET /v3.1/name/" + countryName, response);

        when(countryClient.getCountryByName(countryName)).thenThrow(internalError);

        RuntimeException thrown = assertThrows(RuntimeException.class, () ->
                countryService.getCountryInfo(countryName)
        );

        assertTrue(thrown.getMessage().startsWith("Error while calling external API"));
    }
}