package io.iljapavlovs.homework.controllers;

import io.iljapavlovs.homework.dtos.CountriesByPhoneNumberResponseDto;
import io.iljapavlovs.homework.exceptions.PhoneValidationException;
import io.iljapavlovs.homework.services.CountryPhoneCodeService;
import io.iljapavlovs.homework.dtos.ApiErrorDto;
import io.iljapavlovs.homework.exceptions.CountryByPhoneNumberNotFoundException;
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
public class CountryController {

  private static final Logger LOGGER = LoggerFactory.getLogger(CountryController.class);

  private CountryPhoneCodeService countryPhoneCodeService;

  @Autowired
  public CountryController(CountryPhoneCodeService countryPhoneCodeService) {
    this.countryPhoneCodeService = countryPhoneCodeService;
  }

  @GetMapping("/countries")
  public CountriesByPhoneNumberResponseDto getCountry(@RequestParam(name = "phoneNumber") String phoneNumber) {
    LOGGER.info("Requesting country for {} phone number", phoneNumber);
    final List<String> countries = countryPhoneCodeService.getCountriesByPhoneNumber(phoneNumber);
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
