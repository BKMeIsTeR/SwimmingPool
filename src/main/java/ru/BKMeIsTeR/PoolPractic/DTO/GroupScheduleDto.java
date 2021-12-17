package ru.BKMeIsTeR.PoolPractic.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupScheduleDto {
    //Дата тренировки
    private LocalDate date;
    //Время начала  тренировок
    private int preferredTimeStart;
    //Время конца тренировок
    private int preferredTimeEnd;
}
