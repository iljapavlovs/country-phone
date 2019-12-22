package io.iljapavlovs.homework.services.countryprovider;

import io.iljapavlovs.homework.config.HtmlCountryCodeProviderSettings;
import io.iljapavlovs.homework.services.CountryPhoneCodeService;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class HtmlRetrieverClient {
//todo - how to test clients?
  private static final Logger LOGGER = LoggerFactory.getLogger(CountryPhoneCodeService.class);

  private HtmlCountryCodeProviderSettings settings;

  public HtmlRetrieverClient(HtmlCountryCodeProviderSettings settings) {
    this.settings = settings;
  }

  //  todo - how to test this?
  public Document retrieveDocument() {
    Document doc;
    final String url = settings.getUrl();
    try {
      LOGGER.info("Parsing HTML at {}", url);
      doc = Jsoup
          .connect(url)
          .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:23.0) Gecko/20100101 Firefox/23.0")
          .get();
    } catch (IOException e) {
      LOGGER.warn("Cannot connect to URL " + url);
      throw new RuntimeException(e);
//      todo should I propogate the exception?
//      throw e;
    }
    return doc;
  }

}
