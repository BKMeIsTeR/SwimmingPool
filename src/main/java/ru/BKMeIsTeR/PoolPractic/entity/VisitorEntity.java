package ru.BKMeIsTeR.PoolPractic.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import ru.BKMeIsTeR.PoolPractic.DTO.VisitorDto;

import javax.persistence.*;
import java.util.List;

@Data
//@Getter
//@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"userEntity"})
@Entity
@Table(name = "visitors")
public class VisitorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  id;

    //    @Size(min=2, message = "Не меньше 5 знаков")
    @Column(name = "full_name")
    private String fullName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "sex")
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
}
