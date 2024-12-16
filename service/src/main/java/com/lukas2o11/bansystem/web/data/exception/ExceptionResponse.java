package com.lukas2o11.bansystem.web.data.exception;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ExceptionResponse {

    private final int statusCode;
    private final String message;
    private final long timestamp = System.currentTimeMillis();
}
