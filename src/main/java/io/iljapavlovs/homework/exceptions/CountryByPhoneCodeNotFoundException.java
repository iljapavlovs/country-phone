package io.iljapavlovs.homework.exceptions;

import static java.lang.String.format;

public class CountryByPhoneCodeNotFoundException extends RuntimeException {

  public CountryByPhoneCodeNotFoundException(String phoneNumber) {
    super(format("Country with %s phone code not found", phoneNumber));
  }
}
