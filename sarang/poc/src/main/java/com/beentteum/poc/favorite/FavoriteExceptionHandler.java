package com.beentteum.poc.favorite;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class FavoriteExceptionHandler {

    @ExceptionHandler(FavoriteNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFound(FavoriteNotFoundException exception) {
        return new ErrorResponse(exception.getMessage());
    }

    public record ErrorResponse(String message) {
    }
}
