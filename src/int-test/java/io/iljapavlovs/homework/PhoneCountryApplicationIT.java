package io.iljapavlovs.homework;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.iljapavlovs.homework.controllers.CountryController;
import io.iljapavlovs.homework.dtos.CountriesByPhoneNumberResponseDto;
import io.iljapavlovs.homework.utils.FileLoader;
import io.restassured.RestAssured;
import java.util.Collections;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

//RuntWith not needed as of Spring Boot 2.1
//@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles({"local", "int-test"})
public class PhoneCountryApplicationIT {

  @Autowired
  private CountryController subject;

  private WireMockServer wireMockServer;

  @BeforeEach
  public void setup() {
    wireMockServer = new WireMockServer(8089);
    wireMockServer.start();
  }

  @AfterEach
  void stopWireMockServer() {
    wireMockServer.stop();
  }

  @Test
  public void shouldProvideCountryCodes() throws Exception {

    wireMockServer.stubFor(get(urlPathEqualTo("/wiki/List_of_country_calling_codes"))
        .willReturn(aResponse()
            .withBody(FileLoader.read("classpath:phone_country_table.html"))
//            .withHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .withStatus(200)));

    final CountriesByPhoneNumberResponseDto actualCountriesByPhoneNumberResponseDto = subject
        .getCountry("+3711111111111");


//
////    todo - based way comparing objects and their fields? - https://stackoverflow.com/questions/57476351/junit5-how-to-assert-several-properties-of-an-object-with-a-single-assert-call
    assertThat(actualCountriesByPhoneNumberResponseDto).isEqualTo(CountriesByPhoneNumberResponseDto.builder().countries(
        Collections.singletonList("Latvia")).phoneNumber("+3711111111111").build());

  }


}
