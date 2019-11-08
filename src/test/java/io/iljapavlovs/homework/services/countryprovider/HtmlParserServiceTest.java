package io.iljapavlovs.homework.services.countryprovider;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.entry;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import io.iljapavlovs.homework.config.HtmlCountryCodeProviderSettings;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;

@ExtendWith(MockitoExtension.class)
class HtmlParserServiceTest {

  @Mock
  private HtmlRetrieverClient htmlRetrieverClient;

  private HtmlParserService subject;

  @BeforeEach
  void setUp() {
    subject = new HtmlParserService(htmlRetrieverClient);
  }

  @Test
  void getCountryToPhoneCountryCodes_shouldParse() {
    when(htmlRetrieverClient.retrieveDocument()).thenReturn(Jsoup.parse(asString(new ClassPathResource("phone_country_table.html"))));

    final Map<String, List<String>> countryToPhoneCountryCodes = subject.getCountryToPhoneCountryCodes();

    // todo - is it ok that test data is all in external file. like checking []
    assertAll(
        () -> assertThat(countryToPhoneCountryCodes)
            .containsExactly(
                entry("Latvia", Collections.singletonList("+371")),
                entry("Guernsey", Arrays.asList("+441481", "+447781", "+447839","+447911")),
                entry("Kazakhstan", Arrays.asList("+76", "+77"))
    ));
  }


//  todo - is correct?
  private static String asString(Resource resource) {
    try (Reader reader = new InputStreamReader(resource.getInputStream(), UTF_8)) {
      return FileCopyUtils.copyToString(reader);
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }
}
