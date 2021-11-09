package ru.BKMeIsTeR.PoolPractic.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor
@ToString
public class InstructorDtoResponse {
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



    //Текущая занятость
    private Long currentEmployment;
    //пересекается ли с желаемым временем инструктора
    private boolean intersects;



    public InstructorDtoResponse(String fullName, int experience, String education, int workRate,
                                 int preferredTimeStart, int preferredTimeEnd,
                                 Long currentEmployment, Long intersects) {
        this.fullName = fullName;
        this.experience = experience;
        this.education = education;
        this.workRate = workRate;
        this.preferredTimeStart = preferredTimeStart;
        this.preferredTimeEnd = preferredTimeEnd;
        this.currentEmployment = currentEmployment;
        this.intersects = intersects > 0;
    }
}
