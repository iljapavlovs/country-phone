package io.iljapavlovs.homework.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

import io.iljapavlovs.homework.services.countryprovider.CountryProviderService;
import io.iljapavlovs.homework.services.validation.ValidationService;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CountryPhoneCodeServiceTest {

  @InjectMocks
  private CountryPhoneCodeService subject;

  @Mock
  private ValidationService validationService;
  @Mock
  private CountryCodeExtractionService countryCodeExtractionService;
  @Mock
  private CountryProviderService countryProviderService;

  // todo - should it be shared, if input not changing?
  private static final String PHONE_NUMBER = "+37111111111";

  @BeforeEach
  void setUp() throws Exception {

//    todo - how to mock void methods?

////    todo why not this structure?
//    when(phoneValidationService.validatePhoneNumber(anyString())).then(doNothing());
//
//// todo   why not this?
//    doNothing().when(phoneValidationService.validatePhoneNumber(anyString()));

    doNothing().when(validationService).validatePhoneNumber(anyString());

  }

  //  todo - convention for naming unit tests?
//  [the name of the tested method]_[expected input / tested state]_[expected behavior]
  @Test
  void shouldGetCountriesByPhoneNumber() {

    // Arrange
    when(countryCodeExtractionService.extractCountryCode(PHONE_NUMBER)).thenReturn("+371");
    when(countryProviderService.getCountriesByPhoneCountryCode(anyString()))
        .thenReturn(Collections.singletonList("Latvia"));

    // Act
    final List<String> countriesByPhoneNumber = subject.getCountriesByPhoneNumber(PHONE_NUMBER);

    // Assert
    verify(validationService).validatePhoneNumber(PHONE_NUMBER);//todo or anyString()
    verify(countryCodeExtractionService).extractCountryCode(PHONE_NUMBER);//todo or anyString()
    verify(countryProviderService).getCountriesByPhoneCountryCode(anyString());
//    todo verify(countryProviderService, times(1)).getCountriesByPhoneCountryCode("+371");

    assertThat(countriesByPhoneNumber).containsOnly("Latvia");

  }
// todo
//  1. How to test orchestration services? just with one use case?
//  2. Do i need to verify method invocation and with what arguments? - getCountriesByPhoneCountryCode() - do i need to verify that it was called with specific argument?


}
