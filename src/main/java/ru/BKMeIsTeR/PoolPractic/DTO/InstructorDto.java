package ru.BKMeIsTeR.PoolPractic.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

import java.time.LocalTime;

@NoArgsConstructor
@Data
public class InstructorDto {
    //Полное имя
    private String fullName;
    //Опыт работы
    private int experience;
    //Образование
    private String education;
    //Количество рабочих часов
    private int workRate;
    //Начало удобного времени тренировок
    private int preferredTimeStart;
    //Конец удобного времени тренировок
    private int preferredTimeEnd;



    //Имя пользователя
    private String userName;
    //Пароль
    private String password;
    //Подтверждение пароля
    private String passwordConfirm;
}
