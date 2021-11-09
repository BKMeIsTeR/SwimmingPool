package ru.BKMeIsTeR.PoolPractic.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExseptionHandler {
    @ExceptionHandler
    public ResponseEntity<PeopleIncorrectData> handleException(BaseExteption exception) {
        PeopleIncorrectData data = new PeopleIncorrectData();
        data.setInfo(exception.getMessage());

        return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<PeopleIncorrectData> handleException(Exception exception) {
        PeopleIncorrectData data = new PeopleIncorrectData();
        data.setInfo(exception.getMessage());
        
        return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
    }
}
