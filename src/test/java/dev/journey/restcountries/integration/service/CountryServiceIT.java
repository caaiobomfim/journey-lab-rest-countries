package dev.journey.restcountries.integration.service;

import com.github.tomakehurst.wiremock.client.WireMock;
import dev.journey.restcountries.exception.CountryNotFoundException;
import dev.journey.restcountries.service.CountryServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.testcontainers.containers.BindMode.READ_ONLY;

@SpringBootTest
@Testcontainers
@Tag("integration")
public class CountryServiceIT {

    @Autowired
    private CountryServiceImpl countryService;

    @Container
    static GenericContainer<?> wiremock = new GenericContainer<>("wiremock/wiremock")
            .withExposedPorts(8080)
            .withFileSystemBind("tools/wiremock", "/home/wiremock", READ_ONLY);

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        String mockUrl = String.format("http://%s:%d/v3.1",
                wiremock.getHost(),
                wiremock.getMappedPort(8080));
        registry.add("COUNTRY_API_URL", () -> mockUrl);
    }

    @BeforeAll
    static void setupWireMockClient() {
        WireMock.configureFor(
                wiremock.getHost(),
                wiremock.getMappedPort(8080)
        );
    }

    @Test
    void shouldReturnBrazilDetails() {
        var country = countryService.getCountryInfo("brazil");
        assertEquals("Brazil", country.name());
        assertEquals("Brasília", country.capital());
        assertEquals("Americas", country.region());
        assertEquals(212559409, country.population());
        assertEquals("https://flagcdn.com/w320/br.png", country.flagUrl());
        WireMock.verify(getRequestedFor(urlEqualTo("/v3.1/name/brazil")));
    }

    @Test
    void shouldReturnCanadaDetails() {
        var country = countryService.getCountryInfo("canada");
        assertEquals("Canada", country.name());
        assertEquals("Ottawa", country.capital());
        assertEquals("Americas", country.region());
        assertEquals(38005238, country.population());
        assertEquals("https://flagcdn.com/w320/ca.png", country.flagUrl());
        WireMock.verify(getRequestedFor(urlEqualTo("/v3.1/name/canada")));
    }

    @Test
    void shouldReturnGermanyDetails() {
        var country = countryService.getCountryInfo("germany");
        assertEquals("Germany", country.name());
        assertEquals("Berlin", country.capital());
        assertEquals("Europe", country.region());
        assertEquals(83240525, country.population());
        assertEquals("https://flagcdn.com/w320/de.png", country.flagUrl());
        WireMock.verify(getRequestedFor(urlEqualTo("/v3.1/name/germany")));
    }

    @Test
    void shouldReturnJapanDetails() {
        var country = countryService.getCountryInfo("japan");
        assertEquals("Japan", country.name());
        assertEquals("Tokyo", country.capital());
        assertEquals("Asia", country.region());
        assertEquals(125836021, country.population());
        assertEquals("https://flagcdn.com/w320/jp.png", country.flagUrl());
        WireMock.verify(getRequestedFor(urlEqualTo("/v3.1/name/japan")));
    }

    @Test
    void shouldReturnNauruDetails() {
        var country = countryService.getCountryInfo("nauru");
        assertEquals("Nauru", country.name());
        assertEquals("Yaren", country.capital());
        assertEquals("Oceania", country.region());
        assertEquals(10834, country.population());
        assertEquals("https://flagcdn.com/w320/nr.png", country.flagUrl());
        WireMock.verify(getRequestedFor(urlEqualTo("/v3.1/name/nauru")));
    }

    @Test
    void shouldReturnNotFoundForUnknownCountry() {
        var exception = assertThrows(CountryNotFoundException.class, () -> {
            countryService.getCountryInfo("unknown-country");
        });

        assertEquals("Country 'unknown-country' not found.", exception.getMessage());
        WireMock.verify(getRequestedFor(urlEqualTo("/v3.1/name/unknown-country")));
    }
}
