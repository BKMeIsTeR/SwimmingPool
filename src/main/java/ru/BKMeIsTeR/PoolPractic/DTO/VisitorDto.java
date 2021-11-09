package ru.BKMeIsTeR.PoolPractic.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VisitorDto {
    //Полное ФИО посетителя
    private String fullName;
    //e-mail
    private String email;
    //телефон
    private String phone;
    //пол true-мужской, false-женский
    private boolean sex;


    //Имя пользователя
    private String userName;
    //Пароль
    private String password;
    //Подтверждение пароля
    private String passwordConfirm;
}
