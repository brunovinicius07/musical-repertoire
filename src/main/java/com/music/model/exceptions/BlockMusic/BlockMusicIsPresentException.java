package com.music.model.exceptions.BlockMusic;

import com.music.model.exceptions.exceptionHandler.AlertException;
import org.springframework.http.HttpStatus;

public class BlockMusicIsPresentException extends AlertException {

    private static final String DEFAULT_MESSAGE = "Bloco Músical já Existe";

    public BlockMusicIsPresentException() {
        super("409", DEFAULT_MESSAGE, HttpStatus.CONFLICT);
    }
}