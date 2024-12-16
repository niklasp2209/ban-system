package com.github.lukas2o11.bansystem.bungee.messaging;

import com.github.lukas2o11.bansystem.bungee.ban.BanManager;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class UnbanMessageConsumer implements DeliverCallback {

    @NotNull
    private final RabbitMQ mq;

    @NotNull
    private final BanManager banManager;

    public UnbanMessageConsumer(@NotNull RabbitMQ mq, @NotNull BanManager banManager) {
        this.mq = mq;
        this.banManager = banManager;
    }

    @Override
    public void handle(String s, Delivery delivery) {
        System.out.println("test");
        String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
        if (message.isEmpty()) {
            return;
        }

        /*String correlationId = Optional.ofNullable(delivery.getProperties().getCorrelationId())
                .orElseThrow(() -> new IllegalStateException("correlationId is missing"));*/
        int banId;

        try {
            banId = Integer.parseInt(message);
        } catch (NumberFormatException e) {
            System.err.println("invalid unban message: " + message);
            return;
        }

        banManager.unbanUser(banId, "WEB_INTERFACE").thenAccept(o -> {
            /*AMQP.BasicProperties basicProperties = new AMQP.BasicProperties().builder()
                    .correlationId(correlationId)
                    .build();
            mq.publishMessage("unban.confirmation", basicProperties, message);*/
            System.out.println("ggwp");
        });
    }
}
