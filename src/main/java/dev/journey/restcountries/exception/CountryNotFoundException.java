package dev.journey.restcountries.exception;

public class CountryNotFoundException extends RuntimeException {
    public CountryNotFoundException(String name) {
        super("Country '" + name + "' not found.");
    }
}
