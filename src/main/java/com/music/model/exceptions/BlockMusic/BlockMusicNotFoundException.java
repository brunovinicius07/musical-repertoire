package com.music.model.exceptions.BlockMusic;

import com.music.authentication.config.exceptionHandler.AlertException;
import org.springframework.http.HttpStatus;

public class BlockMusicNotFoundException extends AlertException {
    private static final String DEFAULT_MESSAGE = "Bloco músical não localizado";
    public BlockMusicNotFoundException() {
        super("404", DEFAULT_MESSAGE, HttpStatus.NOT_FOUND);

    }
}
