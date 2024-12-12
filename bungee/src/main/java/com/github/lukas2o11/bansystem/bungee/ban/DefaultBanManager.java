package com.github.lukas2o11.bansystem.bungee.ban;

import com.github.lukas2o11.bansystem.api.BanType;
import com.github.lukas2o11.bansystem.bungee.BanSystemPlugin;
import com.github.lukas2o11.bansystem.bungee.database.MySQL;
import com.github.lukas2o11.bansystem.bungee.ban.models.BanList;
import com.github.lukas2o11.bansystem.bungee.ban.models.BanListEntry;
import com.github.lukas2o11.bansystem.bungee.database.result.DBRow;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class DefaultBanManager implements BanManager {

    private static final String GET_BAN_QUERY = "SELECT bb.player, bb.bannedBy, bb.bannedAt, bb.expiresAt, " +
            "bt.id, bt.reason, bt.duration, bt.type " +
            "FROM bansystem_bans bb " +
            "INNER JOIN bansystem_templates bt ON bt.id = bb.templateId " +
            "WHERE bb.player = ? " +
            "AND bb.active = true " +
            "AND bt.type = ?;";

    private static final String GET_BANS_QUERY = "SELECT bb.player, bb.bannedBy, bb.bannedAt, bb.expiresAt, " +
            "bt.id, bt.reason, bt.duration, bt.type " +
            "FROM bansystem_bans bb " +
            "INNER JOIN bansystem_templates bt ON bt.id = bb.templateId" +
            "WHERE bb.player = ? " +
            "AND bt.type = ?";

    private final MySQL mySQL;

    public DefaultBanManager(@NotNull BanSystemPlugin plugin) {
        this.mySQL = plugin.getMySQL();
        createTables();
    }

    private void createTables() {
        mySQL.update("CREATE TABLE IF NOT EXISTS bansystem_bans(" +
                "id INT AUTO_INCREMENT NOT NULL, " +
                "player VARCHAR(36) NOT NULL, " +
                "templateId VARCHAR(8) NOT NULL, " +
                "bannedBy VARCHAR(36) NOT NULL, " +
                "bannedAt BIGINT NOT NULL, " +
                "expiresAt BIGINT NOT NULL, " +
                "active BOOLEAN NOT NULL, " + // active flag for history, so we do not need an extra table for this
                "PRIMARY KEY(id), " +
                "FOREIGN KEY(templateId) REFERENCES bansystem_templates(id)" +
                ");").join();

        // TODO: bansystem_unbans table
    }

    @Override
    public @NotNull CompletableFuture<Void> banUser(@NotNull UUID player, @NotNull BanType type) {
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public @NotNull CompletableFuture<Void> unbanUser(@NotNull UUID player, @NotNull BanType type) {
        // TODO: ban.active = false on unban
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public @NotNull CompletableFuture<Boolean> isUserBanned(@NotNull UUID player, @NotNull BanType type) {
        return CompletableFuture.completedFuture(true);
    }

    @Override
    public @NotNull CompletableFuture<Optional<BanListEntry>> getBan(@NotNull UUID player, @NotNull BanType type) {
        return mySQL.query(GET_BAN_QUERY, player.toString(), type.toString()).thenApply(result -> {
            List<DBRow> rows = result.getRows();
            if (rows.isEmpty()) {
                System.out.println("No active ban found for player '" + player + "' with type '" + type + "'");
                return Optional.empty();
            }

            if (rows.size() > 1) {
                System.out.println("More than 1 active ban found for player '" + player + "' with type '" + type + "'");
                return Optional.empty();
            }

            DBRow row = rows.getFirst();
            return Optional.of(listEntryFromRow(row));
        });
    }

    @Override
    public @NotNull CompletableFuture<BanList> getBans(
            @NotNull UUID player, @NotNull BanType type,
            Optional<Integer> page, Optional<Integer> pageSize
    ) {
        return mySQL.query(GET_BANS_QUERY, player.toString(), type.toString()).thenApply(result -> {
            List<DBRow> rows = result.getRows();
            if (rows.isEmpty()) {
                System.out.println("No active ban found for player '" + player + "' with type '" + type + "'");
                return new BanList(player, new ArrayList<>());
            }

            return new BanList(player, rows.stream()
                    .map(this::listEntryFromRow)
                    .toList());
        });
    }

    private @NotNull BanListEntry listEntryFromRow(@NotNull DBRow row) {
        return new BanListEntry(
                UUID.fromString(row.getValue("player", String.class)),
                row.getValue("bannedBy", String.class),
                row.getValue("reason", String.class),
                row.getValue("duration", Long.class),
                row.getValue("expiresAt", Long.class)
        );
    }
}
