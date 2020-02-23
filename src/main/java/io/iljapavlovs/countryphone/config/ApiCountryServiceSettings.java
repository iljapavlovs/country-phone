package io.iljapavlovs.countryphone.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "country-phone-code-provider.external-api")
public class ApiCountryServiceSettings {

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  private String url;
}