package io.iljapavlovs.homework.services.phonecodecountryprovider;

import java.util.List;
import java.util.Map;

public interface PhoneCodeCountryProvider {

  Map<String, List<String>> getPhoneCodeCountries();

}
