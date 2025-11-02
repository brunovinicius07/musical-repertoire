package com.music.model.exceptions.exceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class AlertException extends RuntimeException{
    private final String errorCode;
    private final String message;
    private final HttpStatus httpStatus;
}
