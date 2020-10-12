package io.iljapavlovs.countryphone.rabbitmq;

import static io.iljapavlovs.countryphone.rabbitmq.RabbitMqConfig.QUEUE_NAME_FANOUT1;
import static io.iljapavlovs.countryphone.rabbitmq.RabbitMqConfig.QUEUE_NAME_FANOUT2;
import static io.iljapavlovs.countryphone.rabbitmq.RabbitMqConfig.QUEUE_NAME_ROUTING1;
import static io.iljapavlovs.countryphone.rabbitmq.RabbitMqConfig.QUEUE_NAME_ROUTING2;
import static io.iljapavlovs.countryphone.rabbitmq.RabbitMqConfig.QUEUE_NAME_WORKER_QUEUE;

import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

// IMPORTANT -
//* Messages are not published directly to a queue, instead, the producer sends messages to an exchange.
//* An exchange is responsible for the routing of the messages to the different queues.
//* An exchange accepts messages from the producer application and routes them to message queues with the help of bindings and routing keys.
//* A binding is a link between a queue and an exchange.

//NOT NEEDED - @EnableRabbit //нужно для активации обработки аннотаций @RabbitListener
@Component
public class RabbitMqListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMqListener.class);
  Random random = new Random();

  //  1. HEllo Wolrd
  @RabbitListener(queues = RabbitMqConfig.QUEUE_NAME_HELLO_WORLD)
  public void processQueue1(String message) {
    LOGGER.info("Received from queue 1: " + message);
  }


  // 2.  competing consumers pattern - WORK QUEUES - 2 consumers listen to one Queue
  @RabbitListener(queues = QUEUE_NAME_WORKER_QUEUE)
  public void worker1(String message) throws InterruptedException {
    LOGGER.info("worker 1 : " + message);
    Thread.sleep(100 * random.nextInt(20));
  }

  @RabbitListener(queues = QUEUE_NAME_WORKER_QUEUE)
  public void worker2(String message) throws InterruptedException {
    LOGGER.info("worker 2 : " + message);
    Thread.sleep(100 * random.nextInt(20));
  }

  // 3. PUBLISH/SUBSCRIBE - 2 consumers listen to their own QUEUES and receive ALL messages (FANOUT - Broadcast). Sending messages to many consumers at once
  @RabbitListener(queues = QUEUE_NAME_FANOUT1)
  public void worker3(String message) throws InterruptedException {
    LOGGER.info("worker 1 : " + message);
  }

  @RabbitListener(queues = QUEUE_NAME_FANOUT2)
  public void worker4(String message) throws InterruptedException {
    LOGGER.info("worker 2 : " + message);
  }


  //  4. ROUTING
  @RabbitListener(queues = QUEUE_NAME_ROUTING1)
  public void worker5(String message) throws InterruptedException {
    LOGGER.info("worker 1 : " + message);
  }

  @RabbitListener(queues = QUEUE_NAME_ROUTING2)
  public void worker6(String message) throws InterruptedException {
    LOGGER.info("worker 2 : " + message);
  }
}