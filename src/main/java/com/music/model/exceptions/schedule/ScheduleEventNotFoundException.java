package com.music.model.exceptions.schedule;

import com.music.model.exceptions.exceptionHandler.AlertException;
import org.springframework.http.HttpStatus;

public class ScheduleEventNotFoundException extends AlertException {
    private static final String DEFAULT_MESSAGE = "Evento não localizado";
    public ScheduleEventNotFoundException() {
        super("404", DEFAULT_MESSAGE, HttpStatus.NOT_FOUND);

    }
}
