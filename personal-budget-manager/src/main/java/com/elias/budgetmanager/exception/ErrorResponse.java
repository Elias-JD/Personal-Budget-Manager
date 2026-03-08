package com.elias.budgetmanager.exception;

import java.time.Instant;

public record ErrorResponse(
        int status,
        String message,
        Instant timestamp,
        String path) {

}
