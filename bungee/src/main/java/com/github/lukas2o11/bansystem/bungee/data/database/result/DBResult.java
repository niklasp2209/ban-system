package com.github.lukas2o11.bansystem.bungee.data.database.result;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public record DBResult(@NotNull List<DBRow> rows) {
}