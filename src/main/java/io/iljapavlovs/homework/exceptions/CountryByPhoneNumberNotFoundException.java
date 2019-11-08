package io.iljapavlovs.homework.exceptions;

import static java.lang.String.format;

public class CountryByPhoneNumberNotFoundException extends RuntimeException {

  public CountryByPhoneNumberNotFoundException(String phoneNumber) {
    super(format("Country with %s phone number not found", phoneNumber));
  }
}
