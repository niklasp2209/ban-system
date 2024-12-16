package com.github.lukas2o11.bansystem.bungee;

import com.github.lukas2o11.bansystem.bungee.database.MySQL;
import com.github.lukas2o11.bansystem.bungee.ban.BanManager;
import com.github.lukas2o11.bansystem.bungee.ban.DefaultBanManager;
import com.github.lukas2o11.bansystem.bungee.messaging.RabbitMQ;
import com.github.lukas2o11.bansystem.bungee.template.BanTemplateManager;
import com.github.lukas2o11.bansystem.bungee.template.DefaultBanTemplateManager;
import lombok.Getter;
import net.md_5.bungee.api.plugin.Plugin;

@Getter
public class BanSystemPlugin extends Plugin {

    private MySQL mySQL;
    private BanManager banManager;
    private BanTemplateManager templateManager;
    private RabbitMQ rabbitMQ;

    @Override
    public void onEnable() {
        this.mySQL = new MySQL();
        mySQL.connect();

        this.templateManager = new DefaultBanTemplateManager(this);
        this.banManager = new DefaultBanManager(this);

        this.rabbitMQ = new RabbitMQ(this);
        rabbitMQ.connect();
    }

    @Override
    public void onDisable() {
        rabbitMQ.disconnect();
        mySQL.disconnect();
    }
}
