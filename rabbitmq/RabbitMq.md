* https://habr.com/ru/post/262069/
* http://localhost:15672/
* https://www.rabbitmq.com/getstarted.html


1. Start Rabbit
```
docker-compose -f docker-compose-infra.yml up 
```

##  IMPORTANT 
* Messages are not published directly to a queue, instead, the producer sends messages to an exchange. 
* An exchange is responsible for the routing of the messages to the different queues. 
* An exchange accepts messages from the producer application and routes them to message queues with the help of bindings and routing keys. 
* A binding is a link between a queue and an exchange


![alt](img/queue-rabbit.png)
Message flow in RabbitMQ

1. The producer publishes a message to an exchange. When you create the exchange, you have to specify the type of it. The different types of exchanges are explained in detail later on.
2. The exchange receives the message and is now responsible for the routing of the message. The exchange takes different message attributes into account, such as routing key, depending on the exchange type.
3. Bindings have to be created from the exchange to queues. In this case, we see two bindings to two different queues from the exchange. The Exchange routes the message into the queues depending on message attributes.
4. The messages stay in the queue until they are handled by a consumer
5. The consumer handles the message.



![alt](img/typesOfExchange.png)

* **Direct:** A direct exchange delivers messages to queues based on a message routing key. In a direct exchange, the message is routed to the queues whose binding key exactly matches the routing key of the message. If the queue is bound to the exchange with the binding key pdfprocess, a message published to the exchange with a routing key pdfprocess will be routed to that queue.
* **Fanout:** A fanout exchange routes messages to all of the queues that are bound to it.
* **Topic:** The topic exchange does a wildcard match between the routing key and the routing pattern specified in the binding.
* **Headers**: Headers exchanges use the message header attributes for routing.