package ru.BKMeIsTeR.PoolPractic.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VisitorDto extends UserDto{
    //Полное ФИО посетителя
    private String fullName;
    //e-mail
    private String email;
    //телефон
    private String phone;
    //Пол (true-мужской, false-женский)
    private boolean sex;
}
