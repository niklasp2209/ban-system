package com.github.lukas2o11.bansystem.bungee.data.ban;

import com.github.lukas2o11.bansystem.api.Ban;
import com.github.lukas2o11.bansystem.api.BanType;
import com.github.lukas2o11.bansystem.bungee.data.ban.models.BanList;
import com.github.lukas2o11.bansystem.bungee.data.ban.models.BanListEntry;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface BanManager {

    @NotNull CompletableFuture<Void> banUser(@NotNull UUID player, @NotNull BanType type);

    @NotNull CompletableFuture<Void> unbanUser(@NotNull UUID player, int banId, @NotNull String unbannedBy);

    @NotNull CompletableFuture<Void> unbanUser(int banId, @NotNull String unbannedBy);

    @NotNull CompletableFuture<Boolean> isUserBanned(@NotNull UUID player, @NotNull BanType type);

    @NotNull CompletableFuture<Optional<Ban>> getBanById(int banId);

    @NotNull CompletableFuture<Optional<Ban>> getBan(@NotNull UUID player, @NotNull BanType type);

    @NotNull CompletableFuture<List<Ban>> getBans(@NotNull UUID player, @NotNull BanType type);

    @NotNull CompletableFuture<Optional<BanListEntry>> getBanAsListEntry(@NotNull UUID player, @NotNull BanType type);

    @NotNull CompletableFuture<BanList> getBanList(@NotNull UUID player, @NotNull BanType type);
}
