package io.iljapavlovs.countryphone.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
  private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMqConfig.class);
  public static final String QUEUE_NAME_HELLO_WORLD = "queue1";
  public static final String QUEUE_NAME_WORKER_QUEUE = "queue2";
  public static final String QUEUE_NAME_FANOUT1 = "queue3";
  public static final String QUEUE_NAME_FANOUT2 = "queue4";

  public static final String QUEUE_NAME_ROUTING1 = "queue5";
  public static final String QUEUE_NAME_ROUTING2 = "queue6";
  public static final String EXCHNAGE_NAME_FANOUT = "echange";
  public static final String EXCHNAGE_NAME_DIRECT = "echange_direct";

  //NOT NEEDED - SPRING AUTO CONFIGURES настраиваем соединение с RabbitMQ - сonnectionFactory — для соединения с RabbitMQ
//    @Bean
//    public ConnectionFactory connectionFactory() {
//      return new CachingConnectionFactory("localhost");
//    }

//     rabbitAdmin — для регистрации/отмены регистрации очередей и т.п.;
    @Bean
    public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory) {
      return new RabbitAdmin(connectionFactory);
    }

//    PRODUCER - CLIENT TO SEND MESSAGE
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
      final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
       rabbitTemplate
          .setMessageConverter(messageConverter);
      return rabbitTemplate;
    }

    //объявляем очередь с именем queue1
    @Bean
    public Queue myQueue1() {
      return new Queue(QUEUE_NAME_HELLO_WORLD, false);
    }

  @Bean
  public Queue myQueue2() {
    return new Queue(QUEUE_NAME_WORKER_QUEUE, false);
  }

  @Bean
  public Queue myQueue3() {
    return new Queue(QUEUE_NAME_FANOUT1, false);
  }

  @Bean
  public Queue myQueue4() {
    return new Queue(QUEUE_NAME_FANOUT2, false);
  }


  //CONSUMER - объявляем контейнер, который будет содержать листенер для сообщений - WILL EXCEUTE WHEN BEANS LOADS
    //-> SHOULD BE DONE VIA @RABBITLISTENER
//    @Bean
//    public SimpleMessageListenerContainer messageListenerContainer1(ConnectionFactory connectionFactory) {
//      SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
//      container.setConnectionFactory(connectionFactory);
//      container.setQueueNames(QUEUE_NAME);
//      //тут ловим сообщения из queue1
//      container.setMessageListener(message -> LOGGER.info("RabbitMqConfig: received from queue1 : " + new String(message.getBody())));
//      return container;
//    }

  @Bean
  public MessageConverter messageConverter() {
    return new Jackson2JsonMessageConverter();
  }


  //3. Publish/Subscribe - FANOUT - BROADCAST TO ALL

  // CREATE EXCHNAGE WITH TYPE FANOUT (BROADCAST)
  @Bean
  public FanoutExchange fanoutExchangeA(){
    return new FanoutExchange(EXCHNAGE_NAME_FANOUT);
  }

  // NEED TO BIND QUEUE TO EXCHANGE
  @Bean
  public Binding binding1(){
    return BindingBuilder.bind(myQueue3()).to(fanoutExchangeA());
  }

  @Bean
  public Binding binding2(){
    return BindingBuilder.bind(myQueue4()).to(fanoutExchangeA());
  }


  // 4. Routing -

  @Bean
  public Queue myQueue5() {
    return new Queue(QUEUE_NAME_ROUTING1, false);
  }

  @Bean
  public Queue myQueue6() {
    return new Queue(QUEUE_NAME_ROUTING2, false);
  }

  // Exchange TYPE - DIRECT
  @Bean
  public DirectExchange directExchange(){
    return new DirectExchange(EXCHNAGE_NAME_DIRECT);
  }

  @Bean
  public Binding errorBinding1(){
      //SET ROUTING KEY TO THE BINDING
    return BindingBuilder.bind(myQueue5()).to(directExchange()).with("error");
  }

  @Bean
  public Binding errorBinding2(){
    return BindingBuilder.bind(myQueue6()).to(directExchange()).with("error");
  }

  @Bean
  public Binding infoBinding(){
    return BindingBuilder.bind(myQueue6()).to(directExchange()).with("info");
  }

  @Bean
  public Binding warningBinding(){
    return BindingBuilder.bind(myQueue6()).to(directExchange()).with("warning");
  }


}
