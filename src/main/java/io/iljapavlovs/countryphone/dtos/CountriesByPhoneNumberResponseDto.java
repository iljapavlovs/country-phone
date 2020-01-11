package io.iljapavlovs.countryphone.dtos;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CountriesByPhoneNumberResponseDto {
  private List<String> countries;
  private String phoneNumber;
}
