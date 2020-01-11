package io.iljapavlovs.countryphone.services.countryprovider;

import io.iljapavlovs.countryphone.config.HtmlCountryCodeProviderSettings;
import io.iljapavlovs.countryphone.exceptions.HtmlRetrieverClientException;
import io.iljapavlovs.countryphone.services.CountryPhoneService;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class HtmlRetrieverClient {
  private static final Logger LOGGER = LoggerFactory.getLogger(CountryPhoneService.class);
  private HtmlCountryCodeProviderSettings settings;

  public HtmlRetrieverClient(HtmlCountryCodeProviderSettings settings) {
    this.settings = settings;
  }

  public Document retrieveDocument() {
    Document doc;
    final String url = settings.getUrl();
    try {
      LOGGER.info("Parsing HTML at {}", url);
      doc = Jsoup.connect(url).get();
    } catch (IOException e) {
      LOGGER.warn("Cannot connect to URL " + url);
      throw new HtmlRetrieverClientException(e);
    }
    return doc;
  }

}
