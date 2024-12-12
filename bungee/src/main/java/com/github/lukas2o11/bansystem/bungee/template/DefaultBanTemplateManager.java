package com.github.lukas2o11.bansystem.bungee.template;

import com.github.lukas2o11.bansystem.bungee.BanSystemPlugin;
import com.github.lukas2o11.bansystem.bungee.database.MySQL;

public class DefaultBanTemplateManager implements BanTemplateManager {

    private final MySQL mySQL;

    public DefaultBanTemplateManager(BanSystemPlugin plugin) {
        this.mySQL = plugin.getMySQL();
        createTables();
    }

    private void createTables() {
        mySQL.update("CREATE TABLE IF NOT EXISTS bansystem_templates(" +
                "id VARCHAR(8) NOT NULL, " +
                "type VARCHAR(32) NOT NULL, " +
                "reason VARCHAR(64) NOT NULL, " +
                "duration BIGINT NOT NULL, " +
                "PRIMARY KEY(id)" +
                ");").join();
    }
}
