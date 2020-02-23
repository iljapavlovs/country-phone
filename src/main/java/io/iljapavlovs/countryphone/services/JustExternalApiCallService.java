package io.iljapavlovs.countryphone.services;

import io.iljapavlovs.countryphone.config.ApiCountryServiceSettings;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class JustExternalApiCallService {

  private static final Logger LOGGER = LoggerFactory.getLogger(JustExternalApiCallService.class);
  private RestTemplate restTemplate;
  private ApiCountryServiceSettings apiCountryServiceSettings;

  @Autowired
  public JustExternalApiCallService(RestTemplate restTemplate,
      ApiCountryServiceSettings apiCountryServiceSettings) {
    this.restTemplate = restTemplate;
    this.apiCountryServiceSettings = apiCountryServiceSettings;
  }

  public Map<String, String> getPhoneCodeCountries() {
    LOGGER.info("Sending request to {}", apiCountryServiceSettings.getUrl());
    ResponseEntity<Map<String, String>> response =
        restTemplate.exchange(apiCountryServiceSettings.getUrl(), HttpMethod.GET, null,
            new ParameterizedTypeReference<Map<String, String>>() {});

    LOGGER.info("Received response from {}: {}", apiCountryServiceSettings.getUrl(), response);

    HttpStatus statusCode = response.getStatusCode();
    Map<String, String> countryPhoneCodeMap = response.getBody();

    if (HttpStatus.OK.equals(statusCode) && countryPhoneCodeMap != null) {
      return countryPhoneCodeMap;
    } else {
      LOGGER.warn("Request to Country Phone Code Service {} returned an unspecified status {}",
          apiCountryServiceSettings.getUrl(), statusCode);
      throw new UnsupportedOperationException();
    }
  }
}