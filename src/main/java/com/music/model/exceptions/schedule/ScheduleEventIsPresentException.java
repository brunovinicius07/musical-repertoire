package com.music.model.exceptions.schedule;

import com.music.model.exceptions.exceptionHandler.AlertException;
import org.springframework.http.HttpStatus;

public class ScheduleEventIsPresentException extends AlertException {

    private static final String DEFAULT_MESSAGE = "Já existe um evento para esse dia e horário";

    public ScheduleEventIsPresentException() {
        super("409", DEFAULT_MESSAGE, HttpStatus.CONFLICT);
    }
}