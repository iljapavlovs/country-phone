package io.iljapavlovs.homework.services.countryprovider;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.iljapavlovs.homework.exceptions.CountryByPhoneCodeNotFoundException;
import io.iljapavlovs.homework.exceptions.CountryByPhoneNumberNotFoundException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CountryProviderServiceTest {

  private CountryProviderService subject;

  @Mock
  private HtmlParserService htmlParserService;

  @BeforeEach
  void setUp() {
    subject = new CountryProviderService(htmlParserService);
  }

  //  todo - is it ok to test in one test, name?
  @Test
  void getCountryToPhoneCountryCodesStorage_invokeTwice_shouldInvokeParserServiceOnce() {

    final HashMap<String, List<String>> countryToPhoneCountryCodes = new HashMap<String, List<String>>() {{
      put("Isle of Man", Arrays.asList("+441624", "+447524", "+447624", "+447924"));
      put("United Kingdom", Collections.singletonList("+44"));
      put("Latvia", Collections.singletonList("+371"));
    }};

    when(htmlParserService.getCountryToPhoneCountryCodes()).thenReturn(countryToPhoneCountryCodes);

    subject.getCountryToPhoneCountryCodesStorage();

    verify(htmlParserService).getCountryToPhoneCountryCodes();
    reset(htmlParserService);
    subject.getCountryToPhoneCountryCodesStorage();
    verify(htmlParserService, times(0)).getCountryToPhoneCountryCodes();
  }

  @Test
  void getCountriesByPhoneCountryCode_phoneCodeWithSeveralCountries_shouldReturnListOfCountries() {
    final HashMap<String, List<String>> countryToPhoneCountryCodes = new HashMap<String, List<String>>() {{
      put("Isle of Man", Arrays.asList("+441624", "+447524", "+447624", "+447924"));
      put("United Kingdom", Collections.singletonList("+44"));
      put("Latvia", Collections.singletonList("+371"));
      put("United States", Collections.singletonList("+1"));
      put("Canada", Collections.singletonList("+1"));
    }};
    when(htmlParserService.getCountryToPhoneCountryCodes()).thenReturn(countryToPhoneCountryCodes);

    final List<String> countriesByPhoneCountryCode = subject.getCountriesByPhoneCountryCode("+1");

    verify(htmlParserService).getCountryToPhoneCountryCodes();
    assertThat(countriesByPhoneCountryCode).containsOnly("United States", "Canada");

  }

  @Test
  void getCountriesByPhoneCountryCode_nonExistantPhoneCode_shouldThrowException() {
    final HashMap<String, List<String>> countryToPhoneCountryCodes = new HashMap<String, List<String>>() {{
      put("Isle of Man", Arrays.asList("+441624", "+447524", "+447624", "+447924"));
      put("United Kingdom", Collections.singletonList("+44"));
    }};
    when(htmlParserService.getCountryToPhoneCountryCodes()).thenReturn(countryToPhoneCountryCodes);

    final CountryByPhoneCodeNotFoundException countryByPhoneCodeNotFoundException = assertThrows(
        CountryByPhoneCodeNotFoundException.class, () -> {
      subject.getCountriesByPhoneCountryCode("+4");
    });
    assertThat(countryByPhoneCodeNotFoundException.getMessage()).isEqualTo("Country with +4 phone code not found");

  }
}
