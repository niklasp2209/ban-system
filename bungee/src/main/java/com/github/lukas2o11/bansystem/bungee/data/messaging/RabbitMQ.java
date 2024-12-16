package com.github.lukas2o11.bansystem.bungee.data.messaging;

import com.github.lukas2o11.bansystem.bungee.BanSystemPlugin;
import com.rabbitmq.client.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RabbitMQ {

    private static final String EXCHANGE_NAME = "bansystem-exchange";
    private static final String QUEUE_NAME = "unbans";
    private static final String ROUTING_KEY = "unban.player";

    private @NotNull final BanSystemPlugin plugin;
    private @NotNull final ConnectionFactory connectionFactory;

    private @Nullable Connection connection;
    private @Nullable Channel channel;

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
            channel.basicConsume(QUEUE_NAME, true, new UnbanMessageConsumer(plugin.getBanManager()), cancelCallback());

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

    private CancelCallback cancelCallback() {
        return consumerTag -> {
            System.out.println("Cancelled consumer: " + consumerTag);
        };
    }
}
