package io.iljapavlovs.countryphone.rabbitmq;

import static io.iljapavlovs.countryphone.rabbitmq.RabbitMqConfig.QUEUE_NAME_HELLO_WORLD;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMqSender {


  @Autowired
  private RabbitTemplate template;


  public void sendRabbitSimpleMessage(String message){
    template.convertAndSend(QUEUE_NAME_HELLO_WORLD, message);

  }

}
