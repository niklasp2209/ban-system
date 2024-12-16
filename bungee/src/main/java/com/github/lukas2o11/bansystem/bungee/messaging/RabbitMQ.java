package com.github.lukas2o11.bansystem.bungee.messaging;

import com.github.lukas2o11.bansystem.bungee.BanSystemPlugin;
import com.rabbitmq.client.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.charset.StandardCharsets;

public class RabbitMQ {

    private static final String EXCHANGE_NAME = "bansystem-exchange";
    private static final String QUEUE_NAME = "unbans";
    private static final String ROUTING_KEY = "unban.player";

    @NotNull
    private final BanSystemPlugin plugin;

    @NotNull
    private final ConnectionFactory connectionFactory;

    private Connection connection;
    private Channel channel;

    public RabbitMQ(@NotNull BanSystemPlugin plugin) {
        this.plugin = plugin;
        this.connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
    }

    public void connect() {
        try {
            this.connection = connectionFactory.newConnection();
            this.channel = connection.createChannel();

            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT, false, true, null);
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);

            channel.basicConsume(QUEUE_NAME, true, new UnbanMessageConsumer(this, plugin.getBanManager()), consumerTag -> {
                System.out.println("Cancelled consumer: " + consumerTag);
            });

            System.out.println("RabbitMQ connected");
        } catch (Exception e) {
            System.err.println("Error opening RabbitMQ connection: " + e.getMessage());
        }
    }

    public void disconnect() {
        try {
            if (channel != null) {
                channel.close();
            }

            if (connection != null) {
                connection.close();
            }

            System.out.println("RabbitMQ connection closed");
        } catch (Exception e) {
            System.err.println("Error closing RabbitMQ connection: " + e.getMessage());
        }
    }

    public void publishMessage(@NotNull String routingKey, @Nullable AMQP.BasicProperties basicProperties, @NotNull String message) {
        if (channel != null) {
            try {
                channel.basicPublish(EXCHANGE_NAME, routingKey, basicProperties, message.getBytes(StandardCharsets.UTF_8));
                System.out.println("publish message: " + routingKey + " - " + message);
            } catch (Exception e) {
                System.err.println("Error publishing message: " + e.getMessage());
            }
        }
    }
}
