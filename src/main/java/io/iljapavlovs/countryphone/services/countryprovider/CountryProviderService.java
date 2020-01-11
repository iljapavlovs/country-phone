package io.iljapavlovs.countryphone.services.countryprovider;

import io.iljapavlovs.countryphone.exceptions.CountryByPhoneCodeNotFoundException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CountryProviderService {

  private HtmlParserService htmlParserService;
  private Map<String, List<String>> countryToPhoneCountryCodesStorage = Collections.synchronizedMap(new HashMap<>());;

  @Autowired
  public CountryProviderService(HtmlParserService htmlParserService) {
    this.htmlParserService = htmlParserService;
  }

  public Map<String, List<String>> getCountryToPhoneCountryCodesStorage() {
    if (countryToPhoneCountryCodesStorage.isEmpty()) {
      countryToPhoneCountryCodesStorage.putAll(htmlParserService.getCountryToPhoneCountryCodes());
    }
    return countryToPhoneCountryCodesStorage;
  }

  public List<String> getCountriesByPhoneCountryCode(String countryCode) {
    final List<String> countryCodes = getCountryToPhoneCountryCodesStorage().entrySet().stream()
        .filter(entry -> entry.getValue().contains(countryCode))
        .map(Entry::getKey)
        .collect(Collectors.toList());

    if (countryCodes.isEmpty()) {
      throw new CountryByPhoneCodeNotFoundException(countryCode);
    }
    return countryCodes;
  }
}

