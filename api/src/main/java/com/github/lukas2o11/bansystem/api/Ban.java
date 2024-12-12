package com.github.lukas2o11.bansystem.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class Ban {

    @NotNull
    private final UUID player;

    @NotNull
    private final String templateId;

    @NotNull
    private final String bannedBy; // UUID or CONSOLE

    private final long bannedAt;
    private final long expiresAt;
    private boolean active;
}