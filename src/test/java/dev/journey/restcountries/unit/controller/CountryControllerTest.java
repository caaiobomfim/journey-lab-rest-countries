package dev.journey.restcountries.unit.controller;

import dev.journey.restcountries.controller.CountryController;
import dev.journey.restcountries.dto.CountryResponse;
import dev.journey.restcountries.unit.factory.CountryInstancioFactory;
import dev.journey.restcountries.service.CountryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("unit")
class CountryControllerTest {

    private CountryServiceImpl countryService;
    private CountryController countryController;

    @BeforeEach
    void setUp() {
        countryService = mock(CountryServiceImpl.class);
        countryController = new CountryController(countryService);
    }

    @Test
    void shouldReturnCountryInfoWhenValidCountryNameProvided() {
        CountryResponse mockResponse = CountryInstancioFactory.createCountryResponse();
        String countryName = CountryInstancioFactory.createCountryResponse().name();

        when(countryService.getCountryInfo(countryName)).thenReturn(mockResponse);

        ResponseEntity<CountryResponse> response = countryController.getCountry(countryName);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(mockResponse.name(), response.getBody().name());
        assertEquals(mockResponse.capital(), response.getBody().capital());
    }
}
