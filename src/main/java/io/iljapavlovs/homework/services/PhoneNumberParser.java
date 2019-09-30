package io.iljapavlovs.homework.services;

import static io.iljapavlovs.homework.services.PhoneValidationConstants.MAXIMUM_COUNTRY_CODE_LENGTH;
import static io.iljapavlovs.homework.services.PhoneValidationConstants.MINIMUM_PHONE_LENGTH_WITHOUT_COUNTRY_CODE;

import io.iljapavlovs.homework.exceptions.CountryNotFoundException;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhoneNumberParser {

  private Map<String, List<String>> countryPhoneCodes;

  @Autowired
  public PhoneNumberParser(Map<String, List<String>> countryPhoneCodes) {
    this.countryPhoneCodes = countryPhoneCodes;
  }

  public String getCountryCode(String phoneNumber) {

    phoneNumber = replaceLeadingZeros(phoneNumber);

    int maxPossibleCodeLength = getMaxPossibleCodeLength(phoneNumber);

    for (int i = maxPossibleCodeLength; i > 1; --i) {
      String potentialCountryCode = phoneNumber.substring(0, i);

      if (containsCountryCode(potentialCountryCode)) {
        return potentialCountryCode;
      }
    }
    throw new CountryNotFoundException(phoneNumber);
  }

  private int getMaxPossibleCodeLength(String phoneNumber) {
    int maxPossibleCodeLength = phoneNumber.length() - MINIMUM_PHONE_LENGTH_WITHOUT_COUNTRY_CODE;

    if (maxPossibleCodeLength > MAXIMUM_COUNTRY_CODE_LENGTH) {
      maxPossibleCodeLength = MAXIMUM_COUNTRY_CODE_LENGTH;
    }
    return maxPossibleCodeLength;
  }

  private String replaceLeadingZeros(String phoneNumber) {
    if (phoneNumber.startsWith("00")) {
      phoneNumber = phoneNumber.replaceFirst("00", "+");
    }
    return phoneNumber;
  }

  private boolean containsCountryCode(String possibleCode) {
    return countryPhoneCodes.entrySet().stream()
        .anyMatch(countryPhoneCode -> countryPhoneCode.getValue().stream()
            .anyMatch(code -> code.equals(possibleCode)));
  }

}
