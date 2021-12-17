package ru.BKMeIsTeR.PoolPractic.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import ru.BKMeIsTeR.PoolPractic.DTO.InstructorDto;

import javax.persistence.*;
import java.util.Set;

@Data
//@Getter
//@Setter
@ToString(exclude = {"userEntity", "groupEntities"})
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "instructors")
public class InstructorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name")
    //    @Size(min=2, message = "Не меньше 5 знаков")
    private String fullName;

    @Column(name = "experience")
    private int experience; //опыт работы

    @Column(name = "education")
    private String education; //Образование

    @Column(name = "workRate")
    private int workRate; //Количество рабочих часов в неделе

    @Column(name = "preferred_time_start")
    private int preferredTimeStart; //Начало удобного времени тренировок

    @Column(name = "preferred_time_end")
    private int preferredTimeEnd; //Конец удобного времени тренировок

    @JsonBackReference
    @OneToMany(mappedBy = "instructorEntity", fetch = FetchType.LAZY)
    private Set<GroupEntity> groupEntities;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    private UserEntity userEntity;

    public InstructorEntity(InstructorDto instructorDto) {
        this.fullName = instructorDto.getFullName();
        this.experience = instructorDto.getExperience();
        this.education = instructorDto.getEducation();
        this.workRate = instructorDto.getWorkRate();
        this.preferredTimeStart = instructorDto.getPreferredTimeStart();
        this.preferredTimeEnd = instructorDto.getPreferredTimeEnd();

        this.userEntity = new UserEntity();

        this.userEntity.setUsername(instructorDto.getUserName());
        this.userEntity.setPassword(instructorDto.getPassword());
        this.userEntity.setUserRole(new RoleEntity(2L, "ROLE_INSTRUCTOR"));
    }
}
