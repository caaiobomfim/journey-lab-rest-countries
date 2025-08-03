package dev.journey.restcountries.exception;

import dev.journey.restcountries.controller.CountryController;
import dev.journey.restcountries.service.CountryServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CountryController.class)
@Import({RestExceptionHandler.class})
class RestExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CountryServiceImpl mockService;

    @Test
    void shouldReturnBadRequestWhenPathVariableIsInvalid() throws Exception {
        mockMvc.perform(get("/countries/c")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/problem+json"))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.type").value("https://journey.dev/problems/validation-error"))
                .andExpect(jsonPath("$.title").value("Validation error"))
                .andExpect(jsonPath("$.detail").value("One or more fields are invalid."))
                .andExpect(jsonPath("$.instance").value("/countries/c"))
                .andExpect(jsonPath("$.violations[0].field").value("name"))
                .andExpect(jsonPath("$.violations[0].message").exists())
                .andExpect(jsonPath("$.violations[0].rejectedValue").value("c"));
    }

    @Test
    void shouldReturnNotFoundWhenCountryNotFoundExceptionIsThrown() throws Exception {
        when(mockService.getCountryInfo("unknown"))
                .thenThrow(new CountryNotFoundException("unknown"));

        mockMvc.perform(get("/countries/unknown")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("application/problem+json"))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.type").value("https://journey.dev/problems/country-not-found"))
                .andExpect(jsonPath("$.title").value("Country not found"))
                .andExpect(jsonPath("$.detail").value("Country 'unknown' not found."))
                .andExpect(jsonPath("$.instance").value("/countries/unknown"));
    }

    @Test
    void shouldReturnInternalServerErrorWhenRuntimeExceptionIsThrown() throws Exception {
        when(mockService.getCountryInfo("brazil"))
                .thenThrow(new RuntimeException("Unexpected failure"));

        mockMvc.perform(get("/countries/brazil")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType("application/problem+json"))
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(jsonPath("$.type").value("https://journey.dev/problems/internal-server-error"))
                .andExpect(jsonPath("$.title").value("Unexpected error"))
                .andExpect(jsonPath("$.detail").value("Unexpected failure"))
                .andExpect(jsonPath("$.instance").value("/countries/brazil"));
    }
}