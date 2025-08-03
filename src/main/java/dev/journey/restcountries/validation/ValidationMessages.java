package dev.journey.restcountries.validation;

public class ValidationMessages {

    public static final String NAME_REQUIRED = "The country name must be provided.";
    public static final String NAME_SIZE = "The country name must be between 2 and 56 characters.";
    public static final String NAME_INVALID = "The country name is invalid.";
    public static final String NAME_REGEXP = "^[A-Za-zÀ-ÿ\\s]+$";
}
