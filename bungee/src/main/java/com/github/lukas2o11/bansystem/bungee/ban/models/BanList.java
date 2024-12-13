package com.github.lukas2o11.bansystem.bungee.ban.models;

import com.github.lukas2o11.bansystem.api.BanType;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

public record BanList(@NotNull UUID player, @NotNull List<BanListEntry> entries) {

    public List<BanListEntry> getActiveBans(){
        return entries.stream()
                .filter(entry -> !entry.isExpired())
                .toList();
    }
}
