package ru.BKMeIsTeR.PoolPractic.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.BKMeIsTeR.PoolPractic.DTO.GroupScheduleDto;

import javax.persistence.*;
import java.time.LocalDate;

@Data
//@Getter
//@Setter
//@ToString(exclude = {"visitorEntityList", "instructorEntity"})
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "group_schedule")
public class GroupScheduleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long  id;

    @Column(name = "date")
    private LocalDate date;

    //Время в минутах!!!
    //Время начала тренировки
    @Column(name = "preferred_time_start")
    private int preferredTimeStart;

    //Время конца тренировки
    @Column(name = "preferred_time_end")
    private int preferredTimeEnd;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private GroupEntity groupEntity;

    public GroupScheduleEntity(GroupScheduleDto groupScheduleDto) {
        this.date = groupScheduleDto.getDate();
        this.preferredTimeStart = groupScheduleDto.getPreferredTimeStart();
        this.preferredTimeEnd = groupScheduleDto.getPreferredTimeEnd();
    }
}
