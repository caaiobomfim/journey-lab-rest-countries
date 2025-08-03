package dev.journey.restcountries.factory;

import dev.journey.restcountries.dto.CountryApiResponse;
import dev.journey.restcountries.dto.CountryResponse;
import org.instancio.Instancio;
import org.instancio.Select;

import java.util.List;

public class CountryInstancioFactory {

    public static CountryApiResponse createCountryApiResponse() {
        return Instancio.of(CountryApiResponse.class)
                .set(Select.field(CountryApiResponse::capital), List.of("Brasília"))
                .set(Select.field(CountryApiResponse::population), 212559409L)
                .set(Select.field(CountryApiResponse::region), "Americas")
                .set(Select.field(CountryApiResponse::name), new CountryApiResponse.CountryName("Brazil"))
                .set(Select.field(CountryApiResponse::flags), new CountryApiResponse.CountryFlags("https://flagcdn.com/w320/br.png"))
                .create();
    }

    public static CountryApiResponse createCountryApiResponseWithCapitalEmpty() {
        return Instancio.of(CountryApiResponse.class)
                .set(Select.field(CountryApiResponse::capital), List.of())
                .set(Select.field(CountryApiResponse::population), 212559409L)
                .set(Select.field(CountryApiResponse::region), "Americas")
                .set(Select.field(CountryApiResponse::name), new CountryApiResponse.CountryName("Brazil"))
                .set(Select.field(CountryApiResponse::flags), new CountryApiResponse.CountryFlags("https://flagcdn.com/w320/br.png"))
                .create();
    }

    public static CountryResponse createCountryResponse() {
        return Instancio.of(CountryResponse.class)
                .set(Select.field(CountryResponse::capital), "Brasília")
                .set(Select.field(CountryResponse::population), 212559409L)
                .set(Select.field(CountryResponse::region), "Americas")
                .set(Select.field(CountryResponse::name), "Brazil")
                .set(Select.field(CountryResponse::flagUrl), "https://flagcdn.com/w320/br.png")
                .create();
    }
}
