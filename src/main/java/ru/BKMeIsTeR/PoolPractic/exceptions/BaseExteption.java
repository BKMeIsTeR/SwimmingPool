package ru.BKMeIsTeR.PoolPractic.exceptions;

import lombok.Data;

@Data
public class BaseExteption extends RuntimeException{
    public BaseExteption(String message) {
        super(message);
    }
}
