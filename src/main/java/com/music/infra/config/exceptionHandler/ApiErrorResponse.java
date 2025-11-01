package com.music.infra.config.exceptionHandler;

import java.time.LocalDateTime;

public record ApiErrorResponse(String guid, String errorCode, String message, Integer statusCode, String statusName,
                               String path, String method, LocalDateTime timesTamp) {
}

