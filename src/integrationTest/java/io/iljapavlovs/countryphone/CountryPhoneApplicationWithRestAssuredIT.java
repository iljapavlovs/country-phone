package io.iljapavlovs.countryphone;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.iljapavlovs.countryphone.dtos.CountriesByPhoneNumberResponseDto;
import io.iljapavlovs.countryphone.utils.FileLoader;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import java.util.Collections;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

//RuntWith not needed as of Spring Boot 2.1
//@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles({"local", "int-test"})
public class CountryPhoneApplicationWithRestAssuredIT {

  @LocalServerPort
  private int port;

  private WireMockServer wireMockServer;

  private static RequestSpecification spec;

  @BeforeEach
  public void setup() {

    //spec reuse in different tests
    spec = new RequestSpecBuilder()
        .setContentType(ContentType.JSON)
        .setBasePath("/")
        .setPort(port)
        .addFilter(new ResponseLoggingFilter())//log request and response for better debugging. You can also only log if a requests fails.
        .addFilter(new RequestLoggingFilter())
        .build();
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
            .withStatus(200)));

    final CountriesByPhoneNumberResponseDto actualDto = given()
        .spec(spec)
        .queryParam("phoneNumber", "+3711111111111")
        .when()
        .get("/countries")
        .then()
        .statusCode(200)
        .extract().as(CountriesByPhoneNumberResponseDto.class);

    assertThat(actualDto).isEqualTo(CountriesByPhoneNumberResponseDto.builder().countries(
        Collections.singletonList("Latvia")).phoneNumber("+3711111111111").build());

  }


}
