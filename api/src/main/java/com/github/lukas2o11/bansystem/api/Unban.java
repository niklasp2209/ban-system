package com.github.lukas2o11.bansystem.api;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public record Unban(@NotNull UUID player, @NotNull Integer banId, @NotNull String unbannedBy, long unbannedAt) {
}
