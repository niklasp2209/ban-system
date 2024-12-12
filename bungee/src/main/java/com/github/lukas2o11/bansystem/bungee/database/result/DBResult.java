package com.github.lukas2o11.bansystem.bungee.database.result;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public record DBResult(@NotNull List<DBRow> rows) {
}