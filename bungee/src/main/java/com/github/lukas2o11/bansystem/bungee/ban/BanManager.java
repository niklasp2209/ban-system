package com.github.lukas2o11.bansystem.bungee.ban;

import com.github.lukas2o11.bansystem.api.BanType;
import com.github.lukas2o11.bansystem.bungee.ban.models.BanList;
import com.github.lukas2o11.bansystem.bungee.ban.models.BanListEntry;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface BanManager {

    CompletableFuture<Void> banUser(UUID player, BanType type);

    CompletableFuture<Void> unbanUser(UUID player, BanType type);

    CompletableFuture<Boolean> isUserBanned(UUID player, BanType type);

    CompletableFuture<Optional<BanListEntry>> getBan(UUID player, BanType type);

    CompletableFuture<BanList> getBans(
            UUID player, BanType type,
            Optional<Integer> page, Optional<Integer> pageSize
    );
}
