package io.iljapavlovs.homework.controllers;


import static io.iljapavlovs.homework.utils.ResponseBodyMatchers.responseBody;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.iljapavlovs.homework.dtos.CountriesByPhoneNumberResponseDto;
import io.iljapavlovs.homework.exceptions.CountryByPhoneNumberNotFoundException;
import io.iljapavlovs.homework.exceptions.PhoneValidationException;
import io.iljapavlovs.homework.services.CountryPhoneCodeService;
import java.io.IOException;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CountryController.class)
class CountryControllerIT {

  @MockBean
  private CountryPhoneCodeService countryPhoneCodeService;

  @Autowired
  private MockMvc mockMvc;

  @Test
  void shouldGet() throws Exception {
    when(countryPhoneCodeService.getCountriesByPhoneNumber("+11122222222"))
        .thenReturn(Arrays.asList("Latvia", "Estonia"));

    mockMvc.perform(get("/countries")
        .contentType("application/json")
        .param("phoneNumber", "+11122222222"))
        .andExpect(status().isOk())
        .andExpect(responseBody().containsObjectAsJson(CountriesByPhoneNumberResponseDto.builder()
                .countries(Arrays.asList("Latvia", "Estonia"))
                .phoneNumber("+11122222222")
                .build(),
            CountriesByPhoneNumberResponseDto.class));

    verify(countryPhoneCodeService).getCountriesByPhoneNumber("+11122222222");
  }

  @Test
  void shouldGet_withJsonPath() throws Exception {
    when(countryPhoneCodeService.getCountriesByPhoneNumber("+11122222222"))
        .thenReturn(Arrays.asList("Latvia", "Estonia"));

    mockMvc.perform(get("/countries")
        .contentType("application/json")
        .param("phoneNumber", "+11122222222"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.phoneNumber").value("+11122222222"))
        .andExpect(jsonPath("$.countries", hasSize(2)))
        .andExpect(jsonPath("$.countries", hasItem("Latvia")))
        .andExpect(jsonPath("$.countries", hasItem("Estonia")));

    verify(countryPhoneCodeService).getCountriesByPhoneNumber("+11122222222");
  }

  @Test
  void whenCountryNotFoundExceptionIsThrown_shouldReturns404() throws Exception {
    when(countryPhoneCodeService.getCountriesByPhoneNumber("+11122222222"))
        .thenThrow(new CountryByPhoneNumberNotFoundException("+11122222222"));

    mockMvc.perform(get("/countries")
        .contentType("application/json")
        .param("phoneNumber", "+11122222222"))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.message").value("Country with +11122222222 phone number not found"));


    verify(countryPhoneCodeService).getCountriesByPhoneNumber("+11122222222");
  }

  @Test
  void whenPhoneNotValidExceptionIsThrown_shouldReturn400() throws Exception {
    when(countryPhoneCodeService.getCountriesByPhoneNumber("+11122222222"))
        .thenThrow(new PhoneValidationException("Exception message"));

    mockMvc.perform(get("/countries")
        .contentType("application/json")
        .param("phoneNumber", "+11122222222"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("Exception message"));

    verify(countryPhoneCodeService).getCountriesByPhoneNumber("+11122222222");
  }

  @Test
  void whenOtherExceptionIsThrown_shouldReturn500() throws Exception {
    when(countryPhoneCodeService.getCountriesByPhoneNumber("+11122222222"))
        .thenThrow(new RuntimeException("Exception message"));

    mockMvc.perform(get("/countries")
        .contentType("application/json")
        .param("phoneNumber", "+11122222222"))
        .andExpect(status().isInternalServerError())
        .andExpect(jsonPath("$.message").value("Exception message"));

    verify(countryPhoneCodeService).getCountriesByPhoneNumber("+11122222222");
  }
}
