package ru.BKMeIsTeR.PoolPractic.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
public class GroupDto {
    //Название группы
    private String name;
    //Количество мужских мест в раздевалке
    private int numberMen;
    //Количество женских мест в раздевалке
    private int numberWomen;
}


