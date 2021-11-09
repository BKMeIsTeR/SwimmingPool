package ru.BKMeIsTeR.PoolPractic.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.BKMeIsTeR.PoolPractic.DTO.VisitorDto;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_visitor")
public class VisitorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  id;

    //    @Size(min=2, message = "Не меньше 5 знаков")
    private String fullName;

    private String email;

    private String phone;

    private boolean sex;

    @JsonBackReference
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "groups_visitors",
            joinColumns = @JoinColumn(name = "visitor_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    private List<GroupEntity> groupEntities;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    private UserEntity userEntity;



    public void addGroup(GroupEntity group) {
        groupEntities.add(group);
        //group.addVisitor(this);
    }


    public void removeGroup(GroupEntity group) {
        //group.removeVisitor(this);
        groupEntities.remove(group);
    }


    public VisitorEntity(VisitorDto visitorDto) {
        this.fullName = visitorDto.getFullName();
        this.email = visitorDto.getEmail();
        this.phone = visitorDto.getPhone();
        this.sex = visitorDto.isSex();

        this.userEntity = new UserEntity();
        this.userEntity.setUsername(visitorDto.getUserName());
        this.userEntity.setPassword(visitorDto.getPassword());

        this.userEntity.setUserRole(new RoleEntity(1L, "ROLE_USER"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VisitorEntity )) return false;
        return id != null && id.equals(((VisitorEntity) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
