package com.github.lukas2o11.bansystem.api;

import lombok.*;
import org.jetbrains.annotations.NotNull;

@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class BanTemplate {

    @NotNull
    private final String id;

    @NotNull
    private final BanType type;

    @NotNull
    private String reason;

    private long duration;
}
