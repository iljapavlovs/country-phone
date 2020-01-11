package io.iljapavlovs.countryphone.services.validation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.iljapavlovs.countryphone.exceptions.PhoneValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class ValidationServiceTest {

  private ValidationService subject;

  @BeforeEach
  void setUp() {
    subject = new ValidationService();
  }

  @ParameterizedTest(name = "should validate phone number when it not starts with double zero or plus sign - {0}")
  @ValueSource(strings = {"1234567890", "0123456789"})
  void validatePhoneNumber_notStartsWithZerosOrPlusSign_shouldThrowException(String phoneNumer) {
    final PhoneValidationException phoneValidationException = assertThrows(PhoneValidationException.class, () -> {
      subject.validatePhoneNumber(phoneNumer);
    });
    assertThat(phoneValidationException.getMessage())
        .isEqualTo("Invalid phone number format. Phone number should contain only numbers (except for leading '+' sign)");
  }

  @ParameterizedTest
  @ValueSource(strings = {"00abcd@$&", "+12345d789"})
  void validatePhoneNumber_containsNonnumeric_shouldThrowException(String phoneNumer) {
    final PhoneValidationException phoneValidationException = assertThrows(PhoneValidationException.class, () -> {
      subject.validatePhoneNumber(phoneNumer);
    });
    assertThat(phoneValidationException.getMessage()).isEqualTo(
        "Invalid phone number format. Phone number should contain only numbers (except for leading '+' sign)");
  }

  @Test
  void validatePhoneNumber_tooShort_shouldThrowException() {
    // Arrange
    String phoneNumer = "+123456";

    // Act
    final PhoneValidationException phoneValidationException = assertThrows(PhoneValidationException.class, () -> {
      subject.validatePhoneNumber(phoneNumer);
    });

    // Assert
    assertThat(phoneValidationException.getMessage()).isEqualTo("Phone number is too short");
  }

  @Test
  void validatePhoneNumber_tooLong_shouldThrowException() {
    // Arrange
    String phoneNumer = "+123456789012345";

    // Act
    final PhoneValidationException phoneValidationException = assertThrows(PhoneValidationException.class, () -> {
      subject.validatePhoneNumber(phoneNumer);
    });

    // Assert
    assertThat(phoneValidationException.getMessage()).isEqualTo("Phone number is too long");
  }

  @ParameterizedTest(name = "should validate phone number when phone number is valid - {1} - {0}")
  @CsvSource({
      "0012345678,can start with double zeros",
      "+123456789,can start with plus sign",
      "+1234567,can be minimal length",
      "+12345678901234,can be maximum length"
  })
  void validatePhoneNumber_validPhoneNumber_shouldValidate(String phoneNumer, String useCase) {
    subject.validatePhoneNumber(phoneNumer);
  }

}
