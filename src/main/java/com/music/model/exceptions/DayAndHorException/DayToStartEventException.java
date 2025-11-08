package com.music.model.exceptions.DayAndHorException;

import com.music.model.exceptions.exceptionHandler.AlertException;
import org.springframework.http.HttpStatus;

public class DayToStartEventException extends AlertException {

    private static final String DEFAULT_MESSAGE = "O dia do horário de início deve pertencer ao mesmo dia selecionado.";

    public DayToStartEventException() {
        super("409", DEFAULT_MESSAGE, HttpStatus.CONFLICT);
    }
}