package io.iljapavlovs.countryphone.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.iljapavlovs.countryphone.services.countryprovider.CountryProviderService;
import io.iljapavlovs.countryphone.services.validation.ValidationService;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CountryPhoneServiceTest {

  @InjectMocks
  private CountryPhoneService subject;

  @Mock
  private ValidationService validationService;
  @Mock
  private CountryCodeExtractionService countryCodeExtractionService;
  @Mock
  private CountryProviderService countryProviderService;

  @Test
  void shouldGetCountriesByPhoneNumber() {
    String phoneNumber = "+37111111111";

    // Arrange
    when(countryCodeExtractionService.extractCountryCode(phoneNumber)).thenReturn("+371");
    when(countryProviderService.getCountriesByPhoneCountryCode(anyString()))
        .thenReturn(Collections.singletonList("Latvia"));

    // Act
    final List<String> countriesByPhoneNumber = subject.getCountriesByPhoneNumber(phoneNumber);

    // Assert
    verify(validationService).validatePhoneNumber(phoneNumber);
    verify(countryCodeExtractionService).extractCountryCode(phoneNumber);
    verify(countryProviderService).getCountriesByPhoneCountryCode("+371");

    assertThat(countriesByPhoneNumber).containsOnly("Latvia");

  }
}
