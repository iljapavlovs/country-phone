package io.iljapavlovs.homework.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import io.iljapavlovs.homework.services.validation.PhoneValidationService;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
  private Map<String, List<String>> countryPhoneCodesMap;

  @Mock
  private PhoneValidationService phoneValidationService;

  @Mock
  private PhoneNumberParser phoneNumberParser;

  // todo - should it be shared, if input not changing?
  private String phoneNumber = "+37126833125";


  @Test
  public void shouldInit() {
  }

  @BeforeEach
  public void setUp() throws Exception {

//    todo - how to mock void methods?

////    todo why not this structure?
//    when(phoneValidationService.validatePhoneNumber(anyString())).then(doNothing());
//
//// todo   why not this?
//    doNothing().when(phoneValidationService.validatePhoneNumber(anyString()));

    doNothing().when(phoneValidationService).validatePhoneNumber(anyString());

  }

  @Test
  public void getCountriesByPhoneNumber() {

    when(phoneNumberParser.getCountryCode(phoneNumber)).thenReturn("Latvia");

//    when(countryPhoneCodes) -NO!

    ;

    final Map<String, List<String>> mockedCountryPhoneCodes = new HashMap<>();

    mockedCountryPhoneCodes.put("Latvia", Collections.singletonList("+371"));
    mockedCountryPhoneCodes.put("Estonia", Collections.singletonList("+372"));

    when(countryPhoneCodesMap.entrySet()).thenReturn(mockedCountryPhoneCodes.entrySet());

    final List<String> countriesByPhoneNumber = subject.getCountriesByPhoneNumber(phoneNumber);
    assertThat(countriesByPhoneNumber).containsOnly("Latvia");

  }
//
//  @Test
//  public void shouldGetCountryByPhoneNumber() {
//    subject.getCountryByPhoneNumber()
//
//  }
}
