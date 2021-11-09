package ru.BKMeIsTeR.PoolPractic.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.BKMeIsTeR.PoolPractic.DTO.InstructorDto;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_instructor")
public class InstructorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //    @Size(min=2, message = "Не меньше 5 знаков")
    private String fullName;

    //опыт работы
    private int experience;

    //Образование
    private String education;

    //Количество рабочих часов в неделе
    private int workRate;

    //Начало удобного времени тренировок
    private int preferredTimeStart;

    //Конец удобного времени тренировок
    private int preferredTimeEnd;

    @JsonBackReference
    @OneToMany(mappedBy = "instructorEntity", fetch = FetchType.LAZY)
    private Set<GroupEntity> groupEntities;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    private UserEntity userEntity;


    public boolean resetGroups() {
        if (groupEntities.isEmpty())
            return false;

        for(GroupEntity o : groupEntities)
            o.setInstructorEntity(null);

        groupEntities.clear();

        return true;
    }


    public void addGroup(GroupEntity group) {
        groupEntities.add(group);
        //group.setInstructorEntity(this);
    }

    public void removeGroup(GroupEntity group) {
        groupEntities.remove(group);

        group.setInstructorEntity(null);
    }



    @Override
    public String toString() {
        return "InstructorEntity{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", experience=" + experience +
                ", education='" + education + '\'' +
                ", workRate=" + workRate +
                ", preferredTimeStart=" + preferredTimeStart +
                ", preferredTimeEnd=" + preferredTimeEnd +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InstructorEntity )) return false;
        return id != null && id.equals(((InstructorEntity) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

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
