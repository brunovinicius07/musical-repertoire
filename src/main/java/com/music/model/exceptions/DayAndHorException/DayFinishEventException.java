package com.music.model.exceptions.DayAndHorException;

import com.music.model.exceptions.exceptionHandler.AlertException;
import org.springframework.http.HttpStatus;

public class DayFinishEventException extends AlertException {

    private static final String DEFAULT_MESSAGE = "O evento deve terminar no mesmo dia ou no dia seguinte ao início.";

    public DayFinishEventException() {
        super("409", DEFAULT_MESSAGE, HttpStatus.CONFLICT);
    }
}