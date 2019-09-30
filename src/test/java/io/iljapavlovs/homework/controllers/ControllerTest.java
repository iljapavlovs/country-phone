//package io.iljapavlovs.homework.controllers;
//
//import static org.hamcrest.core.Is.is;
//import static org.junit.Assert.*;
//import static org.mockito.BDDMockito.given;
//
//import io.iljapavlovs.homework.model.CountryPhoneNumber;
//import io.iljapavlovs.homework.services.CountryPhoneCodeService;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//
///**
// * https://martinfowler.com/articles/practical-test-pyramid.html
// * https://reflectoring.io/unit-testing-spring-boot/
// */
//@RunWith(MockitoJUnitRunner.class)
//public class ControllerTest {
//
//  private Controller subject;
//
//  @Mock
//  private CountryPhoneCodeService countryPhoneCodeService;
//
//
//  @Before
//  public void setUp() {
//    subject = new Controller(countryPhoneCodeService);
//  }
//
//  //TODO - no sense testing controller which just acts as a proxy
//  @Test
//  public void shouldGetCountry() {
//    final CountryPhoneNumber dto = CountryPhoneNumber.builder()
//        .phoneNumber("3711111111")
//        .countries("LV")
//        .build();
//    given(countryPhoneCodeService.getCountryByPhoneNumber(dto.getPhoneNumber()))
//        .willReturn(dto);
//
//    final CountryPhoneNumber country = subject.getCountry(dto.getPhoneNumber());
//    assertThat(country, is(dto));
//  }
//
//  @Test
//  public void shouldHandleCountryNotFoundException() {
//  }
//
//  @Test
//  public void shouldHandleGeneralError() {
//  }
//}
