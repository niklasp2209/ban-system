package com.github.lukas2o11.bansystem.bungee.database.ban.models;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

public record BanList(@NotNull UUID player, @NotNull List<BanListEntry> entries) {
}
