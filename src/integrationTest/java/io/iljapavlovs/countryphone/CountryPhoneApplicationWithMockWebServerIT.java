package io.iljapavlovs.countryphone;

import static io.restassured.RestAssured.given;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

import io.iljapavlovs.countryphone.config.HtmlCountryCodeProviderSettings;
import io.iljapavlovs.countryphone.dtos.CountriesByPhoneNumberResponseDto;
import io.iljapavlovs.countryphone.utils.FileLoader;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import java.io.IOException;
import java.util.Collections;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

//https://github.com/rest-assured/rest-assured/blob/master/examples/kotlin-example/src/test/kotlin/io.restassured.kotlin/KotlinITest.kt
//RuntWith not needed as of Spring Boot 2.1
//@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles({"local", "int-test"})
public class CountryPhoneApplicationWithMockWebServerIT {

  @LocalServerPort
  private int port;

  private MockWebServer mockWebServer;

  @MockBean
  private HtmlCountryCodeProviderSettings settings;

  private static RequestSpecification spec;

  @BeforeEach
  public void setup() throws IOException {

    //spec reuse in different tests
    spec = new RequestSpecBuilder()
        .setContentType(ContentType.JSON)
        .setBasePath("/")
        .setPort(port)
        .addFilter(
            new ResponseLoggingFilter())//log request and response for better debugging. You can also only log if a requests fails.
        .addFilter(new RequestLoggingFilter())
        .build();
    mockWebServer = new MockWebServer();
    mockWebServer.start();
    mockWebServer.url("/wiki/List_of_country_calling_codes");

    // todo  how to override property in test
    Mockito.when(settings.getUrl()).thenReturn(format("http://localhost:%s/wiki/List_of_country_calling_codes", mockWebServer.getPort()));
  }

  @AfterEach
  void stopWireMockServer() throws IOException {
    mockWebServer.shutdown();
  }

  @Test
  public void shouldProvideCountryCodes() throws Exception {

    mockWebServer
        .enqueue(new MockResponse()
            .setBody(FileLoader.read("classpath:phone_country_table.html")));

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
