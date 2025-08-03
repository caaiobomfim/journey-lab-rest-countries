package dev.journey.restcountries.exception;

import java.time.Instant;
import java.util.List;

public record ErrorResponse(
        String type,
        String title,
        int status,
        String detail,
        String instance,
        Instant timestamp,
        List<Violation> violations
) {
    public ErrorResponse(String type, String title, int status, String detail, String instance) {
        this(type, title, status, detail, instance, Instant.now(), null);
    }

    public record Violation(
            String field,
            String message,
            Object rejectedValue
    ) {}
}
