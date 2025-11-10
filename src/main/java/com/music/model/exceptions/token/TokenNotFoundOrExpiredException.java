package com.music.model.exceptions.token;

import com.music.model.exceptions.exceptionHandler.AlertException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class TokenNotFoundOrExpiredException extends AlertException {
    private static final String DEFAULT_MESSAGE = "Token inválido ou expirado";
    public TokenNotFoundOrExpiredException() {
        super("404", DEFAULT_MESSAGE, HttpStatus.NOT_FOUND);
    }
}
