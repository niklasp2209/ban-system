package com.lukas2o11.bansystem.web.data.models;

import lombok.*;
import org.jetbrains.annotations.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class UnbanRequest {

    private @NotNull Integer id;
}
