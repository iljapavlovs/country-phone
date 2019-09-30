package io.iljapavlovs.homework.services.phonecodecountryprovider;

import static java.lang.String.format;

import io.iljapavlovs.homework.config.HtmlCountryCodeProviderSettings;
import io.iljapavlovs.homework.services.CountryPhoneCodeService;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.stereotype.Service;

@Service
public class HtmlPhoneCodeCountryProvider implements PhoneCodeCountryProvider {

  private static final Logger LOGGER = LoggerFactory.getLogger(CountryPhoneCodeService.class);

  private static final String COUNTRY_PHONE_CODE_LISTING_HEADER_LOCATOR = "#Alphabetical_listing_by_country_or_region";
  private HtmlCountryCodeProviderSettings settings;

  public HtmlPhoneCodeCountryProvider(HtmlCountryCodeProviderSettings settings) {
    this.settings = settings;
  }

  @Override
  public Map<String, List<String>> getPhoneCodeCountries() {
    final Document doc = getDocument();
    final Element countryPhoneCodeListingTable = getCountryPhoneCodeListingTable(doc);
    final Elements tableRows = getCountryPhoneCodeRows(countryPhoneCodeListingTable);
    return transformToCountryPhoneCodeMap(tableRows);
  }

  private String normalizePhoneCode(String phoneCode) {
    return phoneCode
        .trim()
        .replaceAll("\\s+", "")
        .replaceAll("[a-zA-Z\\[\\]]", "");
  }

  private List<String> splitPhoneCode(String phoneCode) {
    return Arrays.asList(phoneCode.split(","));
  }

  private Map<String, List<String>> transformToCountryPhoneCodeMap(Elements tableRows) {
    return tableRows.stream()
        .collect(Collectors.toMap(row -> row.selectFirst("td").text(), row -> splitPhoneCode(
            normalizePhoneCode(
                row.select("td")
                    .get(1)
                    .text()))));
  }

  private Elements getCountryPhoneCodeRows(Element element) {
    return element.select("tbody tr:not(:first-child)");
  }

  private Element getCountryPhoneCodeListingTable(Document doc) {
    return doc.selectFirst(COUNTRY_PHONE_CODE_LISTING_HEADER_LOCATOR).parent()
        .nextElementSibling();
  }

  private Document getDocument() {

    //    todo how to handle null pointer?
//    final Elements tableRows = doc != null ? doc.select("#Alphabetical_listing_by_country_or_region").get(0).parent()
//        .nextElementSibling().select("tbody tr:not(:first-child)") : throw new HtmlElementNotFoundException("");

//    Document get() does not say that it will return null, however select
//    how idea knows that there might be null?

//    Optional.ofNullable(doc).orElseThrow(() -> new HtmlElementNotFoundException(""));
    Document doc = null;
    try {
      LOGGER.info("Parsing HTML at {}", settings.getUrl());
      doc = Jsoup.connect(settings.getUrl()).get();
    } catch (IOException e) {
      // todo how to handle?
//      todo - dont need this log?
      LOGGER.error("Error occurred while trying to connect to {}", settings.getUrl());
//      todo - what should be error message?
      throw new BeanInitializationException(format("Html cannot be parsed at %s", settings.getUrl()));
    }
    return doc;
  }
}
