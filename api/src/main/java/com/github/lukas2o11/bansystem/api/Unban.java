package com.github.lukas2o11.bansystem.api;

import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Unban {

    @NotNull
    private final UUID player;

    @NotNull
    private final Integer banId;

    @NotNull
    private final String unbannedBy; // UUID or CONSOLE

    private final long unbannedAt;
}
