package com.music.exception;

import java.time.LocalDateTime;

public record ApiErrorResponse(String guid, String errorCode, String message, Integer statusCode, String statusName,
                               String path, String method, LocalDateTime timesTamp) {
}

