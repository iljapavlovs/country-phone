package io.iljapavlovs.homework.services;

import static io.iljapavlovs.homework.services.PhoneConstants.MAXIMUM_COUNTRY_CODE_LENGTH;
import static io.iljapavlovs.homework.services.PhoneConstants.MINIMUM_PHONE_LENGTH_WITHOUT_COUNTRY_CODE;

import io.iljapavlovs.homework.exceptions.CountryByPhoneNumberNotFoundException;
import io.iljapavlovs.homework.services.countryprovider.CountryProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CountryCodeExtractionService {

  private CountryProviderService countryProviderService;

  @Autowired
  public CountryCodeExtractionService(CountryProviderService countryProviderService) {
    this.countryProviderService = countryProviderService;
  }

  public String extractCountryCode(String phoneNumber) {

    phoneNumber = replaceLeadingZeros(phoneNumber);

    int maxPossibleCodeLength = getMaxPossibleCodeLength(phoneNumber);

    for (int i = maxPossibleCodeLength; i > 1; --i) {
      String potentialCountryCode = phoneNumber.substring(0, i);

      if (containsCountryCode(potentialCountryCode)) {
        return potentialCountryCode;
      }
    }
    throw new CountryByPhoneNumberNotFoundException(phoneNumber);
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
    return countryProviderService.getCountryToPhoneCountryCodesStorage().entrySet().stream()
        .anyMatch(countryPhoneCode -> countryPhoneCode.getValue().stream()
            .anyMatch(code -> code.equals(possibleCode))
        );
  }

}
