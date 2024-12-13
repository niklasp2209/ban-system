package com.github.lukas2o11.bansystem.bungee.ban.models;

import com.github.lukas2o11.bansystem.api.BanType;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public record BanListEntry(@NotNull UUID player, @NotNull String bannedBy, @NotNull String reason, long duration, long expiresAt) {

    public boolean isExpired(){
        return System.currentTimeMillis() > expiresAt;
    }
}
