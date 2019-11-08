package io.iljapavlovs.homework;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.iljapavlovs.homework.controllers.CountryController;
import io.iljapavlovs.homework.dtos.CountriesByPhoneNumberResponseDto;
import io.iljapavlovs.homework.utils.FileLoader;
import java.io.IOException;
import java.util.Collections;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

//RuntWith not needed as of Spring Boot 2.1
//@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc // needed since it MockMvc is only provided with @MockMvcTest
@ActiveProfiles({"local", "int-test"})
public class PhoneCountryApplicationWithMockMvcIT {

  @Autowired
  private MockMvc mockMvc;

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
            .withStatus(200)));

    mockMvc.perform(MockMvcRequestBuilders.get("/countries")
        .contentType("application/json")
        .param("phoneNumber", "+3711111111111"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.phoneNumber").value("+3711111111111"))
        .andExpect(jsonPath("$.countries", hasSize(1)))
        .andExpect(jsonPath("$.countries", hasItem("Latvia")));
  }
}
