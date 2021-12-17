package ru.BKMeIsTeR.PoolPractic.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import ru.BKMeIsTeR.PoolPractic.DTO.GroupDto;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@ToString(exclude = {"visitorEntityList", "instructorEntity"})
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "groups")
public class GroupEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long  id;
    @Column(name = "name")
    private String name;
    @Column(name = "number_men")
    private int numberMen;
    @Column(name = "number_women")
    private int numberWomen;

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

    @JsonBackReference
    @OneToMany(mappedBy = "groupEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<GroupScheduleEntity> groupScheduleEntities;

    public GroupEntity(GroupDto groupDto) {
        this.name = groupDto.getName();
        this.numberMen = groupDto.getNumberMen();
        this.numberWomen = groupDto.getNumberWomen();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GroupEntity )) return false;
        return id != null && id.equals(((GroupEntity) o).getId());
    }
}
