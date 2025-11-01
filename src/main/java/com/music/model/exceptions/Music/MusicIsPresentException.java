package com.music.model.exceptions.Music;

import com.music.infra.config.exceptionHandler.AlertException;
import org.springframework.http.HttpStatus;

public class MusicIsPresentException extends AlertException {

    private static final String DEFAULT_MESSAGE = "Música já existe";

    public MusicIsPresentException() {
        super("409", DEFAULT_MESSAGE, HttpStatus.CONFLICT);
    }
}