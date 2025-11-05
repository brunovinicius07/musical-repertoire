package com.music.model.exceptions.music;

import com.music.model.exceptions.exceptionHandler.AlertException;
import org.springframework.http.HttpStatus;

public class MusicNotFoundException extends AlertException {
    private static final String DEFAULT_MESSAGE = "Música não localizada";
    public MusicNotFoundException() {
        super("404", DEFAULT_MESSAGE, HttpStatus.NOT_FOUND);

    }
}
