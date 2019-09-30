package io.iljapavlovs.homework.exceptions;

import static java.lang.String.format;

public class CountryNotFoundException extends RuntimeException {

  public CountryNotFoundException(String phoneNumber) {
    super(format("Country with %s phone number not found", phoneNumber));
  }
}
