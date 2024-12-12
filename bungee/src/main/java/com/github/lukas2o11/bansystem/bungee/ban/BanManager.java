package com.github.lukas2o11.bansystem.bungee.ban;

import com.github.lukas2o11.bansystem.api.BanType;
import com.github.lukas2o11.bansystem.bungee.ban.models.BanList;
import com.github.lukas2o11.bansystem.bungee.ban.models.BanListEntry;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface BanManager {

    @NotNull CompletableFuture<Void> banUser(@NotNull UUID player, @NotNull BanType type);

    @NotNull CompletableFuture<Void> unbanUser(@NotNull UUID player, @NotNull BanType type);

    @NotNull CompletableFuture<Boolean> isUserBanned(@NotNull UUID player, @NotNull BanType type);

    @NotNull CompletableFuture<Optional<BanListEntry>> getBan(@NotNull UUID player, @NotNull BanType type);

    @NotNull CompletableFuture<BanList> getBans(
            @NotNull UUID player, @NotNull BanType type,
            Optional<Integer> page, Optional<Integer> pageSize
    );
}
