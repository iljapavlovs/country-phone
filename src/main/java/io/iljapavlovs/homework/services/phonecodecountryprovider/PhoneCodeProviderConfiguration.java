package io.iljapavlovs.homework.services.phonecodecountryprovider;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PhoneCodeProviderConfiguration {

  private PhoneCodeCountryProvider htmlPhoneCodeCountryProvider;

  @Autowired
  public PhoneCodeProviderConfiguration(PhoneCodeCountryProvider htmlPhoneCodeCountryProvider) {
    this.htmlPhoneCodeCountryProvider = htmlPhoneCodeCountryProvider;
  }

  @Bean
  Map<String, List<String>> countryPhoneCodes() {
    return htmlPhoneCodeCountryProvider.getPhoneCodeCountries();
  }
}
