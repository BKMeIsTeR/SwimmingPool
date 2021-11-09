package ru.BKMeIsTeR.PoolPractic.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.BKMeIsTeR.PoolPractic.DTO.GroupDto;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_group")
public class GroupEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  id;

    private String name;

    private int numberMen;

    private int numberWomen;

    //Время в минутах!!!

    //Время начала тренировок
    private int preferredTimeStart;

    //Время конца тренировок
    private int preferredTimeEnd;

    @JsonManagedReference
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "groups_visitors",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "visitor_id")
    )
    private List<VisitorEntity> visitorEntityList;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "instructor_id")
    private InstructorEntity instructorEntity;


    public void deleteVisitorAndInstructor() {
        visitorEntityList.clear();
        instructorEntity = null;
    }


    public void addVisitor(VisitorEntity visitor) {
        visitorEntityList.add(visitor);
    }

    public void removeVisitor(VisitorEntity visitor) {
        visitorEntityList.remove(visitor);
    }



    public GroupEntity(GroupDto groupDto) {
        this.name = groupDto.getName();
        this.numberMen = groupDto.getNumberMen();
        this.numberWomen = groupDto.getNumberWomen();
        this.preferredTimeStart = groupDto.getPreferredTimeStart();
        this.preferredTimeEnd = groupDto.getPreferredTimeEnd();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GroupEntity )) return false;
        return id != null && id.equals(((GroupEntity) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "GroupEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", numberMen=" + numberMen +
                ", numberWomen=" + numberWomen +
                ", preferredTimeStart=" + preferredTimeStart +
                ", preferredTimeEnd=" + preferredTimeEnd +
                '}';
    }
}
