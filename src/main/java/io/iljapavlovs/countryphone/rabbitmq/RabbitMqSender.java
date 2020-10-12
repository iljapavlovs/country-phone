package io.iljapavlovs.countryphone.rabbitmq;

import static io.iljapavlovs.countryphone.rabbitmq.RabbitMqConfig.QUEUE_NAME_HELLO_WORLD;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


// IMPORTANT -
//* Messages are not published directly to a queue, instead, the producer sends messages to an exchange.
//* An exchange is responsible for the routing of the messages to the different queues.
//* An exchange accepts messages from the producer application and routes them to message queues with the help of bindings and routing keys.
//* A binding is a link between a queue and an exchange.


@Component
public class RabbitMqSender {


  @Autowired
  private RabbitTemplate template;


  public void sendRabbitSimpleMessage(String message){
    template.convertAndSend(QUEUE_NAME_HELLO_WORLD, message);

  }

}
