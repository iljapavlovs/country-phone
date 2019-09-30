package io.iljapavlovs.homework.services;

import io.iljapavlovs.homework.exceptions.CountryNotFoundException;
import io.iljapavlovs.homework.services.validation.PhoneValidationService;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CountryPhoneCodeService {

  private static final Logger LOGGER = LoggerFactory.getLogger(CountryPhoneCodeService.class);

  private Map<String, List<String>> countryPhoneCodesMap;
  private PhoneValidationService phoneValidationService;
  private PhoneNumberParser phoneNumberParser;

  @Autowired
  public CountryPhoneCodeService(Map<String, List<String>> countryPhoneCodesMap,
      PhoneValidationService phoneValidationService, PhoneNumberParser phoneNumberParser) {
    this.countryPhoneCodesMap = countryPhoneCodesMap;
    this.phoneValidationService = phoneValidationService;
    this.phoneNumberParser = phoneNumberParser;
  }

  public List<String> getCountriesByPhoneNumber(String phoneNumber) {
    phoneValidationService.validatePhoneNumber(phoneNumber);
    final String countryCode = phoneNumberParser.getCountryCode(phoneNumber);
    return getCountriesByCountryCode(countryCode);
  }

  private List<String> getCountriesByCountryCode(String countryCode) {
    final List<String> countryCodes = countryPhoneCodesMap.entrySet().stream()
        .filter(entry -> entry.getValue().contains(countryCode))
        .map(Entry::getKey)
        .collect(Collectors.toList());

    if (countryCodes.isEmpty()) {
      throw new CountryNotFoundException(countryCode);
    }
    return countryCodes;
  }

}
