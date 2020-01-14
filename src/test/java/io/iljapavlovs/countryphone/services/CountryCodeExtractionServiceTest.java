package io.iljapavlovs.countryphone.services;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

import io.iljapavlovs.countryphone.exceptions.CountryByPhoneNumberNotFoundException;
import io.iljapavlovs.countryphone.services.countryprovider.CountryProviderService;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CountryCodeExtractionServiceTest {

  private CountryCodeExtractionService subject;

  @Mock
  private CountryProviderService countryProviderService;

  @BeforeEach
  void setUp() {
    subject = new CountryCodeExtractionService(countryProviderService);
  }

  @ParameterizedTest(name = "should extract country code from phone number - {1} - {0}")
  @CsvSource({
      "+37123134234,can start with double zeros",
      "0037123134234,can start with plus sign"
  })
  void shouldExtractCountryCode(String phoneNumber, String useCase) {
    final HashMap<String, List<String>> countryToPhoneCountryCodes = new HashMap<String, List<String>>() {{
      put("Mycountry", Collections.singletonList("+371"));
    }};

    // Arrange
    when(countryProviderService.getCountryToPhoneCountryCodesStorage()).thenReturn(countryToPhoneCountryCodes);

    // Act
    final String countryCode = subject.extractCountryCode(phoneNumber);

    // Assert
    assertThat(countryCode).isEqualTo("+371");
    verify(countryProviderService, times(5)).getCountryToPhoneCountryCodesStorage();
  }

  @Test
  void extractCountryCode_withDifferentPhoneNumberLength() {
    // Arrange
    final HashMap<String, List<String>> countryToPhoneCountryCodes = new HashMap<String, List<String>>() {{
      put("Mycountry", Collections.singletonList("+1234567"));
    }};
    when(countryProviderService.getCountryToPhoneCountryCodesStorage()).thenReturn(countryToPhoneCountryCodes);
    String phoneNumber = "+1234567123456";

    // Act
    final String countryCode = subject.extractCountryCode(phoneNumber);

    // Assert
    assertThat(countryCode).isEqualTo("+1234567");
    verify(countryProviderService).getCountryToPhoneCountryCodesStorage();
  }


  @Test
  void extractCountryCode_countryCodeWithEqualBase_shouldExtractCorrectOne() {
    final HashMap<String, List<String>> countryToPhoneCountryCodes = new HashMap<String, List<String>>() {{
      put("Isle of Man", Arrays.asList("+441624", "+447524", "+447624", "+447924"));
      put("United Kingdom", Collections.singletonList("+44"));
      put("Latvia", Collections.singletonList("+371"));
    }};

    // Arrange
    when(countryProviderService.getCountryToPhoneCountryCodesStorage()).thenReturn(countryToPhoneCountryCodes);
    String phoneNumer = "+4416243123";

    // Act
    final String countryCode = subject.extractCountryCode(phoneNumer);

    // Assert
    assertThat(countryCode).isEqualTo("+441624");
  }

  @Test
  void extractCountryCode_nonExistantCountryCode_shouldThrowException() {

    final HashMap<String, List<String>> countryToPhoneCountryCodes = new HashMap<String, List<String>>() {{
      put("Latvia", Collections.singletonList("+371"));
    }};

    // Arrange
    when(countryProviderService.getCountryToPhoneCountryCodesStorage()).thenReturn(countryToPhoneCountryCodes);
    String phoneNumber = "+4416243123";

    // Act
    final CountryByPhoneNumberNotFoundException countryByPhoneNumberNotFoundException = assertThrows(
        CountryByPhoneNumberNotFoundException.class, () -> {
      subject.extractCountryCode(phoneNumber);
    });
    assertThat(countryByPhoneNumberNotFoundException.getMessage())
        .isEqualTo(format("Country with %s phone number not found", phoneNumber));

  }
}
