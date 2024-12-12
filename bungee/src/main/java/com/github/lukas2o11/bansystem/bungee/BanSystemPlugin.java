package com.github.lukas2o11.bansystem.bungee;

import com.github.lukas2o11.bansystem.api.BanType;
import com.github.lukas2o11.bansystem.bungee.database.MySQL;
import com.github.lukas2o11.bansystem.bungee.ban.BanManager;
import com.github.lukas2o11.bansystem.bungee.ban.DefaultBanManager;
import com.github.lukas2o11.bansystem.bungee.template.BanTemplateManager;
import com.github.lukas2o11.bansystem.bungee.template.DefaultBanTemplateManager;
import lombok.Getter;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.UUID;

@Getter
public class BanSystemPlugin extends Plugin {

    private MySQL mySQL;
    private BanManager banManager;
    private BanTemplateManager templateManager;

    @Override
    public void onEnable() {
        this.mySQL = new MySQL();
        mySQL.connect();

        this.templateManager = new DefaultBanTemplateManager(this);
        this.banManager = new DefaultBanManager(this);

        banManager.getBan(UUID.fromString("3486676c-1055-4953-aa83-d274ff95c5cc"), BanType.BAN).thenAccept(o -> {
            if (o.isPresent()) {
                System.out.println(o.get().toString());
            }
        });
    }

    @Override
    public void onDisable() {
        mySQL.disconnect();
    }
}
