package com.lukas2o11.bansystem.web.data.messaging.publisher;

import org.jetbrains.annotations.NotNull;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Service
public class UnbanMessagePublisher implements RabbitMQMessagePublisher {

    private final RabbitTemplate template;

    @Value("${rabbitmq.exchange_name}")
    private String exchangeName;

    @Value("${rabbitmq.unban.player.routing_key}")
    private String unbanPlayerRoutingKey;

    @Autowired
    public UnbanMessagePublisher(RabbitTemplate template) {
        this.template = template;
    }

    @Override
    public void sendMessage(@NotNull String message) {
        MessageProperties properties = new MessageProperties();
        properties.setReplyTo("");  // Keine Antwort erwartet
        Message rabbitMessage = new Message(message.getBytes(StandardCharsets.UTF_8), properties);

        try {
            // Nachricht senden an den richtigen Routing Key
            template.convertAndSend(exchangeName, unbanPlayerRoutingKey, rabbitMessage);
            System.out.println("Nachricht gesendet: " + message);
        } catch (Exception e) {
            System.err.println("Fehler beim Senden der Nachricht: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
