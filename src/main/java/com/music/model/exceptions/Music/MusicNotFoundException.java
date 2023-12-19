package com.music.model.exceptions.Music;

import com.music.authentication.config.exceptionHandler.AlertException;
import org.springframework.http.HttpStatus;

public class MusicNotFoundException extends AlertException {
    private static final String DEFAULT_MESSAGE = "Música não localizado";
    public MusicNotFoundException() {
        super("404", DEFAULT_MESSAGE, HttpStatus.NOT_FOUND);

    }
}
