package dev.journey.restcountries.controller;

import dev.journey.restcountries.dto.CountryResponse;
import dev.journey.restcountries.service.CountryServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/countries")
public class CountryController {

    private final CountryServiceImpl service;

    public CountryController(CountryServiceImpl service){
        this.service = service;
    }

    @GetMapping("/{name}")
    public ResponseEntity<CountryResponse> getCountry(@PathVariable String name){
        return ResponseEntity.ok(service.getCountryInfo(name));
    }
}
