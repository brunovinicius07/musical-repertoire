package com.music.model.exceptions.Repertoire;

import com.music.infra.config.exceptionHandler.AlertException;
import org.springframework.http.HttpStatus;

public class RepertoireIsPresentException extends AlertException {

    private static final String DEFAULT_MESSAGE = "Repertorio já Existe";

    public RepertoireIsPresentException() {
        super("409", DEFAULT_MESSAGE, HttpStatus.CONFLICT);
    }
}