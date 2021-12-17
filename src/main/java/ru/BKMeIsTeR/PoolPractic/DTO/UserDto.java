package ru.BKMeIsTeR.PoolPractic.DTO;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public abstract class UserDto {
    //Имя пользователя
    private String userName;
    //Пароль
    private String password;

    private String passwordConfirm;
}
