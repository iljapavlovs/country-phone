package io.iljapavlovs.homework.services;

public class PhoneValidationConstants {

  private PhoneValidationConstants(){
  }
  // https://stackoverflow.com/questions/14894899/what-is-the-minimum-length-of-a-valid-international-phone-number
  public static final int MINIMUM_PHONE_LENGTH_WITHOUT_COUNTRY_CODE = 4;
//  https://en.wikipedia.org/wiki/E.164
  public static final int MAXIMUM_COUNTRY_CODE_LENGTH = 8;

//  https://en.wikipedia.org/wiki/Telephone_numbering_plan
  public static final int MINIMUM_PHONE_NUMBER_LENGTH = 8;

  //  https://en.wikipedia.org/wiki/Telephone_numbering_plan
  public static final int MAXIMUM_PHONE_NUMBER_LENGTH = 15;
}
