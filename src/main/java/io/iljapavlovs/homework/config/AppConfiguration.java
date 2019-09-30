package io.iljapavlovs.homework.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfiguration {

  @Bean
  RestTemplate restTemplate() {
    return new RestTemplate();
  }

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }

}
