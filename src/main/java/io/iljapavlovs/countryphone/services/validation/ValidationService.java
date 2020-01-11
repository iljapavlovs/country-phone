package io.iljapavlovs.countryphone.services.validation;


import static io.iljapavlovs.countryphone.services.PhoneConstants.MAXIMUM_PHONE_NUMBER_LENGTH;
import static io.iljapavlovs.countryphone.services.PhoneConstants.MINIMUM_PHONE_NUMBER_LENGTH;

import io.iljapavlovs.countryphone.exceptions.PhoneValidationException;
import org.springframework.stereotype.Service;

@Service
public class ValidationService {

  public void validatePhoneNumber(String phoneNumber) {
    if (!phoneNumber.matches("^(\\+|00)\\d+")) {
      throw new PhoneValidationException(
          "Invalid phone number format. Phone number should contain only numbers (except for leading '+' sign)");
    }
    if (!(phoneNumber.startsWith("00") || phoneNumber.startsWith("+"))) {
      throw new PhoneValidationException("Invalid phone number format. Phone number should start with '+' or 00");
    }
    if (phoneNumber.length() < MINIMUM_PHONE_NUMBER_LENGTH) {
      throw new PhoneValidationException("Phone number is too short");
    }

    if (phoneNumber.length() > MAXIMUM_PHONE_NUMBER_LENGTH) {
      throw new PhoneValidationException("Phone number is too long");
    }
  }
}
