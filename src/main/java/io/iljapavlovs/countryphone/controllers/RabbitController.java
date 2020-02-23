package io.iljapavlovs.countryphone.controllers;

import static io.iljapavlovs.countryphone.rabbitmq.RabbitMqConfig.EXCHNAGE_NAME_FANOUT;
import static io.iljapavlovs.countryphone.rabbitmq.RabbitMqConfig.QUEUE_NAME_HELLO_WORLD;
import static io.iljapavlovs.countryphone.rabbitmq.RabbitMqConfig.QUEUE_NAME_WORKER_QUEUE;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RabbitController {
  private static final Logger LOGGER = LoggerFactory.getLogger(RabbitController.class);

  @Autowired
  RabbitTemplate template;

  @RequestMapping("/helloworld")
  @ResponseBody
  String queue1() {
    final String msg = "Emit to Queue: " + QUEUE_NAME_HELLO_WORLD;
    LOGGER.info(msg);
    template.convertAndSend(QUEUE_NAME_HELLO_WORLD,"Message to queue");
    return msg;
  }

  @RequestMapping("/workerqueue")
  @ResponseBody
  String workerQueues() {
    final String msg = "Emit to Queue: " + QUEUE_NAME_WORKER_QUEUE;
    LOGGER.info(msg);
    for(int i = 0;i<10;i++)
      template.convertAndSend(QUEUE_NAME_WORKER_QUEUE,"Message " + i);
    return msg;
  }

  @RequestMapping("/fanout")
  @ResponseBody
  String fanout() {
    final String msg = "Emit to EXCHANGE: " + EXCHNAGE_NAME_FANOUT;
    LOGGER.info(msg);
    //SET EXCHNAGE NAME, NOT QUEUEU
    template.setExchange(EXCHNAGE_NAME_FANOUT);
    template.convertAndSend("Message to queue");
    return msg;
  }


//  4. ROUTING
  @RequestMapping("/emit/error")
  @ResponseBody
  String error() {
    LOGGER.info("Emit as error");
    template.convertAndSend("error", "Error");
    return "Emit as error";
  }

  @RequestMapping("/emit/info")
  @ResponseBody
  String info() {
    LOGGER.info("Emit as info");
    template.convertAndSend("info", "Info");
    return "Emit as info";
  }

  @RequestMapping("/emit/warning")
  @ResponseBody
  String warning() {
    LOGGER.info("Emit as warning");
    template.convertAndSend("warning", "Warning");
    return "Emit as warning";
  }
}
