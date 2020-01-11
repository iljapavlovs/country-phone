package io.iljapavlovs.countryphone.services;

import io.iljapavlovs.countryphone.services.countryprovider.CountryProviderService;
import io.iljapavlovs.countryphone.services.validation.ValidationService;
import java.util.List;
import org.springframework.stereotype.Service;


@Service
public class CountryPhoneService {

  private final ValidationService validationService;
  private final CountryCodeExtractionService countryCodeExtractionService;
  private final CountryProviderService countryProviderService;

  public CountryPhoneService(
      ValidationService validationService,
      CountryCodeExtractionService countryCodeExtractionService,
      CountryProviderService countryProviderService) {
    this.validationService = validationService;
    this.countryCodeExtractionService = countryCodeExtractionService;
    this.countryProviderService = countryProviderService;
  }

  public List<String> getCountriesByPhoneNumber(String phoneNumber) {
    validationService.validatePhoneNumber(phoneNumber);
    final String countryCode = countryCodeExtractionService.extractCountryCode(phoneNumber);
    return countryProviderService.getCountriesByPhoneCountryCode(countryCode);
  }

}
