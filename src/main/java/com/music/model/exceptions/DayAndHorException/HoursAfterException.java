package com.music.model.exceptions.DayAndHorException;

import com.music.model.exceptions.exceptionHandler.AlertException;
import org.springframework.http.HttpStatus;

public class HoursAfterException extends AlertException {

    private static final String DEFAULT_MESSAGE = "O horário de início deve ser antes do horário de encerramento.";

    public HoursAfterException() {
        super("409", DEFAULT_MESSAGE, HttpStatus.CONFLICT);
    }
}