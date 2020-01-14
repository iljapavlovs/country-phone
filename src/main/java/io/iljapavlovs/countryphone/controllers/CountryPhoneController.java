package io.iljapavlovs.countryphone.controllers;

import io.iljapavlovs.countryphone.dtos.CountriesByPhoneNumberResponseDto;
import io.iljapavlovs.countryphone.exceptions.PhoneValidationException;
import io.iljapavlovs.countryphone.services.CountryPhoneService;
import io.iljapavlovs.countryphone.dtos.ApiErrorDto;
import io.iljapavlovs.countryphone.exceptions.CountryByPhoneNumberNotFoundException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CountryPhoneController {

  private static final Logger LOGGER = LoggerFactory.getLogger(CountryPhoneController.class);

  private CountryPhoneService countryPhoneService;

  @Autowired
  public CountryPhoneController(CountryPhoneService countryPhoneService) {
    this.countryPhoneService = countryPhoneService;
  }

  @GetMapping("/countries")
  public CountriesByPhoneNumberResponseDto getCountry(@RequestParam String phoneNumber) {
    LOGGER.info("Requesting country for {} phone number", phoneNumber);
    final List<String> countries = countryPhoneService.getCountriesByPhoneNumber(phoneNumber);
    return CountriesByPhoneNumberResponseDto.builder().countries(countries).phoneNumber(phoneNumber).build();
  }

  @ExceptionHandler(CountryByPhoneNumberNotFoundException.class)
  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  public ApiErrorDto handleCountryNotFoundException(CountryByPhoneNumberNotFoundException ex) {
    LOGGER.error("Request processing failed", ex);
    return new ApiErrorDto.Builder().message(ex.getMessage()).build();
  }

  @ExceptionHandler(PhoneValidationException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ApiErrorDto handlePhoneNotValidException(PhoneValidationException ex) {
    LOGGER.error("Request processing failed", ex);
    return new ApiErrorDto.Builder().message(ex.getMessage()).build();
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  public ApiErrorDto handleGeneralError(Exception ex) {
    LOGGER.error("Request processing failed", ex);
    return new ApiErrorDto.Builder().message(ex.getMessage()).build();
  }

}
