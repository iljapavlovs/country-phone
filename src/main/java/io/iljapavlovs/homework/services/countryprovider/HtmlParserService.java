package io.iljapavlovs.homework.services.countryprovider;

import static java.lang.String.format;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

@Service
public class HtmlParserService {

  private HtmlRetrieverClient htmlRetrieverClient;

  public HtmlParserService(HtmlRetrieverClient htmlRetrieverClient) {
    this.htmlRetrieverClient = htmlRetrieverClient;
  }

  private static final String COUNTRY_PHONE_CODE_LISTING_HEADER_LOCATOR = "#Alphabetical_listing_by_country_or_region";

  public Map<String, List<String>> getCountryToPhoneCountryCodes() {
    final Document doc = htmlRetrieverClient.retrieveDocument();
    final Element countryPhoneCodeListingTable = getCountryPhoneCodeListingTable(doc);
    final Elements tableRows = getCountryPhoneCodeRows(countryPhoneCodeListingTable);
    return transformToCountryToPhoneCodesMap(tableRows);
  }

  private String normalizePhoneCode(String phoneCode) {
    return phoneCode
        .trim()
        .replaceAll("\\s+", "")
        .replaceAll("[a-zA-Z]", "")
        .replaceAll("(\\[(.*)\\])", "");
  }

  private List<String> splitPhoneCode(String phoneCode) {
    return Arrays.asList(phoneCode.split(","));
  }

  private Map<String, List<String>> transformToCountryToPhoneCodesMap(Elements tableRows) {
    return tableRows.stream()
        .collect(Collectors.toMap(row -> row.selectFirst("td").text(), row -> splitPhoneCode(
            normalizePhoneCode(
                row.select("td")
                    .get(1)
                    .text()))));
  }

  private Elements getCountryPhoneCodeRows(Element element) {
    return element.select("tbody tr");
  }

  private Element getCountryPhoneCodeListingTable(Document doc) {
    return doc.selectFirst(COUNTRY_PHONE_CODE_LISTING_HEADER_LOCATOR).parent()
        .nextElementSibling();
  }


}
