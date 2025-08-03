package dev.journey.restcountries.controller;

import dev.journey.restcountries.dto.CountryResponse;
import dev.journey.restcountries.service.CountryServiceImpl;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static dev.journey.restcountries.validation.ValidationMessages.*;

@RestController
@RequestMapping("/countries")
@Validated
public class CountryController {

    private final CountryServiceImpl service;

    public CountryController(CountryServiceImpl service){
        this.service = service;
    }

    @GetMapping("/{name}")
    public ResponseEntity<CountryResponse> getCountry(
            @PathVariable
            @NotBlank(message = NAME_REQUIRED)
            @Size(min = 2, max = 56, message = NAME_SIZE)
            @Pattern(regexp = NAME_REGEXP, message = NAME_INVALID)
            String name){
        return ResponseEntity.ok(service.getCountryInfo(name));
    }
}
